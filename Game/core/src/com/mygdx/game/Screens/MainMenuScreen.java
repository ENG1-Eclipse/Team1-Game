package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

public class MainMenuScreen implements Screen {


    private OrthographicCamera gamecam;
    private Viewport gamePort;


    private static final int E_X = 300;
    private static final int E_Y = 150;

    //Defining co-ordinates to place buttons
    int x = MainGame.Game_Width / 2 - E_X / 2;
    int y = MainGame.Game_Height / 10;
    int y1 = y * 2 + E_Y / 2 ;
    int y2 = 2 * y1 - y;

    private Stage stage;

    Texture Play_Button_inactive;
    Texture Exit_Button_inactive;
    Texture Play_Button_active;
    Texture Exit_Button_active;
    Texture Settings_Button_inactive;
    Texture Settings_Button_active;
    Texture backgroundTexture;
    Texture logoTexture;
    Texture Help_Button_inactive;
    Texture Help_Button_active;





    MainGame game;
    public MainMenuScreen (MainGame game) {
        this.game = game;

        gamecam = new OrthographicCamera();
        gamecam.position.set(MainGame.Game_Width,MainGame.Game_Height,0);
        gamePort = new FitViewport(MainGame.Game_Width,MainGame.Game_Height,gamecam);
        gamePort.apply();


    }


    @Override
    public void show() {

        //Mapping textures

        Help_Button_inactive = new Texture("buttons/help_button.png");
        Help_Button_active = new Texture("buttons/help_button_down.png");

        Play_Button_inactive = new Texture("buttons/play_button.png");
        Play_Button_active = new Texture("buttons/play_button_down.png");
        Exit_Button_inactive = new Texture("buttons/exit_button.png");
        Exit_Button_active = new Texture("buttons/exit_button_down.png");
        Settings_Button_inactive = new Texture("buttons/settings_button.png");
        Settings_Button_active = new Texture("buttons/settings_button_down.png");
        backgroundTexture = new Texture("parallax-space-background.jpg");
        logoTexture =  new Texture("AuberLogo.png");
        logoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        stage = new Stage(gamePort);

        //creating play button
        final TextureRegion MyTextureRegion = new TextureRegion(Play_Button_inactive);
        Drawable drawable = new TextureRegionDrawable(MyTextureRegion);
        final ImageButton Button = new ImageButton(drawable);
        Button.setPosition(x,y2);
        Button.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Play_Button_active);
                Button.setStyle(_oldStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Play_Button_inactive);
                Button.setStyle(_oldStyle);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayerTest(game));

            }
        });

        // creating settings button
        final TextureRegion MyTextureRegion1 = new TextureRegion(Settings_Button_inactive);
        Drawable drawable1 = new TextureRegionDrawable(MyTextureRegion1);
        final ImageButton Button1 = new ImageButton(drawable1);
        Button1.setPosition(x,y1);
        Button1.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button1.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Settings_Button_active);
                Button1.setStyle(_oldStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button1.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Settings_Button_inactive);
                Button1.setStyle(_oldStyle);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));

            }
        });

        //creating exit button
        final TextureRegion MyTextureRegion2 = new TextureRegion(Exit_Button_inactive);
        Drawable drawable2 = new TextureRegionDrawable(MyTextureRegion2);
        final ImageButton Button2 = new ImageButton(drawable2);
        Button2.setPosition(x,y);
        Button2.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button2.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Exit_Button_active);
                Button2.setStyle(_oldStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button2.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Exit_Button_inactive);
                Button2.setStyle(_oldStyle);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        //creating help button
        final TextureRegion MyTextureRegion3 = new TextureRegion(Help_Button_inactive);
        Drawable drawable3 = new TextureRegionDrawable(MyTextureRegion3);
        final ImageButton Button3 = new ImageButton(drawable3);
        Button3.setPosition(10,0);
        Button3.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button3.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Help_Button_active);
                Button3.setStyle(_oldStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                ImageButton.ImageButtonStyle _oldStyle = Button3.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Help_Button_inactive);
                Button3.setStyle(_oldStyle);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HelpScreen(game));

            }
        });

        //Adding buttons to screen
        stage.addActor(Button);
        stage.addActor(Button1);
        stage.addActor(Button2);
        stage.addActor(Button3);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.06f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();


        game.batch.draw(backgroundTexture, 0, 0, MainGame.Game_Width, MainGame.Game_Height);
        game.batch.draw(logoTexture, MainGame.Game_Width / 6.2f,
                MainGame.Game_Height / 1.5f,
                MainGame.Game_Width / 1.5f, MainGame.Game_Height/3.2f);
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
