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






    MainGame game;
    public PlayerTest (MainGame game) {
        this.game = game;

    }

    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0, 0, 0.06f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += delta;

        if(teleportingState == 1 || teleportingState == 3){
            //Teleportation animation is run. Once it is finished it changes the state,
            textureRegion = teleportAnimation.getKeyFrame(time);
            if(teleportAnimation.isAnimationFinished(time)){
                if(teleportingState == 1){
                    teleportingState = 2;
                }else if(teleportingState == 3){
                    teleportingState = 0;
                }

            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            interact();
        }else if(teleportingState == 2){
            // Teleportation blocks all other inputs to stop the player moving around
            textureRegion = textureAtlas.findRegion("blank");
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
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
        }else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            quit();
        }else{
            textureRegion = idleAnimation.getKeyFrame(time);
        }

        game.batch.begin();
        game.batch.draw(textureRegion,xPos,yPos,100,100);

        game.batch.end();

    }

    public void interact(){
        ;
        //Temp teleport animation test
        teleport();

    }

    public void setTelportState(int teleportState){
        teleportingState = teleportState;
    }

    public void teleport(){
        /*
        teleportingStates
            Not Teleporting = 0
            Teleporting Out = 1
            Blank           = 2
            Teleporting In  = 3
         */
        if(teleportingState==0) {
            teleportAnimation.setPlayMode(Animation.PlayMode.NORMAL);
            teleportingState = 1;
            time = 0f;
        }else if (teleportingState == 2){
            teleportingState = 3;
            teleportAnimation.setPlayMode(Animation.PlayMode.REVERSED);
            time = 0f;
        }
    }

    public void quit(){
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }


    @Override
    public void show() {


        textureAtlas = new TextureAtlas(Gdx.files.internal("player/player.atlas"));
        downAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("down"), Animation.PlayMode.LOOP_PINGPONG);
        upAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("up"), Animation.PlayMode.LOOP_PINGPONG);
        rightAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("right"), Animation.PlayMode.LOOP_PINGPONG);
        leftAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("left"), Animation.PlayMode.LOOP_PINGPONG);

        idleAnimation = new Animation<TextureRegion>(0.25f, textureAtlas.findRegions("player_idle"), Animation.PlayMode.LOOP_PINGPONG);
        teleportAnimation = new Animation<TextureRegion>(0.15f, textureAtlas.findRegions("teleport"), Animation.PlayMode.NORMAL);

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