package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
    private Skin skin;
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
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.background = skin.newDrawable("white", 0.2f, 0.2f, 0.2f, 0.8f);



        stage = new Stage(gamePort);

        int row_height = MainGame.Game_Height/12;
        Label label1 = new Label("Game Story:",labelStyle);
        label1.setSize(MainGame.Game_Width,row_height);
        label1.setPosition(0,MainGame.Game_Height-row_height);
        label1.setAlignment(Align.center);
        label1.setAlignment(Align.top);
        stage.addActor(label1);

        Label label2 = new Label("Deep Space Y is a space station on the outskirts of the galaxy, inhabited by humans and\n" +
                "different species of aliens. You are Auber, the constable of the station, and your job is to\n" +
                "enforce law and order. The space station has been recently infiltrated by a team of hostiles\n" +
                "operatives whose mission is to sabotage key systems of the station and render it inoperable.\n" +
                "When a sabotage attempt is reported somewhere in the station, you are notified, and you need\n" +
                "to get there as quickly as possible, arrest the perpetrator, and teleport them to the brig.\n" +
                "Infiltrators have different special abilities that can make them difficult to arrest.\n"
                ,labelStyle);
        label2.setSize(MainGame.Game_Width,MainGame.Game_Height-row_height);
        label2.setPosition(0,0);
        label2.setAlignment(Align.left);
        label2.setAlignment(Align.top);
        label2.setWrap(true);
        stage.addActor(label2);

        Label label3 = new Label("Controls:",skin);
        label3.setSize(MainGame.Game_Width,row_height);
        label3.setPosition(0,MainGame.Game_Height/2);
        label3.setAlignment(Align.center);
        label3.setAlignment(Align.top);
        stage.addActor(label3);

        Label label4 = new Label(" 'W'  -  Moving Up \n" +
                " 'S'  -  Moving Down \n" +
                " 'D'  -  Moving Right \n" +
                " 'A'  -  Moving Left \n" +
                " 'E'  -  Arrest enemy/Use Teleport \n" +
                " 'ESC'  -  Pause"
                ,skin);
        label4.setSize(MainGame.Game_Width,MainGame.Game_Height/2);
        label4.setPosition(0,row_height/2);
        label4.setAlignment(Align.left);
        label4.setAlignment(Align.top);
        label4.setWrap(true);
        stage.addActor(label4);


        final TextureRegion MyTextureRegion = new TextureRegion(Exit_Button_inactive);
        Drawable drawable = new TextureRegionDrawable(MyTextureRegion);
        final ImageButton Button = new ImageButton(drawable);
        Button.setPosition(MainGame.Game_Width / 2 - R_X / 2,MainGame.Game_Height / 8);
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
