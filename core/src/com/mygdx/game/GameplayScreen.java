package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import util.Assets;
import util.Constants;
import util.Enums.Difficulty;
import util.QuestionsManager.ProgrammingQ;
import util.QuestionsManager.TheoreticalQ;
import util.Util;

public class GameplayScreen extends InputAdapter implements Screen {
    private static final String TAG = GameplayScreen.class.getName();

    private Sprite sprite;

    private ProgrammerGame programmerGame;
    private Questions questions;
    private Difficulty difficulty;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private TheoreticalQ[] theoreticalQ;
    private ProgrammingQ[] programmingQ;

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
    private Vector2 instructionCenter;
    private Rectangle instructionRectangleBounds;
    private Vector2 choiceAButtonCenterText;
    private Rectangle choiceAButtonBoundingBoxText;
    private Vector2 choiceCButtonCenterText;
    private Rectangle choiceCButtonBoundingBoxText;
    private Vector2 choiceBButtonCenterText;
    private Rectangle choiceBButtonBoundingBoxText;
    private Vector2 choiceDButtonCenterText;
    private Rectangle choiceDButtonBoundingBoxText;

    private long storeScoreStartTime;
    private int userCounter;
    private int score;

    private boolean askedGoogle;
    private boolean askedAColleague;
    private boolean calledAFamilyMember;

    private Vector2 askGooglePosition;
    private Vector2 askAColleaguePosition;
    private Vector2 callAFamilyMemberPosition;

    private boolean isAskAColleagueLucky;
    private boolean isCallAFamilyMemberLucky;

    private List<Character> askAColleagueDummyChoices;
    private List<Character> callAFamilyMemberDummyChoices;

    private long askAColleagueDummyStartTime;
    private long callAFamilyMemberDummyStartTime;


    public GameplayScreen(ProgrammerGame programmerGame, Difficulty difficulty, SpriteBatch batch) {
        this.programmerGame = programmerGame;
        this.difficulty = difficulty;
        this.batch = batch;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        this.questions = programmerGame.getQuestions();
        this.theoreticalQ = questions.getTheoreticalQuestion();
        this.programmingQ = questions.getProgrammingQuestion();
        Assets.instance.font.setViewport(this.viewport);
        initTextBounds();
        initTheoreticalQuestions();
        initProgrammingQuestions();
        askedGoogle = false;
        askedAColleague = false;
        calledAFamilyMember = false;
    }

    @Override
    public void show() {
        askGooglePosition = new Vector2(viewport.getCamera().viewportWidth / 2 - 100, viewport.getCamera().viewportHeight / 1.15f);
        askAColleaguePosition = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.15f);
        callAFamilyMemberPosition = new Vector2(viewport.getCamera().viewportWidth / 2 + 100, viewport.getCamera().viewportHeight / 1.15f);
        Random rand = new Random();

        /*
         * Handles the probability of the correctness of each lifeline.
         * Ask Google lifeline is 100% reliable so it doesn't need a special case.
         */

        //Ask a Colleague lifeline is 75% reliable
        if(rand.nextInt(100) < 75){
            isAskAColleagueLucky = true;
        }

        //Call a Family Member lifeline is 25% reliable
        if(rand.nextInt(100) < 25){
            isCallAFamilyMemberLucky = true;
        }
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
        renderLifelines(delta);

