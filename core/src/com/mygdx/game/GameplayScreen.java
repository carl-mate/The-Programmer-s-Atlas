package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


import util.Assets;
import util.Constants;
import util.Enums.Difficulty;
import util.Util;

public class GameplayScreen extends InputAdapter implements Screen {

    private ProgrammerGame programmerGame;
    private Questions questions;
    private Difficulty difficulty;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    public GameplayScreen(ProgrammerGame programmerGame, Difficulty difficulty, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.difficulty = difficulty;
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
        if(difficulty == Difficulty.THEORETICAL_VERY_EASY){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.THEORETICAL_EASY){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.THEORETICAL_MEDIUM){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionMedium, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.THEORETICAL_HARD){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.THEORETICAL_VERY_HARD){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.PROGRAMMING_VERY_EASY){
//            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.PROGRAMMING_EASY){

        } else if(difficulty == Difficulty.PROGRAMMING_MEDIUM){

        } else if(difficulty == Difficulty.PROGRAMMING_HARD){

        } else if(difficulty == Difficulty.PROGRAMMING_VERY_HARD){

        }
        renderAnswerBubbles();
        batch.end();
    }

    private void renderAnswerBubbles(){
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f), Constants.ANSWERBUBBLE_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f - 100), Constants.ANSWERBUBBLE_BUTTON_CENTER);

        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f), Constants.ANSWERBUBBLE_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f - 100), Constants.ANSWERBUBBLE_BUTTON_CENTER);

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
