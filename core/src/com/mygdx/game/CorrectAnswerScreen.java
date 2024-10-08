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

import util.Assets;
import util.Constants;
import util.Util;
/**
 *  This screen is shown whenever the user selects the correct answer
 */
public class CorrectAnswerScreen extends InputAdapter implements Screen {
    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private ArrayList<String> praise;
    private Vector2 continueButtonCenter;
    private Rectangle continueButtonBoundingBox;

    private int earnedPoints;
    private int previousScore;
    private int currentScore;
    private int increment;

    private boolean isContinueButtonHovered;
    private long continueButtonHoverTime;

    private float time;

    public CorrectAnswerScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        this.previousScore = programmerGame.getPreviousScore();
        this.currentScore = programmerGame.getCurrentScore();
        /* increment depends on the earned points.
         * If earnedPoints <= 1000, then increment by 10
         *    earnedPoints <= 10000, then increment by 100
         *    earnedPoints <= 100000, then increment by 1000
         *    earnedPoints <= 1000000+, then increment by 10000
         */
        earnedPoints = currentScore - previousScore;
        if(earnedPoints <= 1000){
            increment = 10;
        } else if(earnedPoints <= 10000){
            increment = 100;
        } else if(earnedPoints <= 100000){
            increment = 1000;
        } else if(earnedPoints <= 1000000){
            increment = 10000;
        } else{
            increment = 100000;
        }
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        //continue button
        continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160);
        continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_HEIGHT / 2, Constants.CONTINUE_BUTTON_WIDTH, Constants.CONTINUE_BUTTON_HEIGHT);

    }

    @Override
    public void show() {
        praise = new ArrayList<>();
        praise.add("Awesome!");
        praise.add("Amazing!");
        praise.add("Brilliant!");
        praise.add("Incredible!");
        praise.add("Wonderful!");
        praise.add("Superb!");
        praise.add("Excellent!");
        praise.add("Exceptional!");
        praise.add("Splendid!");
        //shuffle to ensure randomness
        Collections.shuffle(praise);
        Assets.instance.soundClass.correctAnswerSound.play();
        Assets.instance.soundClass.coinsSound.play();
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
        //bg
        Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.correctAnswerBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.GAMEOVER_BG_CENTER);

        //draw praise
        Vector2 praiseCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f + 20);
        Rectangle praiseRectangleBounds = new Rectangle(praiseCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, praiseCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "praise", praise.get(0), praiseRectangleBounds);

        //draw earned points
        Vector2 earnedPointsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 25);
        Rectangle earnedPointsRectangleBounds = new Rectangle(earnedPointsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, earnedPointsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "usernameEarnings", "You earned:" + "\n" + "$" + earnedPoints, earnedPointsRectangleBounds);

        //draw user earnings label
        Vector2 usernameEarningsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 70);
        Rectangle usernameEarningsRectangleBounds = new Rectangle(usernameEarningsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, usernameEarningsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "usernameEarnings", Constants.MENU_SCREEN_NAME + "'s Earnings:", usernameEarningsRectangleBounds);

        //draw user earnings
        Vector2 earningsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 100);
        Rectangle earningsRectangleBounds = new Rectangle(earningsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, earningsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "earnings", "$" + previousScore, earningsRectangleBounds);
        time += delta;
        if(time >= Constants.SCORE_DELAY){
            if(previousScore < currentScore){
                previousScore += increment;
            }
            time -= Constants.SCORE_DELAY;

        }
//        else{
//            Assets.instance.font.drawSourceCodeProBoldFont(batch, "earnings", "$" + previousScore, earningsRectangleBounds);
//        }


        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        isContinueButtonHovered = continueButtonBoundingBox.contains(mousePosition);

        if(!isContinueButtonHovered){
            //continue button
            Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160), Constants.CONTINUE_BUTTON_CENTER);
            if(continueButtonHoverTime > 0){
                continueButtonHoverTime = 0;
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160), Constants.CONTINUE_BUTTON_BIG_CENTER);
            if(continueButtonHoverTime == 0){
                continueButtonHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverSound.play();
            }
        }
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //continue button
        Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160);
        Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_HEIGHT / 2, Constants.CONTINUE_BUTTON_WIDTH, Constants.CONTINUE_BUTTON_HEIGHT);

        if(continueButtonBoundingBox.contains(worldTouch)){
            //update the previous score
            Assets.instance.soundClass.buttonClickSound.play();
            programmerGame.setPreviousScore(this.currentScore);
            programmerGame.showJigsawScreen();
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
