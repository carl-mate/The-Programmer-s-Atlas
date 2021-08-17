package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Constants;
import util.Util;
/**
 *  This screen is shown whenever the user chooses to guess the important figure
 */
public class JigsawGuessResultScreen extends InputAdapter implements Screen {
    private static final String TAG = JigsawGuessResultScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private boolean isGuessCorrect;
    private Texture importantFigureBiography;

    private boolean isContinueButtonHovered;
    private int isContinueButtonPressed;
    private long continueButtonHoverTime;

    private int userCounter;

    public JigsawGuessResultScreen(ProgrammerGame programmerGame, SpriteBatch batch, boolean isGuessCorrect, Texture importantFigureBiography){
        this.programmerGame = programmerGame;
        this.batch = batch;
        this.isGuessCorrect = isGuessCorrect;
        this.importantFigureBiography = importantFigureBiography;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
    }

    @Override
    public void show() {

        if(isGuessCorrect){
            Assets.instance.soundClass.clappingSound.play();
            programmerGame.setPreviousScore(programmerGame.getPreviousScore());
            programmerGame.setCurrentScore(programmerGame.getPreviousScore() + Constants.IMPORTANT_FIGURE_CORRECT_POINTS);
        } else{
            Assets.instance.soundClass.awwwSound.play();
            programmerGame.setPreviousScore(programmerGame.getPreviousScore());
            programmerGame.setCurrentScore(programmerGame.getPreviousScore() + Constants.IMPORTANT_FIGURE_INCORRECT_POINTS);
        }
        //store the scores
        while(true){
            if(!Constants.preferences.contains("user-"+userCounter)){
                Constants.preferences.putString("user-"+userCounter, Constants.MENU_SCREEN_NAME);
                Constants.preferences.putInteger("score-"+userCounter, programmerGame.getCurrentScore());
                break;
            } else{
                userCounter++;
            }
        }
        Constants.preferences.putInteger("userCounter", userCounter);
        Constants.preferences.flush();
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
        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        //continue button
        Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
        Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WHITE_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_WHITE_HEIGHT / 2, Constants.CONTINUE_BUTTON_WHITE_WIDTH, Constants.CONTINUE_BUTTON_WHITE_HEIGHT);
        isContinueButtonHovered = continueButtonBoundingBox.contains(mousePosition);

        if(isGuessCorrect){
            if(isContinueButtonPressed == 0){
                Util.drawTextureRegion(batch, Assets.instance.jigsawGuessResultScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
                Util.drawTextureRegion(batch, importantFigureBiography, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.IMPORTANT_FIGURE_BIOGRAPHY_CENTER);
                if(!isContinueButtonHovered){
                    Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_WHITE_CENTER);
                    if(continueButtonHoverTime > 0){
                        continueButtonHoverTime = 0;
                    }
                } else{
                    Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_WHITE_BIG_CENTER);
                    if(continueButtonHoverTime == 0){
                        continueButtonHoverTime = TimeUtils.nanoTime();
                        Assets.instance.soundClass.buttonHoverSound.play();
                    }
                }
            } else if(isContinueButtonPressed == 1){
                Util.drawTextureRegion(batch, Assets.instance.jigsawGuessResultScreenAssets.correctGuessBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
                if(!isContinueButtonHovered){
                    Util.drawTextureRegion(batch, Assets.instance.jigsawScreenAssets.continueButtonWhite, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_WHITE_CENTER);
                    if(continueButtonHoverTime > 0){
                        continueButtonHoverTime = 0;
                    }
                } else{
                    Util.drawTextureRegion(batch, Assets.instance.jigsawScreenAssets.continueButtonWhiteBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_WHITE_BIG_CENTER);
                    if(continueButtonHoverTime == 0){
                        continueButtonHoverTime = TimeUtils.nanoTime();
                        Assets.instance.soundClass.buttonHoverSound.play();
                    }
                }
            }

        } else{
            if(isContinueButtonPressed == 0){
                Util.drawTextureRegion(batch, Assets.instance.jigsawGuessResultScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
                Util.drawTextureRegion(batch, Assets.instance.jigsawGuessResultScreenAssets.incorrectGuessBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
                //TODO
                if(!isContinueButtonHovered){
                    Util.drawTextureRegion(batch, Assets.instance.jigsawScreenAssets.continueButtonWhite, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_WHITE_CENTER);
                    if(continueButtonHoverTime > 0){
                        continueButtonHoverTime = 0;
                    }
                } else{
                    Util.drawTextureRegion(batch, Assets.instance.jigsawScreenAssets.continueButtonWhiteBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_WHITE_BIG_CENTER);
                    if(continueButtonHoverTime == 0){
                        continueButtonHoverTime = TimeUtils.nanoTime();
                        Assets.instance.soundClass.buttonHoverSound.play();
                    }
                }
            }
        }
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //continue button
        Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
        Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WHITE_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_WHITE_HEIGHT / 2, Constants.CONTINUE_BUTTON_WHITE_WIDTH, Constants.CONTINUE_BUTTON_WHITE_HEIGHT);

        if(continueButtonBoundingBox.contains(worldTouch)){
            Gdx.app.log(TAG, "TOUCHED CONTINUE BUTTON");
            Assets.instance.soundClass.buttonClickSound.play();
            isContinueButtonPressed++;
        }

        if(isGuessCorrect){
            if(isContinueButtonPressed == 2){
                programmerGame.showVictoryScreen(true);
            }
        } else{
            if(isContinueButtonPressed == 1){
                programmerGame.showVictoryScreen(false);
            }
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
