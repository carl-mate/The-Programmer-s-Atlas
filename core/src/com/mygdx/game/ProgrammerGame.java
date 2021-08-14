package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import entity.ImportantFigure;
import util.Assets;
import util.Enums;
import util.Enums.Colleague;
import util.Enums.Difficulty;
import util.Util;

public class ProgrammerGame extends Game {

	private static final String TAG = ProgrammerGame.class.getName();

	private SplashWorker splashWorker;
	private AssetManager am;
	private SpriteBatch batch;
	private Questions questions;
	private JigsawScreen jigsawScreen;
	private DifficultyScreen difficultyScreen;
	private GameplayScreen gameplayScreen;
	private Colleague colleague;
	private Difficulty difficulty;
	private int noOfclues;

	private int previousScore;
	private int currentScore;

	private boolean usedGoogleLifeline;
	private boolean usedAColleagueLifeline;
	private boolean usedCallAFamilyMember;

	private Music musicTheme;
	private Music gameplayMusic;

	private boolean musicOn;
	private boolean hasNotPlayedYet;

	private final long greetingStartTime;

	public ProgrammerGame(){
		greetingStartTime = TimeUtils.nanoTime();
		hasNotPlayedYet = true;
	}


	@Override
	public void create() {
		am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();
		musicTheme = Assets.instance.musicClass.theme;
		gameplayMusic = Assets.instance.musicClass.gameplayMusic;
		musicOn = true;

		if(am.isFinished()){
			splashWorker.closeSplashScreen();
			showMainMenuScreen();
			Assets.instance.soundClass.greetingsSound.play();
		}
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
		batch.dispose();
	}

	public void showMainMenuScreen(){
		initQuestions();
		initScreens();
		initVariables();
		setGameplayScreenMusicOff();
		setScreen(new MainMenuScreen(this, this.batch));

		if(musicOn){
			setMenuScreenMusicOn();
		} else{
			setMenuScreenMusicPause();
		}

	}

	public void showOptionsScreen(){
		setScreen(new OptionsScreen(this, this.batch));
	}

	public void setMenuScreenMusicOn(){
		musicTheme.setLooping(true);
		musicTheme.setVolume(0.3f);
		musicTheme.play();
	}

	public void setMenuScreenMusicOff(){
		musicTheme.stop();
	}

	public void setMenuScreenMusicPause(){
		musicTheme.pause();
	}

	public void setGameplayScreenMusicOn(){
		gameplayMusic.setLooping(true);
		gameplayMusic.play();
	}

	public void setGameplayScreenMusicOff(){
		gameplayMusic.stop();
	}

	public void setGameplayScreenMusicPause(){
		gameplayMusic.pause();
	}


	public void showHowToPlayScreen(){
		setScreen(new HowToPlayScreen(this, this.batch));
	}

	public void showChooseColleagueScreen(){
		setScreen(new ChooseColleagueScreen(this, this.batch));
	}

	public void showDifficultyScreen(){
		setGameplayScreenMusicOff();
		setScreen(difficultyScreen);
		if(musicOn){
			setMenuScreenMusicOn();
		} else{
			setMenuScreenMusicPause();
		}
	}

	public void showGameplayScreen(Difficulty difficulty){
		setMenuScreenMusicOff();
		this.difficulty = difficulty;
		setScreen(new GameplayScreen(this, this.batch));

		if(musicOn){
			setGameplayScreenMusicOn();
		} else{
			setGameplayScreenMusicPause();
		}
	}

	public void showGameOverScreen(){
		setGameplayScreenMusicOff();
		setScreen(new GameOverScreen(this, this.batch));
	}

	public void showHighScoresScreen(){
		setGameplayScreenMusicOff();
		setScreen(new HighScoresScreen(this, this.batch));
	}

	public void showCorrectAnswerScreen(){
		setGameplayScreenMusicOff();
		setScreen(new CorrectAnswerScreen(this, this.batch));
	}

	public void showJigsawScreen(){
		setGameplayScreenMusicOff();
		setMenuScreenMusicOff();
		jigsawScreen.setClue(++noOfclues);
		setScreen(jigsawScreen);
	}

	public void showJigsawGuessResultScreen(boolean isGuessCorrect, Texture importantFigureBiography){
		setScreen(new JigsawGuessResultScreen(this, this.batch, isGuessCorrect, importantFigureBiography));
	}

	public void showVictoryScreen(boolean victory){
		setGameplayScreenMusicOff();
		setMenuScreenMusicOff();
		setScreen(new VictoryScreen(this, this.batch, victory));
	}


