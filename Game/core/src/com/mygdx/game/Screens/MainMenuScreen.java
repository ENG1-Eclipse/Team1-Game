package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

public class MainMenuScreen implements Screen {





    private static final int E_X = 300;
    private static final int P_X = 300;
    private static final int E_Y = 150;
    private static final int P_Y = 150;

    Texture Play_Button_inactive;
    Texture Exit_Button_inactive;
    Texture Play_Button_active;
    Texture Exit_Button_active;
    Texture Settings_Button_inactive;
    Texture Settings_Button_active;
    Texture backgroundTexture;
    Texture logoTexture;

    MainGame game;
    public MainMenuScreen (MainGame game) {
        this.game = game;


    }


    @Override
    public void show() {
        Play_Button_inactive = new Texture("buttons/play_button.png");
        Play_Button_active = new Texture("buttons/play_button_down.png");
        Exit_Button_inactive = new Texture("buttons/exit_button.png");
        Exit_Button_active = new Texture("buttons/exit_button_down.png");
        Settings_Button_inactive = new Texture("buttons/settings_button.png");
        Settings_Button_active = new Texture("buttons/settings_button_down.png");
        backgroundTexture = new Texture("parallax-space-background.jpg");
        logoTexture =  new Texture("AuberLogo.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = MainGame.Game_Width / 2 - E_X / 2;
        int y = MainGame.Game_Height / 10;
        int y1 = y * 2 + E_Y / 2 ;
        int y2 = 2 * y1 - y;
        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, MainGame.Game_Width, MainGame.Game_Height);
        game.batch.draw(logoTexture, MainGame.Game_Width / 2 - logoTexture.getWidth() / 2, MainGame.Game_Height - logoTexture.getHeight() * 16/13);

        if (Gdx.input.getX() < x + E_X && Gdx.input.getX() > x &&
                MainGame.Game_Height - Gdx.input.getY() < y+E_Y &&
                MainGame.Game_Height - Gdx.input.getY() > y  ) {
            game.batch.draw(Exit_Button_active, x, y);
            if (Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(Exit_Button_inactive, x, y);
        }
        if (Gdx.input.getX() < x + E_X && Gdx.input.getX() > x &&
                MainGame.Game_Height - Gdx.input.getY() < y1+E_Y &&
                MainGame.Game_Height - Gdx.input.getY() > y1  ) {
            game.batch.draw(Settings_Button_active, x, y1);
            if (Gdx.input.isTouched()){
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.batch.draw(Settings_Button_inactive, x, y1);
        }
        if (Gdx.input.getX() < x + P_X && Gdx.input.getX() > x &&
                MainGame.Game_Height - Gdx.input.getY() < y2+P_Y &&
                MainGame.Game_Height - Gdx.input.getY() > y2  ) {
            game.batch.draw(Play_Button_active, x, y2);
            if (Gdx.input.isTouched()){
                game.setScreen(new PlayerTest(game));
            }
        } else {
            game.batch.draw(Play_Button_inactive, x, y2);
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

    }
}
