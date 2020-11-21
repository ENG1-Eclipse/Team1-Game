package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

public class HelpScreen implements Screen {


    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private static final int R_X = 300;

    private Stage stage;
    private Table table;
    private Skin skin;
    private String Heading;
    BitmapFont font;

    Texture backgroundTexture;
    public static Texture Exit_Button_inactive;
    public static Texture Exit_Button_active;

    MainGame game;
    public HelpScreen (MainGame game) {
        this.game = game;

        gamecam = new OrthographicCamera();
        gamecam.position.set(MainGame.Game_Width,MainGame.Game_Height,0);
        gamePort = new FitViewport(MainGame.Game_Width,MainGame.Game_Height,gamecam);
        gamePort.apply();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

    }

    @Override
    public void show() {
        backgroundTexture = new Texture("parallax-space-background.jpg");
        Exit_Button_inactive = new Texture("buttons/exit_button.png");
        Exit_Button_active = new Texture("buttons/exit_button_down.png");

        Texture texture = new Texture(Gdx.files.internal("bauhaus93_size72.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("bauhaus93_size72.fnt"), new TextureRegion(texture), false);
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font;


        Heading = "Hello World";

        table = new Table(skin);

        stage = new Stage(gamePort);

        int row_height = MainGame.Game_Height/12;
        Label label1 = new Label("HELP",label1Style);
        label1.setSize(MainGame.Game_Width,row_height);
        label1.setPosition(0,MainGame.Game_Height-row_height*2);
        label1.setAlignment(Align.center);
        stage.addActor(label1);


        final TextureRegion MyTextureRegion = new TextureRegion(Exit_Button_inactive);
        Drawable drawable = new TextureRegionDrawable(MyTextureRegion);
        final ImageButton Button = new ImageButton(drawable);
        Button.setPosition(MainGame.Game_Width / 2 - R_X / 2,MainGame.Game_Height / 5);
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
        stage.dispose();
    }
}
