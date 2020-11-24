package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Simple class to implement a system being on fire.
 */
public class Fire extends GameObject {

    public Fire(float x,float y){
        super(x,y);
        super.setInteractDistance(100f);
        super.setScale(0.25f);
        super.setAnimation("Fire/fire.atlas","Fire",0.2f, Animation.PlayMode.LOOP_RANDOM);
    }
}
