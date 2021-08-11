package util;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final Color BACKGROUND_COLOR = Color.SKY;

    public static final float WORLD_SIZE_WIDTH = 960f;
    public static final float WORLD_SIZE_HEIGHT = 540f;

    public static final String TEXTURE_ATLAS = "images/the-programmer's-atlas.pack.atlas";

    public static Preferences preferences;

    public static String MENU_SCREEN_NAME;

    //Main Menu Assets
    public static final String USERNAME_TEXTFIELD = "username-textfield";
    public static final String USERNAME_TEXTFIELD_CONFIRM = "username-textfield-confirm";
    public static final Vector2 USERNAME_TEXTFIELD_CENTER = new Vector2(88, 13);
    public static final float USERNAME_TEXTFIELD_WIDTH = 175;
    public static final float USERNAME_TEXTFIELD_HEIGHT = 25;

    public static final String MAIN_MENU_BG = "mainMenu-bg";
    public static final String PLAY_BUTTON = "play-button";
    public static final String OPTIONS_BUTTON = "options-button";
    public static final String HOWTOPLAY_BUTTON = "howToPlay-button";

    public static final String HOWTOPLAY_BUTTON_BIG = "howToPlay-button-big";
    public static final String OPTIONS_BUTTON_BIG = "options-button-big";
    public static final String PLAY_BUTTON_BIG = "play-button-big";
    public static final Vector2 MAIN_MENU_BUTTON_BIG_CENTER = new Vector2(96, 30);

    public static final float MAIN_MENU_BUTTON_WIDTH = 136f;
    public static final float MAIN_MENU_BUTTON_HEIGHT = 36f;
    public static final Vector2 MAIN_MENU_BUTTON_CENTER = new Vector2(80, 25);
    public static final Vector2 BG_CENTER = new Vector2(480, 270);

    //How to Play Screen Assets
    public static final String INSTRUCTIONS_BG = "instructions-bg";
    public static final String LIFELINE_BG = "lifeline-bg";

    //Choose Colleague Screen Assets
    public static final String COLLEAGUE_BG = "colleague-bg";
    public static final String CLEMENT_COLLEAGUE = "clement-colleague";
    public static final String GENNADY_COLLEAGUE = "gennady-colleague";
    public static final String MICHELLE_COLLEAGUE  = "michelle-colleague";
    public static final String MIKHAILA_COLLEAGUE = "mikhaila-colleague";
    public static final String NICK_COLLEAGUE = "nick-colleague";
    public static final float COLLEAGUE_WIDTH = 165;
    public static final float COLLEAGUE_HEIGHT = 290f;
    public static final Vector2 COLLEAGUE_CENTER = new Vector2(83, 145);

    public static final String CLEMENT_COLLEAGUE_BIG = "clement-colleague-big";
    public static final String GENNADY_COLLEAGUE_BIG = "gennady-colleague-big";
    public static final String MICHELLE_COLLEAGUE_BIG = "michelle-colleague-big";
    public static final String MIKHAILA_COLLEAGUE_BIG = "mikhaila-colleague-big";
    public static final String NICK_COLLEAGUE_BIG = "nick-colleague-big";
    public static final float COLLEAGUE_BIG_WIDTH = 198;
    public static final float COLLEAGUE_BIG_HEIGHT = 348f;
    public static final Vector2 COLLEAGUE_BIG_CENTER = new Vector2(99, 174);


    //Difficulty Screen Assets
    public static final String DIFFICULTY_BG = "difficulty-bg";
    public static final String THEORETICALQUESTIONS_LABEL = "theoreticalQuestions-label";
    public static final String PROGRAMMINGQUESTIONS_LABEL = "programmingQuestions-label";
    public static final String VERYEASY_BUTTON = "veryEasy-button";
    public static final String EASY_BUTTON = "easy-button";
    public static final String MEDIUM_BUTTON = "medium-button";
    public static final String HARD_BUTTON = "hard-button";
    public static final String VERYHARD_BUTTON = "veryHard-button";
    public static final String VERYEASY_BUTTON_LOCKED = "veryEasy-button-locked";
    public static final String EASY_BUTTON_LOCKED = "easy-button-locked";
    public static final String MEDIUM_BUTTON_LOCKED = "medium-button-locked";
    public static final String HARD_BUTTON_LOCKED = "hard-button-locked";
    public static final String VERYHARD_BUTTON_LOCKED = "veryHard-button-locked";
    public static final String MYSTERYQUESTION_BUTTON = "mysteryQuestion-button";
    public static final String MYSTERYQUESTION_BUTTON_LOCKED = "mysteryQuestion-button-locked";
    public static final String MYSTERYQUESTION_BUTTON_SOLVED = "mysteryQuestion-button-solved";
    public static final String MYSTERYQUESTION_BUTTON_BIG = "mysteryQuestion-button-big";

    public static final String VERYEASY_BUTTON_BIG = "veryEasy-button-big";
    public static final String EASY_BUTTON_BIG = "easy-button-big";
    public static final String MEDIUM_BUTTON_BIG = "medium-button-big";
    public static final String HARD_BUTTON_BIG = "hard-button-big";
    public static final String VERYHARD_BUTTON_BIG = "veryHard-button-big";
    public static final Vector2 DIFFICULTY_SCREEN_BUTTON_BIG_CENTER = new Vector2(180, 36);

    public static final float DIFFICULTY_SCREEN_LABEL_WIDTH = 250f;
    public static final float DIFFICULTY_SCREEN_LABEL_HEIGHT = 40f;
    public static final float DIFFICULTY_SCREEN_BUTTON_WIDTH = 270f;
    public static final float DIFFICULTY_SCREEN_BUTTON_HEIGHT = 45f;
    public static final Vector2 DIFFICULTY_SCREEN_BUTTON_CENTER = new Vector2(150, 30);
    public static final Vector2 DIFFICULTY_SCREEN_LABEL_CENTER = new Vector2(125, 20);
    public static final float MYSTERYQUESTION_BUTTON_WIDTH = 611f;
    public static final float MYSTERYQUESTION_BUTTON_HEIGHT = 47f;
    public static final Vector2 MYSTERYQUESTION_BUTTON_CENTER = new Vector2(323, 42);
    public static final Vector2 MYSTERYQUESTION_BUTTON_BIG_CENTER = new Vector2(387, 50);


    public static final String BAIL_OUT_BUTTON = "bailOut-button";
    public static final String BRING_IT_ON_BUTTON  = "bringItOn-button";
    public static final String BAIL_OUT_BUTTON_BIG = "bailOut-button-big";
    public static final String BRING_IT_ON_BUTTON_BIG  = "bringItOn-button-big";
    public static final String TAKE_THE_RISK_BG = "takeTheRisk-bg";
    public static final float BO_BIO_BUTTON_WIDTH = 140;
    public static final float BO_BIO_BUTTON_HEIGHT = 37;
    public static final Vector2 BO_BIO_BUTTON_CENTER = new Vector2(70, 19);
    public static final Vector2 BO_BIO_BUTTON_BIG_CENTER = new Vector2(89, 27);
    public static final Vector2 TAKE_THE_RISK_BG_CENTER = new Vector2(280, 229);
    public static final float TAKE_THE_RISK_BG_WIDTH = 560f;
    public static final float TAKE_THE_RISK_BG_HEIGHT = 458f;


    //Gameplay Screen Assets
    public static final String ASK_GOOGLE_LIFELINE = "askGoogle-lifeline";
    public static final String ASK_CLEMENT_LIFELINE = "askClement-lifeline";
    public static final String ASK_GENNADY_LIFELINE = "askGennady-lifeline";
    public static final String ASK_MICHELLE_LIFELINE = "askMichelle-lifeline";
    public static final String ASK_MIKHAILA_LIFELINE = "askMikhaila-lifeline";
    public static final String ASK_NICK_LIFELINE = "askNick-lifeline";
    public static final String CALL_A_FAMILY_MEMBER = "callAFamilyMember-lifeline";
    public static final float LIFELINE_WIDTH = 55;
    public static final float LIFELINE_HEIGHT = 55;
    public static final Vector2 LIFELINE_CENTER = new Vector2(28, 28);

    public static final String ASK_GOOGLE_LIFELINE_BIG = "askGoogle-lifeline-110";
    public static final String ASK_CLEMENT_LIFELINE_BIG = "askClement-lifeline-110";
    public static final String ASK_MICHELLE_LIFELINE_BIG = "askMichelle-lifeline-big";
    public static final String ASK_MIKHAILA_LIFELINE_BIG = "askMikhaila-lifeline-big";
    public static final String ASK_GENNADY_LIFELINE_BIG = "askGennady-lifeline-big";
    public static final String ASK_NICK_LIFELINE_BIG = "askNick-lifeline-big";
    public static final String CALL_A_FAMILY_MEMBER_BIG = "callAFamilyMember-lifeline-110";

    public static final Vector2 LIFELINE_110_CENTER = new Vector2(33, 33);

    public static final String NORMAL_BG = "bg";
    public static final String QUESTIONVERYEASY_BG = "questionVeryEasy-bg";
    public static final String QUESTIONEASY_BG = "questionEasy-bg";
    public static final String QUESTIONMEDIUM_BG = "questionMedium-bg";
    public static final String QUESTIONHARD_BG = "questionHard-bg";
    public static final String QUESTIONVERYHARD_BG = "questionVeryHard-bg";

    public static final String ANSWERBUBBLE_BUTTON = "answerBubble-button";
    public static final float ANSWERBUBBLE_BUTTON_WIDTH = 356f;
    public static final float ANSWERBUBBLE_BUTTON_HEIGHT = 82f;
    public static final Vector2 ANSWERBUBBLE_BUTTON_CENTER = new Vector2(188, 49);

    public static final String FADE_BG = "fade-bg";

    public static final float QUESTIONBUBBLE_WIDTH = 750;
    public static final float QUESTIONBUBBLE_HEIGHT = 100;

    public static final String CONTINUE_BUTTON = "continue-button";
    public static final String CONTINUE_BUTTON_BIG = "continue-button-big";
    public static final float CONTINUE_BUTTON_WIDTH = 165;
    public static final float CONTINUE_BUTTON_HEIGHT = 50;
    public static final Vector2 CONTINUE_BUTTON_CENTER = new Vector2(83, 25);
    public static final Vector2 CONTINUE_BUTTON_BIG_CENTER= new Vector2(99, 30);

    public static final int THEORETICAL_VERY_EASY_POINTS = 1000;
    public static final int THEORETICAL_EASY_POINTS = 2000;
    public static final int THEORETICAL_MEDIUM_POINTS = 5000;
    public static final int THEORETICAL_HARD_POINTS = 10000;
    public static final int THEORETICAL_VERY_HARD_POINTS = 25000;

    public static final int PROGRAMMING_VERY_EASY_POINTS = 50000;
    public static final int PROGRAMMING_EASY_POINTS = 100000;
    public static final int PROGRAMMING_MEDIUM_POINTS = 175000;
    public static final int PROGRAMMING_HARD_POINTS = 300000;
    public static final int PROGRAMMING_VERY_HARD_POINTS = 500000;

    public static final int MYSTERY_QUESTION_POINTS = 1000000;

    public static final int IMPORTANT_FIGURE_CORRECT_POINTS = 2000000;
    public static final int IMPORTANT_FIGURE_INCORRECT_POINTS = 500000;

    //GameOver Screen Assets
    public static final String GAMEOVER_BG = "gameOver-bg";
    public static final Vector2 GAMEOVER_BG_CENTER = new Vector2(140, 230);

    public static final float GAMEOVER_BG_WIDTH = 260f;
    public static final float GAMEOVER_BG_HEIGHT = 100f;

    //CorrectAnswer Screen Assets
    public static final String CORRECTANSWER_BG = "correctAnswer-bg";
    public static final Vector2 CORRECTANSWER_BG_CENTER = new Vector2(140, 230);

    public static final String HIGHSCORES_BUTTON_GRADIENT = "highScores-button-gradient";
    public static final String RETURN_TO_MENU_BUTTON_GRADIENT = "returnToMenu-button-gradient";
    public static final String HIGHSCORES_BUTTON_GRADIENT_BIG = "highScores-button-gradient-big";
    public static final String RETURN_TO_MENU_BUTTON_GRADIENT_BIG = "returnToMenu-button-gradient-big";
    public static final float H_RTM_BUTTON_WIDTH = 138f;
    public static final float H_RTM_BUTTON_HEIGHT = 38f;
    public static final Vector2 H_RTM_BUTTON_CENTER = new Vector2(73, 22);
    public static final Vector2 H_RTM_BUTTON_BIG_CENTER = new Vector2(87, 26);

    //Victory Screen Assets
    public static final String VICTORY_BG = "victory-bg";
    public static final Vector2 VICTORY_BG_CENTER = new Vector2(140, 230);

    //Highscores Screen Assets
    public static final String HIGHSCORES_BG = "highScores-bg";
    public static final String HIGHSCORES_BUTTON_WHITE = "highScores-bg-white";
    public static final String RETURN_TO_MENU_BUTTON_WHITE = "returnToMenu-button-white";





}
