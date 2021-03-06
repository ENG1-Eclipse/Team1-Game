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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

/**
 * Allows the user to change the resolution of the screen
 */
public class ResolutionScreen implements Screen {

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Stage stage;
    private Table table;
    private Skin skin;


    Texture Exit_Button_inactive;
    Texture Exit_Button_active;
    Texture backgroundTexture;



    MainGame game;
    public ResolutionScreen (MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        Exit_Button_inactive = new Texture("buttons/exit_button.png");
        Exit_Button_active = new Texture("buttons/exit_button_down.png");
        backgroundTexture = new Texture("parallax-space-background.jpg");

        gamecam = new OrthographicCamera();
        gamecam.position.set(MainGame.Game_Width,MainGame.Game_Height,0);
        gamePort = new FitViewport(MainGame.Game_Width,MainGame.Game_Height,gamecam);
        gamePort.apply();


        skin = new Skin(Gdx.files.internal("uiskin.json"));

        //creating resolution buttons
        final TextButton Button1 = new TextButton("1920x1080", skin);
        Button1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.Game_Width = 1920;
                MainGame.Game_Height = 1080;
                gamecam.update();
                game.setScreen(new ResolutionScreen(game));
            }
        });
        final TextButton Button2 = new TextButton("1440x900", skin);
        Button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.Game_Width = 1440;
                MainGame.Game_Height = 900;
                gamecam.update();
                game.setScreen(new ResolutionScreen(game));
            }
        });
        final TextButton Button3 = new TextButton("1366x768", skin);
        Button3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.Game_Width = 1366;
                MainGame.Game_Height = 768;
                gamecam.update();
                game.setScreen(new ResolutionScreen(game));
            }
        });



        table = new Table(skin);
        table.setWidth(MainGame.Game_Width );
        table.align(Align.center |Align.top);
        table.setPosition(0, MainGame.Game_Height);
        table.add("SELECT RESOLUTION").row();
        table.add(Button1).padBottom(5).row();
        table.add(Button2).padBottom(5).row();
        table.add(Button3);

        stage = new Stage(gamePort);

        //creating exit button
        final TextureRegion MyTextureRegion = new TextureRegion(Exit_Button_inactive);
        Drawable drawable = new TextureRegionDrawable(MyTextureRegion);
        final ImageButton ExButton = new ImageButton(drawable);
        ExButton.setPosition(MainGame.Game_Width / 2 - 150,MainGame.Game_Height / 5);
        ExButton.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                ImageButton.ImageButtonStyle _oldStyle = ExButton.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Exit_Button_active);
                ExButton.setStyle(_oldStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                ImageButton.ImageButtonStyle _oldStyle = ExButton.getStyle();
                _oldStyle.imageUp = new TextureRegionDrawable(Exit_Button_inactive);
                ExButton.setStyle(_oldStyle);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        stage.addActor(table);
        stage.addActor(ExButton);
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
        Exit_Button_inactive.dispose();
        Exit_Button_active.dispose();
        backgroundTexture.dispose();
        stage.dispose();
        skin.dispose();

    }
}
