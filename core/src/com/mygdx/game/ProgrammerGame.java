package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	private Colleague colleague;
	private int noOfclues;

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
		jigsawScreen = new JigsawScreen(this, this.batch);
		noOfclues = 0;
		showMainMenuScreen();
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

	public void showDifficultyScreen(){ setScreen(new DifficultyScreen(this, this.batch));}

	public void showGameplayScreen(Difficulty difficulty){
		setScreen(new GameplayScreen(this, difficulty, this.batch));
	}

	public void showGameOverScreen(){
//		jigsawScreen = new JigsawScreen(this, this.batch);
		setScreen(new GameOverScreen(this, this.batch));
	}

	public void showCorrectAnswerScreen(int score){
		setScreen(new CorrectAnswerScreen(this, this.batch, score));
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
}
