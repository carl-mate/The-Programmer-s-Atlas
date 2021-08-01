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
import util.QuestionsManager;
import util.QuestionsManager.TheoreticalQ;
import util.Util;

public class GameplayScreen extends InputAdapter implements Screen {
    private static final String TAG = GameplayScreen.class.getName();

    private ProgrammerGame programmerGame;
    private Questions questions;
    private Difficulty difficulty;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private TheoreticalQ[] theoreticalQ;

    private String question;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private boolean isCorrectChoiceA;
    private boolean isCorrectChoiceB;
    private boolean isCorrectChoiceC;
    private boolean isCorrectChoiceD;

    private Vector2 questionCenter;
    private Rectangle questionRectangleBounds;
    private Vector2 choiceAButtonCenterText;
    private Rectangle choiceAButtonBoundingBoxText;
    private Vector2 choiceCButtonCenterText;
    private Rectangle choiceCButtonBoundingBoxText;
    private Vector2 choiceBButtonCenterText;
    private Rectangle choiceBButtonBoundingBoxText;
    private Vector2 choiceDButtonCenterText;
    private Rectangle choiceDButtonBoundingBoxText;


    public GameplayScreen(ProgrammerGame programmerGame, Difficulty difficulty, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.difficulty = difficulty;
        this.batch = batch;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        this.questions = programmerGame.getQuestions();
        this.theoreticalQ = questions.getTheoreticalQuestion();
        Assets.instance.font.setViewport(this.viewport);
        initTextBounds();
        initTheoreticalQuestions();

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

        renderQuestions();

        batch.end();
    }

    private void renderQuestions(){
        if(difficulty == Difficulty.THEORETICAL_VERY_EASY){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderChoices();
        } else if(difficulty == Difficulty.THEORETICAL_EASY){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderChoices();
        } else if(difficulty == Difficulty.THEORETICAL_MEDIUM){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionMedium, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderChoices();
        } else if(difficulty == Difficulty.THEORETICAL_HARD){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderChoices();
        } else if(difficulty == Difficulty.THEORETICAL_VERY_HARD){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderChoices();
        } else if(difficulty == Difficulty.PROGRAMMING_VERY_EASY){
//            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.PROGRAMMING_EASY){

        } else if(difficulty == Difficulty.PROGRAMMING_MEDIUM){

        } else if(difficulty == Difficulty.PROGRAMMING_HARD){

        } else if(difficulty == Difficulty.PROGRAMMING_VERY_HARD){

        }
    }

