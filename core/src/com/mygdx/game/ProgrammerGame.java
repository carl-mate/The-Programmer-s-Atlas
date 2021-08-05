package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import util.Assets;
import util.Enums.Difficulty;

public class ProgrammerGame extends Game {

	private static final String TAG = ProgrammerGame.class.getName();

	private AssetManager am;
	private SpriteBatch batch;
	private Questions questions;


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
//		showMainMenuScreen();
		showJigsawScren();
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
		batch.dispose();
	}

	public void showMainMenuScreen(){
		setScreen(new MainMenuScreen(this, this.batch));
	}

	public void showDifficultyScreen(){ setScreen(new DifficultyScreen(this, this.batch));}

	public void showGameplayScreen(Difficulty difficulty){
		setScreen(new GameplayScreen(this, difficulty, this.batch));
	}

	public void showJigsawScren(){
		setScreen(new JigsawScreen(this, this.batch));
	}

	public Questions getQuestions(){
		return this.questions;
	}
}
