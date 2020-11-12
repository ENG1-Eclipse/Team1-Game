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
import com.mygdx.game.Screens.MainMenuScreen;


public class PlayerTest implements Screen {
    private TextureRegion textureRegion;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> downAnimation;
    private float time = 0f;
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> upAnimation;
    private  Animation<TextureRegion> rightAnimation;
    private  Animation<TextureRegion> leftAnimation;
    public float xPos = 0;
    public float yPos = 0;
    public float speed = 5;






    MainGame game;
    public PlayerTest (MainGame game) {
        this.game = game;






    }

    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0, 0, 0.06f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += delta;

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            textureRegion = downAnimation.getKeyFrame(time);
            yPos-=speed;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            textureRegion = upAnimation.getKeyFrame(time);
            yPos += speed;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            textureRegion = rightAnimation.getKeyFrame(time);
            xPos+=speed;
        }else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            textureRegion = leftAnimation.getKeyFrame(time);
            xPos -=speed;
        }
        else{
            textureRegion = idleAnimation.getKeyFrame(time);
        }
        game.batch.begin();
        game.batch.draw(textureRegion,xPos,yPos,100,100);

        game.batch.end();

    }

    @Override
    public void show() {


        textureAtlas = new TextureAtlas(Gdx.files.internal("player/player.atlas"));
        downAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("down"), Animation.PlayMode.LOOP_PINGPONG);
        upAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("up"), Animation.PlayMode.LOOP_PINGPONG);
        rightAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("right"), Animation.PlayMode.LOOP_PINGPONG);
        leftAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("left"), Animation.PlayMode.LOOP_PINGPONG);

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
        dispose();
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();

    }
}