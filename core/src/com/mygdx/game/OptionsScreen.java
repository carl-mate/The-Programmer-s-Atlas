package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Constants;
import util.Util;
/**
 *  This screen will give the user the option to turn the music on/off
 */
public class OptionsScreen extends InputAdapter implements Screen {
    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private boolean musicOn;
    private boolean isReturnToMenuButtonHovered;
    private long returnToMenuButtonHoverTime;

    public OptionsScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        musicOn = programmerGame.getMusicOn();
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
        Util.drawTextureRegion(batch, Assets.instance.optionsScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        if(musicOn){
            Util.drawTextureRegion(batch, Assets.instance.optionsScreenAssets.musicOn, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.MUSIC_BUTTON_CENTER);
        } else{
            Util.drawTextureRegion(batch, Assets.instance.optionsScreenAssets.musicOff, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.MUSIC_BUTTON_CENTER);
        }

        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        Vector2 instructionCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.25f);
        Rectangle instructionRectangleBounds = new Rectangle(instructionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, instructionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 220);
        Rectangle returnToMenuButtonRectangleBounds = new Rectangle(returnToMenuButtonCenter.x - Constants.CD_RTM_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.CD_RTM_BUTTON_HEIGHT / 2, Constants.CD_RTM_BUTTON_WIDTH, Constants.CD_RTM_BUTTON_HEIGHT);

        isReturnToMenuButtonHovered = returnToMenuButtonRectangleBounds.contains(mousePosition);

        if(!isReturnToMenuButtonHovered){
            Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.returnToMenuButtonWhite, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 220), Constants.CD_RTM_BUTTON_CENTER);
            if(returnToMenuButtonHoverTime > 0){
                returnToMenuButtonHoverTime = 0;
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.highScoresScreenAssets.returnToMenuButtonWhiteBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 220), Constants.CD_RTM_BUTTON_BIG_CENTER);
            if(returnToMenuButtonHoverTime == 0){
                returnToMenuButtonHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverSound.play();
            }
        }

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "instruction", "TURN MUSIC ON/OFF", instructionRectangleBounds);


        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            programmerGame.showMainMenuScreen();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //playButton attributes
        Vector2 musicButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2);
        Rectangle musicButtonBoundingBox = new Rectangle(musicButtonCenter.x - Constants.MUSIC_BUTTON_WIDTH / 2, musicButtonCenter.y - Constants.MUSIC_BUTTON_HEIGHT / 2, Constants.MUSIC_BUTTON_WIDTH, Constants.MUSIC_BUTTON_HEIGHT);

        Vector2 returnToMenuButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2 - 220);
        Rectangle returnToMenuButtonRectangleBounds = new Rectangle(returnToMenuButtonCenter.x - Constants.CD_RTM_BUTTON_WIDTH / 2, returnToMenuButtonCenter.y - Constants.CD_RTM_BUTTON_HEIGHT / 2, Constants.CD_RTM_BUTTON_WIDTH, Constants.CD_RTM_BUTTON_HEIGHT);

        if(musicButtonBoundingBox.contains(worldTouch)){
            if(musicOn){
                musicOn = false;
                programmerGame.setMenuScreenMusicPause();
                programmerGame.setGameplayScreenMusicPause();
            } else{
                musicOn = true;
                programmerGame.setMenuScreenMusicOn();
            }
            programmerGame.setMusicOnOrOff();
        }

        if(returnToMenuButtonRectangleBounds.contains(worldTouch)){
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
