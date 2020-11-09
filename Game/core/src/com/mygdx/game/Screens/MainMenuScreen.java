package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

    MainGame game;
    public MainMenuScreen (MainGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        Play_Button_inactive = new Texture("buttons/playwhite.png");
        Play_Button_active = new Texture("buttons/playyellow.png");
        Exit_Button_inactive = new Texture("buttons/exitwhite.png");
        Exit_Button_active = new Texture("buttons/exityellow.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = MainGame.Game_Width / 2 - E_X / 2;
        int y = MainGame.Game_Height / 6;
        int y1 = E_Y  * 2;
        game.batch.begin();
        if (Gdx.input.getX() < x + E_X && Gdx.input.getX() > x &&
                600 - Gdx.input.getY() < y+E_Y && 600 - Gdx.input.getY() > y  ) {
            game.batch.draw(Exit_Button_active, x, y);
            if (Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(Exit_Button_inactive, x, y);
        }
        if (Gdx.input.getX() < x + P_X && Gdx.input.getX() > x &&
                600 - Gdx.input.getY() < y1+P_Y && 600 - Gdx.input.getY() > y1  ) {
            game.batch.draw(Play_Button_active, x, y1);
            if (Gdx.input.isTouched()){
                game.setScreen(new PlayerTest(game));
            }
        } else {
            game.batch.draw(Play_Button_inactive, x, y1);
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
