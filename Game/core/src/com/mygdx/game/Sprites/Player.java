package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.Collision.CheatCollision;
import com.mygdx.game.MainGame;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.Screens.PlayerTest;
import com.mygdx.game.Collision.*;

import javax.swing.plaf.synth.SynthEditorPaneUI;


public class Player{
    private TextureRegion textureRegion;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> downAnimation;
    private float time = 0f;
    private int animationInput;
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> upAnimation;
    private  Animation<TextureRegion> rightAnimation;
    private  Animation<TextureRegion> leftAnimation;
    private  Animation<TextureRegion> teleportAnimation;
    private  int teleportingState;
    public float xPos = 0;
    public float yPos = 0;
    public int width;
    public int height;
    private CheatCollision coll;

    public float speed = 5;

    public Player(int mapScale,int playerWidth ,int playerHeight){
        width = playerWidth;
        height = playerHeight;

        textureAtlas = new TextureAtlas(Gdx.files.internal("player/player.atlas"));
        downAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("down"), Animation.PlayMode.LOOP_PINGPONG);
        upAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("up"), Animation.PlayMode.LOOP_PINGPONG);
        rightAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("right"), Animation.PlayMode.LOOP_PINGPONG);
        leftAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("left"), Animation.PlayMode.LOOP_PINGPONG);

        idleAnimation = new Animation<TextureRegion>(0.25f, textureAtlas.findRegions("player_idle"), Animation.PlayMode.LOOP_PINGPONG);
        teleportAnimation = new Animation<TextureRegion>(0.15f, textureAtlas.findRegions("teleport"), Animation.PlayMode.NORMAL);
        coll = new CheatCollision(mapScale);
    }

    public void setPos(int x, int y){
        xPos = x;
        yPos = y;
    }
    public TextureRegion render(float delta){
        time += delta;

        if(teleportingState == 1 || teleportingState == 3){
            //Teleportation animation is run. Once it is finished it changes the state,
            textureRegion = teleportAnimation.getKeyFrame(time);
            if(teleportAnimation.isAnimationFinished(time)){
                if(teleportingState == 1){
                    teleportingState = 2;
                    int[] teleCoords = PlayerTest.teleportFrom(xPos, yPos);
                    xPos = teleCoords[0];
                    yPos = teleCoords[1];
                    teleport();
                }else if(teleportingState == 3){
                    teleportingState = 0;
                }
            }
        }else if(animationInput == Input.Keys.E) {
            if (PlayerTest.isTeleportValid(xPos, yPos)) {
                teleport();
            }
        }else if(teleportingState == 2){
            // Teleportation blocks all other inputs to stop the player moving around
            textureRegion = textureAtlas.findRegion("blank");
        }else if(animationInput == Input.Keys.S) {
            textureRegion = downAnimation.getKeyFrame(time);
            //yPos-=speed;
            move(0f,-speed);
        }else if(animationInput == Input.Keys.W){
            textureRegion = upAnimation.getKeyFrame(time);
            //yPos += speed;
            move(0f,speed);
        }else if(animationInput == Input.Keys.D){
            textureRegion = rightAnimation.getKeyFrame(time);
            //xPos+=speed;
            move(speed,0f);
        }else if(animationInput == Input.Keys.A){
            textureRegion = leftAnimation.getKeyFrame(time);
            //xPos -=speed;
            move(-speed,0f);
        }else if(animationInput == Input.Keys.ESCAPE) {
            quit();
        }else{
            textureRegion = idleAnimation.getKeyFrame(time);
        }
        return textureRegion;

    }
    public float getX(){
        return xPos;
    }
    public float getY(){
        return yPos;
    }
    public int getWidth(){ return width;}
    public int getHeight(){return height;}

    public void updateInput(int input){
        animationInput = input;
    }

    public void move(float dx,float dy){
        //TODO Add collision check and stop player from moving into the object
        if(coll.getPositionType((int)(xPos+dx+width/4),(int)(yPos+dy+height/8))!=0 && coll.getPositionType((int)(xPos+dx+width*3/4),(int)(yPos+dy+height/8))!=0 ){
            yPos += dy;
            xPos += dx;
        }
        //java.lang.System.out.print(xPos);
        //java.lang.System.out.print(":");
        //java.lang.System.out.println(yPos);

    }

    public void interact(){
        ;
        //TODO extended collision detection to allow user to interact with objects
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
        ;
    }

}
