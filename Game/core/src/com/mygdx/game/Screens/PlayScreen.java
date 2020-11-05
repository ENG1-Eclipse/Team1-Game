package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

public class PlayScreen implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gamePort;




    Texture img;


    MainGame game;
    public PlayScreen (MainGame game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamecam.position.set(MainGame.Game_Width/2,MainGame.Game_Height/2,0);
        gamePort = new FitViewport(MainGame.Game_Width,MainGame.Game_Height,gamecam);
        gamePort.apply();

    }
    @Override
    public void show() {

        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();


        game.batch.draw(img, 0, 0);
        game.batch.end();



    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        img.dispose();

    }
}
