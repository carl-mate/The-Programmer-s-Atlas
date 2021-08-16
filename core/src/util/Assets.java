package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.game.JigsawScreen;
import com.mygdx.game.VictoryScreen;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.soap.Text;

import entity.ImportantFigure;
/**
 *  Singleton class that manages the Assets of the entire game.
 *  Game Assets are loaded from the texture atlas while the Question-related
 *  resources and sounds/music are loaded from the internal path. i.e. /android/assets
 */
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
    public ImportantFigureAssets importantFigureAssets;
    public VictoryScreenAssets victoryScreenAssets;
    public JigsawScreenAssets jigsawScreenAssets;
    public JigsawGuessResultScreenAssets jigsawGuessResultScreenAssets;
    public HighScoresScreenAssets highScoresScreenAssets;
    public MusicClass musicClass;
    public SoundClass soundClass;
    public OptionsScreenAssets optionsScreenAssets;

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
        importantFigureAssets = new ImportantFigureAssets();
        victoryScreenAssets = new VictoryScreenAssets(atlas);
        jigsawScreenAssets = new JigsawScreenAssets(atlas);
        highScoresScreenAssets = new HighScoresScreenAssets(atlas);
        jigsawGuessResultScreenAssets = new JigsawGuessResultScreenAssets(atlas);
        optionsScreenAssets = new OptionsScreenAssets(atlas);
        musicClass = new MusicClass();
        soundClass = new SoundClass();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    /**
     *  Dispose unused assets
     */
    @Override
    public void dispose() {
        assetManager.dispose();
    }

    /**
     *  Initializes the question databases and its resources
     */
    public void initResourcesFilePath(){
        resourcesFilePath = new ResourcesFilePath();
    }

    public class MusicClass{
        public final Music theme;
        public final Music gameplayMusic;
        public MusicClass(){
            theme = Gdx.audio.newMusic(Gdx.files.internal("music/game_theme.mp3"));
            gameplayMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameplay_music.mp3"));
        }
    }

    public class SoundClass{
        public final Sound buttonHoverSound;
        public final Sound buttonHoverOneSound;
        public final Sound buttonHoverTwoSound;
        public final Sound buttonClickSound;
        public final Sound correctAnswerSound;
        public final Sound victorySound;
        public final Sound gameOverSound;
        public final Sound coinsSound;
        public final Sound clappingSound;
        public final Sound greetingsSound;
        public final Sound chooseColleagueSound;
        public final Sound pickAQuestionSound;
        public final Sound enterUsernameSound;
        public final Sound googleHoverSound;
        public final Sound colleagueHoverSound;
        public final Sound familyMemberHoverSound;
        public final Sound lifelineClickedSound;

        public SoundClass(){
            buttonHoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_hover.mp3"));
            buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click.mp3"));
            buttonHoverOneSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_hover_1.mp3"));
            buttonHoverTwoSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_hover_2.mp3"));
            correctAnswerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/correctanswer.mp3"));
            victorySound = Gdx.audio.newSound(Gdx.files.internal("sounds/victory-sound-effect.mp3"));
            gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover_sound.mp3"));
            coinsSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coins_sound.mp3"));
            clappingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/audience_clapping.mp3"));
            greetingsSound = Gdx.audio.newSound(Gdx.files.internal("sounds/greetings_sound.mp3"));
            chooseColleagueSound = Gdx.audio.newSound(Gdx.files.internal("sounds/choosecolleague_sound.mp3"));
            pickAQuestionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickaquestion_sound.mp3"));
            enterUsernameSound = Gdx.audio.newSound(Gdx.files.internal("sounds/enterusername_sound.mp3"));
            googleHoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/google_hover.wav"));
            colleagueHoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/askcolleague_sound.mp3"));
            familyMemberHoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/callfamilymember_sound.mp3"));
            lifelineClickedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lifelineclicked.mp3"));
        }
    }

    public class OptionsScreenAssets{
        public final TextureAtlas.AtlasRegion musicOn;
        public final TextureAtlas.AtlasRegion musicOff;
        public final TextureAtlas.AtlasRegion normalBG;

        public OptionsScreenAssets(TextureAtlas atlas){
            musicOn = atlas.findRegion(Constants.MUSIC_ON);
            musicOff = atlas.findRegion(Constants.MUSIC_OFF);
            normalBG = atlas.findRegion(Constants.NORMAL_BG);

        }
    }

    public class ImportantFigureAssets{
        public ArrayList<ImportantFigure> importantFigureArrayList;
        public Texture alanTuringImage;
        public Texture dennisRitchieImage;
        public Texture johnVonNeumannImage;
        public Texture kenThompsonImage;
        public Texture alonzoChurchImage;
        public Texture bertrandRusselImage;
        public Texture christopherAlexanderImage;
        public Texture giuseppePeanoImage;
        public Texture gottlobFregeImage;
        public Texture haskellCurryImage;
        public Texture ivanSutherlandImage;
        public Texture jacquesHerbrandImage;
        public Texture kurtGodelImage;
        public Texture niklausWirthImage;
        public Texture robertKowalskiImage;

        public Texture alanTuringBiography;
        public Texture dennisRitchieBiography;
        public Texture johnVonNeumannBiography;
        public Texture kenThompsonBiography;
        public Texture alonzoChurchBiography;
        public Texture bertrandRusselBiography;
        public Texture christopherAlexanderBiography;
        public Texture giuseppePeanoBiography;
        public Texture gottlobFregeBiography;
        public Texture haskellCurryBiography;
        public Texture ivanSutherlandBiography;
        public Texture jacquesHerbrandBiography;
        public Texture kurtGodelBiography;
        public Texture niklausWirthBiography;
        public Texture robertKowalskiBiography;

        public String alanTuringName;
        public String dennisRitchieName;
        public String johnVonNeumannName;
        public String kenThompsonName;
        public String alonzoChurchName;
        public String bertrandRusselName;
        public String christopherAlexanderName;
        public String giuseppePeanoName;
        public String gottlobFregeName;
        public String haskellCurryName;
        public String ivanSutherlandName;
        public String jacquesHerbrandName;
        public String kurtGodelName;
        public String niklausWirthName;
        public String robertKowalskiName;


        public ImportantFigureAssets(){
            importantFigureArrayList = new ArrayList<>();

            alanTuringImage = new Texture(Gdx.files.internal("important-figures/alanturing.jpg"));
            dennisRitchieImage = new Texture(Gdx.files.internal("important-figures/dennis_ritchie.jpg"));
            johnVonNeumannImage = new Texture(Gdx.files.internal("important-figures/john_von_neumann.png"));
            kenThompsonImage = new Texture(Gdx.files.internal("important-figures/ken_thompson.jpg"));
            alonzoChurchImage = new Texture(Gdx.files.internal("important-figures/alonzo_church.jpg"));
            bertrandRusselImage = new Texture(Gdx.files.internal("important-figures/bertrand_russell.jpg"));
            christopherAlexanderImage = new Texture(Gdx.files.internal("important-figures/christopher_alexander.jpg"));
            giuseppePeanoImage = new Texture(Gdx.files.internal("important-figures/giuseppe_peano.jpg"));
            gottlobFregeImage = new Texture(Gdx.files.internal("important-figures/gottlob_frege.jpg"));
            haskellCurryImage = new Texture(Gdx.files.internal("important-figures/haskell_curry.jpg"));
            ivanSutherlandImage = new Texture(Gdx.files.internal("important-figures/ivan_sutherland.jpg"));
            jacquesHerbrandImage = new Texture(Gdx.files.internal("important-figures/jacques_herbrand.jpg"));
            kurtGodelImage = new Texture(Gdx.files.internal("important-figures/kurt_godel.jpg"));
            niklausWirthImage = new Texture(Gdx.files.internal("important-figures/niklaus_wirth.jpg"));
            robertKowalskiImage = new Texture(Gdx.files.internal("important-figures/robert_kowalski.jpg"));

            alanTuringBiography = new Texture(Gdx.files.internal("important-figures/alan_turing-bio.png"));
            dennisRitchieBiography = new Texture(Gdx.files.internal("important-figures/dennis_ritchie-bio.png"));
            johnVonNeumannBiography = new Texture(Gdx.files.internal("important-figures/john_von_neumann-bio.png"));
            kenThompsonBiography = new Texture(Gdx.files.internal("important-figures/ken_thompson-bio.png"));
            alonzoChurchBiography = new Texture(Gdx.files.internal("important-figures/alonzo_church-bio.png"));
            bertrandRusselBiography = new Texture(Gdx.files.internal("important-figures/bertrand_russell-bio.png"));
            christopherAlexanderBiography = new Texture(Gdx.files.internal("important-figures/christopher_alexander-bio.png"));
            giuseppePeanoBiography = new Texture(Gdx.files.internal("important-figures/giuseppe_peano-bio.png"));
            gottlobFregeBiography = new Texture(Gdx.files.internal("important-figures/gottlob_frege-bio.png"));
            haskellCurryBiography = new Texture(Gdx.files.internal("important-figures/haskell_curry-bio.png"));
            ivanSutherlandBiography = new Texture(Gdx.files.internal("important-figures/ivan_sutherland-bio.png"));
            jacquesHerbrandBiography = new Texture(Gdx.files.internal("important-figures/jacques_herbrand-bio.png"));
            kurtGodelBiography = new Texture(Gdx.files.internal("important-figures/kurt_godel-bio.png"));
            niklausWirthBiography = new Texture(Gdx.files.internal("important-figures/niklaus_wirth-bio.png"));
            robertKowalskiBiography = new Texture(Gdx.files.internal("important-figures/robert_kowalski-bio.png"));

            alanTuringName = "ALAN TURING";
            dennisRitchieName = "DENNIS RITCHIE";
            johnVonNeumannName = "JOHN VON NEUMANN";
            kenThompsonName = "KEN THOMPSON";
            alonzoChurchName = "ALONZO CHURCH";
            bertrandRusselName = "BERTRAND RUSSEL";
            christopherAlexanderName = "CHRISTOPHER ALEXANDER";
            giuseppePeanoName = "GIUSEPPE PEANO";
            gottlobFregeName = "GOTTLOB FREGE";
            haskellCurryName = "HASKELL CURRY";
            ivanSutherlandName = "IVAN SUTHERLAND";
            jacquesHerbrandName = "JACQUES HERBRAND";
            kurtGodelName = "KURT GODEL";
            niklausWirthName = "NIKLAUS WIRTH";
            robertKowalskiName = "ROBERT KOWALSKI";

            importantFigureArrayList.add(new ImportantFigure(alanTuringImage, alanTuringBiography, alanTuringName));
            importantFigureArrayList.add(new ImportantFigure(dennisRitchieImage, dennisRitchieBiography,dennisRitchieName));
            importantFigureArrayList.add(new ImportantFigure(johnVonNeumannImage, johnVonNeumannBiography, johnVonNeumannName));
            importantFigureArrayList.add(new ImportantFigure(kenThompsonImage, kenThompsonBiography, kenThompsonName));
            importantFigureArrayList.add(new ImportantFigure(alonzoChurchImage, alonzoChurchBiography, alonzoChurchName));
            importantFigureArrayList.add(new ImportantFigure(bertrandRusselImage, bertrandRusselBiography, bertrandRusselName));
            importantFigureArrayList.add(new ImportantFigure(christopherAlexanderImage, christopherAlexanderBiography, christopherAlexanderName));
            importantFigureArrayList.add(new ImportantFigure(giuseppePeanoImage, giuseppePeanoBiography, giuseppePeanoName));
            importantFigureArrayList.add(new ImportantFigure(gottlobFregeImage, gottlobFregeBiography, gottlobFregeName));
            importantFigureArrayList.add(new ImportantFigure(haskellCurryImage, haskellCurryBiography, haskellCurryName));
            importantFigureArrayList.add(new ImportantFigure(jacquesHerbrandImage, jacquesHerbrandBiography, jacquesHerbrandName));
            importantFigureArrayList.add(new ImportantFigure(kurtGodelImage, kurtGodelBiography,kurtGodelName));
            importantFigureArrayList.add(new ImportantFigure(niklausWirthImage, niklausWirthBiography, niklausWirthName));
            importantFigureArrayList.add(new ImportantFigure(robertKowalskiImage, robertKowalskiBiography, robertKowalskiName));
        }
    }

    public class Font{
        private final String TAG = Font.class.getName();

        private ExtendViewport viewport;
        public FreeTypeFontGenerator sourceCodeProBoldFontGenerator;
        public FreeTypeFontGenerator sourceCodeProFontGenerator;
        public final FreeTypeFontGenerator.FreeTypeFontParameter questionFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter choicesFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter usernameFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter praiseFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter earningsFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter usernameEarningsFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter clueLabelFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter instructionFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter topicFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter importantFigureNameClueFontParameter;
        public final FreeTypeFontGenerator.FreeTypeFontParameter highScoreFontParameter;

        public BitmapFont questionFont;
        public BitmapFont choicesFont;
        public BitmapFont praiseFont;
        public BitmapFont earningsFont;
        public BitmapFont usernameFont;
        public BitmapFont usernameEarningsFont;
        public BitmapFont clueLabelFont;
        public BitmapFont instructionFont;
        public BitmapFont topicFont;
        public BitmapFont importantFigureNameClueFont;
        public BitmapFont highScoreFont;

        public GlyphLayout glyphLayout;

        public Font(){
            sourceCodeProBoldFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("SourceCodeProBold.ttf"));
            sourceCodeProFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("SourceCodePro.ttf"));

            //font parameters
            usernameFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            usernameFontParameter.size = 20;
            usernameFontParameter.color = Color.BLACK;
            usernameFont = sourceCodeProBoldFontGenerator.generateFont(usernameFontParameter);

            questionFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            questionFontParameter.size = 15;
            questionFontParameter.color = Color.BLACK;
            questionFont = sourceCodeProFontGenerator.generateFont(questionFontParameter);

            choicesFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            choicesFontParameter.size = 12;
            choicesFontParameter.color = Color.BLACK;
            choicesFont = sourceCodeProFontGenerator.generateFont(choicesFontParameter);

            usernameEarningsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            usernameEarningsFontParameter .size = 20;
            usernameEarningsFontParameter .color = Color.BLACK;
            usernameEarningsFont = sourceCodeProBoldFontGenerator.generateFont(usernameEarningsFontParameter);

            praiseFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            praiseFontParameter .size = 30;
            praiseFontParameter .color = Color.BLACK;
            praiseFont = sourceCodeProBoldFontGenerator.generateFont(praiseFontParameter);

            earningsFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            earningsFontParameter.size = 30;
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

            topicFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            topicFontParameter.size = 11;
            topicFontParameter.color = Color.WHITE;
            topicFont = sourceCodeProBoldFontGenerator.generateFont(topicFontParameter);

            importantFigureNameClueFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            importantFigureNameClueFontParameter.size = 50;
            importantFigureNameClueFontParameter.color = Color.BLACK;
            importantFigureNameClueFont = sourceCodeProBoldFontGenerator.generateFont(importantFigureNameClueFontParameter);

            highScoreFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            highScoreFontParameter.size = 20;
            highScoreFontParameter.color = Color.BLACK;
            highScoreFont = sourceCodeProBoldFontGenerator.generateFont(highScoreFontParameter);


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
                case "topic":
                    drawLeftAligned(topicFont, batch, text, bounds);
                    break;
                case "importantFigureNameClue":
                    drawCentered(importantFigureNameClueFont, batch, text, bounds);
                    break;
                case "highScoreFont":
                    drawLeftAligned(highScoreFont, batch, text, bounds);
                    break;
            }
        }

        private void drawLeftAligned(BitmapFont font, SpriteBatch spriteBatch, String text, Rectangle bounds) {
            glyphLayout.setText(font, text, font.getColor(), bounds.width, Align.left, true);
            font.draw(
                    spriteBatch,
                    text,
                    bounds.x,
                    bounds.y + bounds.height / 2f + glyphLayout.height / 2f,
                    bounds.width,
                    Align.left,
                    true);
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
            //C FILES
            //easy
            image.put("e_c_question1.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_question1.png")));
            image.put("e_c_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q1A.png")));
            image.put("e_c_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q1B.png")));
            image.put("e_c_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q1C.png")));
            image.put("e_c_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q1D.png")));

            image.put("e_c_question2.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_question2.png")));
            image.put("e_c_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q2A.png")));
            image.put("e_c_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q2B.png")));
            image.put("e_c_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q2C.png")));
            image.put("e_c_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q2D.png")));

            image.put("e_c_question3.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_question3.png")));
            image.put("e_c_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q3A.png")));
            image.put("e_c_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q3B.png")));
            image.put("e_c_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q3C.png")));
            image.put("e_c_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q3D.png")));

            image.put("e_c_question4.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_question4.png")));
            image.put("e_c_q4A.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q4A.png")));
            image.put("e_c_q4B.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q4B.png")));
            image.put("e_c_q4C.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q4C.png")));
            image.put("e_c_q4D.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q4D.png")));

            image.put("e_c_question5.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_question5.png")));
            image.put("e_c_q5A.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5A.png")));
            image.put("e_c_q5B.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5B.png")));
            image.put("e_c_q5C.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5C.png")));
            image.put("e_c_q5D.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5D.png")));

            //hard
            image.put("h_c_question1.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_question1.png")));
            image.put("h_c_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q1A.png")));
            image.put("h_c_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q1B.png")));
            image.put("h_c_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q1C.png")));
            image.put("h_c_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q1D.png")));

            image.put("h_c_question2.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_question2.png")));
            image.put("h_c_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q2A.png")));
            image.put("h_c_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q2B.png")));
            image.put("h_c_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q2C.png")));
            image.put("h_c_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q2D.png")));

            image.put("h_c_question3.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_question3.png")));
            image.put("h_c_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q3A.png")));
            image.put("h_c_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q3B.png")));
            image.put("h_c_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q3C.png")));
            image.put("h_c_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q3D.png")));

            image.put("h_c_question4.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_question4.png")));
            image.put("h_c_q4A.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q4A.png")));
            image.put("h_c_q4B.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q4B.png")));
            image.put("h_c_q4C.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q4C.png")));
            image.put("h_c_q4D.png", new Texture(Gdx.files.internal("Resources/Images/C/h_c_q4D.png")));

            image.put("h_c_question5.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_question5.png")));
            image.put("h_c_q5A.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5A.png")));
            image.put("h_c_q5B.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5B.png")));
            image.put("h_c_q5C.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5C.png")));
            image.put("h_c_q5D.png", new Texture(Gdx.files.internal("Resources/Images/C/e_c_q5D.png")));

            //medium
            image.put("m_c_question1.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_question1.png")));
            image.put("m_c_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q1A.png")));
            image.put("m_c_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q1B.png")));
            image.put("m_c_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q1C.png")));
            image.put("m_c_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q1D.png")));

            image.put("m_c_question2.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_question2.png")));
            image.put("m_c_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q2A.png")));
            image.put("m_c_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q2B.png")));
            image.put("m_c_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q2C.png")));
            image.put("m_c_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q2D.png")));

            image.put("m_c_question3.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_question3.png")));
            image.put("m_c_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q3A.png")));
            image.put("m_c_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q3B.png")));
            image.put("m_c_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q3C.png")));
            image.put("m_c_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q3D.png")));

            image.put("m_c_question4.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_question4.png")));
            image.put("m_c_q4A.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q4A.png")));
            image.put("m_c_q4B.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q4B.png")));
            image.put("m_c_q4C.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q4C.png")));
            image.put("m_c_q4D.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q4D.png")));

            image.put("m_c_question5.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_question5.png")));
            image.put("m_c_q5A.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q5A.png")));
            image.put("m_c_q5B.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q5B.png")));
            image.put("m_c_q5C.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q5C.png")));
            image.put("m_c_q5D.png", new Texture(Gdx.files.internal("Resources/Images/C/m_c_q5D.png")));

            //very easy
            image.put("ve_c_question1.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_question1.png")));
            image.put("ve_c_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q1A.png")));
            image.put("ve_c_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q1B.png")));
            image.put("ve_c_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q1C.png")));
            image.put("ve_c_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q1D.png")));

            image.put("ve_c_question2.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_question2.png")));
            image.put("ve_c_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q2A.png")));
            image.put("ve_c_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q2B.png")));
            image.put("ve_c_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q2C.png")));
            image.put("ve_c_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q2D.png")));

            image.put("ve_c_question3.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_question3.png")));
            image.put("ve_c_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q3A.png")));
            image.put("ve_c_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q3B.png")));
            image.put("ve_c_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q3C.png")));
            image.put("ve_c_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q3D.png")));

            image.put("ve_c_question4.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_question4.png")));
            image.put("ve_c_q4A.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q4A.png")));
            image.put("ve_c_q4B.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q4B.png")));
            image.put("ve_c_q4C.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q4C.png")));
            image.put("ve_c_q4D.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q4D.png")));

            image.put("ve_c_question5.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_question5.png")));
            image.put("ve_c_q5A.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q5A.png")));
            image.put("ve_c_q5B.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q5B.png")));
            image.put("ve_c_q5C.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q5C.png")));
            image.put("ve_c_q5D.png", new Texture(Gdx.files.internal("Resources/Images/C/ve_c_q5D.png")));

            //very hard
            image.put("vh_c_question1.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_question1.png")));
            image.put("vh_c_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q1A.png")));
            image.put("vh_c_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q1B.png")));
            image.put("vh_c_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q1C.png")));
            image.put("vh_c_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q1D.png")));

            image.put("vh_c_question2.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_question2.png")));
            image.put("vh_c_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q2A.png")));
            image.put("vh_c_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q2B.png")));
            image.put("vh_c_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q2C.png")));
            image.put("vh_c_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q2D.png")));

            image.put("vh_c_question3.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_question3.png")));
            image.put("vh_c_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q3A.png")));
            image.put("vh_c_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q3B.png")));
            image.put("vh_c_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q3C.png")));
            image.put("vh_c_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q3D.png")));

            image.put("vh_c_question4.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_question4.png")));
            image.put("vh_c_q4A.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q4A.png")));
            image.put("vh_c_q4B.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q4B.png")));
            image.put("vh_c_q4C.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q4C.png")));
            image.put("vh_c_q4D.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q4D.png")));

            image.put("vh_c_question5.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_question5.png")));
            image.put("vh_c_q5A.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q5A.png")));
            image.put("vh_c_q5B.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q5B.png")));
            image.put("vh_c_q5C.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q5C.png")));
            image.put("vh_c_q5D.png", new Texture(Gdx.files.internal("Resources/Images/C/vh_c_q5D.png")));

            //C++ files
            //easy
            image.put("e_c++_question1.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_question1.png")));
            image.put("e_c++_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q1A.png")));
            image.put("e_c++_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q1B.png")));
            image.put("e_c++_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q1C.png")));
            image.put("e_c++_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q1D.png")));

            image.put("e_c++_question2.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_question2.png")));
            image.put("e_c++_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q2A.png")));
            image.put("e_c++_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q2B.png")));
            image.put("e_c++_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q2C.png")));
            image.put("e_c++_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q2D.png")));

            image.put("e_c++_question3.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_question3.png")));
            image.put("e_c++_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q3A.png")));
            image.put("e_c++_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q3B.png")));
            image.put("e_c++_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q3C.png")));
            image.put("e_c++_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C++/e_c++_q3D.png")));

            //hard
            image.put("h_c++_question1.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_question1.png")));
            image.put("h_c++_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q1A.png")));
            image.put("h_c++_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q1B.png")));
            image.put("h_c++_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q1C.png")));
            image.put("h_c++_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q1D.png")));

            image.put("h_c++_question2.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_question2.png")));
            image.put("h_c++_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q2A.png")));
            image.put("h_c++_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q2B.png")));
            image.put("h_c++_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q2C.png")));
            image.put("h_c++_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q2D.png")));

            image.put("h_c++_question3.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_question3.png")));
            image.put("h_c++_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q3A.png")));
            image.put("h_c++_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q3B.png")));
            image.put("h_c++_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q3C.png")));
            image.put("h_c++_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C++/h_c++_q3D.png")));

            //medium
            image.put("m_c++_question1.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_question1.png")));
            image.put("m_c++_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q1A.png")));
            image.put("m_c++_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q1B.png")));
            image.put("m_c++_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q1C.png")));
            image.put("m_c++_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q1D.png")));

            image.put("m_c++_question2.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_question2.png")));
            image.put("m_c++_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q2A.png")));
            image.put("m_c++_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q2B.png")));
            image.put("m_c++_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q2C.png")));
            image.put("m_c++_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q2D.png")));

            image.put("m_c++_question3.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_question3.png")));
            image.put("m_c++_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q3A.png")));
            image.put("m_c++_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q3B.png")));
            image.put("m_c++_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q3C.png")));
            image.put("m_c++_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C++/m_c++_q3D.png")));

            //very easy
            image.put("ve_c++_question1.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_question1.png")));
            image.put("ve_c++_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q1A.png")));
            image.put("ve_c++_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q1B.png")));
            image.put("ve_c++_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q1C.png")));
            image.put("ve_c++_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q1D.png")));

            image.put("ve_c++_question2.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_question2.png")));
            image.put("ve_c++_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q2A.png")));
            image.put("ve_c++_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q2B.png")));
            image.put("ve_c++_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q2C.png")));
            image.put("ve_c++_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q2D.png")));

            image.put("ve_c++_question3.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_question3.png")));
            image.put("ve_c++_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q3A.png")));
            image.put("ve_c++_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q3B.png")));
            image.put("ve_c++_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q3C.png")));
            image.put("ve_c++_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C++/ve_c++_q3D.png")));

            //very hard
            image.put("vh_c++_question1.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_question1.png")));
            image.put("vh_c++_q1A.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q1A.png")));
            image.put("vh_c++_q1B.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q1B.png")));
            image.put("vh_c++_q1C.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q1C.png")));
            image.put("vh_c++_q1D.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q1D.png")));

            image.put("vh_c++_question2.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_question2.png")));
            image.put("vh_c++_q2A.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q2A.png")));
            image.put("vh_c++_q2B.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q2B.png")));
            image.put("vh_c++_q2C.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q2C.png")));
            image.put("vh_c++_q2D.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q2D.png")));

            image.put("vh_c++_question3.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_question3.png")));
            image.put("vh_c++_q3A.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q3A.png")));
            image.put("vh_c++_q3B.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q3B.png")));
            image.put("vh_c++_q3C.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q3C.png")));
            image.put("vh_c++_q3D.png", new Texture(Gdx.files.internal("Resources/Images/C++/vh_c++_q3D.png")));

            //Java files
            //easy
            image.put("e_java_question1.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_question1.png")));
            image.put("e_java_q1A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q1A.png")));
            image.put("e_java_q1B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q1B.png")));
            image.put("e_java_q1C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q1C.png")));
            image.put("e_java_q1D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q1D.png")));

            image.put("e_java_question2.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_question2.png")));
            image.put("e_java_q2A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q2A.png")));
            image.put("e_java_q2B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q2B.png")));
            image.put("e_java_q2C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q2C.png")));
            image.put("e_java_q2D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q2D.png")));

            image.put("e_java_question3.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_question3.png")));
            image.put("e_java_q3A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q3A.png")));
            image.put("e_java_q3B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q3B.png")));
            image.put("e_java_q3C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q3C.png")));
            image.put("e_java_q3D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q3D.png")));

            image.put("e_java_question4.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_question4.png")));
            image.put("e_java_q4A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q4A.png")));
            image.put("e_java_q4B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q4B.png")));
            image.put("e_java_q4C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q4C.png")));
            image.put("e_java_q4D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q4D.png")));

            image.put("e_java_question5.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_question5.png")));
            image.put("e_java_q5A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q5A.png")));
            image.put("e_java_q5B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q5B.png")));
            image.put("e_java_q5C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q5C.png")));
            image.put("e_java_q5D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/e_java_q5D.png")));

            //hard
            image.put("h_java_question1.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_question1.png")));
            image.put("h_java_q1A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q1A.png")));
            image.put("h_java_q1B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q1B.png")));
            image.put("h_java_q1C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q1C.png")));
            image.put("h_java_q1D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q1D.png")));

            image.put("h_java_question2.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_question2.png")));
            image.put("h_java_q2A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q2A.png")));
            image.put("h_java_q2B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q2B.png")));
            image.put("h_java_q2C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q2C.png")));
            image.put("h_java_q2D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q2D.png")));

            image.put("h_java_question3.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_question3.png")));
            image.put("h_java_q3A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q3A.png")));
            image.put("h_java_q3B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q3B.png")));
            image.put("h_java_q3C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q3C.png")));
            image.put("h_java_q3D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q3D.png")));

            image.put("h_java_question4.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_question4.png")));
            image.put("h_java_q4A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q4A.png")));
            image.put("h_java_q4B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q4B.png")));
            image.put("h_java_q4C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q4C.png")));
            image.put("h_java_q4D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q4D.png")));

            image.put("h_java_question5.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_question5.png")));
            image.put("h_java_q5A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q5A.png")));
            image.put("h_java_q5B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q5B.png")));
            image.put("h_java_q5C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q5C.png")));
            image.put("h_java_q5D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/h_java_q5D.png")));

            //medium
            image.put("m_java_question1.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_question1.png")));
            image.put("m_java_q1A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q1A.png")));
            image.put("m_java_q1B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q1B.png")));
            image.put("m_java_q1C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q1C.png")));
            image.put("m_java_q1D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q1D.png")));

            image.put("m_java_question2.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_question2.png")));
            image.put("m_java_q2A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q2A.png")));
            image.put("m_java_q2B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q2B.png")));
            image.put("m_java_q2C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q2C.png")));
            image.put("m_java_q2D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q2D.png")));

            image.put("m_java_question3.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_question3.png")));
            image.put("m_java_q3A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q3A.png")));
            image.put("m_java_q3B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q3B.png")));
            image.put("m_java_q3C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q3C.png")));
            image.put("m_java_q3D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q3D.png")));

            image.put("m_java_question4.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_question4.png")));
            image.put("m_java_q4A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q4A.png")));
            image.put("m_java_q4B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q4B.png")));
            image.put("m_java_q4C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q4C.png")));
            image.put("m_java_q4D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q4D.png")));

            image.put("m_java_question5.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_question5.png")));
            image.put("m_java_q5A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q5A.png")));
            image.put("m_java_q5B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q5B.png")));
            image.put("m_java_q5C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q5C.png")));
            image.put("m_java_q5D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/m_java_q5D.png")));

            //very easy
            image.put("ve_java_question1.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_question1.png")));
            image.put("ve_java_q1A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q1A.png")));
            image.put("ve_java_q1B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q1B.png")));
            image.put("ve_java_q1C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q1C.png")));
            image.put("ve_java_q1D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q1D.png")));

            image.put("ve_java_question2.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_question2.png")));
            image.put("ve_java_q2A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q2A.png")));
            image.put("ve_java_q2B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q2B.png")));
            image.put("ve_java_q2C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q2C.png")));
            image.put("ve_java_q2D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q2D.png")));

            image.put("ve_java_question3.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_question3.png")));
            image.put("ve_java_q3A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q3A.png")));
            image.put("ve_java_q3B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q3B.png")));
            image.put("ve_java_q3C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q3C.png")));
            image.put("ve_java_q3D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q3D.png")));

            image.put("ve_java_question4.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_question4.png")));
            image.put("ve_java_q4A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q4A.png")));
            image.put("ve_java_q4B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q4B.png")));
            image.put("ve_java_q4C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q4C.png")));
            image.put("ve_java_q4D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q4D.png")));

            image.put("ve_java_question5.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_question5.png")));
            image.put("ve_java_q5A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q5A.png")));
            image.put("ve_java_q5B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q5B.png")));
            image.put("ve_java_q5C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q5C.png")));
            image.put("ve_java_q5D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/ve_java_q5D.png")));

            //very hard
            image.put("vh_java_question1.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_question1.png")));
            image.put("vh_java_q1A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q1A.png")));
            image.put("vh_java_q1B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q1B.png")));
            image.put("vh_java_q1C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q1C.png")));
            image.put("vh_java_q1D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q1D.png")));

            image.put("vh_java_question2.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_question2.png")));
            image.put("vh_java_q2A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q2A.png")));
            image.put("vh_java_q2B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q2B.png")));
            image.put("vh_java_q2C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q2C.png")));
            image.put("vh_java_q2D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q2D.png")));

            image.put("vh_java_question3.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_question3.png")));
            image.put("vh_java_q3A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q3A.png")));
            image.put("vh_java_q3B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q3B.png")));
            image.put("vh_java_q3C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q3C.png")));
            image.put("vh_java_q3D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q3D.png")));

            image.put("vh_java_question4.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_question4.png")));
            image.put("vh_java_q4A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q4A.png")));
            image.put("vh_java_q4B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q4B.png")));
            image.put("vh_java_q4C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q4C.png")));
            image.put("vh_java_q4D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q4D.png")));

            image.put("vh_java_question5.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_question5.png")));
            image.put("vh_java_q5A.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q5A.png")));
            image.put("vh_java_q5B.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q5B.png")));
            image.put("vh_java_q5C.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q5C.png")));
            image.put("vh_java_q5D.png", new Texture(Gdx.files.internal("Resources/Images/JAVA/vh_java_q5D.png")));

            //Python files
            //easy
            image.put("e_python_question1.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_question1.png")));
            image.put("e_python_q1A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q1A.png")));
            image.put("e_python_q1B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q1B.png")));
            image.put("e_python_q1C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q1C.png")));
            image.put("e_python_q1D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q1D.png")));

            image.put("e_python_question2.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_question2.png")));
            image.put("e_python_q2A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q2A.png")));
            image.put("e_python_q2B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q2B.png")));
            image.put("e_python_q2C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q2C.png")));
            image.put("e_python_q2D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/e_python_q2D.png")));

            //hard
            image.put("h_python_question1.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_question1.png")));
            image.put("h_python_q1A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q1A.png")));
            image.put("h_python_q1B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q1B.png")));
            image.put("h_python_q1C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q1C.png")));
            image.put("h_python_q1D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q1D.png")));

            image.put("h_python_question2.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_question2.png")));
            image.put("h_python_q2A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q2A.png")));
            image.put("h_python_q2B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q2B.png")));
            image.put("h_python_q2C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q2C.png")));
            image.put("h_python_q2D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/h_python_q2D.png")));

            //medium
            image.put("m_python_question1.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_question1.png")));
            image.put("m_python_q1A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q1A.png")));
            image.put("m_python_q1B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q1B.png")));
            image.put("m_python_q1C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q1C.png")));
            image.put("m_python_q1D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q1D.png")));

            image.put("m_python_question2.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_question2.png")));
            image.put("m_python_q2A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q2A.png")));
            image.put("m_python_q2B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q2B.png")));
            image.put("m_python_q2C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q2C.png")));
            image.put("m_python_q2D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/m_python_q2D.png")));

            //very easy
            image.put("ve_python_question1.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_question1.png")));
            image.put("ve_python_q1A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q1A.png")));
            image.put("ve_python_q1B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q1B.png")));
            image.put("ve_python_q1C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q1C.png")));
            image.put("ve_python_q1D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q1D.png")));

            image.put("ve_python_question2.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_question2.png")));
            image.put("ve_python_q2A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q2A.png")));
            image.put("ve_python_q2B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q2B.png")));
            image.put("ve_python_q2C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q2C.png")));
            image.put("ve_python_q2D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/ve_python_q2D.png")));

            //very hard
            image.put("vh_python_question1.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_question1.png")));
            image.put("vh_python_q1A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q1A.png")));
            image.put("vh_python_q1B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q1B.png")));
            image.put("vh_python_q1C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q1C.png")));
            image.put("vh_python_q1D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q1D.png")));

            image.put("vh_python_question2.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_question2.png")));
            image.put("vh_python_q2A.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q2A.png")));
            image.put("vh_python_q2B.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q2B.png")));
            image.put("vh_python_q2C.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q2C.png")));
            image.put("vh_python_q2D.png", new Texture(Gdx.files.internal("Resources/Images/PYTHON/vh_python_q2D.png")));

        }
    }

    public class MainMenuAssets{
        public final TextureAtlas.AtlasRegion mainMenuBG;
        public final TextureAtlas.AtlasRegion playButton;
        public final TextureAtlas.AtlasRegion optionsButton;
        public final TextureAtlas.AtlasRegion howToPlayButton;
        public final TextureAtlas.AtlasRegion usernameTextfield;
        public final TextureAtlas.AtlasRegion usernameTextfieldConfirm;
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
            usernameTextfieldConfirm = atlas.findRegion(Constants.USERNAME_TEXTFIELD_CONFIRM);
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

        public final TextureAtlas.AtlasRegion clementColleagueBig;
        public final TextureAtlas.AtlasRegion gennadyColleagueBig;
        public final TextureAtlas.AtlasRegion michelleColleagueBig;
        public final TextureAtlas.AtlasRegion mikhailaColleagueBig;
        public final TextureAtlas.AtlasRegion nickColleagueBig;

        public ChooseColleagueScreenAssets(TextureAtlas atlas){
            colleagueBG = atlas.findRegion(Constants.COLLEAGUE_BG);
            clementColleague = atlas.findRegion(Constants.CLEMENT_COLLEAGUE);
            gennadyColleague = atlas.findRegion(Constants.GENNADY_COLLEAGUE);
            michelleColleague = atlas.findRegion(Constants.MICHELLE_COLLEAGUE);
            mikhailaColleague = atlas.findRegion(Constants.MIKHAILA_COLLEAGUE);
            nickColleague = atlas.findRegion(Constants.NICK_COLLEAGUE);

            clementColleagueBig = atlas.findRegion(Constants.CLEMENT_COLLEAGUE_BIG);
            gennadyColleagueBig = atlas.findRegion(Constants.GENNADY_COLLEAGUE_BIG);
            michelleColleagueBig = atlas.findRegion(Constants.MICHELLE_COLLEAGUE_BIG);
            mikhailaColleagueBig = atlas.findRegion(Constants.MIKHAILA_COLLEAGUE_BIG);
            nickColleagueBig = atlas.findRegion(Constants.NICK_COLLEAGUE_BIG);

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
        public final TextureAtlas.AtlasRegion veryEasyButtonLocked;
        public final TextureAtlas.AtlasRegion easyButtonLocked;
        public final TextureAtlas.AtlasRegion mediumButtonLocked;
        public final TextureAtlas.AtlasRegion hardButtonLocked;
        public final TextureAtlas.AtlasRegion veryHardButtonLocked;
        public final TextureAtlas.AtlasRegion mysteryQuestionButton;
        public final TextureAtlas.AtlasRegion mysteryQuestionButtonLocked;
        public final TextureAtlas.AtlasRegion veryEasyButtonBig;
        public final TextureAtlas.AtlasRegion easyButtonBig;
        public final TextureAtlas.AtlasRegion mediumButtonBig;
        public final TextureAtlas.AtlasRegion hardButtonBig;
        public final TextureAtlas.AtlasRegion veryHardButtonBig;
        public final TextureAtlas.AtlasRegion mysteryQuestionButtonBig;
        public final TextureAtlas.AtlasRegion mysteryQuestionButtonSolved;
        public final TextureAtlas.AtlasRegion bailOutButton;
        public final TextureAtlas.AtlasRegion bailOutButtonBig;
        public final TextureAtlas.AtlasRegion bringItOnButton;
        public final TextureAtlas.AtlasRegion bringItOnButtonBig;
        public final TextureAtlas.AtlasRegion takeTheRiskBG;
        public final TextureAtlas.AtlasRegion fadeBG;



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
            mysteryQuestionButtonLocked = atlas.findRegion(Constants.MYSTERYQUESTION_BUTTON_LOCKED);
            mysteryQuestionButtonBig = atlas.findRegion(Constants.MYSTERYQUESTION_BUTTON_BIG);
            veryEasyButtonBig = atlas.findRegion(Constants.VERYEASY_BUTTON_BIG);
            easyButtonBig = atlas.findRegion(Constants.EASY_BUTTON_BIG);
            mediumButtonBig = atlas.findRegion(Constants.MEDIUM_BUTTON_BIG);
            hardButtonBig = atlas.findRegion(Constants.HARD_BUTTON_BIG);
            veryHardButtonBig = atlas.findRegion(Constants.VERYHARD_BUTTON_BIG);
            veryEasyButtonLocked = atlas.findRegion(Constants.VERYEASY_BUTTON_LOCKED);
            easyButtonLocked = atlas.findRegion(Constants.EASY_BUTTON_LOCKED);
            mediumButtonLocked = atlas.findRegion(Constants.MEDIUM_BUTTON_LOCKED);
            hardButtonLocked = atlas.findRegion(Constants.HARD_BUTTON_LOCKED);
            veryHardButtonLocked = atlas.findRegion(Constants.VERYHARD_BUTTON_LOCKED);
            mysteryQuestionButtonSolved = atlas.findRegion(Constants.MYSTERYQUESTION_BUTTON_SOLVED);
            bailOutButton = atlas.findRegion(Constants.BAIL_OUT_BUTTON);
            bailOutButtonBig = atlas.findRegion(Constants.BAIL_OUT_BUTTON_BIG);
            bringItOnButton = atlas.findRegion(Constants.BRING_IT_ON_BUTTON);
            bringItOnButtonBig = atlas.findRegion(Constants.BRING_IT_ON_BUTTON_BIG);
            takeTheRiskBG = atlas.findRegion(Constants.TAKE_THE_RISK_BG);
            fadeBG = atlas.findRegion(Constants.FADE_BG);
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

        public final TextureAtlas.AtlasRegion askGoogleLifelineBig;
        public final TextureAtlas.AtlasRegion askClementLifelineBig;
        public final TextureAtlas.AtlasRegion askGennadyLifelineBig;
        public final TextureAtlas.AtlasRegion askMichelleLifelineBig;
        public final TextureAtlas.AtlasRegion askMikhailaLifelineBig;
        public final TextureAtlas.AtlasRegion askNickLifelineBig;
        public final TextureAtlas.AtlasRegion callAFamilyMemberLifelineBig;

        public final TextureAtlas.AtlasRegion mysteryQuestionBG;

        public final TextureAtlas.AtlasRegion javaTopic;
        public final TextureAtlas.AtlasRegion pythonTopic;
        public final TextureAtlas.AtlasRegion cppTopic;
        public final TextureAtlas.AtlasRegion cTopic;

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

            askGoogleLifelineBig = atlas.findRegion(Constants.ASK_GOOGLE_LIFELINE_BIG);
            askClementLifelineBig = atlas.findRegion(Constants.ASK_CLEMENT_LIFELINE_BIG);
            askGennadyLifelineBig = atlas.findRegion(Constants.ASK_GENNADY_LIFELINE_BIG);
            askMichelleLifelineBig = atlas.findRegion(Constants.ASK_MICHELLE_LIFELINE_BIG);
            askMikhailaLifelineBig = atlas.findRegion(Constants.ASK_MIKHAILA_LIFELINE_BIG);
            askNickLifelineBig = atlas.findRegion(Constants.ASK_NICK_LIFELINE_BIG);
            callAFamilyMemberLifelineBig = atlas.findRegion(Constants.CALL_A_FAMILY_MEMBER_BIG);

            mysteryQuestionBG = atlas.findRegion(Constants.MYSTERYQUESTION_BG);

            cppTopic = atlas.findRegion(Constants.CPP_TOPIC);
            cTopic = atlas.findRegion(Constants.C_TOPIC);
            javaTopic = atlas.findRegion(Constants.JAVA_TOPIC);
            pythonTopic = atlas.findRegion(Constants.PYTHON_TOPIC);
        }
    }

    public class GameOverScreenAssets{
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion gameOverBG;
        public final TextureAtlas.AtlasRegion returnToMenuButton;
        public final TextureAtlas.AtlasRegion highScoresButton;
        public final TextureAtlas.AtlasRegion returnToMenuButtonBig;
        public final TextureAtlas.AtlasRegion highScoresButtonBig;

        public GameOverScreenAssets(TextureAtlas atlas){
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            gameOverBG = atlas.findRegion(Constants.GAMEOVER_BG);
            returnToMenuButton = atlas.findRegion(Constants.RETURN_TO_MENU_BUTTON_GRADIENT);
            highScoresButton = atlas.findRegion(Constants.HIGHSCORES_BUTTON_GRADIENT);
            returnToMenuButtonBig = atlas.findRegion(Constants.RETURN_TO_MENU_BUTTON_GRADIENT_BIG);
            highScoresButtonBig = atlas.findRegion(Constants.HIGHSCORES_BUTTON_GRADIENT_BIG);
        }
    }

    public class CorrectAnswerScreenAssets{
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion correctAnswerBG;
        public final TextureAtlas.AtlasRegion continueButton;
        public final TextureAtlas.AtlasRegion continueButtonBig;


        public CorrectAnswerScreenAssets(TextureAtlas atlas){
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            correctAnswerBG = atlas.findRegion(Constants.CORRECTANSWER_BG);
            continueButton = atlas.findRegion(Constants.CONTINUE_BUTTON);
            continueButtonBig = atlas.findRegion(Constants.CONTINUE_BUTTON_BIG);

        }
    }

    public class VictoryScreenAssets{
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion victoryBG;
        public final TextureAtlas.AtlasRegion returnToMenuButton;
        public final TextureAtlas.AtlasRegion highScoresButton;
        public final TextureAtlas.AtlasRegion returnToMenuButtonBig;
        public final TextureAtlas.AtlasRegion highScoresButtonBig;

        public VictoryScreenAssets(TextureAtlas atlas){
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            victoryBG = atlas.findRegion(Constants.VICTORY_BG);
            returnToMenuButton = atlas.findRegion(Constants.RETURN_TO_MENU_BUTTON_GRADIENT);
            highScoresButton = atlas.findRegion(Constants.HIGHSCORES_BUTTON_GRADIENT);
            returnToMenuButtonBig = atlas.findRegion(Constants.RETURN_TO_MENU_BUTTON_GRADIENT_BIG);
            highScoresButtonBig = atlas.findRegion(Constants.HIGHSCORES_BUTTON_GRADIENT_BIG);
        }
    }

    public class JigsawScreenAssets{
        public final TextureAtlas.AtlasRegion confirmButton;
        public final TextureAtlas.AtlasRegion confirmButtonBig;
        public final TextureAtlas.AtlasRegion continueButtonWhite;
        public final TextureAtlas.AtlasRegion continueButtonWhiteBig;

        public JigsawScreenAssets(TextureAtlas atlas){
            confirmButton = atlas.findRegion(Constants.CONFIRM_BUTTON);
            confirmButtonBig = atlas.findRegion(Constants.CONFIRM_BUTTON_BIG);
            continueButtonWhite = atlas.findRegion(Constants.CONTINUE_BUTTON_WHITE);
            continueButtonWhiteBig = atlas.findRegion(Constants.CONTINUE_BUTTON_WHITE_BIG);
        }
    }

    public class JigsawGuessResultScreenAssets{
        public final TextureAtlas.AtlasRegion normalBG;
        public final TextureAtlas.AtlasRegion correctGuessBG;
        public final TextureAtlas.AtlasRegion incorrectGuessBG;
        public final TextureAtlas.AtlasRegion continueButton;
        public final TextureAtlas.AtlasRegion continueButtonBig;

        public JigsawGuessResultScreenAssets(TextureAtlas atlas){
            normalBG = atlas.findRegion(Constants.NORMAL_BG);
            correctGuessBG = atlas.findRegion(Constants.CORRECT_GUESS_BG);
            incorrectGuessBG = atlas.findRegion(Constants.INCORRECT_GUESS_BG);
            continueButton = atlas.findRegion(Constants.CONTINUE_BUTTON);
            continueButtonBig = atlas.findRegion(Constants.CONTINUE_BUTTON_BIG);
        }
    }

    public class HighScoresScreenAssets{
        public final TextureAtlas.AtlasRegion highScoresBG;
        public final TextureAtlas.AtlasRegion clearDataButton;
        public final TextureAtlas.AtlasRegion clearDataButtonBig;
        public final TextureAtlas.AtlasRegion returnToMenuButtonWhite;
        public final TextureAtlas.AtlasRegion returnToMenuButtonWhiteBig;

        public HighScoresScreenAssets(TextureAtlas atlas){
            highScoresBG = atlas.findRegion(Constants.HIGHSCORES_BG);
            clearDataButton = atlas.findRegion(Constants.CLEAR_DATA_BUTTON);
            clearDataButtonBig = atlas.findRegion(Constants.CLEAR_DATA_BUTTON_BIG);
            returnToMenuButtonWhite = atlas.findRegion(Constants.RETURN_TO_MENU_BUTTON_WHITE);
            returnToMenuButtonWhiteBig = atlas.findRegion(Constants.RETURN_TO_MENU_BUTTON_WHITE_BIG);
        }
    }




}
