package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;

public class Infiltrator {
    //Animation
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

    //Position Data
    public float xPos = 0;
    public float yPos = 0;
    public float speed = 4;

    public float targetX;
    public float targetY;

    // AI

    public Infiltrator(){
        // Init textures for animation
        textureAtlas = new TextureAtlas(Gdx.files.internal("infiltrator/infiltrator.atlas"));
        downAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("down"), Animation.PlayMode.LOOP_PINGPONG);
        upAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("up"), Animation.PlayMode.LOOP_PINGPONG);
        rightAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("right"), Animation.PlayMode.LOOP_PINGPONG);
        leftAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("left"), Animation.PlayMode.LOOP_PINGPONG);

        idleAnimation = new Animation<TextureRegion>(0.25f, textureAtlas.findRegions("player_idle"), Animation.PlayMode.LOOP_PINGPONG);
        teleportAnimation = new Animation<TextureRegion>(0.15f, textureAtlas.findRegions("teleport"), Animation.PlayMode.NORMAL);
    }
    private int moveDir;


    public void updateTarget(float x,float y){
        /*
            Updates the target the infiltrator will walk towards. To allow the infiltrator to turn faster adjust pathMulti value.
        */
        targetX = x;
        targetY = y;
    }

    public TextureRegion render(float delta){
        time += delta;
        moveDir = calculateMove(targetX,targetY);

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
        }else if(teleportingState == 2){
            // Teleportation blocks all other inputs to stop the player moving around
            textureRegion = textureAtlas.findRegion("blank");
        }else if(moveDir == 2) {
            textureRegion = downAnimation.getKeyFrame(time);
            //yPos-=speed;
            move(0f,-speed);
        }else if(moveDir == 0){
            textureRegion = upAnimation.getKeyFrame(time);
            //yPos += speed;
            move(0f,speed);
        }else if(moveDir == 1){
            textureRegion = rightAnimation.getKeyFrame(time);
            //xPos+=speed;
            move(speed,0f);
        }else if(moveDir == 3){
            textureRegion = leftAnimation.getKeyFrame(time);
            //xPos -=speed;
            move(-speed,0f);
        }else{
            textureRegion = idleAnimation.getKeyFrame(time);
        }

        return textureRegion;

    }

    private float minDis = 50;
    final float pathMulti = 15f;
    private float pathXmulti = 2.5f;
    private float pathYmulti = 2.5f;

    public int calculateMove(float x,float y){
        float dx = x-xPos;
        float dy = y-yPos;
        if(dx*dx+dy*dy>minDis*minDis){
            if(dx*dx*pathXmulti>dy*dy*pathYmulti){
                pathYmulti = 1;
                pathXmulti = pathMulti;
                if(dx>0){
                    return 1;
                }
                else {
                    return 3;
                }
            }else{
                pathXmulti = 1;
                pathYmulti = pathMulti;
                if(dy>0){
                    return 0;
                }
                else{
                    return 2;
                }
            }

        }
        else{
            return -1;
        }
    }

    public float getX(){
        return xPos;
    }
    public float getY(){
        return yPos;
    }

    public void move(float dx,float dy){
        //TODO Add collision check and stop player from moving into the object
        yPos += dy;
        xPos += dx;
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
}
