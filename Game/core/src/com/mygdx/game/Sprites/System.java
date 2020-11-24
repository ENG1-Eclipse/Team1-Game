package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class handles the systems throughout the game world. If an infiltrator interacts with one of the systems,
 * it will be set on fire.
 */
public class System extends GameObject {
    private Fire fire;
    private boolean isWorking;
    private TextureAtlas blankTexture;

    private String systemName;


    public System (float x,float y,String name){
        super(x,y);
        systemName = name;
        blankTexture = new TextureAtlas("blankAtlas.atlas");
        setInteractDistance(50f);
        fire = new Fire(x,y);
        fire.setScale(0.25f);
        isWorking = true;
    }

    public boolean getStatus(){
        return isWorking;
    }
    public String getSystemName(){
        return systemName;
    }

    // If the infiltrator can interact with the system they destroy it (set it on fire)
    public boolean infiltratorInteract(float x ,float y){
        if(getCanInteract(x,y) && isWorking){
            isWorking = false;
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public TextureRegion render(float delta) {
        if (isWorking) {
            setHeight(250);
            setWidth(250);
            return blankTexture.findRegion("blank");

        }
        else{
            return fire.render(delta);
        }
    }

}
