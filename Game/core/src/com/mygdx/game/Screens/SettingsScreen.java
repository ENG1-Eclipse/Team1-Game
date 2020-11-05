package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MainGame;
import sun.rmi.rmic.Main;

public class SettingsScreen implements Screen {


    private static final int R_Y = 150;
    private static final int R_X = 300;

    private Stage stage;

    Table table = new Table();



    Texture Resolution_inactive;
    Texture Resolution_active;


    MainGame game;
    public SettingsScreen (MainGame game) {
        this.game = game;

        stage = new Stage();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Resolution_active = new Texture("buttons/resolutionyellow.png");
        Resolution_inactive = new Texture("buttons/resolutionwhite.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = MainGame.Game_Width / 2 - R_X / 2;
        int y = MainGame.Game_Height / 2;
        stage.draw();
        game.batch.begin();
        if (Gdx.input.getX() < x + R_X && Gdx.input.getX() > x &&
                MainGame.Game_Height - Gdx.input.getY() < y+R_Y &&
                MainGame.Game_Height - Gdx.input.getY() > y  ) {
            game.batch.draw(Resolution_active, x, y);
            if (Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(Resolution_inactive, x, y);
        }
        game.batch.end();

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

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        stage.dispose();
    }
}