	private void initQuestions(){
		Assets.instance.initResourcesFilePath();
		try{
			questions = new Questions();
		} catch(Exception e){
			Gdx.app.log(TAG, "NOTHING HAPPENED");
		}
	}

	private void initVariables(){
		noOfclues = 0;
		previousScore = 0;
		currentScore = 0;
		usedGoogleLifeline = false;
		usedAColleagueLifeline = false;
		usedCallAFamilyMember = false;
	}

	private void initScreens(){

		jigsawScreen = new JigsawScreen(this, this.batch);
		difficultyScreen = new DifficultyScreen(this, this.batch);
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public boolean isUsedGoogleLifeline() {
		return usedGoogleLifeline;
	}

	public void setUsedGoogleLifeline(boolean usedGoogleLifeline) {
		this.usedGoogleLifeline = usedGoogleLifeline;
	}

	public boolean isUsedAColleagueLifeline() {
		return usedAColleagueLifeline;
	}

	public void setUsedAColleagueLifeline(boolean usedAColleagueLifeline) {
		this.usedAColleagueLifeline = usedAColleagueLifeline;
	}

	public boolean isUsedCallAFamilyMember() {
		return usedCallAFamilyMember;
	}

	public void setUsedCallAFamilyMember(boolean usedCallAFamilyMember) {
		this.usedCallAFamilyMember = usedCallAFamilyMember;
	}

	public void setColleague(Colleague colleague){
		this.colleague = colleague;
	}

	public Colleague getColleague(){
		return this.colleague;
	}

	public Questions getQuestions(){
		return this.questions;
	}

	public int getPreviousScore() {
		return previousScore;
	}

	public void setPreviousScore(int previousScore) {
		this.previousScore = previousScore;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getNoOfAnsweredQuestions() {
		return this.difficultyScreen.getNoOfAnsweredQuestions();
	}

	public void incrementNoOfAnsweredQuestions() {
		this.difficultyScreen.incrementNoOfAnsweredQuestions();

	}

	public void setMysteryQuestionAnswered(boolean mysteryQuestionAnswered) {
		this.difficultyScreen.setMysteryQuestionAnswered(mysteryQuestionAnswered);
	}

	public void setTheoreticalVeryEasyAnswered(boolean theoreticalVeryEasyAnswered) {
		this.difficultyScreen.setTheoreticalVeryEasyAnswered(theoreticalVeryEasyAnswered);
	}

	public void setTheoreticalEasyAnswered(boolean theoreticalEasyAnswered) {
		this.difficultyScreen.setTheoreticalEasyAnswered(theoreticalEasyAnswered);
	}

	public void setTheoreticalMediumAnswered(boolean theoreticalMediumAnswered) {
		this.difficultyScreen.setTheoreticalMediumAnswered(theoreticalMediumAnswered);
	}

	public void setTheoreticalHardAnswered(boolean theoreticalHardAnswered) {
		this.difficultyScreen.setTheoreticalHardAnswered(theoreticalHardAnswered);
	}

	public void setTheoreticalVeryHardAnswered(boolean theoreticalVeryHardAnswered) {
		this.difficultyScreen.setTheoreticalVeryHardAnswered(theoreticalVeryHardAnswered);
	}

	public void setProgrammingVeryEasyAnswered(boolean programmingVeryEasyAnswered) {
		this.difficultyScreen.setProgrammingVeryEasyAnswered(programmingVeryEasyAnswered);
	}

	public void setProgrammingEasyAnswered(boolean programmingEasyAnswered) {
		this.difficultyScreen.setProgrammingEasyAnswered(programmingEasyAnswered);
	}

	public void setProgrammingMediumAnswered(boolean programmingMediumAnswered) {
		this.difficultyScreen.setProgrammingMediumAnswered(programmingMediumAnswered);
	}

	public void setProgrammingHardAnswered(boolean programmingHardAnswered) {
		this.difficultyScreen.setProgrammingHardAnswered(programmingHardAnswered);
	}

	public void setProgrammingVeryHardAnswered(boolean programmingVeryHardAnswered) {
		this.difficultyScreen.setProgrammingVeryHardAnswered(programmingVeryHardAnswered);
	}

	public Object getMysteryQuestion(){
		return this.questions.getMysteryQuestion();
	}

	public boolean getMusicOn(){
		return this.musicOn;
	}

	public void setMusicOnOrOff(){
		if(musicOn){
			musicOn = false;
		} else{
			musicOn = true;
		}
	}

	public void setSplashWorker(SplashWorker splashWorker){
		this.splashWorker = splashWorker;
	}
}
