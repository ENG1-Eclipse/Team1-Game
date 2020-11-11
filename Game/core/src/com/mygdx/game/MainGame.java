package com.mygdx.game;


import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenuScreen;

import java.awt.*;

public class MainGame extends Game {
	public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public static final int Game_Width = (int) dimension.getWidth();
	public static final int Game_Height = (int) dimension.getHeight();

	public SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
