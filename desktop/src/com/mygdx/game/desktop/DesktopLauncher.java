package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ProgrammerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		ProgrammerGame programmerGame = new ProgrammerGame();
		programmerGame.setSplashWorker(new DesktopSplashWorker());
		config.title = "The Programmer's Atlas";
		config.width = 960;
		config.height = 540;
		config.resizable = false;
		new LwjglApplication(programmerGame, config);
	}
}
