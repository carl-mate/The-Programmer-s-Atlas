package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.ChooseColleagueScreen;
import com.mygdx.game.CorrectAnswerScreen;
import com.mygdx.game.GameOverScreen;
import com.mygdx.game.GameplayScreen;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.soap.Text;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();
    public MainMenuAssets mainMenuAssets;
    public DifficultyScreenAssets difficultyScreenAssets;
    public GameplayScreenAssets gameplayScreenAssets;
    public GameOverScreenAssets gameOverScreenAssets;
    public CorrectAnswerScreenAssets correctAnswerScreenAssets;
    public ResourcesFilePath resourcesFilePath;
    public Font font;
    public HowToPlayScreenAssets howToPlayScreenAssets;
    public ChooseColleagueScreenAssets chooseColleagueScreenAssets;

    private AssetManager assetManager;

    private Assets(){

    }

    public void init(AssetManager assetManager){
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        mainMenuAssets = new MainMenuAssets(atlas);
        difficultyScreenAssets = new DifficultyScreenAssets(atlas);
        gameplayScreenAssets = new GameplayScreenAssets(atlas);
        gameOverScreenAssets = new GameOverScreenAssets(atlas);
        correctAnswerScreenAssets = new CorrectAnswerScreenAssets(atlas);
        howToPlayScreenAssets = new HowToPlayScreenAssets(atlas);
        font = new Font();
        chooseColleagueScreenAssets = new ChooseColleagueScreenAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public void initResourcesFilePath(){
        resourcesFilePath = new ResourcesFilePath();
    }

    public class Font{
        private final String TAG = Font.class.getName();

        private ExtendViewport viewport;
        public FreeTypeFontGenerator sourceCodeProBoldFontGenerator;

        public final FreeTypeFontGenerator.FreeTypeFontParameter questionFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter choicesFontParameter;

        public final FreeTypeFontGenerator.FreeTypeFontParameter usernameFontParameter;

        public final FreeTypeFontGenerator.FreeTypeFontParameter praiseFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter earningsFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter usernameEarningsFontParameter;

        public final FreeTypeFontGenerator.FreeTypeFontParameter clueLabelFontParameter;

        public final FreeTypeFontGenerator.FreeTypeFontParameter instructionFontParameter;

        public BitmapFont questionFont;
        public BitmapFont choicesFont;
        public BitmapFont praiseFont;
        public BitmapFont earningsFont;
        public BitmapFont usernameFont;
        public BitmapFont usernameEarningsFont;
        public BitmapFont clueLabelFont;
        public BitmapFont instructionFont;

        public GlyphLayout glyphLayout;

        public Font(){
            sourceCodeProBoldFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("SourceCodeProBold.ttf"));

            //font parameters
            usernameFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            usernameFontParameter.size = 20;
            usernameFontParameter.color = Color.BLACK;
            usernameFont = sourceCodeProBoldFontGenerator.generateFont(usernameFontParameter);

            questionFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            questionFontParameter.size = 15;
            questionFontParameter.color = Color.BLACK;
            questionFont = sourceCodeProBoldFontGenerator.generateFont(questionFontParameter);

            choicesFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            choicesFontParameter.size = 12;
            choicesFontParameter.color = Color.BLACK;
            choicesFont = sourceCodeProBoldFontGenerator.generateFont(choicesFontParameter);

            usernameEarningsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            usernameEarningsFontParameter .size = 20;
            usernameEarningsFontParameter .color = Color.BLACK;
            usernameEarningsFont = sourceCodeProBoldFontGenerator.generateFont(usernameEarningsFontParameter);

            praiseFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            praiseFontParameter .size = 30;
            praiseFontParameter .color = Color.BLACK;
            praiseFont = sourceCodeProBoldFontGenerator.generateFont(praiseFontParameter);

            earningsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            earningsFontParameter.size = 40;
            earningsFontParameter.color = Color.BLACK;
            earningsFont = sourceCodeProBoldFontGenerator.generateFont(earningsFontParameter);

            clueLabelFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            clueLabelFontParameter.size = 30;
            clueLabelFontParameter.color = Color.WHITE;
            clueLabelFont = sourceCodeProBoldFontGenerator.generateFont(clueLabelFontParameter);

            instructionFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            instructionFontParameter.size = 15;
            instructionFontParameter.color = Color.BLACK;
            instructionFont = sourceCodeProBoldFontGenerator.generateFont(instructionFontParameter);
            glyphLayout = new GlyphLayout();
        }

        public void drawSourceCodeProBoldFont(SpriteBatch batch, String type, String text, Rectangle bounds){

            switch(type){
                case "username":
                    drawCentered(usernameFont, batch, text, bounds);
                    break;
                case "question":
                    drawCentered(questionFont, batch, text, bounds);
                    break;
                case "choiceA":
                case "choiceB":
                case "choiceC":
                case "choiceD":
                    drawCentered(choicesFont, batch, text, bounds);
                    break;
                case "earnings":
                    drawCentered(earningsFont, batch, text, bounds);
                    break;
                case "usernameEarnings":
                    drawCentered(usernameEarningsFont, batch, text, bounds);
                    break;
                case "praise":
                    drawCentered(praiseFont, batch, text, bounds);
                    break;
                case "clueLabel":
                    drawCentered(clueLabelFont, batch, text, bounds);
                    break;
                case "instruction":
                    drawCentered(instructionFont, batch, text, bounds);
                    break;
            }
        }

        private void drawCentered(BitmapFont font, SpriteBatch spriteBatch, String text, Rectangle bounds) {
            glyphLayout.setText(font, text, font.getColor(), bounds.width, Align.center, true);
            font.draw(
                    spriteBatch,
                    text,
                    bounds.x,
                    bounds.y + bounds.height / 2f + glyphLayout.height / 2f,
                    bounds.width,
                    Align.center,
                    true);
        }

        public void setViewport(ExtendViewport viewport) {
            this.viewport = viewport;
        }

    }

    public class ResourcesFilePath {
        public InputStream theoreticalQuestionsInputStream;
        public InputStream programmingQuestionsInputStream;
        public final HashMap<String, Texture> image;

        public ResourcesFilePath(){
            theoreticalQuestionsInputStream = this.getClass().getResourceAsStream("/Resources/MCQ/TheoreticalQ/TheoreticalQ-Database.xlsx");
            programmingQuestionsInputStream = this.getClass().getResourceAsStream("/Resources/MCQ/ProgrammingQ/ProgrammingQ-Database.xlsx");

            image = new HashMap<>();

            image.put("ve_c++_question.png", new Texture(Gdx.files.internal("Resources/Images/ve_c++_question.png")));
            image.put("ve_c++_choiceA.png", new Texture(Gdx.files.internal("Resources/Images/ve_c++_choiceA.png")));
            image.put("ve_c++_choiceB.png", new Texture(Gdx.files.internal("Resources/Images/ve_c++_choiceB.png")));
            image.put("ve_c++_choiceC.png", new Texture(Gdx.files.internal("Resources/Images/ve_c++_choiceC.png")));
            image.put("ve_c++_choiceD.png", new Texture(Gdx.files.internal("Resources/Images/ve_c++_choiceD.png")));

            image.put("e_c++_question.png", new Texture(Gdx.files.internal("Resources/Images/e_c++_question.png")));
            image.put("e_c++_choiceA.png", new Texture(Gdx.files.internal("Resources/Images/e_c++_choiceA.png")));
            image.put("e_c++_choiceB.png", new Texture(Gdx.files.internal("Resources/Images/e_c++_choiceB.png")));
            image.put("e_c++_choiceC.png", new Texture(Gdx.files.internal("Resources/Images/e_c++_choiceC.png")));
            image.put("e_c++_choiceD.png", new Texture(Gdx.files.internal("Resources/Images/e_c++_choiceD.png")));

            image.put("m_c++_question.png", new Texture(Gdx.files.internal("Resources/Images/m_c++_question.png")));
            image.put("m_c++_choiceA.png", new Texture(Gdx.files.internal("Resources/Images/m_c++_choiceA.png")));
            image.put("m_c++_choiceB.png", new Texture(Gdx.files.internal("Resources/Images/m_c++_choiceB.png")));
            image.put("m_c++_choiceC.png", new Texture(Gdx.files.internal("Resources/Images/m_c++_choiceC.png")));
            image.put("m_c++_choiceD.png", new Texture(Gdx.files.internal("Resources/Images/m_c++_choiceD.png")));

        }
    }

    public class MainMenuAssets{
        public final TextureAtlas.AtlasRegion mainMenuBG;
        public final TextureAtlas.AtlasRegion playButton;
        public final TextureAtlas.AtlasRegion optionsButton;
        public final TextureAtlas.AtlasRegion howToPlayButton;
        public final TextureAtlas.AtlasRegion usernameTextfield;
        public final TextureAtlas.AtlasRegion playButtonBig;
        public final TextureAtlas.AtlasRegion optionsButtonBig;
        public final TextureAtlas.AtlasRegion howToPlayButtonBig;

        public MainMenuAssets(TextureAtlas atlas){
            mainMenuBG = atlas.findRegion(Constants.MAIN_MENU_BG);
            playButton = atlas.findRegion(Constants.PLAY_BUTTON);
            optionsButton = atlas.findRegion(Constants.OPTIONS_BUTTON);
            howToPlayButton = atlas.findRegion(Constants.HOWTOPLAY_BUTTON);
            usernameTextfield = atlas.findRegion(Constants.USERNAME_TEXTFIELD);
            playButtonBig = atlas.findRegion(Constants.PLAY_BUTTON_BIG);
            optionsButtonBig = atlas.findRegion(Constants.OPTIONS_BUTTON_BIG);
            howToPlayButtonBig = atlas.findRegion(Constants.HOWTOPLAY_BUTTON_BIG);
        }
    }

    public class HowToPlayScreenAssets{
        public final TextureAtlas.AtlasRegion instructionsBG;
        public final TextureAtlas.AtlasRegion lifelineBG;

        public HowToPlayScreenAssets(TextureAtlas atlas){
            instructionsBG = atlas.findRegion(Constants.INSTRUCTIONS_BG);
            lifelineBG = atlas.findRegion(Constants.LIFELINE_BG);
        }
    }

    public class ChooseColleagueScreenAssets{
        public final TextureAtlas.AtlasRegion colleagueBG;
        public final TextureAtlas.AtlasRegion clementColleague;
        public final TextureAtlas.AtlasRegion gennadyColleague;
        public final TextureAtlas.AtlasRegion michelleColleague;
        public final TextureAtlas.AtlasRegion mikhailaColleague;
        public final TextureAtlas.AtlasRegion nickColleague;

        public ChooseColleagueScreenAssets(TextureAtlas atlas){
            colleagueBG = atlas.findRegion(Constants.COLLEAGUE_BG);
            clementColleague = atlas.findRegion(Constants.CLEMENT_COLLEAGUE);
            gennadyColleague = atlas.findRegion(Constants.GENNADY_COLLEAGUE);
            michelleColleague = atlas.findRegion(Constants.MICHELLE_COLLEAGUE);
            mikhailaColleague = atlas.findRegion(Constants.MIKHAILA_COLLEAGUE);
            nickColleague = atlas.findRegion(Constants.NICK_COLLEAGUE);

        }
    }

    public class DifficultyScreenAssets{
        public final TextureAtlas.AtlasRegion difficultyBG;
        public final TextureAtlas.AtlasRegion theoreticalQuestionsLabel;
        public final TextureAtlas.AtlasRegion programmingQuestionsLabel;
        public final TextureAtlas.AtlasRegion veryEasyButton;
        public final TextureAtlas.AtlasRegion easyButton;
        public final TextureAtlas.AtlasRegion mediumButton;
        public final TextureAtlas.AtlasRegion hardButton;
        public final TextureAtlas.AtlasRegion veryHardButton;
        public final TextureAtlas.AtlasRegion mysteryQuestionButton;
        public final TextureAtlas.AtlasRegion veryEasyButtonBig;
        public final TextureAtlas.AtlasRegion easyButtonBig;
        public final TextureAtlas.AtlasRegion mediumButtonBig;
        public final TextureAtlas.AtlasRegion hardButtonBig;
        public final TextureAtlas.AtlasRegion veryHardButtonBig;


        public DifficultyScreenAssets(TextureAtlas atlas){
            difficultyBG = atlas.findRegion(Constants.DIFFICULTY_BG);
            theoreticalQuestionsLabel = atlas.findRegion(Constants.THEORETICALQUESTIONS_LABEL);
            programmingQuestionsLabel = atlas.findRegion(Constants.PROGRAMMINGQUESTIONS_LABEL);
            veryEasyButton = atlas.findRegion(Constants.VERYEASY_BUTTON);
            easyButton = atlas.findRegion(Constants.EASY_BUTTON);
            mediumButton = atlas.findRegion(Constants.MEDIUM_BUTTON);
            hardButton = atlas.findRegion(Constants.HARD_BUTTON);
            veryHardButton = atlas.findRegion(Constants.VERYHARD_BUTTON);
            mysteryQuestionButton = atlas.findRegion(Constants.MYSTERYQUESTION_BUTTON);
            veryEasyButtonBig = atlas.findRegion(Constants.VERYEASY_BUTTON_BIG);
            easyButtonBig = atlas.findRegion(Constants.EASY_BUTTON_BIG);
            mediumButtonBig = atlas.findRegion(Constants.MEDIUM_BUTTON_BIG);
            hardButtonBig = atlas.findRegion(Constants.HARD_BUTTON_BIG);
            veryHardButtonBig = atlas.findRegion(Constants.VERYHARD_BUTTON_BIG);
        }
    }

    public class GameplayScreenAssets{
        public final TextureAtlas.AtlasRegion fadeBG;
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion questionVeryEasy;
        public final TextureAtlas.AtlasRegion questionEasy;
        public final TextureAtlas.AtlasRegion questionMedium;
        public final TextureAtlas.AtlasRegion questionHard;
        public final TextureAtlas.AtlasRegion questionVeryHard;
        public final TextureAtlas.AtlasRegion answerBubbleButton;
        public final TextureAtlas.AtlasRegion askGoogleLifeline;
        public final TextureAtlas.AtlasRegion askClementLifeline;
        public final TextureAtlas.AtlasRegion askMikhailaLifeline;
        public final TextureAtlas.AtlasRegion askMichelleLifeline;
        public final TextureAtlas.AtlasRegion askNickLifeline;
        public final TextureAtlas.AtlasRegion askGennadyLifeline;
        public final TextureAtlas.AtlasRegion callAFamilyMemberLifeline;

        public final TextureAtlas.AtlasRegion askGoogleLifeline110;
        public final TextureAtlas.AtlasRegion askClementLifeline110;
        public final TextureAtlas.AtlasRegion callAFamilyMemberLifeline110;


        public GameplayScreenAssets(TextureAtlas atlas){
            fadeBG = atlas.findRegion(Constants.FADE_BG);
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            questionVeryEasy = atlas.findRegion(Constants.QUESTIONVERYEASY_BG);
            questionEasy = atlas.findRegion(Constants.QUESTIONEASY_BG);
            questionMedium = atlas.findRegion(Constants.QUESTIONMEDIUM_BG);
            questionHard = atlas.findRegion(Constants.QUESTIONHARD_BG);
            questionVeryHard = atlas.findRegion(Constants.QUESTIONVERYHARD_BG);
            answerBubbleButton = atlas.findRegion(Constants.ANSWERBUBBLE_BUTTON);
            askGoogleLifeline = atlas.findRegion(Constants.ASK_GOOGLE_LIFELINE);
            askClementLifeline = atlas.findRegion(Constants.ASK_CLEMENT_LIFELINE);
            askMikhailaLifeline = atlas.findRegion(Constants.ASK_MIKHAILA_LIFELINE);
            askMichelleLifeline = atlas.findRegion(Constants.ASK_MICHELLE_LIFELINE);
            askNickLifeline = atlas.findRegion(Constants.ASK_NICK_LIFELINE);
            askGennadyLifeline = atlas.findRegion(Constants.ASK_GENNADY_LIFELINE);
            callAFamilyMemberLifeline = atlas.findRegion(Constants.CALL_A_FAMILY_MEMBER);

            askGoogleLifeline110 = atlas.findRegion(Constants.ASK_GOOGLE_LIFELINE_110);
            askClementLifeline110 = atlas.findRegion(Constants.ASK_CLEMENT_LIFELINE_110);
            callAFamilyMemberLifeline110 = atlas.findRegion(Constants.CALL_A_FAMILY_MEMBER_110);
        }
    }

    public class GameOverScreenAssets{
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion gameOverBG;

        public GameOverScreenAssets(TextureAtlas atlas){
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            gameOverBG = atlas.findRegion(Constants.GAMEOVER_BG);
        }
    }

    public class CorrectAnswerScreenAssets{
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion correctAnswerBG;
        public final TextureAtlas.AtlasRegion continueButton;

        public CorrectAnswerScreenAssets(TextureAtlas atlas){
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            correctAnswerBG = atlas.findRegion(Constants.CORRECTANSWER_BG);
            continueButton = atlas.findRegion(Constants.CONTINUE_BUTTON);
        }
    }
}
