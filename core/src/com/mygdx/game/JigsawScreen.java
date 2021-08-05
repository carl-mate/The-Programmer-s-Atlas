package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import entity.PuzzlePiece;
import util.Assets;
import util.Constants;
import util.Util;

public class JigsawScreen extends InputAdapter implements Screen {
    private static final String TAG = JigsawScreen.class.getName();

    private SpriteBatch batch;
    private ProgrammerGame programmerGame;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private ArrayList<PuzzlePiece> puzzlePiece;

    private Vector2 worldTouch;
    //image parameters
    private Texture texture;
    private TextureRegion[][] temp;
    private int imageWidth;
    private int imageHeight;
    private int pieceWidth;
    private int pieceHeight;

    private int numberRows;
    private int numberCols;

    private boolean targetable;

    private int touchCounter;

    public JigsawScreen(ProgrammerGame programmerGame, SpriteBatch batch) {
        this.programmerGame = programmerGame;
        this.batch = batch;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        puzzlePiece = new ArrayList<>();

        targetable = true;
        numberRows = 3;
        numberCols = 3;
        //initialize image parameters
        texture = new Texture(Gdx.files.internal("alanturing.jpg"));
        imageWidth = texture.getWidth();
        imageHeight = texture.getHeight();
        pieceWidth = imageWidth / numberCols;
        pieceHeight = imageHeight / numberRows;

        temp = TextureRegion.split(texture, pieceWidth, pieceHeight);

        for (int r = 0; r < numberRows; r++) {
            for (int c = 0; c < numberCols; c++) {
                // create puzzle piece at random location on left half of screen
                int pieceX = (int) MathUtils.random(0, 960 - pieceWidth);
                int pieceY = (int) MathUtils.random(0, 540 - pieceHeight);
                puzzlePiece.add(new PuzzlePiece(new Vector2(pieceX, pieceY), temp[r][c]));
            }
        }
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
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        for(PuzzlePiece x: puzzlePiece){
            if(x.getTouched()){ //if touched, follow mouse
                Vector2 followCursor = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                x.setPosition(followCursor);
            }
            x.render(batch);
        }

        //initialize bounds for puzzle pieces
        for(PuzzlePiece x: puzzlePiece){
            Vector2 puzzlePieceCenter = x.getPosition();
            Rectangle puzzlePieceBoundingBox = new Rectangle(puzzlePieceCenter.x - x.getWidth() / 2f, puzzlePieceCenter.y - x.getHeight() / 2f, x.getWidth(), x.getHeight());
            x.setPuzzlePieceBoundingBox(puzzlePieceBoundingBox);
        }
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTouch = viewport.unproject(new Vector2(screenX, screenY));

       //check if touched
        for(PuzzlePiece x: puzzlePiece){

            if(x.getPuzzlePieceBoundingBox().contains(worldTouch) && !x.getTouched()){
                x.setTouched(true);
                touchCounter++;
                Gdx.app.log(TAG, "TOUCHED PUZZLE PIECE " + touchCounter);
            }else if(x.getPuzzlePieceBoundingBox().contains(worldTouch) && x.getTouched()){
                x.setTouched(false);
                touchCounter++;
                Gdx.app.log(TAG, "UNTOUCHED PUZZLE PIECE " + touchCounter);
            }
        }

        return true;
    }

    public void setTargetable(boolean t) {
        targetable = t;
    }

    public boolean isTargetable() {
        return targetable;
    }
}
