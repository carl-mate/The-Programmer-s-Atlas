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

import util.Assets;
import util.Constants;
import util.Enums;
import util.Enums.Colleague;
import util.Util;

public class ChooseColleagueScreen extends InputAdapter implements Screen {
    private static final String TAG = ChooseColleagueScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private boolean isClementHovered;
    private boolean isGennadyHovered;
    private boolean isMichelleHovered;
    private boolean isMikhailaHovered;
    private boolean isNickHovered;

    private long clementHoverTime;
    private long gennadyHoverTime;
    private long michelleHoverTime;
    private long mikhailaHoverTime;
    private long nickHoverTime;


    public ChooseColleagueScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth(), viewport.getWorldHeight(), 0);
        isClementHovered = false;
        isGennadyHovered = false;
        isMichelleHovered = false;
        isMikhailaHovered = false;
        isNickHovered = false;
    }
    @Override
    public void show() {
        Assets.instance.soundClass.chooseColleagueSound.play();
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
        Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.colleagueBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        checkHovered();

        //not hovered states
        if(!isClementHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.clementColleague, new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_CENTER);
            if(clementHoverTime > 0){
                clementHoverTime = 0;
            }
        }
        if(!isGennadyHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.gennadyColleague, new Vector2(viewport.getCamera().viewportWidth / 2f + Constants.COLLEAGUE_WIDTH + 20, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_CENTER);
            if(gennadyHoverTime > 0){
                gennadyHoverTime = 0;
            }
        }
        if(!isMichelleHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.michelleColleague, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_CENTER);
            if(michelleHoverTime > 0){
                michelleHoverTime = 0;
            }
        }
        if(!isMikhailaHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.mikhailaColleague, new Vector2(viewport.getCamera().viewportWidth / 2f - Constants.COLLEAGUE_WIDTH - 20, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_CENTER);
            if(mikhailaHoverTime > 0){
                mikhailaHoverTime = 0;
            }
        }
        if(!isNickHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.nickColleague, new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_CENTER);
            if(nickHoverTime > 0){
                nickHoverTime = 0;
            }
        }

        //hovered states
        if(isClementHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.clementColleagueBig, new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_BIG_CENTER);
            if(clementHoverTime == 0){
                clementHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverOneSound.play();
            }
        }
        if(isGennadyHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.gennadyColleagueBig, new Vector2(viewport.getCamera().viewportWidth / 2f + Constants.COLLEAGUE_WIDTH + 20, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_BIG_CENTER);
            if(gennadyHoverTime == 0){
                gennadyHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverOneSound.play();
            }
        }
        if(isMichelleHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.michelleColleagueBig, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_BIG_CENTER);
            if(michelleHoverTime == 0){
                michelleHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverOneSound.play();
            }
        }
        if(isMikhailaHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.mikhailaColleagueBig, new Vector2(viewport.getCamera().viewportWidth / 2f - Constants.COLLEAGUE_WIDTH - 20, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_BIG_CENTER);
            if(mikhailaHoverTime == 0){
                mikhailaHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverOneSound.play();
            }
        }
        if(isNickHovered){
            Util.drawTextureRegion(batch, Assets.instance.chooseColleagueScreenAssets.nickColleagueBig, new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2), Constants.COLLEAGUE_BIG_CENTER);
            if(nickHoverTime == 0){
                nickHoverTime = TimeUtils.nanoTime();
                Assets.instance.soundClass.buttonHoverOneSound.play();
            }
        }


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //clement attributes
        Vector2 clementButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2);
        Rectangle clementButtonBoundingBox = new Rectangle(clementButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, clementButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //gennady attributes
        Vector2 gennadyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f + Constants.COLLEAGUE_WIDTH + 20, viewport.getCamera().viewportHeight / 2);
        Rectangle gennadyButtonBoundingBox = new Rectangle(gennadyButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, gennadyButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //michelle attributes
        Vector2 michelleButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2);
        Rectangle michelleButtonBoundingBox = new Rectangle(michelleButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, michelleButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //mikhaila attributes
        Vector2 mikhailaButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f - Constants.COLLEAGUE_WIDTH - 20, viewport.getCamera().viewportHeight / 2);
        Rectangle mikhailaButtonBoundingBox = new Rectangle(mikhailaButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, mikhailaButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //nick attributes
        Vector2 nickButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2);
        Rectangle nickButtonBoundingBox = new Rectangle(nickButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, nickButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        if(clementButtonBoundingBox.contains(worldTouch)){
            programmerGame.setColleague(Colleague.CLEMENT);
            Assets.instance.soundClass.buttonClickSound.play();
            programmerGame.showDifficultyScreen();
        }

        if(gennadyButtonBoundingBox.contains(worldTouch)){
            programmerGame.setColleague(Colleague.GENNADY);
            Assets.instance.soundClass.buttonClickSound.play();
            programmerGame.showDifficultyScreen();
        }

        if(michelleButtonBoundingBox.contains(worldTouch)){
            programmerGame.setColleague(Colleague.MICHELLE);
            Assets.instance.soundClass.buttonClickSound.play();
            programmerGame.showDifficultyScreen();
        }

        if(mikhailaButtonBoundingBox.contains(worldTouch)){
            programmerGame.setColleague(Colleague.MIKHAILA);
            Assets.instance.soundClass.buttonClickSound.play();
            programmerGame.showDifficultyScreen();
        }

        if(nickButtonBoundingBox.contains(worldTouch)){
            programmerGame.setColleague(Colleague.NICK);
            Assets.instance.soundClass.buttonClickSound.play();
            programmerGame.showDifficultyScreen();
        }
        return true;
    }

    private void checkHovered(){
        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //clement attributes
        Vector2 clementButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2);
        Rectangle clementButtonBoundingBox = new Rectangle(clementButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, clementButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //gennady attributes
        Vector2 gennadyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f + Constants.COLLEAGUE_WIDTH + 20, viewport.getCamera().viewportHeight / 2);
        Rectangle gennadyButtonBoundingBox = new Rectangle(gennadyButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, gennadyButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //michelle attributes
        Vector2 michelleButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2);
        Rectangle michelleButtonBoundingBox = new Rectangle(michelleButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, michelleButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //mikhaila attributes
        Vector2 mikhailaButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f - Constants.COLLEAGUE_WIDTH - 20, viewport.getCamera().viewportHeight / 2);
        Rectangle mikhailaButtonBoundingBox = new Rectangle(mikhailaButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, mikhailaButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        //nick attributes
        Vector2 nickButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.COLLEAGUE_WIDTH + 10 * 2) * 2, viewport.getCamera().viewportHeight / 2);
        Rectangle nickButtonBoundingBox = new Rectangle(nickButtonCenter.x - Constants.COLLEAGUE_WIDTH / 2, nickButtonCenter.y - Constants.COLLEAGUE_HEIGHT / 2, Constants.COLLEAGUE_WIDTH, Constants.COLLEAGUE_HEIGHT);

        isClementHovered = clementButtonBoundingBox.contains(mousePosition);
        isGennadyHovered = gennadyButtonBoundingBox.contains(mousePosition);
        isMichelleHovered = michelleButtonBoundingBox.contains(mousePosition);
        isMikhailaHovered = mikhailaButtonBoundingBox.contains(mousePosition);
        isNickHovered = nickButtonBoundingBox.contains(mousePosition);
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
