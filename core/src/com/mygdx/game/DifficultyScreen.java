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
import util.Enums.Difficulty;
import util.Util;

public class DifficultyScreen extends InputAdapter implements Screen {
    private static final String TAG = DifficultyScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    public DifficultyScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth(), viewport.getWorldHeight(), 0);
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
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.difficultyBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        //mystery question button
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mysteryQuestionButton, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.20f), Constants.MYSTERYQUESTION_BUTTON_CENTER);


        //theoretical question label
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.theoreticalQuestionsLabel, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.30f), Constants.DIFFICULTY_SCREEN_LABEL_CENTER);

        //buttons
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);

        //programming question label
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.programmingQuestionsLabel, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.30f), Constants.DIFFICULTY_SCREEN_LABEL_CENTER);

        //buttons
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //THEORETICAL QUESTIONS BUTTONS
        //veryEasyButton attributes
        Vector2 theoreticalVeryEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f);
        Rectangle theoreticalVeryEasyButtonBoundingBox = new Rectangle(theoreticalVeryEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalVeryEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //easyButton attributes
        Vector2 theoreticalEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60);
        Rectangle theoreticalEasyButtonBoundingBox = new Rectangle(theoreticalEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 theoreticalMediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120);
        Rectangle theoreticalMediumButtonBoundingBox = new Rectangle(theoreticalMediumButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalMediumButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 theoreticalHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180);
        Rectangle theoreticalHardButtonBoundingBox = new Rectangle(theoreticalHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //veryHardButton attributes
        Vector2 theoreticalVeryHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240);
        Rectangle theoreticalVeryHardButtonBoundingBox = new Rectangle(theoreticalVeryHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalVeryHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);
        //END OF THEORETICAL QUESTIONS BUTTONS

        //PROGRAMMING QUESTIONS BUTTONS
        //veryEasyButton attributes
        Vector2 programmingVeryEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f);
        Rectangle programmingVeryEasyButtonBoundingBox = new Rectangle(programmingVeryEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingVeryEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //easyButton attributes
        Vector2 programmingEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60);
        Rectangle programmingEasyButtonBoundingBox = new Rectangle(programmingEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 programmingMediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120);
        Rectangle programmingMediumButtonBoundingBox = new Rectangle(programmingMediumButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingMediumButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 programmingHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180);
        Rectangle programmingHardButtonBoundingBox = new Rectangle(programmingHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //veryHardButton attributes
        Vector2 programmingVeryHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240);
        Rectangle programmingVeryHardButtonBoundingBox = new Rectangle(programmingVeryHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingVeryHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);
        //END OF PROGRAMMING QUESTIONS BUTTONS


        if(theoreticalVeryEasyButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_VERY_EASY);
            Gdx.app.log(TAG, "CLICKED THEORETICAL VERY EASY");
        }
        if(theoreticalEasyButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_EASY);
            Gdx.app.log(TAG, "CLICKED THEORETICAL EASY");
        }
        if(theoreticalMediumButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_MEDIUM);
            Gdx.app.log(TAG, "CLICKED THEORETICAL MEDIUM");
        }
        if(theoreticalHardButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_HARD);
            Gdx.app.log(TAG, "CLICKED THEORETICAL HARD");
        }
        if(theoreticalVeryHardButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_VERY_HARD);
            Gdx.app.log(TAG, "CLICKED THEORETICAL VERY HARD");
        }

        if(programmingVeryEasyButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_VERY_EASY);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING VERY EASY");
        }
        if(programmingEasyButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_EASY);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING EASY");
        }
        if(programmingMediumButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_MEDIUM);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING MEDIUM");
        }
        if(programmingHardButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_HARD);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING HARD");
        }
        if(programmingVeryHardButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_VERY_HARD);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING VERY HARD");
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
