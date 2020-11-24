package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * GameObject acts as a base class for a number of other objects to extend. It allows rendering and interaction with itself.
 */

public class GameObject {
    private float xPos,yPos,animationTime;
    private Animation<TextureRegion> animation;
    private float scale = 1;
    private int width;
    private int height;
    public float interactDistance;

    public GameObject(float x , float y){
    xPos = x;
    yPos = y;
    }

    public TextureRegion render(float delta){
        animationTime += delta;
        width = animation.getKeyFrame(animationTime).getRegionWidth();
        height = animation.getKeyFrame(animationTime).getRegionHeight();

        return animation.getKeyFrame(animationTime);
    }
    public void setWidth(int newWidth){
        width = newWidth;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
    }

    public int getWidth(){
        return (int)(width*scale);
    }
    public int getHeight(){
        return (int)(height*scale);
    }
    public void setAnimation(String textureAtlasPath,String name,float frameTime,Animation.PlayMode animationMode){
        TextureAtlas textureAtlas;
        textureAtlas = new TextureAtlas(Gdx.files.internal(textureAtlasPath));
        animation = new Animation<TextureRegion>(frameTime, textureAtlas.findRegions(name), animationMode);
    }

    public void updatePos(float x, float y){
        xPos = x;
        yPos = y;
    }
    public float getScale(){
        return scale;
    }
    public void setScale(float newScale){
        scale = newScale;
    }
    public float getX(){
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public void setInteractDistance(float newDistance){
        interactDistance = newDistance;
    }
    public boolean getCanInteract(float x, float y){
        if((xPos-x)*(xPos-x)+(yPos-y)*(yPos-y)<=interactDistance*interactDistance){
            return true;
        }
        else{
            return false;
        }
    }

    public void interact(){
        // Place your interact code here
    }


}
