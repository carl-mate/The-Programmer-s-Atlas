package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.Collections;

import entity.PuzzleArea;
import entity.PuzzlePiece;
import util.Assets;
import util.Constants;
import util.Util;

public class JigsawScreen extends InputAdapter implements Screen {
    private static final String TAG = JigsawScreen.class.getName();

    private ShapeRenderer shapeRenderer;
    private Rectangle textureRectangle;

    private SpriteBatch batch;
    private ProgrammerGame programmerGame;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private ArrayList<PuzzlePiece> puzzlePiece;
    private ArrayList<PuzzleArea> puzzleArea;

    private Vector2 worldTouch;

    private int noOfclues;
    private int noOfDroppedPuzzlePieces;

    private boolean handledClue;



    public JigsawScreen(ProgrammerGame programmerGame, SpriteBatch batch) {
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        noOfclues = 0;
        noOfDroppedPuzzlePieces = 0;
        shapeRenderer = new ShapeRenderer();
        puzzlePiece = new ArrayList<>();
        puzzleArea = new ArrayList<>();

        int numberRows = 3;
        int numberCols = 3;

        //initialize image parameters
        //image parameters
        Texture texture = new Texture(Gdx.files.internal("alanturing.jpg"));
//        Texture texture = new Texture(Gdx.files.internal("ken_thompson.jpg"));

        int imageWidth = texture.getWidth();
        int imageHeight = texture.getHeight();
        int pieceWidth = imageWidth / numberCols;
        int pieceHeight = imageHeight / numberRows;

        //dummy puzzle area
//        Vector2 screenCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2);
//        textureRectangle = new Rectangle(screenCenter.x - texture.getWidth() / 2f, screenCenter.y - texture.getHeight() / 2f, texture.getWidth(), texture.getHeight());

        TextureRegion[][] temp = TextureRegion.split(texture, pieceWidth, pieceHeight);

        for (int r = 0; r < numberRows; r++) {
            for (int c = 0; c < numberCols; c++) {
//                // create puzzle piece at random location on left half of screen
//                int pieceX = (int) MathUtils.random(0, viewport.getCamera().viewportWidth / 2 - pieceWidth);
//                int pieceY = (int) MathUtils.random(0, viewport.getCamera().viewportHeight - pieceHeight);
//                puzzlePiece.add(new PuzzlePiece(new Vector2(pieceX, pieceY), temp[r][c]));

                // create puzzle piece at the following coordinates
                puzzlePiece.add(new PuzzlePiece(new Vector2(viewport.getCamera().viewportWidth / 1.25f, viewport.getCamera().viewportHeight / 2f), temp[r][c]));

                int marginX = (int) (((viewport.getCamera().viewportWidth / 2.79) - imageWidth) / 2);
                int marginY = (540 - imageHeight) / 2;

                int areaX = (int) (((viewport.getCamera().viewportWidth / 2.79) + marginX) + (pieceWidth + 1) * c);
                int areaY = (540 - marginY) - (pieceHeight + 1) * r;

                puzzleArea.add(new PuzzleArea(new Vector2(areaX, areaY), new Rectangle(areaX - pieceWidth / 2f, areaY - pieceHeight / 2f, pieceWidth, pieceHeight)));
                puzzleArea.get(puzzleArea.size() - 1).setRow(r);
                puzzleArea.get(puzzleArea.size() - 1).setCol(c);
            }
        }

        //shuffle puzzle pieces to ensure randomness
        Collections.shuffle(puzzlePiece);
    }

