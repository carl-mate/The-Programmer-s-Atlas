package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ProgrammerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Programmer's Atlas";
		config.width = 960;
		config.height = 540;
		config.resizable = false;
		new LwjglApplication(new ProgrammerGame(), config);
	}
}
