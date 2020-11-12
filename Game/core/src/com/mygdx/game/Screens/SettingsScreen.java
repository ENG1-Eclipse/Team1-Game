package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MainGame;

public class SettingsScreen implements Screen {


    private static final int R_Y = 150;
    private static final int R_X = 300;


    private Stage stage;




    Texture Resolution_inactive;
    Texture Resolution_active;
    public static Texture Exit_Button_inactive;
    public static Texture Exit_Button_active;
    public static Texture backgroundTexture;


    MainGame game;
    public SettingsScreen (MainGame game) {
        this.game = game;



    }


    @Override
    public void show() {
        Resolution_active = new Texture("buttons/resolution_button_down.png");
        Resolution_inactive = new Texture("buttons/resolution_button.png");
        Exit_Button_inactive = new Texture("buttons/exit_button.png");
        Exit_Button_active = new Texture("buttons/exit_button_down.png");
        backgroundTexture = new Texture("parallax-space-background.jpg");


        stage = new Stage(new ScreenViewport());
        final TextureRegion MyTextureRegion = new TextureRegion(Exit_Button_inactive);
        Drawable drawable = new TextureRegionDrawable(MyTextureRegion);
        final ImageButton Button = new ImageButton(drawable);
        Button.setPosition(MainGame.Game_Width / 2 - R_X / 2,MainGame.Game_Height / 4);
        Button.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Exit_Button_active);
                Button.setStyle(_oldStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Exit_Button_inactive);
                Button.setStyle(_oldStyle);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });


        stage.addActor(Button);
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = MainGame.Game_Width / 2 - R_X / 2;
        int y = MainGame.Game_Height / 2;
        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, MainGame.Game_Width, MainGame.Game_Height);

        if (Gdx.input.getX() < x + R_X && Gdx.input.getX() > x &&
                MainGame.Game_Height - Gdx.input.getY() < y+R_Y &&
                MainGame.Game_Height - Gdx.input.getY() > y  ) {
            game.batch.draw(Resolution_active, x, y);
            if (Gdx.input.isTouched()){
                game.setScreen(new ResolutionScreen(game));
            }
        } else {
            game.batch.draw(Resolution_inactive, x, y);
        }
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();

    }
}