    @Override
    public void show() {
        handledClue = false;
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
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        for (PuzzleArea x : puzzleArea) {
            shapeRenderer.rect(x.getPuzzleAreaBoundingBox().x, x.getPuzzleAreaBoundingBox().y, x.getPuzzleAreaBoundingBox().width, x.getPuzzleAreaBoundingBox().height);
        }
        shapeRenderer.end();

        batch.begin();
        //initialize bounds for puzzle pieces
        for (PuzzlePiece x : puzzlePiece) {
            Vector2 puzzlePieceCenter = x.getPosition();
            Rectangle puzzlePieceBoundingBox = new Rectangle(puzzlePieceCenter.x - x.getWidth() / 2f, puzzlePieceCenter.y - x.getHeight() / 2f, x.getWidth(), x.getHeight());
            x.setPuzzlePieceBoundingBox(puzzlePieceBoundingBox);

        }

        if(!handledClue){
            //draw clue label
            Vector2 clueCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f);
            Rectangle clueRectangleBounds = new Rectangle(clueCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, clueCenter.y - Constants.QUESTIONBUBBLE_HEIGHT/ 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.fadeBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "clueLabel", "You unlocked a clue!", clueRectangleBounds);

            Gdx.app.log(TAG, "NO OF CLUES: " + noOfclues);
            if(noOfclues == 1){
                this.puzzlePiece.get(0).setUnlocked(true);
                this.puzzlePiece.get(0).render(batch);
            } else if(noOfclues == 2){
                this.puzzlePiece.get(1).setUnlocked(true);
                this.puzzlePiece.get(1).render(batch);
            } else if(noOfclues == 3){
                this.puzzlePiece.get(2).setUnlocked(true);
                this.puzzlePiece.get(2).render(batch);
            } else if(noOfclues == 4){
                this.puzzlePiece.get(3).setUnlocked(true);
                this.puzzlePiece.get(3).render(batch);
            } else if(noOfclues == 5){
                this.puzzlePiece.get(4).setUnlocked(true);
                this.puzzlePiece.get(4).render(batch);
            } else if(noOfclues == 6){
                this.puzzlePiece.get(5).setUnlocked(true);
                this.puzzlePiece.get(5).render(batch);
            } else if(noOfclues == 7){
                this.puzzlePiece.get(6).setUnlocked(true);
                this.puzzlePiece.get(6).render(batch);
            } else if(noOfclues == 8){
                this.puzzlePiece.get(7).setUnlocked(true);
                this.puzzlePiece.get(7).render(batch);
            } else if(noOfclues == 9){
                this.puzzlePiece.get(8).setUnlocked(true);
                this.puzzlePiece.get(8).render(batch);
            }
            //continue button
            Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160), Constants.CONTINUE_BUTTON_CENTER);
        } else{
            //render puzzle pieces
            for (PuzzlePiece x : puzzlePiece) {
                //only render unlocked puzzle pieces
                if(x.isUnlocked()){
                    if (x.getTouched()) { //if touched, follow cursor
                        Vector2 followCursor = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                        x.setPosition(followCursor);
                    }
                    //handle
                    x.render(batch);
                }
            }

            Vector2 instructionCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.10f);
            Rectangle instructionRectangleBounds = new Rectangle(instructionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, instructionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

            Assets.instance.font.drawSourceCodeProBoldFont(batch, "instruction", "Drag and drop puzzle piece to the puzzle area to continue.", instructionRectangleBounds);
        }

        if(puzzlePiece.get(noOfclues - 1).isDropped()){
            //continue button
            Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160), Constants.CONTINUE_BUTTON_CENTER);
        }
        batch.end();

//        batch.begin();
//        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.normalBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
//
//        //draw clue label
//        Vector2 clueCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.25f);
//        Rectangle clueRectangleBounds = new Rectangle(clueCenter.x - Constants.GAMEOVER_BG_WIDTH / 2, clueCenter.y - Constants.GAMEOVER_BG_HEIGHT / 2, Constants.GAMEOVER_BG_WIDTH, Constants.GAMEOVER_BG_HEIGHT);
//        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.fadeBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
//        Assets.instance.font.drawSourceCodeProBoldFont(batch, "clueLabel", "You unlocked a clue!", clueRectangleBounds);
//
//        batch.end();


    }

    public void setClue(int noOfclues){
        this.noOfclues = noOfclues;
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
        shapeRenderer.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        //continue button
        Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 160);
        Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_HEIGHT / 2, Constants.CONTINUE_BUTTON_WIDTH, Constants.CONTINUE_BUTTON_HEIGHT);

        if(!handledClue){
            //continue button
             if(continueButtonBoundingBox.contains(worldTouch)){
                handledClue = true;
            }

        } else{
            //check if touched
            for (PuzzlePiece x : puzzlePiece) {
                if (x.getPuzzlePieceBoundingBox().contains(worldTouch) && !x.getTouched()) {
                    x.setTouched(true);
                }

                //if puzzle piece is touched while targeted on a puzzle area, then set the target area to true
                for (PuzzleArea y : puzzleArea) {
                    if (x.getTouched() && !y.isTargetable()) {
                        y.setTargetable(true);
                        x.setDropped(false);
                        noOfDroppedPuzzlePieces--;
                    }
                }
            }

            if(continueButtonBoundingBox.contains(worldTouch)){
                programmerGame.showDifficultyScreen();
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if(handledClue){
            //check if touched
            for (PuzzlePiece x : puzzlePiece) {
                //check which puzzle area the puzzle piece overlaps
                float closestDistance = Float.MAX_VALUE;
                for (PuzzleArea y : puzzleArea) {
                    if (x.getPuzzlePieceBoundingBox().overlaps(y.getPuzzleAreaBoundingBox()) && y.isTargetable()) {
                        float currentDistance = Vector2.dst(worldTouch.x, worldTouch.y, y.getPosition().x, y.getPosition().y);

                        //ensures that the puzzle piece targets on the puzzle area with the closest distance
                        if (currentDistance < closestDistance) {
                            x.setPosition(y.getPosition());
                            closestDistance = currentDistance;
                            y.setTargetable(false);
                            x.setDropped(true);
                            noOfDroppedPuzzlePieces++;
                            x.setTouched(false);
                        }

                    }
                }

            }
        }


        return true;
    }
}
