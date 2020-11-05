package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;

public class DesktopLauncher {


	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = MainGame.Game_Width;
		config.height = MainGame.Game_Height;
		config.title = "Team1Game";
		config.resizable = true;

		new LwjglApplication(new MainGame(), config);
	}
}
