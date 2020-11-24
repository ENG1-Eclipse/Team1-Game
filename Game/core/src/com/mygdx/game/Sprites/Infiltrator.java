package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.AiPath.Node;
import com.mygdx.game.Collision.CheatCollision;

import java.util.ArrayList;

/**
 * The infiltrator class handles the rendering and moving of the various infiltrators in the game. Their AI is controlled
 * by the UpdateMove, UpdateTarget, and UpdateTargetNodes
 */
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

    private int width,height;
    //Position Data
    public float xPos = 0;
    public float yPos = 0;
    public float speed = 4;

    //Capture Data
    public int captureDistance = 100;
    public boolean captured = false;


    //AI stuff
    public float targetX;
    public float targetY;
    private ArrayList<Node> path;
    private String targetName;
    private Node targetNode;

    public int getWidth(){ return width;}
    public int getHeight(){return height;}


    private CheatCollision coll;

    // AI

    public Infiltrator(int mapScale){
        // Init textures for animation
        textureAtlas = new TextureAtlas(Gdx.files.internal("infiltrator/infiltrator.atlas"));
        downAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("down"), Animation.PlayMode.LOOP_PINGPONG);
        upAnimation = new Animation<TextureRegion>(0.08f, textureAtlas.findRegions("up"), Animation.PlayMode.LOOP_PINGPONG);
        rightAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("right"), Animation.PlayMode.LOOP_PINGPONG);
        leftAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("left"), Animation.PlayMode.LOOP_PINGPONG);

        idleAnimation = new Animation<TextureRegion>(0.25f, textureAtlas.findRegions("player_idle"), Animation.PlayMode.LOOP_PINGPONG);
        teleportAnimation = new Animation<TextureRegion>(0.15f, textureAtlas.findRegions("teleport"), Animation.PlayMode.NORMAL);
        coll = new CheatCollision(mapScale);
    }
    private int moveDir;

    public void setPos(float x,float y){
        xPos = x;
        yPos = y;
    }
    public void updateTarget(float x,float y){
        /*
            Updates the target the infiltrator will walk towards. To allow the infiltrator to turn faster adjust pathMulti value.
        */
        targetX = x;
        targetY = y;

    }

    public void updateTargetNode(String Target, Node currentNode){
        moveList =  currentNode.getPathToTargetNode(Target,null);
        if(moveList != null) {
            targetX = moveList.get(0).getX();
            targetY = moveList.get(0).getY();
            targetNode = moveList.get(moveList.size()-1);
            targetName = targetNode.getName();
        }
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
    private ArrayList<Node> moveList;

    public void setTargetSystem(Node targetNode){
        targetName = targetNode.getName();
    }


    public int calculateMove(float x,float y){
        //TODO BETTER AI: THIS ONE LIKES TO GLITCH IN WALLS AND OBJECTS
        // Done: some sort of AI had been made. It probably works. Who knows. What is an AI. Will the infiltrator demand rights as if it was equal or not?
        // Would you give it rights?
        // One of the big questions you will have to debate in week 10 of INT1...
        if((xPos-x)*(xPos-x)+(yPos-y)*(yPos-y)<minDis*minDis&&moveList.size()>0) {
            moveList.remove(0);
            if (moveList.size() >= 1) {
                updateTarget(moveList.get(0).getX(), moveList.get(0).getY());
            }
        }

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
    public String getTargetName(){return targetName;}
    public Node getTargetNode(){return targetNode;}
    public Node getCurrentNode(){
        if(moveList.size()!=0) {
            return moveList.get(0);
        }
        else{
            return targetNode;
        }
    }

    public void move(float dx,float dy){
        //TODO Add collision check and stop player from moving into the object
        if(coll.getPositionType((int)(xPos+dx+width/4),(int)(yPos+dy+height/8))!=0 && coll.getPositionType((int)(xPos+dx+width*3/4),(int)(yPos+dy+height/8))!=0 ){
            yPos += dy;
            xPos += dx;
        }else{
            float temp;
            temp = pathXmulti;
            pathXmulti = pathYmulti;
            pathYmulti = temp;
        }
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

    public boolean isCaptured(){
        return teleportingState == 2;
    }

    public boolean playerInteract(float x,float y){
        if((xPos-x)*(xPos-x)+(yPos-y)*(yPos-y)<= captureDistance*captureDistance) {
            if (teleportingState == 0) {
                teleport();
                return true;
            } else if (teleportingState == 2) {
                captured = true;
                return true;
            }
        }
        return false;
    }
}
