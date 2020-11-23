package com.mygdx.game.Collision;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;


public class CheatCollision {
    private int mapScale;
    private Pixmap collisionMap;

    public CheatCollision(int scale){
        mapScale = scale;
        Texture temp = new Texture("map\\collisionlayer.png");
        temp.getTextureData().prepare();
        collisionMap = temp.getTextureData().consumePixmap();
    }
    private int pix;

    /**
     * @param x X pos in the world
     * @param y X pos in the world
     * @return
     *     0 : Out of Bounds
     *     1 : Within Bounds
     */
    public int getPositionType(int x,int y){
        /*
        Checks the collision map for the state of the position at x y
        Returns:
            0 : Out of Bounds
            1 : Within Bounds

         */
        pix = collisionMap.getPixel(x/mapScale,collisionMap.getHeight()-y/mapScale);


        //White (255,255,255,255) floor returns 1
        if(pix == Color.rgba8888(1f,1f,1f,1f)){
            return 1;
        }
        //Black (  0,  0,  0) can't go returns 0
        else if(pix == Color.rgba8888(0f,0f,0f,1f)){
            return 0;
        }
        //Teleporter (255,255,  0) returns 2
        else if(pix == Color.rgba8888(1f, 1f, 0f, 1f)) {
            return 2;
        }

        //Systems (  0,255,  0) returns 3

        /*
        noncollide: 255-255-255
        collide:0-0-0
        teleporter: 255-255-0
        systems: 0-255-0
         */

        return -1; //Invalid Colour
    }
}
