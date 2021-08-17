package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ProgrammerGame;

/**
 * authors: Carl Joseph P. Mate (back-end developer)
 * 			Sophia Marie C. Casas (front-end developer i.e. UI/UX design)
 *
 * In compliance for the subject CMSC 13 (SURVEYS OF PROGRAMMING PARADIGMS) under Prof. John Paul Yusiong
 */

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		ProgrammerGame programmerGame = new ProgrammerGame();
		programmerGame.setSplashWorker(new DesktopSplashWorker());
		config.title = "The Programmer's Atlas";
		config.width = 960;
		config.height = 540;
		config.resizable = false;
		config.addIcon("icon-128.png", Files.FileType.Internal);
		config.addIcon("icon-32.png", Files.FileType.Internal);
		config.addIcon("icon-16.png", Files.FileType.Internal);
		new LwjglApplication(programmerGame, config);
	}
}
