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
import com.mygdx.game.Sprites.Infiltrator;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Screens.MainMenuScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;

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
    private OrthographicCamera cam;
    private  int teleportingState;
    public float xPos = 0;
    public float yPos = 0;
    public float speed = 5;

    private Texture teleporter;
    public static int[][] teleporterLocations = {{500,MainGame.Game_Height/2}, {MainGame.Game_Width - 500,MainGame.Game_Height/2}};

    public Infiltrator infiltrator;
    public Player player;

    MainGame game;
    public PlayerTest (MainGame game) {
        this.game = game;

    }

    final int playerWidth = 100;
    final int playerHeight = 100;
    @Override
    public void render (float delta) {
        //---------------Camera-----------------//
        // Update cam pos to center on the player
        cam.position.x = player.getX()+playerWidth/2;
        cam.position.y = player.getY()+playerHeight/2;
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0, 0, 0.06f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(teleporter,500,MainGame.Game_Height/2,
                teleporter.getWidth() / 6,teleporter.getHeight() / 6);
        game.batch.draw(teleporter, MainGame.Game_Width - 500,MainGame.Game_Height/2,
                teleporter.getWidth() / 6,teleporter.getHeight() / 6);

        game.batch.draw(player.render(delta), player.getX(),player.getY(),playerWidth,playerHeight);
        infiltrator.updateTarget(player.getX(), player.getY());
        game.batch.draw(infiltrator.render(delta), infiltrator.getX(),infiltrator.getY(),100,100);

        game.batch.end();


    }

    public static boolean  isTeleportValid (float xLoc, float yLoc){
        //Function to check if the player is within range of a teleporter
        for (int[] coords : teleporterLocations) {
            if ((xLoc > coords[0] - MainGame.Game_Width/16 && xLoc < coords[0] + MainGame.Game_Width/16) &&
                    (yLoc > coords[1] - MainGame.Game_Height/16 && yLoc < coords[1] + MainGame.Game_Height/16)){
                return true;
            }
        }
        return false;
    }

    public static int[]  teleportFrom (float xLoc, float yLoc){
        //Quick and dirty function to determine where to teleport (from 2 teleporters)
        int[][] teleporterLocations = {{500,MainGame.Game_Height/2}, {MainGame.Game_Width - 500,MainGame.Game_Height/2}};
        int i = 0;
        for (int[] coords : teleporterLocations) {
            if ((xLoc > coords[0] - MainGame.Game_Width/16 && xLoc < coords[0] + MainGame.Game_Width/16) &&
                    (yLoc > coords[1] - MainGame.Game_Height/16 && yLoc < coords[1] + MainGame.Game_Height/16)){
                if (i == 0) {
                    return teleporterLocations[i + 1];
                } else {
                    return teleporterLocations[i - 1];
                }
            }
            i++;
        }
        return null;
    }

    public void quit(){
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }


    @Override
    public void show() {
        infiltrator = new Infiltrator();
        player = new Player();
        teleporter = new Texture("map\\teleporter.png");

        cam = new OrthographicCamera(1920, 1080);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

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