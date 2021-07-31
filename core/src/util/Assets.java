package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import java.io.InputStream;
import java.util.HashMap;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();
    public MainMenuAssets mainMenuAssets;
    public DifficultyScreenAssets difficultyScreenAssets;
    public GameplayScreenAssets gameplayScreenAssets;
    public ResourcesFilePath resourcesFilePath;

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

    public class ResourcesFilePath {
        public InputStream theoreticalQuestionsInputStream;
        public InputStream programmingQuestionsInputStream;
        public final HashMap<String, Texture> image;

        public ResourcesFilePath(){
            theoreticalQuestionsInputStream = this.getClass().getResourceAsStream("/Resources/MCQ/TheoreticalQ/TheoreticalQ-Database.xlsx");
            programmingQuestionsInputStream = this.getClass().getResourceAsStream("/Resources/MCQ/ProgrammingQ/ProgrammingQ-Database.xlsx");

            image = new HashMap<>();

            image.put("GT4.png", new Texture(Gdx.files.internal("Resources/Images/GT4.png")));
            image.put("GT5.png", new Texture(Gdx.files.internal("Resources/Images/GT5.png")));
            image.put("GT6.png", new Texture(Gdx.files.internal("Resources/Images/GT6.png")));
            image.put("GT9.png", new Texture(Gdx.files.internal("Resources/Images/GT9.png")));
        }
    }

    public class MainMenuAssets{
        public final TextureAtlas.AtlasRegion mainMenuBG;
        public final TextureAtlas.AtlasRegion playButton;
        public final TextureAtlas.AtlasRegion optionsButton;
        public final TextureAtlas.AtlasRegion howToPlayButton;

        public MainMenuAssets(TextureAtlas atlas){
            mainMenuBG = atlas.findRegion(Constants.MAIN_MENU_BG);
            playButton = atlas.findRegion(Constants.PLAY_BUTTON);
            optionsButton = atlas.findRegion(Constants.OPTIONS_BUTTON);
            howToPlayButton = atlas.findRegion(Constants.HOWTOPLAY_BUTTON);
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

        public DifficultyScreenAssets(TextureAtlas atlas){
            difficultyBG = atlas.findRegion(Constants.DIFFICULTY_BG);
            theoreticalQuestionsLabel = atlas.findRegion(Constants.THEORETICALQUESTIONS_LABEL);
            programmingQuestionsLabel = atlas.findRegion(Constants.PROGRAMMINGQUESTIONS_LABEL);
            veryEasyButton = atlas.findRegion(Constants.VERYEASY_BUTTON);
            easyButton = atlas.findRegion(Constants.EASY_BUTTON);
            mediumButton = atlas.findRegion(Constants.MEDIUM_BUTTON);
            hardButton = atlas.findRegion(Constants.HARD_BUTTON);
            veryHardButton = atlas.findRegion(Constants.VERYHARD_BUTTON);
        }
    }

    public class GameplayScreenAssets{
        public final TextureAtlas.AtlasRegion questionVeryEasy;
        public final TextureAtlas.AtlasRegion questionEasy;
        public final TextureAtlas.AtlasRegion questionMedium;
        public final TextureAtlas.AtlasRegion questionHard;
        public final TextureAtlas.AtlasRegion questionVeryHard;
        public final TextureAtlas.AtlasRegion answerBubbleButton;

        public GameplayScreenAssets(TextureAtlas atlas){
            questionVeryEasy = atlas.findRegion(Constants.QUESTIONVERYEASY_BG);
            questionEasy = atlas.findRegion(Constants.QUESTIONEASY_BG);
            questionMedium = atlas.findRegion(Constants.QUESTIONMEDIUM_BG);
            questionHard = atlas.findRegion(Constants.QUESTIONHARD_BG);
            questionVeryHard = atlas.findRegion(Constants.QUESTIONVERYHARD_BG);
            answerBubbleButton = atlas.findRegion(Constants.ANSWERBUBBLE_BUTTON);
        }
    }
}
