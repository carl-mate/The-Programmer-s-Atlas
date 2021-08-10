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

import util.Assets;
import util.Constants;
import util.Util;

public class GameOverScreen extends InputAdapter implements Screen {
    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    public GameOverScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
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
        Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.gameOverBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.GAMEOVER_BG_CENTER);

        //draw user earnings label
        Vector2 usernameEarningsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 55);
        Rectangle usernameEarningsRectangleBounds = new Rectangle(usernameEarningsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, usernameEarningsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "usernameEarnings", Constants.MENU_SCREEN_NAME + "'s Earnings:", usernameEarningsRectangleBounds);

        //draw user earnings
        Vector2 earningsCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 95);
        Rectangle earningsRectangleBounds = new Rectangle(earningsCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, earningsCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);

        //get the index of the latest user
        int latestUserIndex = Constants.preferences.getInteger("userCounter");
        //get the score of the latest user
        int latestUserScore = Constants.preferences.getInteger("score-"+latestUserIndex);

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "earnings", "$" + latestUserScore, earningsRectangleBounds);

        Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.returnToMenuButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 4.8f), Constants.H_RTM_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.gameOverScreenAssets.highScoresButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 8), Constants.H_RTM_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160), Constants.CONTINUE_BUTTON_CENTER);

        batch.end();
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
