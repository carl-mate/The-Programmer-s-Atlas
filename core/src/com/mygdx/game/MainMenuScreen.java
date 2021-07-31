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

import java.awt.image.VolatileImage;

import util.Assets;
import util.Constants;
import util.Util;

public class MainMenuScreen extends InputAdapter implements Screen {
    private static final String TAG = MainMenuScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    public MainMenuScreen(ProgrammerGame programmerGame, SpriteBatch batch){
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
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.mainMenuBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        //playButton
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.playButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.optionsButton, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.howToPlayButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_CENTER);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //playButton attributes
        Vector2 playButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 10);
        Rectangle playButtonBoundingBox = new Rectangle(playButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, playButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);

        //optionsButton attributes
        Vector2 optionsButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 10);
        Rectangle optionsButtonBoundingBox = new Rectangle(optionsButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, optionsButtonCenter.y - Constants.MAIN_MENU_BUTTON_WIDTH / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_WIDTH);

        //helpButton attributes
        Vector2 howToPlayButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 10);
        Rectangle helpButtonBoundingBox = new Rectangle(howToPlayButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, howToPlayButtonCenter.y - Constants.MAIN_MENU_BUTTON_WIDTH / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_WIDTH);


        if(playButtonBoundingBox.contains(worldTouch)){
            Gdx.app.log(TAG, "CLICKED PLAY");
            programmerGame.showDifficultyScreen();
        }

//        if(optionsButtonBoundingBox.contains(worldTouch)){
//            hackerGame.showOptionsScreen();
//        }
//
//        if(helpButtonBoundingBox.contains(worldTouch)){
//            hackerGame.showHelpScreen();
//        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
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
