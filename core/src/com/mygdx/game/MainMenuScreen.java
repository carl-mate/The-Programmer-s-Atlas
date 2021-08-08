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

    private StringBuilder username;
    private boolean handledUserName;
    private boolean emptyField;

    private boolean isPlayButtonHovered;
    private boolean isOptionsButtonHovered;
    private boolean isHowToPlayButtonHovered;

    public MainMenuScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        isPlayButtonHovered = false;
        isOptionsButtonHovered = false;
        isHowToPlayButtonHovered = false;
    }

    @Override
    public void show() {
        username = new StringBuilder();
        handledUserName = false;
        emptyField = false;
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

        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //playButton attributes
        Vector2 playButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 10);
        Rectangle playButtonBoundingBox = new Rectangle(playButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, playButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        //helpButton attributes
        Vector2 howToPlayButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 10);
        Rectangle howToPlayButtonBoundingBox = new Rectangle(howToPlayButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, howToPlayButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        //optionsButton attributes
        Vector2 optionsButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 10);
        Rectangle optionsButtonBoundingBox = new Rectangle(optionsButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, optionsButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);

        //hovered
        if(playButtonBoundingBox.contains(mousePosition)){
            isPlayButtonHovered = true;
        } else{ //not hovered
            isPlayButtonHovered = false;
        }

        //hovered
        if(howToPlayButtonBoundingBox.contains(mousePosition)){
            isHowToPlayButtonHovered = true;
        } else{ //not hovered
            isHowToPlayButtonHovered = false;
        }

        //hovered
        if(optionsButtonBoundingBox.contains(mousePosition)){
            isOptionsButtonHovered = true;
        } else{ //not hovered
            isOptionsButtonHovered = false;
        }

        //not hovered states
        if(!isPlayButtonHovered){
            //playButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.playButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_CENTER);
        }
        if(!isOptionsButtonHovered){
            //optionsButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.optionsButton, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_CENTER);
        }
        if(!isHowToPlayButtonHovered){
            //howToPlayButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.howToPlayButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_CENTER);
        }

        //hovered states
        if(isPlayButtonHovered){
            //playButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.playButtonBig, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_BIG_CENTER);
        }

        if(isOptionsButtonHovered){
            //optionsButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.optionsButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_BIG_CENTER);
        }

        if(isHowToPlayButtonHovered){
            //howToPlayButton
            Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.howToPlayButtonBig, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 10), Constants.MAIN_MENU_BUTTON_BIG_CENTER);
        }


        if(!handledUserName){
            handleUsernameInput(batch);
        }

        batch.end();

        if(handledUserName){
            Constants.MENU_SCREEN_NAME = username.toString();
            Constants.preferences = Gdx.app.getPreferences("user");

            Constants.preferences.putString("user", username.toString());
            Constants.preferences.flush();

        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //playButton attributes
        Vector2 playButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 10);
        Rectangle playButtonBoundingBox = new Rectangle(playButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, playButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);

        //optionsButton attributes
        Vector2 optionsButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 10);
        Rectangle optionsButtonBoundingBox = new Rectangle(optionsButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, optionsButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);

        //helpButton attributes
        Vector2 howToPlayButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 10);
        Rectangle howToPlayButtonBoundingBox = new Rectangle(howToPlayButtonCenter.x - Constants.MAIN_MENU_BUTTON_WIDTH / 2, howToPlayButtonCenter.y - Constants.MAIN_MENU_BUTTON_HEIGHT / 2, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);


        if(playButtonBoundingBox.contains(worldTouch)){
            if(handledUserName){
                Gdx.app.log(TAG, "CLICKED PLAY");
//                programmerGame.showChooseColleagueScreen();
                programmerGame.showDifficultyScreen();
            }
        }

//        if(optionsButtonBoundingBox.contains(worldTouch)){
//            hackerGame.showOptionsScreen();
//        }
//
        if(howToPlayButtonBoundingBox.contains(worldTouch)){
            programmerGame.showHowToPlayScreen();
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void handleUsernameInput(SpriteBatch batch){
        if(username.length() < 10){
            if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
                username.append('A');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
                username.append('B');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
                username.append('C');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                username.append('D');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                username.append('E');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
                username.append('F');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
                username.append('G');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
                username.append('H');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
                username.append('I');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
                username.append('J');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
                username.append('K');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
                username.append('L');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
                username.append('M');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
                username.append('N');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
                username.append('O');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
                username.append('P');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Q)){
                username.append('Q');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
                username.append('R');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
                username.append('S');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
                username.append('T');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
                username.append('U');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
                username.append('V');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                username.append('W');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
                username.append('X');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Y)){
                username.append('Y');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
                username.append('Z');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)){
                username.append('0');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
                username.append('1');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
                username.append('2');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
                username.append('3');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
                username.append('4');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
                username.append('5');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
                username.append('6');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)){
                username.append('7');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)){
                username.append('8');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
                username.append('9');
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                username.append(' ');
            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            if(username.length() - 1 >= 0){
                username.deleteCharAt(username.length() - 1);
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(username.length() == 0){
                emptyField = true;
            } else{
                handledUserName = true;
            }
        }

        //username textfield
        Util.drawTextureRegion(batch, Assets.instance.mainMenuAssets.usernameTextfield, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 5.5f), Constants.USERNAME_TEXTFIELD_CENTER);

        //initialize textfield bounds
        Vector2 textfieldCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 4.6f);
        Rectangle textfieldRectangleBounds = new Rectangle(textfieldCenter.x - Constants.USERNAME_TEXTFIELD_WIDTH / 2, textfieldCenter.y - Constants.USERNAME_TEXTFIELD_HEIGHT / 2, Constants.USERNAME_TEXTFIELD_WIDTH, Constants.USERNAME_TEXTFIELD_HEIGHT);

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "username", username.toString(), textfieldRectangleBounds);
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
