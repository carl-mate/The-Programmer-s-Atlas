package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import entity.ImportantFigure;
import entity.PuzzleArea;
import entity.PuzzlePiece;
import util.Assets;
import util.Constants;
import util.Util;
/**
 *  This screen is shown whenever the user selects the correct answer and unlocks a clue
 */
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
    private ArrayList<PuzzlePiece> droppedPuzzlePieces;
    private ArrayList<ImportantFigure> importantFigures;

    private Vector2 worldTouch;

    private int noOfclues;

    private boolean handledClue;
    private boolean unlockedClue10;
    private boolean unlockedClue11;

    private char[] importantFigureNameReference;
    private StringBuilder importantFigureNameClue;
    private int importantFigureNameClueUnlockedIndex1;
    private int importantFigureNameClueUnlockedIndex2;

    private final Texture importantFigureBiography;
    private final String importantFigureName;

    private boolean handledGuessInput;
    private boolean hasUserGuessed;
    private int guessIndex;

    private boolean isConfirmButtonHovered;
    private boolean isContinueButtonHovered;

    private long continueButtonHoverTime;
    private long confirmButtonHoverTime;

    private boolean isGuessCorrect;


    public JigsawScreen(ProgrammerGame programmerGame, SpriteBatch batch) {
        this.programmerGame = programmerGame;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        noOfclues = 0;
        shapeRenderer = new ShapeRenderer();
        puzzlePiece = new ArrayList<>();
        puzzleArea = new ArrayList<>();
        droppedPuzzlePieces = new ArrayList<>();

        int numberRows = 3;
        int numberCols = 3;

        //fetch pool of important figures
        importantFigures = Assets.instance.importantFigureAssets.importantFigureArrayList;
        //shuffle important figures to ensure randomness
        Collections.shuffle(importantFigures);
        Texture texture = importantFigures.get(0).getImage();
        importantFigureBiography = importantFigures.get(0).getBiography();
        importantFigureName = importantFigures.get(0).getName();

        importantFigureNameReference = new char[importantFigureName.length()];
        importantFigureNameClue = new StringBuilder();

        for (int i = 0; i < importantFigureName.length(); i++) {
            importantFigureNameReference[i] = importantFigureName.charAt(i);
            if (importantFigureName.charAt(i) != ' ') {
                importantFigureNameClue.append('_');
            } else {
                importantFigureNameClue.append(' ');
            }
        }

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

        if (noOfclues == 12) { //guess important figure phase
            handleGuessInput();
            guessImportantFigurePhase();
        } else { //unlock clues phase
            unlockCluesPhase();
        }

    }

    private void guessImportantFigurePhase() {
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
        for (PuzzlePiece x : puzzlePiece) {
            if (x.isUnlocked()) {
                x.render(batch);
            }

            Vector2 importantFigureNameCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 150);
            Rectangle importantFigureNameRectangleBounds = new Rectangle(importantFigureNameCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, importantFigureNameCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

            Assets.instance.font.drawSourceCodeProBoldFont(batch, "importantFigureNameClue", importantFigureNameClue.toString(), importantFigureNameRectangleBounds);

        }

        if (hasUserGuessed) {
            Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            //continue button
            Vector2 confirmButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
            Rectangle confirmButtonBoundingBox = new Rectangle(confirmButtonCenter.x - Constants.CONFIRM_BUTTON_WIDTH / 2, confirmButtonCenter.y - Constants.CONFIRM_BUTTON_HEIGHT / 2, Constants.CONFIRM_BUTTON_WIDTH, Constants.CONFIRM_BUTTON_HEIGHT);
            isConfirmButtonHovered = confirmButtonBoundingBox.contains(mousePosition);
            //TODO
            if (!isConfirmButtonHovered) {
                Util.drawTextureRegion(batch, Assets.instance.jigsawScreenAssets.confirmButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONFIRM_BUTTON_CENTER);
                if(confirmButtonHoverTime > 0){
                    confirmButtonHoverTime = 0;
                }
            } else {
                Util.drawTextureRegion(batch, Assets.instance.jigsawScreenAssets.confirmButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONFIRM_BUTTON_BIG_CENTER);
                if(confirmButtonHoverTime == 0){
                    confirmButtonHoverTime = TimeUtils.nanoTime();
                    Assets.instance.soundClass.buttonHoverSound.play();
                }
            }


        }
        Vector2 instructionCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.10f);
        Rectangle instructionRectangleBounds = new Rectangle(instructionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, instructionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

        Assets.instance.font.drawSourceCodeProBoldFont(batch, "instruction", "Just start typing!", instructionRectangleBounds);
        batch.end();
    }

    private void unlockCluesPhase() {
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

        if (!handledClue) {
            //draw clue label
            Vector2 clueCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f);
            Rectangle clueRectangleBounds = new Rectangle(clueCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, clueCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.fadeBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "clueLabel", "You unlocked a clue!", clueRectangleBounds);

            Gdx.app.log(TAG, "NO OF CLUES: " + noOfclues);
            if (noOfclues == 1) {
                this.puzzlePiece.get(0).setUnlocked(true);
                this.puzzlePiece.get(0).render(batch);
            } else if (noOfclues == 2) {
                this.puzzlePiece.get(1).setUnlocked(true);
                this.puzzlePiece.get(1).render(batch);
            } else if (noOfclues == 3) {
                this.puzzlePiece.get(2).setUnlocked(true);
                this.puzzlePiece.get(2).render(batch);
            } else if (noOfclues == 4) {
                this.puzzlePiece.get(3).setUnlocked(true);
                this.puzzlePiece.get(3).render(batch);
            } else if (noOfclues == 5) {
                this.puzzlePiece.get(4).setUnlocked(true);
                this.puzzlePiece.get(4).render(batch);
            } else if (noOfclues == 6) {
                this.puzzlePiece.get(5).setUnlocked(true);
                this.puzzlePiece.get(5).render(batch);
            } else if (noOfclues == 7) {
                this.puzzlePiece.get(6).setUnlocked(true);
                this.puzzlePiece.get(6).render(batch);
            } else if (noOfclues == 8) {
                this.puzzlePiece.get(7).setUnlocked(true);
                this.puzzlePiece.get(7).render(batch);
            } else if (noOfclues == 9) {
                this.puzzlePiece.get(8).setUnlocked(true);
                this.puzzlePiece.get(8).render(batch);
            } else if (noOfclues == 10) {
                unlockLetterClue();
            } else if (noOfclues == 11) {
                unlockLetterClue();
            }

            Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
            Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WHITE_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_WHITE_HEIGHT / 2, Constants.CONTINUE_BUTTON_WHITE_WIDTH, Constants.CONTINUE_BUTTON_WHITE_HEIGHT);

            isContinueButtonHovered = continueButtonBoundingBox.contains(mousePosition);

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

        } else {
            //render puzzle pieces
            for (PuzzlePiece x : puzzlePiece) {
                //only render unlocked puzzle pieces
                if (x.isUnlocked()) {
                    if (x.getTouched()) { //if touched, follow cursor
                        Vector2 followCursor = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                        x.setPosition(followCursor);
                    }
                    //draw
                    x.render(batch);
                }
            }

            Vector2 instructionCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.10f);
            Rectangle instructionRectangleBounds = new Rectangle(instructionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, instructionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

            if(noOfclues <= 9){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "instruction", "Drag and drop puzzle piece to the puzzle area to continue!", instructionRectangleBounds);
            }
            if(noOfclues == 10 || noOfclues == 11){
                Assets.instance.font.drawSourceCodeProBoldFont(batch, "instruction", "Take note of the unlocked letter clues!", instructionRectangleBounds);
            }


            if (noOfclues < 10) {
                int noOfPuzzlePiecesToBeDropped = noOfclues;
                if (noOfPuzzlePiecesToBeDropped == droppedPuzzlePieces.size()) {
                    //continue button //TODO
//                    Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_CENTER);
                    Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                    Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
                    Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WHITE_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_WHITE_HEIGHT / 2, Constants.CONTINUE_BUTTON_WHITE_WIDTH, Constants.CONTINUE_BUTTON_WHITE_HEIGHT);

                    isContinueButtonHovered = continueButtonBoundingBox.contains(mousePosition);

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
            } else {
                int noOfPuzzlePiecesToBeDropped = 9;
                if (noOfPuzzlePiecesToBeDropped == droppedPuzzlePieces.size()) {
                    //continue button //TODO
//                    Util.drawTextureRegion(batch, Assets.instance.correctAnswerScreenAssets.continueButton, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220), Constants.CONTINUE_BUTTON_CENTER);
                    Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                    Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
                    Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WHITE_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_WHITE_HEIGHT / 2, Constants.CONTINUE_BUTTON_WHITE_WIDTH, Constants.CONTINUE_BUTTON_WHITE_HEIGHT);

                    isContinueButtonHovered = continueButtonBoundingBox.contains(mousePosition);

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


            Vector2 importantFigureNameCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f - 150);
            Rectangle importantFigureNameRectangleBounds = new Rectangle(importantFigureNameCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, importantFigureNameCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

            Assets.instance.font.drawSourceCodeProBoldFont(batch, "importantFigureNameClue", importantFigureNameClue.toString(), importantFigureNameRectangleBounds);

        }
        batch.end();
    }

    private void unlockLetterClue() {
        Random rand = new Random();
        //randomly select from the importantFigureNameClue StringBuilder a clue to unlock
        if (noOfclues == 10) {
            while (!unlockedClue10) {
                //randomly select a char from importantFigureNameClue
                int index = rand.nextInt(importantFigureNameClue.length());
                char c = importantFigureNameClue.charAt(index);
                //check to make sure it is a '_'
                //if it is, then insert the corresponding letter at that index
                if (c == '_') {
                    importantFigureNameClue.setCharAt(index, importantFigureNameReference[index]);
                    //remember the index of the unlocked clue
                    importantFigureNameClueUnlockedIndex1 = index;
                    unlockedClue10 = true; //break from loop
                }
            }

        } else if (noOfclues == 11) {
            //randomly select from the importantFigureNameClue StringBuilder a clue to unlock
            while (!unlockedClue11) {
                //randomly select a char from importantFigureNameClue
                int index = rand.nextInt(importantFigureNameClue.length());
                char c = importantFigureNameClue.charAt(index);
                //check to make sure it is a '_'
                //if it is, then insert the corresponding letter at that index
                if (c == '_') {
                    importantFigureNameClue.setCharAt(index, importantFigureNameReference[index]);
                    //remember the index of the unlocked clue
                    importantFigureNameClueUnlockedIndex2 = index;
                    unlockedClue11 = true; //break from loop
                }
            }
        }
    }

    public void setClue(int noOfclues) {
        this.noOfclues = noOfclues;
    }

    private void insert(char ch) {
        if (guessIndex == importantFigureNameClue.length()) {
            guessIndex--;
        }
        while (true) {
            if ((guessIndex == importantFigureNameClueUnlockedIndex1 || guessIndex == importantFigureNameClueUnlockedIndex2 || importantFigureNameClue.charAt(guessIndex) == ' ') && guessIndex != importantFigureNameClue.length()) {
                guessIndex++;
                if (guessIndex == importantFigureNameClue.length()) {
                    guessIndex--;
                }
            } else {
                break;
            }
            if (guessIndex == importantFigureNameClue.length() - 1 && (importantFigureNameClueUnlockedIndex1 == importantFigureNameClue.length() - 1 || importantFigureNameClueUnlockedIndex2 == importantFigureNameClue.length() - 1)) { //prevents forever loop
                break;
            }
        }
        if (guessIndex != importantFigureNameClueUnlockedIndex1 && guessIndex != importantFigureNameClueUnlockedIndex2) {
            Gdx.app.log(TAG, "GUESS INDEX INSERT: " + guessIndex);
            importantFigureNameClue.setCharAt(guessIndex, ch);
            guessIndex++;
        }
    }

    private void delete() {
        guessIndex--;
        if (guessIndex < 0) {
            guessIndex = 0;
        }

        while (true) {
            if (guessIndex == importantFigureNameClueUnlockedIndex1 || guessIndex == importantFigureNameClueUnlockedIndex2 || importantFigureNameClue.charAt(guessIndex) == ' ') {
                guessIndex--;
                if (guessIndex < 0) {
                    guessIndex = 0;
                }
            } else {
                break;
            }
            if (guessIndex == 0 && (importantFigureNameClueUnlockedIndex1 == 0 || importantFigureNameClueUnlockedIndex2 == 0)) { //prevents forever loop
                break;
            }
        }

        if (guessIndex != importantFigureNameClueUnlockedIndex1 && guessIndex != importantFigureNameClueUnlockedIndex2) {
            Gdx.app.log(TAG, "GUESS INDEX DELETE: " + guessIndex);
            importantFigureNameClue.setCharAt(guessIndex, '_');
        }

    }

    private void handleGuessInput() {
        if(importantFigureNameClue.indexOf("_") == -1){ //check if user has guessed
            hasUserGuessed = true;
        } else{
            hasUserGuessed = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            insert('A');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            insert('B');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            insert('C');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            insert('D');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            insert('E');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            insert('F');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            insert('G');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            insert('H');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            insert('I');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            insert('J');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            insert('K');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            insert('L');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            insert('M');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            insert('N');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            insert('O');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            insert('P');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            insert('Q');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            insert('R');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            insert('S');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            insert('T');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            insert('U');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            insert('V');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            insert('W');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            insert('X');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            insert('Y');
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            insert('Z');
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            delete();
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
        shapeRenderer.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (noOfclues == 12) {
            if (hasUserGuessed) {
                Vector2 confirmButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
                Rectangle confirmButtonBoundingBox = new Rectangle(confirmButtonCenter.x - Constants.CONFIRM_BUTTON_WIDTH / 2, confirmButtonCenter.y - Constants.CONFIRM_BUTTON_HEIGHT / 2, Constants.CONFIRM_BUTTON_WIDTH, Constants.CONFIRM_BUTTON_HEIGHT);
                if(confirmButtonBoundingBox.contains(worldTouch)){
                    Gdx.app.log(TAG, "TOUCHED CONFIRM BUTTON");
                    if(importantFigureName.contentEquals(importantFigureNameClue)){
                        Gdx.app.log(TAG, "YOU GUESSED IT! 2M!");
                        Assets.instance.soundClass.buttonClickSound.play();
                        programmerGame.showJigsawGuessResultScreen(true, this.importantFigureBiography);
                    } else{
                        Gdx.app.log(TAG, "NOPE! 500k!");
                        Assets.instance.soundClass.buttonClickSound.play();
                        programmerGame.showJigsawGuessResultScreen(false, null);
                    }
                }
            }
        } else {

            //continue button
            Vector2 continueButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f - 220);
            Rectangle continueButtonBoundingBox = new Rectangle(continueButtonCenter.x - Constants.CONTINUE_BUTTON_WIDTH / 2, continueButtonCenter.y - Constants.CONTINUE_BUTTON_HEIGHT / 2, Constants.CONTINUE_BUTTON_WIDTH, Constants.CONTINUE_BUTTON_HEIGHT);

            if (!handledClue) {
                //continue button
                if (continueButtonBoundingBox.contains(worldTouch)) {
                    Assets.instance.soundClass.buttonClickSound.play();
                    handledClue = true;
                }

            } else {
                //check if touched
                for (PuzzlePiece x : puzzlePiece) {
                    if (x.getPuzzlePieceBoundingBox().contains(worldTouch) && !x.getTouched()) {
                        x.setTouched(true);
                    } else if(x.getPuzzlePieceBoundingBox().contains(worldTouch) && x.getTouched()){
                        x.setTouched(false);
                    }

                    //if puzzle piece is touched while targeted on a puzzle area, then set the puzzle area to true
                    for (PuzzleArea y : puzzleArea) {
                        //ensure that we are referring to the correct puzzle area
//                    if (x.getTouched() && x.getDroppedAtRow() == y.getRow() && x.getDroppedAtCol() == y.getCol()) {
//                        y.setTargetable(true);
//                        droppedPuzzlePieces.remove(x);
//                    }
                        if (x.getTouched() && !y.isTargetable()) {
                            y.setTargetable(true);
                            droppedPuzzlePieces.remove(x);
                        }
                    }
                }

                if (noOfclues < 10) {
                    int noOfPuzzlePiecesToBeDropped = noOfclues;
                    if (noOfPuzzlePiecesToBeDropped == droppedPuzzlePieces.size()) {
                        if (continueButtonBoundingBox.contains(worldTouch)) {
                            Assets.instance.soundClass.buttonClickSound.play();
                            programmerGame.showDifficultyScreen();
                        }
                    }
                } else {
                    int noOfPuzzlePiecesToBeDropped = 9;
                    if (noOfPuzzlePiecesToBeDropped == droppedPuzzlePieces.size()) {
                        if (continueButtonBoundingBox.contains(worldTouch)) {
                            Assets.instance.soundClass.buttonClickSound.play();
                            programmerGame.showDifficultyScreen();
                        }
                    }
                }

            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (handledClue) {
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
                            x.setTouched(false);
                            //specify which puzzle area the puzzle piece was dropped
                            x.setDroppedAtRow(y.getRow());
                            x.setDroppedAtCol(y.getCol());
                            if (!droppedPuzzlePieces.contains(x)) {
                                droppedPuzzlePieces.add(x);
                            }
                        }
                    }
                }
            }
        }


        return true;
    }
}