        batch.end();
    }

    private float calculateAlpha(){
        //utility function for lerp();
        float currentTime = Gdx.graphics.getDeltaTime();
        return currentTime;
    }

    private void renderLifelines(float delta){
        //askGoogle
        if(!askedGoogle){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askGoogleLifeline, askGooglePosition, Constants.LIFELINE_CENTER);
        } else{
            if(isCorrectChoiceA){
                Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                askGooglePosition.x = Interpolation.linear.apply(askGooglePosition.x, targetPosition.x, 0.1f);
                askGooglePosition.y = Interpolation.linear.apply(askGooglePosition.y, targetPosition.y, 0.1f);
                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askGoogleLifeline, askGooglePosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceC){
                Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                askGooglePosition.x = Interpolation.linear.apply(askGooglePosition.x, targetPosition.x, 0.1f);
                askGooglePosition.y = Interpolation.linear.apply(askGooglePosition.y, targetPosition.y, 0.1f);
                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askGoogleLifeline, askGooglePosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceB){
                Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                askGooglePosition.x = Interpolation.linear.apply(askGooglePosition.x, targetPosition.x, 0.1f);
                askGooglePosition.y = Interpolation.linear.apply(askGooglePosition.y, targetPosition.y, 0.1f);
                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askGoogleLifeline, askGooglePosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceD){
                Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                askGooglePosition.x = Interpolation.linear.apply(askGooglePosition.x, targetPosition.x, 0.1f);
                askGooglePosition.y = Interpolation.linear.apply(askGooglePosition.y, targetPosition.y, 0.1f);
                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askGoogleLifeline, askGooglePosition, Constants.LIFELINE_CENTER);
            }
        }

        //askAColleague
        if(!askedAColleague){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askClementLifeline, askAColleaguePosition, Constants.LIFELINE_CENTER);
        } else{ //askedAColleague = true
            if(isCorrectChoiceA){
                if(isAskAColleagueLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                    askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                    askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "ASK A COLLEAGUE UNLUCKY");
                    //Randomly select one among B, C, and D
                    askAColleagueDummyChoices = new ArrayList<>(Arrays.asList('B', 'C', 'D'));
                    if(askAColleagueDummyStartTime == 0){
                        Collections.shuffle(askAColleagueDummyChoices);
                        askAColleagueDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(askAColleagueDummyChoices.get(0) == 'B'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'C'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'D'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }

                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askClementLifeline, askAColleaguePosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceC){
                if(isAskAColleagueLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                    askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                    askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "ASK A COLLEAGUE UNLUCKY");
                    //Randomly select one among B, A, and D
                    askAColleagueDummyChoices = new ArrayList<>(Arrays.asList('B', 'A', 'D'));

                    if(askAColleagueDummyStartTime == 0){
                        Collections.shuffle(askAColleagueDummyChoices);
                        askAColleagueDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(askAColleagueDummyChoices.get(0) == 'B'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'A'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'D'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askClementLifeline, askAColleaguePosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceB){
                if(isAskAColleagueLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                    askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                    askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "ASK A COLLEAGUE UNLUCKY");
                    //Randomly select one among C, A, and D
                    askAColleagueDummyChoices = new ArrayList<>(Arrays.asList('C', 'A', 'D'));

                    if(askAColleagueDummyStartTime == 0){
                        Collections.shuffle(askAColleagueDummyChoices);
                        askAColleagueDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(askAColleagueDummyChoices.get(0) == 'C'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'A'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'D'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askClementLifeline, askAColleaguePosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceD){
                if(isAskAColleagueLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                    askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                    askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "ASK A COLLEAGUE UNLUCKY");
                    //Randomly select one among C, A, and D
                    askAColleagueDummyChoices = new ArrayList<>(Arrays.asList('C', 'A', 'B'));

                    if(askAColleagueDummyStartTime == 0){
                        Collections.shuffle(askAColleagueDummyChoices);
                        askAColleagueDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(askAColleagueDummyChoices.get(0) == 'C'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'A'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                    if(askAColleagueDummyChoices.get(0) == 'B'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        askAColleaguePosition.x = Interpolation.linear.apply(askAColleaguePosition.x, targetPosition.x, 0.1f);
                        askAColleaguePosition.y = Interpolation.linear.apply(askAColleaguePosition.y, targetPosition.y, 0.1f);
                    }
                }
                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.askClementLifeline, askAColleaguePosition, Constants.LIFELINE_CENTER);
            }
        }

        //callAFamilyMember
        if(!calledAFamilyMember){
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.callAFamilyMemberLifeline, callAFamilyMemberPosition, Constants.LIFELINE_CENTER);
        } else{
            if(isCorrectChoiceA){
                if(isCallAFamilyMemberLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                    callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                    callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "CALL A FAMILY MEMBER UNLUCKY");
                    //Randomly select one among B, C, and D
                    callAFamilyMemberDummyChoices = new ArrayList<>(Arrays.asList('B', 'C', 'D'));
                    if(callAFamilyMemberDummyStartTime == 0){
                        Collections.shuffle(callAFamilyMemberDummyChoices);
                        callAFamilyMemberDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(callAFamilyMemberDummyChoices.get(0) == 'B'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'C'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'D'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.callAFamilyMemberLifeline, callAFamilyMemberPosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceC){
                if(isCallAFamilyMemberLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                    callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                    callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "CALL A FAMILY MEMBER UNLUCKY");
                    //Randomly select one among B, A, and D
                    callAFamilyMemberDummyChoices = new ArrayList<>(Arrays.asList('B', 'A', 'D'));
                    if(callAFamilyMemberDummyStartTime == 0){
                        Collections.shuffle(callAFamilyMemberDummyChoices);
                        callAFamilyMemberDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(callAFamilyMemberDummyChoices.get(0) == 'B'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'A'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'D'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.callAFamilyMemberLifeline, callAFamilyMemberPosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceB){
                if(isCallAFamilyMemberLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                    callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                    callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "CALL A FAMILY MEMBER UNLUCKY");
                    //Randomly select one among C, A, and D
                    callAFamilyMemberDummyChoices = new ArrayList<>(Arrays.asList('C', 'A', 'D'));
                    if(callAFamilyMemberDummyStartTime == 0){
                        Collections.shuffle(callAFamilyMemberDummyChoices);
                        callAFamilyMemberDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(callAFamilyMemberDummyChoices.get(0) == 'C'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'A'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'D'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.callAFamilyMemberLifeline, callAFamilyMemberPosition, Constants.LIFELINE_CENTER);
            } else if(isCorrectChoiceD){
                if(isCallAFamilyMemberLucky){
                    Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceDButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                    callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                    callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                } else{
                    Gdx.app.log(TAG, "CALL A FAMILY MEMBER UNLUCKY");
                    //Randomly select one among C, A, and B
                    callAFamilyMemberDummyChoices = new ArrayList<>(Arrays.asList('C', 'A', 'B'));
                    if(callAFamilyMemberDummyStartTime == 0){
                        Collections.shuffle(callAFamilyMemberDummyChoices);
                        callAFamilyMemberDummyStartTime = TimeUtils.nanoTime();
                    }

                    if(callAFamilyMemberDummyChoices.get(0) == 'C'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceCButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f - 100);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'A'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 3.5f) - (choiceAButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                    if(callAFamilyMemberDummyChoices.get(0) == 'B'){
                        Vector2 targetPosition = new Vector2((viewport.getCamera().viewportWidth / 1.38f) + (choiceBButtonBoundingBoxText.width / 2 + Constants.LIFELINE_WIDTH / 2), viewport.getCamera().viewportHeight / 2.8f);
                        callAFamilyMemberPosition.x = Interpolation.linear.apply(callAFamilyMemberPosition.x, targetPosition.x, 0.1f);
                        callAFamilyMemberPosition.y = Interpolation.linear.apply(callAFamilyMemberPosition.y, targetPosition.y, 0.1f);
                    }
                }

                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.callAFamilyMemberLifeline, callAFamilyMemberPosition, Constants.LIFELINE_CENTER);
            }
        }
    }

    private void renderQuestions() {
        if (difficulty == Difficulty.THEORETICAL_VERY_EASY) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.THEORETICAL_EASY) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.THEORETICAL_MEDIUM) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionMedium, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.THEORETICAL_HARD) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.THEORETICAL_VERY_HARD) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryHard, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.PROGRAMMING_VERY_EASY) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionVeryEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.PROGRAMMING_EASY) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionEasy, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.PROGRAMMING_MEDIUM) {
            Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.questionMedium, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
            renderAnswerBubbles();
            renderQuestionsChoices();
        } else if (difficulty == Difficulty.PROGRAMMING_HARD) {

        } else if (difficulty == Difficulty.PROGRAMMING_VERY_HARD) {

        }
    }

    private void renderQuestionsChoices() {
        if (difficulty == Difficulty.THEORETICAL_VERY_EASY || difficulty == Difficulty.THEORETICAL_EASY || difficulty == Difficulty.THEORETICAL_MEDIUM ||
                difficulty == Difficulty.THEORETICAL_HARD || difficulty == Difficulty.THEORETICAL_VERY_HARD) {
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", question, this.questionRectangleBounds);
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceA", choiceA, this.choiceAButtonBoundingBoxText);
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceC", choiceC, this.choiceCButtonBoundingBoxText);
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceB", choiceB, this.choiceBButtonBoundingBoxText);
            Assets.instance.font.drawSourceCodeProBoldFont(batch, "choiceD", choiceD, this.choiceDButtonBoundingBoxText);
        } else{
            Texture questionImage = Assets.instance.resourcesFilePath.image.get(question);
            Texture choiceAImage = Assets.instance.resourcesFilePath.image.get(choiceA);
            Texture choiceCImage = Assets.instance.resourcesFilePath.image.get(choiceC);
            Texture choiceBImage = Assets.instance.resourcesFilePath.image.get(choiceB);
            Texture choiceDImage = Assets.instance.resourcesFilePath.image.get(choiceD);

            Util.drawTextureRegion(batch, choiceAImage, new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f), new Vector2(choiceAImage.getWidth() / 2f, choiceAImage.getHeight() / 2f));
            Util.drawTextureRegion(batch, choiceCImage, new Vector2(viewport.getCamera().viewportWidth / 3.5f, viewport.getCamera().viewportHeight / 2.8f - 100), new Vector2(choiceCImage.getWidth() / 2f, choiceCImage.getHeight() / 2f));
            Util.drawTextureRegion(batch, choiceBImage, new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f), new Vector2(choiceBImage.getWidth() / 2f, choiceBImage.getHeight() / 2f));
            Util.drawTextureRegion(batch, choiceDImage, new Vector2(viewport.getCamera().viewportWidth / 1.38f, viewport.getCamera().viewportHeight / 2.8f - 100), new Vector2(choiceDImage.getWidth() / 2f, choiceDImage.getHeight() / 2f));


            Assets.instance.font.drawSourceCodeProBoldFont(batch, "question", "PRESS AND HOLD Q TO VIEW QUESTION", this.instructionRectangleBounds);
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                Util.drawTextureRegion(batch, Assets.instance.gameplayScreenAssets.fadeBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);
                Util.drawTextureRegion(batch, questionImage, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2f), new Vector2(questionImage.getWidth() / 2f, questionImage.getHeight() / 2f));
            }
        }

    }

    private void renderAnswerBubbles() {
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
        Rectangle choiceDButtonBoundingBox = new Rectangle(choiceDButtonCenter.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceDButtonCenter.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

        //askGoogle
        Vector2 askGoogleCenter = new Vector2(viewport.getCamera().viewportWidth / 2 - 100, viewport.getCamera().viewportHeight / 1.15f);
        Rectangle askGoogleBoundingBox = new Rectangle(askGoogleCenter.x - Constants.LIFELINE_WIDTH / 2, askGoogleCenter.y - Constants.LIFELINE_HEIGHT / 2, Constants.LIFELINE_WIDTH, Constants.LIFELINE_HEIGHT);

        //askAColleague
        Vector2 askAColleagueCenter = new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 1.15f);
        Rectangle askAColleagueBoundingBox = new Rectangle(askAColleagueCenter.x - Constants.LIFELINE_WIDTH / 2, askAColleagueCenter.y - Constants.LIFELINE_HEIGHT / 2, Constants.LIFELINE_WIDTH, Constants.LIFELINE_HEIGHT);

        //callAFamilyMember
        Vector2 callAFamilyMemberCenter = new Vector2(viewport.getCamera().viewportWidth / 2 + 100, viewport.getCamera().viewportHeight / 1.15f);
        Rectangle callAFamilyMemberBoundingBox = new Rectangle(callAFamilyMemberCenter.x - Constants.LIFELINE_WIDTH / 2, callAFamilyMemberCenter.y - Constants.LIFELINE_HEIGHT / 2, Constants.LIFELINE_WIDTH, Constants.LIFELINE_HEIGHT);

        if(askGoogleBoundingBox.contains(worldTouch)){
            Gdx.app.log(TAG, "TOUCHED ASK GOOGLE");
            askedGoogle = true;
        }
        if(askAColleagueBoundingBox.contains(worldTouch)){
            Gdx.app.log(TAG, "TOUCHED ASK A COLLEAGUE");
            askedAColleague = true;
        }
        if(callAFamilyMemberBoundingBox.contains(worldTouch)){
            Gdx.app.log(TAG, "TOUCHED CALL A FAMILY MEMBER");
            calledAFamilyMember = true;
        }

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
                score += Constants.THEORETICAL_VERY_EASY_POINTS;
                Gdx.app.log(TAG, "THEORETICAL VERY EASY CORRECT");
            } else if (this.difficulty == Difficulty.THEORETICAL_EASY) {
                score += Constants.THEORETICAL_EASY_POINTS;
                Gdx.app.log(TAG, "THEORETICAL EASY CORRECT");
            } else if (this.difficulty == Difficulty.THEORETICAL_MEDIUM) {
                score += Constants.THEORETICAL_MEDIUM_POINTS;
                Gdx.app.log(TAG, "THEORETICAL MEDIUM CORRECT");
            } else if (this.difficulty == Difficulty.THEORETICAL_HARD) {
                score += Constants.THEORETICAL_HARD_POINTS;
                Gdx.app.log(TAG, "THEORETICAL HARD CORRECT");
            } else if (this.difficulty == Difficulty.THEORETICAL_VERY_HARD) {
                score += Constants.THEORETICAL_VERY_HARD_POINTS;
                Gdx.app.log(TAG, "THEORETICAL VERY HARD CORRECT");
            }

            if (this.difficulty == Difficulty.PROGRAMMING_VERY_EASY) {
                Gdx.app.log(TAG, "PROGRAMMING VERY EASY CORRECT");
            } else if (this.difficulty == Difficulty.PROGRAMMING_EASY) {
                Gdx.app.log(TAG, "PROGRAMMING EASY CORRECT");
            } else if (this.difficulty == Difficulty.PROGRAMMING_MEDIUM) {
                Gdx.app.log(TAG, "PROGRAMMING MEDIUM CORRECT");
            } else if (this.difficulty == Difficulty.PROGRAMMING_HARD) {
                Gdx.app.log(TAG, "PROGRAMMING HARD CORRECT");
            } else if (this.difficulty == Difficulty.PROGRAMMING_VERY_HARD) {
                Gdx.app.log(TAG, "PROGRAMMING VERY HARD CORRECT");
            }

            programmerGame.showCorrectAnswerScreen(score);
        } else {

            //store the scores
            while(true){
                if(!Constants.preferences.contains("user-"+userCounter)){
                    Constants.preferences.putString("user-"+userCounter, Constants.preferences.getString("user"));
                    Constants.preferences.putInteger("score-"+userCounter, score);
                    break;
                } else{
                    userCounter++;
                }
            }
            Constants.preferences.putInteger("userCounter", userCounter);
            Constants.preferences.flush();
            storeScoreStartTime = TimeUtils.nanoTime();

            programmerGame.showGameOverScreen();
            Gdx.app.log(TAG, "WRONG");
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void initTheoreticalQuestions() {
        if (difficulty == Difficulty.THEORETICAL_VERY_EASY) {
            this.question = theoreticalQ[0].getQuestion();
            this.choiceA = theoreticalQ[0].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[0].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[0].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[0].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[0].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[0].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[0].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[0].getChoice().get(3).isCorrectChoice();
        } else if (difficulty == Difficulty.THEORETICAL_EASY) {
            this.question = theoreticalQ[1].getQuestion();
            this.choiceA = theoreticalQ[1].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[1].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[1].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[1].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[1].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[1].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[1].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[1].getChoice().get(3).isCorrectChoice();
        } else if (difficulty == Difficulty.THEORETICAL_MEDIUM) {
            this.question = theoreticalQ[2].getQuestion();
            this.choiceA = theoreticalQ[2].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[2].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[2].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[2].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[2].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[2].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[2].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[2].getChoice().get(3).isCorrectChoice();
        } else if (difficulty == Difficulty.THEORETICAL_HARD) {
            this.question = theoreticalQ[3].getQuestion();
            this.choiceA = theoreticalQ[3].getChoice().get(0).getChoice();
            this.choiceC = theoreticalQ[3].getChoice().get(1).getChoice();
            this.choiceB = theoreticalQ[3].getChoice().get(2).getChoice();
            this.choiceD = theoreticalQ[3].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = theoreticalQ[3].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = theoreticalQ[3].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = theoreticalQ[3].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = theoreticalQ[3].getChoice().get(3).isCorrectChoice();
        } else if (difficulty == Difficulty.THEORETICAL_VERY_HARD) {
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

    private void initProgrammingQuestions() {
        if (difficulty == Difficulty.PROGRAMMING_VERY_EASY) {
            this.question = programmingQ[0].getQuestion();
            this.choiceA = programmingQ[0].getChoice().get(0).getChoice();
            this.choiceC = programmingQ[0].getChoice().get(1).getChoice();
            this.choiceB = programmingQ[0].getChoice().get(2).getChoice();
            this.choiceD = programmingQ[0].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = programmingQ[0].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = programmingQ[0].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = programmingQ[0].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = programmingQ[0].getChoice().get(3).isCorrectChoice();
        } else if(difficulty == Difficulty.PROGRAMMING_EASY){
            this.question = programmingQ[1].getQuestion();
            this.choiceA = programmingQ[1].getChoice().get(0).getChoice();
            this.choiceC = programmingQ[1].getChoice().get(1).getChoice();
            this.choiceB = programmingQ[1].getChoice().get(2).getChoice();
            this.choiceD = programmingQ[1].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = programmingQ[1].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = programmingQ[1].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = programmingQ[1].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = programmingQ[1].getChoice().get(3).isCorrectChoice();
        } else if(difficulty == Difficulty.PROGRAMMING_MEDIUM){
            this.question = programmingQ[2].getQuestion();
            this.choiceA = programmingQ[2].getChoice().get(0).getChoice();
            this.choiceC = programmingQ[2].getChoice().get(1).getChoice();
            this.choiceB = programmingQ[2].getChoice().get(2).getChoice();
            this.choiceD = programmingQ[2].getChoice().get(3).getChoice();
            this.isCorrectChoiceA = programmingQ[2].getChoice().get(0).isCorrectChoice();
            this.isCorrectChoiceC = programmingQ[2].getChoice().get(1).isCorrectChoice();
            this.isCorrectChoiceB = programmingQ[2].getChoice().get(2).isCorrectChoice();
            this.isCorrectChoiceD = programmingQ[2].getChoice().get(3).isCorrectChoice();
        }
    }

    private void initTextBounds() {
        questionCenter = new Vector2(this.viewport.getCamera().viewportWidth / 2, this.viewport.getCamera().viewportHeight / 1.8f);
        questionRectangleBounds = new Rectangle(questionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, questionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

        instructionCenter = new Vector2(this.viewport.getCamera().viewportWidth / 2, this.viewport.getCamera().viewportHeight / 1.6f);
        instructionRectangleBounds = new Rectangle(instructionCenter.x - Constants.QUESTIONBUBBLE_WIDTH / 2, instructionCenter.y - Constants.QUESTIONBUBBLE_HEIGHT / 2, Constants.QUESTIONBUBBLE_WIDTH, Constants.QUESTIONBUBBLE_HEIGHT);

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
        choiceDButtonBoundingBoxText = new Rectangle(choiceDButtonCenterText.x - Constants.ANSWERBUBBLE_BUTTON_WIDTH / 2, choiceDButtonCenterText.y - Constants.ANSWERBUBBLE_BUTTON_HEIGHT / 2, Constants.ANSWERBUBBLE_BUTTON_WIDTH, Constants.ANSWERBUBBLE_BUTTON_HEIGHT);

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
