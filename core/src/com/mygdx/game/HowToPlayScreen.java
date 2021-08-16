package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Constants;
import util.Util;
/**
 *  This screen displays the instructions on how to play the game
 */
public class HowToPlayScreen extends InputAdapter implements Screen {
    private static final String TAG = HowToPlayScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private int pageCounter;

    public HowToPlayScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth(), viewport.getWorldHeight(), 0);

    }

    @Override
    public void show() {
        pageCounter = 0;
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

        if(pageCounter == 0){
            //instructions bg
            Util.drawTextureRegion(batch, Assets.instance.howToPlayScreenAssets.instructionsBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(pageCounter == 1){
            //lifelines bg
            Util.drawTextureRegion(batch, Assets.instance.howToPlayScreenAssets.lifelineBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        }

        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(pageCounter-1 >= 0){
                pageCounter--;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(pageCounter+1 < 2){
                pageCounter++;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            programmerGame.showMainMenuScreen();
        }
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
