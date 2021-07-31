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
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", theoreticalQ[0].getQuestion());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", theoreticalQ[0].getChoice().get(0).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", theoreticalQ[0].getChoice().get(1).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", theoreticalQ[0].getChoice().get(2).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", theoreticalQ[0].getChoice().get(3).getChoice());
        } else if(difficulty == Difficulty.THEORETICAL_EASY){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", theoreticalQ[1].getQuestion());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", theoreticalQ[1].getChoice().get(0).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", theoreticalQ[1].getChoice().get(1).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", theoreticalQ[1].getChoice().get(2).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", theoreticalQ[1].getChoice().get(3).getChoice());
        } else if(difficulty == Difficulty.THEORETICAL_MEDIUM){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionMedium, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", theoreticalQ[2].getQuestion());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", theoreticalQ[2].getChoice().get(0).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", theoreticalQ[2].getChoice().get(1).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", theoreticalQ[2].getChoice().get(2).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", theoreticalQ[2].getChoice().get(3).getChoice());
        } else if(difficulty == Difficulty.THEORETICAL_HARD){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", theoreticalQ[3].getQuestion());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", theoreticalQ[3].getChoice().get(0).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", theoreticalQ[3].getChoice().get(1).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", theoreticalQ[3].getChoice().get(2).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", theoreticalQ[3].getChoice().get(3).getChoice());
        } else if(difficulty == Difficulty.THEORETICAL_VERY_HARD){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", theoreticalQ[4].getQuestion());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", theoreticalQ[4].getChoice().get(0).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", theoreticalQ[4].getChoice().get(1).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", theoreticalQ[4].getChoice().get(2).getChoice());
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", theoreticalQ[4].getChoice().get(3).getChoice());
        } else if(difficulty == Difficulty.PROGRAMMING_VERY_EASY){
//            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
        } else if(difficulty == Difficulty.PROGRAMMING_EASY){

        } else if(difficulty == Difficulty.PROGRAMMING_MEDIUM){

        } else if(difficulty == Difficulty.PROGRAMMING_HARD){

        } else if(difficulty == Difficulty.PROGRAMMING_VERY_HARD){

        }
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

        //choiceB
        Vector2 choiceBButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f);
        Rectangle choiceBButtonBoundingBox = new Rectangle(choiceBButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceBButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceC
        Vector2 choiceCButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f - 100);
        Rectangle choiceCButtonBoundingBox = new Rectangle(choiceCButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceCButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //choiceD
        Vector2 choiceDButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f - 100);
        Rectangle choiceDButtonBoundingBox = new Rectangle(choiceDButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH  / 2, choiceDButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        if (choiceAButtonBoundingBox.contains(worldTouch) || choiceBButtonBoundingBox.contains(worldTouch) || choiceCButtonBoundingBox.contains(worldTouch) || choiceDButtonBoundingBox.contains(worldTouch)) {
            if (theoreticalQ[0].getChoice().get(0).isCorrectChoice() && choiceAButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (theoreticalQ[0].getChoice().get(0).isCorrectChoice() && !choiceAButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);

            }
            if (theoreticalQ[0].getChoice().get(2).isCorrectChoice() && choiceBButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (theoreticalQ[0].getChoice().get(2).isCorrectChoice() && !choiceBButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);

            }
            if (theoreticalQ[0].getChoice().get(1).isCorrectChoice() && choiceCButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (theoreticalQ[0].getChoice().get(1).isCorrectChoice() && !choiceCButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);


            }
            if (theoreticalQ[0].getChoice().get(3).isCorrectChoice() && choiceDButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(true);
            } else if (theoreticalQ[0].getChoice().get(3).isCorrectChoice() && !choiceDButtonBoundingBox.contains(worldTouch)) {
                judgeAnswer(false);

            }
        }

        return true;
    }

    private void judgeAnswer(boolean correct) {
        if (correct) {
            if (this.difficulty == Difficulty.THEORETICAL_VERY_EASY) {  //easy question
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
