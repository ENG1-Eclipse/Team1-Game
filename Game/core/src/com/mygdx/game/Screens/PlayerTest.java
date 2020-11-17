package com.mygdx.game.Screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.mygdx.game.MainGame;
import com.mygdx.game.Sprites.Infiltrator;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Screens.MainMenuScreen;

import java.awt.*;


public class PlayerTest implements Screen {
    private TextureRegion textureRegion;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> downAnimation;
    private float time = 0f;
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> upAnimation;
    private  Animation<TextureRegion> rightAnimation;
    private  Animation<TextureRegion> leftAnimation;
    private  Animation<TextureRegion> teleportAnimation;
    private  int teleportingState;
    public float xPos = 0;
    public float yPos = 0;
    public float speed = 5;


    public Infiltrator infiltrator;
    public Player player;

    MainGame game;
    public PlayerTest (MainGame game) {
        this.game = game;

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(player.render(delta), player.getX(),player.getY(),100,100);
        infiltrator.updateTarget(player.getX(), player.getY());
        game.batch.draw(infiltrator.render(delta), infiltrator.getX(),infiltrator.getY(),100,100);

        game.batch.end();

    }

    public void quit(){
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }


    @Override
    public void show() {
        infiltrator = new Infiltrator();
        player = new Player();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        textureAtlas.dispose();
    }
}