package com.mygdx.game.Screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.MainGame;
import com.mygdx.game.Screens.MainMenuScreen;

public class PlayerTest implements Screen {
    public static final int Game_Width = 800;
    public static final int Game_Height = 600;
    private TextureRegion textureRegion;
    private TextureAtlas textureAtlas;
    private Sprite sprite;
    private Animation<TextureRegion> downAnimation;
    private float time = 0f;
    private Animation<TextureRegion> idleAnimation;
    public SpriteBatch batch;

    MainGame game;
    public PlayerTest (MainGame game) {
        this.game = game;

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += delta;

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            textureRegion = downAnimation.getKeyFrame(time);
        }
        else{
            textureRegion = idleAnimation.getKeyFrame(time);
        }
        batch.begin();
        batch.draw(textureRegion,0,0);

        batch.end();

    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("player/player.atlas"));
        downAnimation = new Animation<TextureRegion>(0.05f, textureAtlas.findRegions("down"), Animation.PlayMode.LOOP_PINGPONG);
        idleAnimation = new Animation<TextureRegion>(0.25f, textureAtlas.findRegions("player_idle"), Animation.PlayMode.LOOP_PINGPONG);
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

    }

    @Override
    public void dispose() {
        game.batch.dispose();

    }
}