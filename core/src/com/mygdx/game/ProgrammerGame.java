package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entity.ImportantFigure;
import util.Assets;
import util.Enums;
import util.Enums.Colleague;
import util.Enums.Difficulty;

public class ProgrammerGame extends Game {

	private static final String TAG = ProgrammerGame.class.getName();

	private AssetManager am;
	private SpriteBatch batch;
	private Questions questions;
	private JigsawScreen jigsawScreen;
	private DifficultyScreen difficultyScreen;
	private GameplayScreen gameplayScreen;
	private Colleague colleague;
	private int noOfclues;

	private int previousScore;
	private int currentScore;

	@Override
	public void create() {
		am = new AssetManager();
		Assets.instance.init(am);
		batch = new SpriteBatch();
		Assets.instance.initResourcesFilePath();
		try{
			questions = new Questions();
		} catch(Exception e){
			Gdx.app.log(TAG, "NOTHING HAPPENED");
		}
		initScreens();
		noOfclues = 0;
		showMainMenuScreen();
	}

	private void initScreens(){
		jigsawScreen = new JigsawScreen(this, this.batch);
		difficultyScreen = new DifficultyScreen(this, this.batch);
//		gameplayScreen = new GameplayScreen(this, this.batch);
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
		batch.dispose();
	}

	public void showMainMenuScreen(){
		setScreen(new MainMenuScreen(this, this.batch));
	}

	public void showHowToPlayScreen(){
		setScreen(new HowToPlayScreen(this, this.batch));
	}

	public void showChooseColleagueScreen(){
		setScreen(new ChooseColleagueScreen(this, this.batch));
	}

	public void showDifficultyScreen(){ setScreen(difficultyScreen);}

	public void showGameplayScreen(Difficulty difficulty){
//		gameplayScreen.setDifficulty(difficulty);
//		setScreen(gameplayScreen);
		setScreen(new GameplayScreen(this, difficulty, this.batch));
	}

	public void showGameOverScreen(){
		initScreens();
		setScreen(new GameOverScreen(this, this.batch));
	}

	public void showCorrectAnswerScreen(){
		setScreen(new CorrectAnswerScreen(this, this.batch));
	}

	public void showJigsawScreen(){
		jigsawScreen.setClue(++noOfclues);
		setScreen(jigsawScreen);
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
}
