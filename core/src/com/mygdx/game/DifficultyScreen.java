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
import util.Util;

public class DifficultyScreen extends InputAdapter implements Screen {
    private static final String TAG = DifficultyScreen.class.getName();

    private ProgrammerGame programmerGame;
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private OrthographicCamera camera;

    private boolean isMysteryQuestionHovered;
    private boolean isTheoreticalVeryEasyHovered;
    private boolean isTheoreticalEasyHovered;
    private boolean isTheoreticalMediumHovered;
    private boolean isTheoreticalHardHovered;
    private boolean isTheoreticalVeryHardHovered;
    private boolean isProgrammingVeryEasyHovered;
    private boolean isProgrammingEasyHovered;
    private boolean isProgrammingMediumHovered;
    private boolean isProgrammingHardHovered;
    private boolean isProgrammingVeryHardHovered;

    private boolean isMysteryQuestionAnswered;
    private boolean isTheoreticalVeryEasyAnswered;
    private boolean isTheoreticalEasyAnswered;
    private boolean isTheoreticalMediumAnswered;
    private boolean isTheoreticalHardAnswered;
    private boolean isTheoreticalVeryHardAnswered;
    private boolean isProgrammingVeryEasyAnswered;
    private boolean isProgrammingEasyAnswered;
    private boolean isProgrammingMediumAnswered;
    private boolean isProgrammingHardAnswered;
    private boolean isProgrammingVeryHardAnswered;

    private int noOfAnsweredQuestions;

    private boolean isBailOutButtonHovered;
    private boolean isBringItOnButtonHovered;

    public DifficultyScreen(ProgrammerGame programmerGame, SpriteBatch batch){
        this.programmerGame = programmerGame;
        this.batch = batch;
        //debug mode
//        this.noOfAnsweredQuestions = 11;
        camera = new OrthographicCamera(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        viewport = new ExtendViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth(), viewport.getWorldHeight(), 0);
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
        //bg
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.difficultyBG, new Vector2(viewport.getCamera().viewportWidth / 2, viewport.getCamera().viewportHeight / 2), Constants.BG_CENTER);

        checkHovered();



