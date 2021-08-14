package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.TotalUsers;
import util.Assets;
import util.Constants;
import util.Util;

public class HighScoresScreen extends InputAdapter implements Screen {
    private static final String TAG = HighScoresScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private ArrayList<TotalUsers> totalUsers;
    private boolean isReturnToMenuButtonHovered;
    private boolean isClearDataButtonHovered;

    private final Vector2 highScoreFontOneCenter;
    private final Rectangle highScoreFontOneRectangleBounds;

    private final Vector2 highScoreFontTwoCenter;
    private final Rectangle highScoreFontTwoRectangleBounds;

    private final Vector2 highScoreFontThreeCenter;
    private final Rectangle highScoreFontThreeRectangleBounds;

    private final Vector2 highScoreFontFourCenter;
    private final Rectangle highScoreFontFourRectangleBounds;

    private final Vector2 highScoreFontFiveCenter;
    private final Rectangle highScoreFontFiveRectangleBounds;

    private long clearDataHoverTime;
    private long returnToMenuHoverTime;

    public HighScoresScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        highScoreFontOneCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + 122);
        highScoreFontOneRectangleBounds = new Rectangle(highScoreFontOneCenter.x - Constants.HIGHSCORES_BUBBLE_WIDTH / 2, highScoreFontOneCenter.y - Constants.HIGHSCORES_BUBBLE_HEIGHT / 2, Constants.HIGHSCORES_BUBBLE_WIDTH, Constants.HIGHSCORES_BUBBLE_HEIGHT);

        highScoreFontTwoCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 + 60);
        highScoreFontTwoRectangleBounds = new Rectangle(highScoreFontTwoCenter .x - Constants.HIGHSCORES_BUBBLE_WIDTH / 2, highScoreFontTwoCenter .y - Constants.HIGHSCORES_BUBBLE_HEIGHT / 2, Constants.HIGHSCORES_BUBBLE_WIDTH, Constants.HIGHSCORES_BUBBLE_HEIGHT);

        highScoreFontThreeCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 5);
        highScoreFontThreeRectangleBounds = new Rectangle(highScoreFontThreeCenter.x - Constants.HIGHSCORES_BUBBLE_WIDTH / 2, highScoreFontThreeCenter.y - Constants.HIGHSCORES_BUBBLE_HEIGHT / 2, Constants.HIGHSCORES_BUBBLE_WIDTH, Constants.HIGHSCORES_BUBBLE_HEIGHT);

        highScoreFontFourCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 65);
        highScoreFontFourRectangleBounds = new Rectangle(highScoreFontFourCenter.x - Constants.HIGHSCORES_BUBBLE_WIDTH / 2, highScoreFontFourCenter.y - Constants.HIGHSCORES_BUBBLE_HEIGHT / 2, Constants.HIGHSCORES_BUBBLE_WIDTH, Constants.HIGHSCORES_BUBBLE_HEIGHT);

        highScoreFontFiveCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 127);
        highScoreFontFiveRectangleBounds = new Rectangle(highScoreFontFiveCenter.x - Constants.HIGHSCORES_BUBBLE_WIDTH / 2, highScoreFontFiveCenter.y - Constants.HIGHSCORES_BUBBLE_HEIGHT / 2, Constants.HIGHSCORES_BUBBLE_WIDTH, Constants.HIGHSCORES_BUBBLE_HEIGHT);

    }

    @Override
    public void show() {
        totalUsers = new ArrayList<>();
        Gdx.app.log(TAG, "userCounter: " + Constants.preferences.getInteger("userCounter"));

        //store the totalUsers from preferences to local ArrayList
        for(int i = 0; i <= Constants.preferences.getInteger("userCounter"); i++){
            totalUsers.add(new TotalUsers(Constants.preferences.getString("user-"+i), Constants.preferences.getInteger("score-"+i)));
        }

        //rank total users from highest to lowest
        Collections.sort(totalUsers, new Comparator<TotalUsers>() {
            @Override public int compare(TotalUsers u1, TotalUsers u2) {
                return u2.getScore() - u1.getScore();
            }

        });

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.highScoresBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        try{
            if(totalUsers.get(0) != null){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "highScoreFont", totalUsers.get(0).getName() + " --- " + "$" + totalUsers.get(0).getScore(), highScoreFontOneRectangleBounds);
            }
            if(totalUsers.get(1) != null){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "highScoreFont", totalUsers.get(1).getName() + " --- " + "$" + totalUsers.get(1).getScore(), highScoreFontTwoRectangleBounds);
            }
            if(totalUsers.get(2) != null){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "highScoreFont", totalUsers.get(2).getName() + " --- " + "$" + totalUsers.get(2).getScore(), highScoreFontThreeRectangleBounds);
            }
            if(totalUsers.get(3) != null){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "highScoreFont", totalUsers.get(3).getName() + " --- " + "$" +  totalUsers.get(3).getScore(), highScoreFontFourRectangleBounds);
            }
            if(totalUsers.get(4) != null){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "highScoreFont", totalUsers.get(4).getName() + " --- " + "$" +  totalUsers.get(4).getScore(), highScoreFontFiveRectangleBounds);
            }
        } catch(Exception e){

        }


        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2 + 120, viewport.getCamera().viewportHeight / 2 - 220);
        Rectangle returnToMenuButtonRectangleBounds = new Rectangle(returnToMenuButtonCenter.x - Constants.CD_RTM_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.CD_RTM_BUTTON_HEIGHT / 2, Constants.CD_RTM_BUTTON_WIDTH, Constants.CD_RTM_BUTTON_HEIGHT);

        Vector2 clearDataButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2 - 120, viewport.getCamera().viewportHeight / 2 - 220);
        Rectangle clearDataButtonRectangleBounds = new Rectangle(clearDataButtonCenter.x - Constants.CD_RTM_BUTTON_WIDTH / 2, clearDataButtonCenter.y - Constants.CD_RTM_BUTTON_HEIGHT / 2, Constants.CD_RTM_BUTTON_WIDTH, Constants.CD_RTM_BUTTON_HEIGHT);

        isReturnToMenuButtonHovered = returnToMenuButtonRectangleBounds.contains(mousePosition);
        isClearDataButtonHovered = clearDataButtonRectangleBounds.contains(mousePosition);

        if(!isReturnToMenuButtonHovered){
            Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.returnToMenuButtonWhite, new Vector2(viewport.getCamera().viewportWidth / 2 + 120, viewport.getCamera().viewportHeight / 2 - 220), Constants.CD_RTM_BUTTON_CENTER);
            if(returnToMenuHoverTime > 0){
                returnToMenuHoverTime = 0;
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.returnToMenuButtonWhiteBig, new Vector2(viewport.getCamera().viewportWidth / 2 + 120, viewport.getCamera().viewportHeight / 2 - 220), Constants.CD_RTM_BUTTON_BIG_CENTER);
            if(returnToMenuHoverTime == 0){
                returnToMenuHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverSound.play();
            }
        }

        if(!isClearDataButtonHovered){
            Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.clearDataButton, new Vector2(viewport.getCamera().viewportWidth / 2 - 120, viewport.getCamera().viewportHeight / 2 - 220), Constants.CD_RTM_BUTTON_CENTER);
            if(clearDataHoverTime > 0){
                clearDataHoverTime = 0;
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.clearDataButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2 - 120, viewport.getCamera().viewportHeight / 2 - 220), Constants.CD_RTM_BUTTON_BIG_CENTER);
            if(clearDataHoverTime == 0){
                clearDataHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverSound.play();
            }
        }
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2 + 120, viewport.getCamera().viewportHeight / 2 - 220);
        Rectangle returnToMenuButtonRectangleBounds = new Rectangle(returnToMenuButtonCenter.x - Constants.CD_RTM_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.CD_RTM_BUTTON_HEIGHT / 2, Constants.CD_RTM_BUTTON_WIDTH, Constants.CD_RTM_BUTTON_HEIGHT);

        Vector2 clearDataButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2 - 120, viewport.getCamera().viewportHeight / 2 - 220);
        Rectangle clearDataButtonRectangleBounds = new Rectangle(clearDataButtonCenter.x - Constants.CD_RTM_BUTTON_WIDTH / 2, clearDataButtonCenter.y - Constants.CD_RTM_BUTTON_HEIGHT / 2, Constants.CD_RTM_BUTTON_WIDTH, Constants.CD_RTM_BUTTON_HEIGHT);

        if(returnToMenuButtonRectangleBounds.contains(worldTouch)){
            Assets.instance.soundClass.buttonClickSound.play();
            Constants.preferences.putString("user", Constants.MENU_SCREEN_NAME);
            Constants.preferences.flush();
            programmerGame.showMainMenuScreen();
        }

        if(clearDataButtonRectangleBounds.contains(worldTouch)){
            Assets.instance.soundClass.buttonClickSound.play();
            this.totalUsers.clear();
            Constants.preferences.clear();
            Constants.preferences.flush();
        }


        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
