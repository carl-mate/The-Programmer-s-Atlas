package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.Collections;

import util.Assets;
import util.Constants;
import util.Util;

public class VictoryScreen extends InputAdapter implements Screen {
    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private Vector2 continueButtonCenter;
    private Rectangle continueButtonBoundingBox;

    private boolean isReturnToMenuButtonHovered;
    private boolean isHighScoresButtonHovered;

    private int previousScore;
    private int currentScore;
    private int increment;

    private float time;

    public VictoryScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        this.previousScore = programmerGame.getPreviousScore();
        this.currentScore = programmerGame.getCurrentScore();
        /* increment depends on the current score.
         * If currentScore <= 1000, then increment by 10
         *    currentScore <= 10000, then increment by 100
         *    currentScore <= 100000, then increment by 1000
         *    currentScore <= 1000000+, then increment by 10000
         */
        if(currentScore <= 9999){
            increment = 10;
        } else if(currentScore <= 99999){
            increment = 100;
        } else if(currentScore <= 999999){
            increment = 1000;
        } else{
            increment = 10000;
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
        Util.drawTextureRegion(batch, Assets.instance.victoryScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.victoryScreenAssets.victoryBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.VICTORY_BG_CENTER);

        //draw praise
        Vector2 praiseCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2.15f);
        Rectangle praiseRectangleBounds = new Rectangle(praiseCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, praiseCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "praise", "Victory!", praiseRectangleBounds);

        //draw user earnings label
        Vector2 usernameEarningsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 55);
        Rectangle usernameEarningsRectangleBounds = new Rectangle(usernameEarningsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, usernameEarningsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "usernameEarnings", Constants.MENU_SCREEN_NAME + "'s Earnings:", usernameEarningsRectangleBounds);

        //draw user earnings
        Vector2 earningsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 95);
        Rectangle earningsRectangleBounds = new Rectangle(earningsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, earningsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "earnings", "$" + previousScore, earningsRectangleBounds);

        time += delta;
        if(time >= Constants.SCORE_DELAY){
            if(previousScore < currentScore){
                previousScore += increment;
            }
            time -= Constants.SCORE_DELAY;
        }

        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        //return to menu button
        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4.8f);
        Rectangle returnToMenuButtonBoundingBox = new Rectangle(returnToMenuButtonCenter.x - Constants.H_RTM_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.H_RTM_BUTTON_HEIGHT / 2, Constants.H_RTM_BUTTON_WIDTH, Constants.H_RTM_BUTTON_HEIGHT);
        //highscores button
        Vector2 highScoresButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 8);
        Rectangle highScoresButtonBoundingBox = new Rectangle(highScoresButtonCenter.x - Constants.H_RTM_BUTTON_WIDTH / 2, highScoresButtonCenter.y - Constants.H_RTM_BUTTON_HEIGHT / 2, Constants.H_RTM_BUTTON_WIDTH, Constants.H_RTM_BUTTON_HEIGHT);

        isHighScoresButtonHovered = highScoresButtonBoundingBox.contains(mousePosition);
        isReturnToMenuButtonHovered = returnToMenuButtonBoundingBox.contains(mousePosition);


        if(!isReturnToMenuButtonHovered){
            Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.returnToMenuButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4.8f), Constants.H_RTM_BUTTON_CENTER);
        } else{
            Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.returnToMenuButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4.8f), Constants.H_RTM_BUTTON_BIG_CENTER);
        }

        if(!isHighScoresButtonHovered){
            Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.highScoresButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 8), Constants.H_RTM_BUTTON_CENTER);
        } else{
            Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.highScoresButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 8), Constants.H_RTM_BUTTON_BIG_CENTER);
        }

        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //return to menu button
        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4.8f);
        Rectangle returnToMenuButtonBoundingBox = new Rectangle(returnToMenuButtonCenter.x - Constants.H_RTM_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.H_RTM_BUTTON_HEIGHT / 2, Constants.H_RTM_BUTTON_WIDTH, Constants.H_RTM_BUTTON_HEIGHT);

        //highscores button
        Vector2 highScoresButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 8);
        Rectangle highScoresButtonBoundingBox = new Rectangle(highScoresButtonCenter.x - Constants.H_RTM_BUTTON_WIDTH / 2, highScoresButtonCenter.y - Constants.H_RTM_BUTTON_HEIGHT / 2, Constants.H_RTM_BUTTON_WIDTH, Constants.H_RTM_BUTTON_HEIGHT);

        if(returnToMenuButtonBoundingBox.contains(worldTouch)){
            programmerGame.showMainMenuScreen();
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