        if(noOfAnsweredQuestions == 10){ //user answered all 10 questions
            if(!isMysteryQuestionHovered){
                //mystery question button
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mysteryQuestionButton, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.18f), Constants.MYSTERYQUESTION_BUTTON_CENTER);
            }

            if(isMysteryQuestionHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mysteryQuestionButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.18f), Constants.MYSTERYQUESTION_BUTTON_BIG_CENTER);
            }
        } else{
            if(noOfAnsweredQuestions == 11){ //user answered all 11 questions including the mystery question
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mysteryQuestionButtonSolved, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.18f), Constants.MYSTERYQUESTION_BUTTON_CENTER);
            } else{ //user answered less than 10 questions
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mysteryQuestionButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.18f), Constants.MYSTERYQUESTION_BUTTON_CENTER);
            }
        }


        //theoretical question label
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.theoreticalQuestionsLabel, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.30f), Constants.DIFFICULTY_SCREEN_LABEL_CENTER);

        //not hovered states
        if(!isTheoreticalVeryEasyAnswered){
            if(!isTheoreticalVeryEasyHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isTheoreticalEasyAnswered){
            if(!isTheoreticalEasyHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isTheoreticalMediumAnswered){
            if(!isTheoreticalMediumHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isTheoreticalHardAnswered){
            if(!isTheoreticalHardHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isTheoreticalVeryHardAnswered){
            if(!isTheoreticalVeryHardHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButton, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        //programming question label
        Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.programmingQuestionsLabel, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.30f), Constants.DIFFICULTY_SCREEN_LABEL_CENTER);

        if(!isProgrammingVeryEasyAnswered){
            if(!isProgrammingVeryEasyHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isProgrammingEasyAnswered){
            if(!isProgrammingEasyHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isProgrammingMediumAnswered){
            if(!isProgrammingMediumHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isProgrammingHardAnswered){
            if(!isProgrammingHardHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        if(!isProgrammingVeryHardAnswered){
            if(!isProgrammingVeryHardHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButton, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
            }
        } else{
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButtonLocked, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_CENTER);
        }

        //hovered states
        if(isTheoreticalVeryEasyHovered && !isTheoreticalVeryEasyAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButtonBig, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isTheoreticalEasyHovered && !isTheoreticalEasyAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonBig, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isTheoreticalMediumHovered && !isTheoreticalMediumAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonBig, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isTheoreticalHardHovered && !isTheoreticalHardAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonBig, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isTheoreticalVeryHardHovered && !isTheoreticalVeryHardAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButtonBig, new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }

        if(isProgrammingVeryEasyHovered && !isProgrammingVeryEasyAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryEasyButtonBig, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isProgrammingEasyHovered && !isProgrammingEasyAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.easyButtonBig, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isProgrammingMediumHovered && !isProgrammingMediumAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.mediumButtonBig, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isProgrammingHardHovered && !isProgrammingHardAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.hardButtonBig, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }
        if(isProgrammingVeryHardHovered && !isProgrammingVeryHardAnswered){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.veryHardButtonBig, new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240), Constants.DIFFICULTY_SCREEN_BUTTON_BIG_CENTER);
        }

        if(noOfAnsweredQuestions == 11){
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.fadeBG, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f), Constants.BG_CENTER);
            Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.takeTheRiskBG, new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 2f), Constants.TAKE_THE_RISK_BG_CENTER);

            if(!isBailOutButtonHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.bailOutButton, new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15)), Constants.BO_BIO_BUTTON_CENTER);
            } else{
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.bailOutButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15)), Constants.BO_BIO_BUTTON_BIG_CENTER);
            }

            if(!isBringItOnButtonHovered){
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.bringItOnButton, new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15)), Constants.BO_BIO_BUTTON_CENTER);
            } else{
                Util.drawTextureRegion(batch, Assets.instance.difficultyScreenAssets.bringItOnButtonBig, new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15)), Constants.BO_BIO_BUTTON_BIG_CENTER);
            }


        }

        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        //mystery question attributes
        Vector2 mysteryQuestionButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.18f);
        Rectangle mysteryQuestionButtonBoundingBox = new Rectangle(mysteryQuestionButtonCenter.x - Constants.MYSTERYQUESTION_BUTTON_WIDTH / 2, mysteryQuestionButtonCenter.y - Constants.MYSTERYQUESTION_BUTTON_HEIGHT / 2, Constants.MYSTERYQUESTION_BUTTON_WIDTH, Constants.MYSTERYQUESTION_BUTTON_HEIGHT);

        //THEORETICAL QUESTIONS BUTTONS
        //veryEasyButton attributes
        Vector2 theoreticalVeryEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f);
        Rectangle theoreticalVeryEasyButtonBoundingBox = new Rectangle(theoreticalVeryEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalVeryEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //easyButton attributes
        Vector2 theoreticalEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60);
        Rectangle theoreticalEasyButtonBoundingBox = new Rectangle(theoreticalEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 theoreticalMediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120);
        Rectangle theoreticalMediumButtonBoundingBox = new Rectangle(theoreticalMediumButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalMediumButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 theoreticalHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180);
        Rectangle theoreticalHardButtonBoundingBox = new Rectangle(theoreticalHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //veryHardButton attributes
        Vector2 theoreticalVeryHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240);
        Rectangle theoreticalVeryHardButtonBoundingBox = new Rectangle(theoreticalVeryHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalVeryHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);
        //END OF THEORETICAL QUESTIONS BUTTONS

        //PROGRAMMING QUESTIONS BUTTONS
        //veryEasyButton attributes
        Vector2 programmingVeryEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f);
        Rectangle programmingVeryEasyButtonBoundingBox = new Rectangle(programmingVeryEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingVeryEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //easyButton attributes
        Vector2 programmingEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60);
        Rectangle programmingEasyButtonBoundingBox = new Rectangle(programmingEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 programmingMediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120);
        Rectangle programmingMediumButtonBoundingBox = new Rectangle(programmingMediumButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingMediumButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 programmingHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180);
        Rectangle programmingHardButtonBoundingBox = new Rectangle(programmingHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //veryHardButton attributes
        Vector2 programmingVeryHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240);
        Rectangle programmingVeryHardButtonBoundingBox = new Rectangle(programmingVeryHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingVeryHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);
        //END OF PROGRAMMING QUESTIONS BUTTONS

        //take the risk buttons
        Vector2 bailOutButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15));
        Rectangle bailOutButtonBoundingBox = new Rectangle(bailOutButtonCenter.x - Constants.BO_BIO_BUTTON_WIDTH / 2, bailOutButtonCenter.y - Constants.BO_BIO_BUTTON_HEIGHT / 2, Constants.BO_BIO_BUTTON_WIDTH, Constants.BO_BIO_BUTTON_HEIGHT);

        Vector2 bringItOnButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15));
        Rectangle bringItOnButtonBoundingBox = new Rectangle(bringItOnButtonCenter.x - Constants.BO_BIO_BUTTON_WIDTH / 2, bringItOnButtonCenter.y - Constants.BO_BIO_BUTTON_HEIGHT / 2, Constants.BO_BIO_BUTTON_WIDTH, Constants.BO_BIO_BUTTON_HEIGHT);


        if(noOfAnsweredQuestions == 10 && mysteryQuestionButtonBoundingBox.contains(worldTouch)){
            programmerGame.showGameplayScreen(Difficulty.MYSTERY_QUESTION);
            Gdx.app.log(TAG, "CLICKED MYSTERY QUESTION");
        }

        if(theoreticalVeryEasyButtonBoundingBox.contains(worldTouch) && !isTheoreticalVeryEasyAnswered){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_VERY_EASY);
            Gdx.app.log(TAG, "CLICKED THEORETICAL VERY EASY");
        }
        if(theoreticalEasyButtonBoundingBox.contains(worldTouch) && !isTheoreticalEasyAnswered){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_EASY);
            Gdx.app.log(TAG, "CLICKED THEORETICAL EASY");
        }
        if(theoreticalMediumButtonBoundingBox.contains(worldTouch) && !isTheoreticalMediumAnswered){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_MEDIUM);
            Gdx.app.log(TAG, "CLICKED THEORETICAL MEDIUM");
        }
        if(theoreticalHardButtonBoundingBox.contains(worldTouch) && !isTheoreticalHardAnswered){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_HARD);
            Gdx.app.log(TAG, "CLICKED THEORETICAL HARD");
        }
        if(theoreticalVeryHardButtonBoundingBox.contains(worldTouch) && !isTheoreticalVeryHardAnswered){
            programmerGame.showGameplayScreen(Difficulty.THEORETICAL_VERY_HARD);
            Gdx.app.log(TAG, "CLICKED THEORETICAL VERY HARD");
        }

        if(programmingVeryEasyButtonBoundingBox.contains(worldTouch) && !isProgrammingVeryEasyAnswered){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_VERY_EASY);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING VERY EASY");
        }
        if(programmingEasyButtonBoundingBox.contains(worldTouch) && !isProgrammingEasyAnswered){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_EASY);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING EASY");
        }
        if(programmingMediumButtonBoundingBox.contains(worldTouch) && !isProgrammingMediumAnswered){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_MEDIUM);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING MEDIUM");
        }
        if(programmingHardButtonBoundingBox.contains(worldTouch) && !isProgrammingHardAnswered){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_HARD);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING HARD");
        }
        if(programmingVeryHardButtonBoundingBox.contains(worldTouch) && !isProgrammingVeryHardAnswered){
            programmerGame.showGameplayScreen(Difficulty.PROGRAMMING_VERY_HARD);
            Gdx.app.log(TAG, "CLICKED PROGRAMMING VERY HARD");
        }

        if(noOfAnsweredQuestions == 11){
            if(bailOutButtonBoundingBox.contains(worldTouch)){
                programmerGame.showVictoryScreen();
            }

            if(bringItOnButtonBoundingBox.contains(worldTouch)){
                programmerGame.showJigsawScreen();
            }
        }

        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    private void checkHovered(){
        Vector2 mousePosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        Vector2 mysteryQuestionButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f, viewport.getCamera().viewportHeight / 1.18f);
        Rectangle mysteryQuestionButtonBoundingBox = new Rectangle(mysteryQuestionButtonCenter.x - Constants.MYSTERYQUESTION_BUTTON_WIDTH / 2, mysteryQuestionButtonCenter.y - Constants.MYSTERYQUESTION_BUTTON_HEIGHT / 2, Constants.MYSTERYQUESTION_BUTTON_WIDTH, Constants.MYSTERYQUESTION_BUTTON_HEIGHT);

        //THEORETICAL QUESTIONS BUTTONS
        //veryEasyButton attributes
        Vector2 theoreticalVeryEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f);
        Rectangle theoreticalVeryEasyButtonBoundingBox = new Rectangle(theoreticalVeryEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalVeryEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //easyButton attributes
        Vector2 theoreticalEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 60);
        Rectangle theoreticalEasyButtonBoundingBox = new Rectangle(theoreticalEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 theoreticalMediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 120);
        Rectangle theoreticalMediumButtonBoundingBox = new Rectangle(theoreticalMediumButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalMediumButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 theoreticalHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 180);
        Rectangle theoreticalHardButtonBoundingBox = new Rectangle(theoreticalHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //veryHardButton attributes
        Vector2 theoreticalVeryHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 3f, viewport.getCamera().viewportHeight / 1.45f - 240);
        Rectangle theoreticalVeryHardButtonBoundingBox = new Rectangle(theoreticalVeryHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, theoreticalVeryHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);
        //END OF THEORETICAL QUESTIONS BUTTONS

        //PROGRAMMING QUESTIONS BUTTONS
        //veryEasyButton attributes
        Vector2 programmingVeryEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f);
        Rectangle programmingVeryEasyButtonBoundingBox = new Rectangle(programmingVeryEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingVeryEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //easyButton attributes
        Vector2 programmingEasyButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 60);
        Rectangle programmingEasyButtonBoundingBox = new Rectangle(programmingEasyButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingEasyButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //mediumButton attributes
        Vector2 programmingMediumButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 120);
        Rectangle programmingMediumButtonBoundingBox = new Rectangle(programmingMediumButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingMediumButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //hardButton attributes
        Vector2 programmingHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 180);
        Rectangle programmingHardButtonBoundingBox = new Rectangle(programmingHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);

        //veryHardButton attributes
        Vector2 programmingVeryHardButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 1.5f, viewport.getCamera().viewportHeight / 1.45f - 240);
        Rectangle programmingVeryHardButtonBoundingBox = new Rectangle(programmingVeryHardButtonCenter.x - Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH / 2, programmingVeryHardButtonCenter.y - Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT / 2, Constants.DIFFICULTY_SCREEN_BUTTON_WIDTH, Constants.DIFFICULTY_SCREEN_BUTTON_HEIGHT);
        //END OF PROGRAMMING QUESTIONS BUTTONS

        Vector2 bailOutButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f - (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15));
        Rectangle bailOutButtonBoundingBox = new Rectangle(bailOutButtonCenter.x - Constants.BO_BIO_BUTTON_WIDTH / 2, bailOutButtonCenter.y - Constants.BO_BIO_BUTTON_HEIGHT / 2, Constants.BO_BIO_BUTTON_WIDTH, Constants.BO_BIO_BUTTON_HEIGHT);

        Vector2 bringItOnButtonCenter = new Vector2(viewport.getCamera().viewportWidth / 2f + (Constants.TAKE_THE_RISK_BG_WIDTH / 2 - Constants.BO_BIO_BUTTON_WIDTH  / 2 - 80), viewport.getCamera().viewportHeight / 2f - (Constants.TAKE_THE_RISK_BG_HEIGHT / 2 - Constants.BO_BIO_BUTTON_HEIGHT  / 2 - 15));
        Rectangle bringItOnButtonBoundingBox = new Rectangle(bringItOnButtonCenter.x - Constants.BO_BIO_BUTTON_WIDTH / 2, bringItOnButtonCenter.y - Constants.BO_BIO_BUTTON_HEIGHT / 2, Constants.BO_BIO_BUTTON_WIDTH, Constants.BO_BIO_BUTTON_HEIGHT);


        isBailOutButtonHovered = bailOutButtonBoundingBox.contains(mousePosition);
        isBringItOnButtonHovered = bringItOnButtonBoundingBox.contains(mousePosition);

        isMysteryQuestionHovered = mysteryQuestionButtonBoundingBox.contains(mousePosition);

        isTheoreticalVeryEasyHovered = theoreticalVeryEasyButtonBoundingBox.contains(mousePosition);
        isTheoreticalEasyHovered = theoreticalEasyButtonBoundingBox.contains(mousePosition);
        isTheoreticalMediumHovered = theoreticalMediumButtonBoundingBox.contains(mousePosition);
        isTheoreticalHardHovered = theoreticalHardButtonBoundingBox.contains(mousePosition);
        isTheoreticalVeryHardHovered = theoreticalVeryHardButtonBoundingBox.contains(mousePosition);

        isProgrammingVeryEasyHovered = programmingVeryEasyButtonBoundingBox.contains(mousePosition);
        isProgrammingEasyHovered = programmingEasyButtonBoundingBox.contains(mousePosition);
        isProgrammingMediumHovered = programmingMediumButtonBoundingBox.contains(mousePosition);
        isProgrammingHardHovered = programmingHardButtonBoundingBox.contains(mousePosition);
        isProgrammingVeryHardHovered = programmingVeryHardButtonBoundingBox.contains(mousePosition);
    }

    public int getNoOfAnsweredQuestions() {
        return noOfAnsweredQuestions;
    }

    public void incrementNoOfAnsweredQuestions() {
        this.noOfAnsweredQuestions++;
    }

    public void setMysteryQuestionAnswered(boolean mysteryQuestionAnswered) {
        isMysteryQuestionAnswered = mysteryQuestionAnswered;
    }

    public void setTheoreticalVeryEasyAnswered(boolean theoreticalVeryEasyAnswered) {
        isTheoreticalVeryEasyAnswered = theoreticalVeryEasyAnswered;
    }

    public void setTheoreticalEasyAnswered(boolean theoreticalEasyAnswered) {
        isTheoreticalEasyAnswered = theoreticalEasyAnswered;
    }

    public void setTheoreticalMediumAnswered(boolean theoreticalMediumAnswered) {
        isTheoreticalMediumAnswered = theoreticalMediumAnswered;
    }

    public void setTheoreticalHardAnswered(boolean theoreticalHardAnswered) {
        isTheoreticalHardAnswered = theoreticalHardAnswered;
    }

    public void setTheoreticalVeryHardAnswered(boolean theoreticalVeryHardAnswered) {
        isTheoreticalVeryHardAnswered = theoreticalVeryHardAnswered;
    }

    public void setProgrammingVeryEasyAnswered(boolean programmingVeryEasyAnswered) {
        isProgrammingVeryEasyAnswered = programmingVeryEasyAnswered;
    }

    public void setProgrammingEasyAnswered(boolean programmingEasyAnswered) {
        isProgrammingEasyAnswered = programmingEasyAnswered;
    }

    public void setProgrammingMediumAnswered(boolean programmingMediumAnswered) {
        isProgrammingMediumAnswered = programmingMediumAnswered;
    }

    public void setProgrammingHardAnswered(boolean programmingHardAnswered) {
        isProgrammingHardAnswered = programmingHardAnswered;
    }

    public void setProgrammingVeryHardAnswered(boolean programmingVeryHardAnswered) {
        isProgrammingVeryHardAnswered = programmingVeryHardAnswered;
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