    private void renderChoices(){
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", question, this.questionRectangleBounds);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", choiceA, this.choiceAButtonBoundingBoxText);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", choiceC, this.choiceCButtonBoundingBoxText);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", choiceB, this.choiceBButtonBoundingBoxText);
        Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", choiceD, this.choiceDButtonBoundingBoxText);
    }

    private void renderAnswerBubbles(){
        //A
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f), Constants.ANSWERBUBBLE_BUTTON_CENTER);
        //C
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f - 100), Constants.ANSWERBUBBLE_BUTTON_CENTER);
        //B
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f), Constants.ANSWERBUBBLE_BUTTON_CENTER);
        //D
        Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.answerBubbleButton, new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f - 100), Constants.ANSWERBUBBLE_BUTTON_CENTER);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //choiceA
        Vector2 choiceAButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f);
        Rectangle choiceAButtonBoundingBox = new Rectangle(choiceAButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceAButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceC
        Vector2 choiceCButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f - 100);
        Rectangle choiceCButtonBoundingBox = new Rectangle(choiceCButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceCButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceB
        Vector2 choiceBButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f);
        Rectangle choiceBButtonBoundingBox = new Rectangle(choiceBButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceBButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceD
        Vector2 choiceDButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f - 100);
        Rectangle choiceDButtonBoundingBox = new Rectangle(choiceDButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH  / 2, choiceDButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        if (choiceAButtonBoundingBox.contains(worldTouch) || choiceBButtonBoundingBox.contains(worldTouch) || choiceCButtonBoundingBox.contains(worldTouch) || choiceDButtonBoundingBox.contains(worldTouch)) {
            if (this.isCorrectChoiceA && choiceAButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (this.isCorrectChoiceA && !choiceAButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);

            }
            if (this.isCorrectChoiceB && choiceBButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (this.isCorrectChoiceB && !choiceBButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);

            }
            if (this.isCorrectChoiceC && choiceCButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (this.isCorrectChoiceC && !choiceCButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);


            }
            if (this.isCorrectChoiceD && choiceDButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (this.isCorrectChoiceD && !choiceDButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);

            }
        }

        return true;
    }

    private void judgeAnswer(boolean correct) {
        if (correct) {
            if (this.difficulty == Difficulty.THEORETICAL_VERY_EASY) {
                Gdx.app.log(TAG, "THEORETICAL VERY EASY CORRECT");
            } else if(this.difficulty == Difficulty.THEORETICAL_EASY){
                Gdx.app.log(TAG, "THEORETICAL EASY CORRECT");
            } else if(this.difficulty == Difficulty.THEORETICAL_MEDIUM){
                Gdx.app.log(TAG, "THEORETICAL MEDIUM CORRECT");
            } else if(this.difficulty == Difficulty.THEORETICAL_HARD){
                Gdx.app.log(TAG, "THEORETICAL HARD CORRECT");
            } else if(this.difficulty == Difficulty.THEORETICAL_VERY_HARD){
                Gdx.app.log(TAG, "THEORETICAL VERY HARD CORRECT");
            }
        } else {
            Gdx.app.log(TAG, "WRONG");
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void initTheoreticalQuestions(){
        if(difficulty == Difficulty.THEORETICAL_VERY_EASY){
            this.question = theoreticalQ[0].getQuestion();
            this.choiceA = theoreticalQ[0].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[0].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[0].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[0].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[0].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[0].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[0].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[0].getChoice().get(3).isCorrectChoice();
        } else if(difficulty == Difficulty.THEORETICAL_EASY){
            this.question = theoreticalQ[1].getQuestion();
            this.choiceA = theoreticalQ[1].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[1].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[1].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[1].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[1].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[1].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[1].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[1].getChoice().get(3).isCorrectChoice();
        } else if(difficulty == Difficulty.THEORETICAL_MEDIUM){
            this.question = theoreticalQ[2].getQuestion();
            this.choiceA = theoreticalQ[2].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[2].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[2].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[2].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[2].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[2].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[2].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[2].getChoice().get(3).isCorrectChoice();
        } else if(difficulty == Difficulty.THEORETICAL_HARD){
            this.question = theoreticalQ[3].getQuestion();
            this.choiceA = theoreticalQ[3].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[3].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[3].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[3].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[3].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[3].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[3].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[3].getChoice().get(3).isCorrectChoice();
        } else if(difficulty == Difficulty.THEORETICAL_VERY_HARD){
            this.question = theoreticalQ[4].getQuestion();
            this.choiceA = theoreticalQ[4].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[4].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[4].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[4].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[4].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[4].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[4].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[4].getChoice().get(3).isCorrectChoice();
        }

    }

    private void initTextBounds(){
        questionCenter = new Vector2(this.viewport.getCamera().viewportWidth / 2, this.viewport.getCamera().viewportHeight / 1.8f);
        questionRectangleBounds = new Rectangle(questionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, questionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

        //choiceA
        choiceAButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f);
        choiceAButtonBoundingBoxText = new Rectangle(choiceAButtonCenterText.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceAButtonCenterText.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceC
        choiceCButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f - 100);
        choiceCButtonBoundingBoxText = new Rectangle(choiceCButtonCenterText.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceCButtonCenterText.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceB
        choiceBButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f);
        choiceBButtonBoundingBoxText = new Rectangle(choiceBButtonCenterText.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceBButtonCenterText.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceD
        choiceDButtonCenterText = new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f - 100);
        choiceDButtonBoundingBoxText = new Rectangle(choiceDButtonCenterText.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH  / 2, choiceDButtonCenterText.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

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
