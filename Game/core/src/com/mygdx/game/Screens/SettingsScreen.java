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
    Texture Exit_Button_inactive;
    Texture Exit_Button_active;
    Texture backgroundTexture;


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
        Resolution_active = new Texture("buttons/resolution_button_down.png");
        Resolution_inactive = new Texture("buttons/resolution_button.png");
        Exit_Button_inactive = new Texture("buttons/exit_button.png");
        Exit_Button_active = new Texture("buttons/exit_button_down.png");
        backgroundTexture = new Texture("parallax-space-background.jpg");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = MainGame.Game_Width / 2 - R_X / 2;
        int y = MainGame.Game_Height / 2;
        int y1 = MainGame.Game_Height / 4;
        stage.draw();
        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, MainGame.Game_Width, MainGame.Game_Height);

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
        if (Gdx.input.getX() < x + R_X && Gdx.input.getX() > x &&
                MainGame.Game_Height - Gdx.input.getY() < y1+R_Y &&
                MainGame.Game_Height - Gdx.input.getY() > y1  ) {
            game.batch.draw(Exit_Button_active, x, y1);
            if (Gdx.input.isTouched()){
                game.setScreen(new MainMenuScreen(game));
            }
        } else {
            game.batch.draw(Exit_Button_inactive, x, y1);
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
