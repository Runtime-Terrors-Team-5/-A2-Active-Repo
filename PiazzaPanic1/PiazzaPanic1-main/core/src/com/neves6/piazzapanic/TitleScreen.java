package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TitleScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture bg;
    int winWidth;
    int winHeight;
    float bgScaleFactor;
    Stage stage;
    TextButton playButton;
    TextButton tutorialButton;
    TextButton leaderboardButton;
    TextButton creditsButton;
    TextButton settingsButton;
    TextButton exitButton;
    TextButton.TextButtonStyle buttonStyle;
    Skin skin;
    TextureAtlas atlas;

    public TitleScreen(PiazzaPanicGame game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("buttons/title/unnamed.atlas"));
        skin.addRegions(atlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable("black_alpha_mid");
        buttonStyle.down = skin.getDrawable("black_alpha_mid");
        buttonStyle.checked = skin.getDrawable("black_alpha_mid");

        playButton = new TextButton("Play", buttonStyle);
        playButton.setPosition(Gdx.graphics.getWidth()/2f - playButton.getWidth()/2, Gdx.graphics.getHeight()/2f + playButton.getHeight()/2);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelSelectorScreen(game));
            }
        });

        stage.addActor(playButton);

        tutorialButton = new TextButton("Tutorial", buttonStyle);
        tutorialButton.setPosition(Gdx.graphics.getWidth()/2f - tutorialButton.getWidth()/2, Gdx.graphics.getHeight()/2f - tutorialButton.getHeight()/2);
        tutorialButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TutorialScreen(game, "title"));
            }
        });

        stage.addActor(tutorialButton);

        leaderboardButton = new TextButton("Leaderboard", buttonStyle);
        leaderboardButton.setPosition(Gdx.graphics.getWidth()/2f - leaderboardButton.getWidth()/2, Gdx.graphics.getHeight()/2f - leaderboardButton.getHeight()/2);
        leaderboardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LeaderboardScreen(game));
            }
        });

        stage.addActor(leaderboardButton);

        creditsButton = new TextButton("Credits", buttonStyle);
        creditsButton.setPosition(Gdx.graphics.getWidth()/2f - creditsButton.getWidth()/2, Gdx.graphics.getHeight()/2f - creditsButton.getHeight()*3/2);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditsScreen(game));
            }
        });

        stage.addActor(creditsButton);

        settingsButton = new TextButton("Settings", buttonStyle);
        settingsButton.setPosition(Gdx.graphics.getWidth()/2f - settingsButton.getWidth()/2, Gdx.graphics.getHeight()/2f - settingsButton.getHeight()*5/2);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game));
            }
        });
        stage.addActor(settingsButton);

        exitButton = new TextButton("Exit", buttonStyle);
        exitButton.setPosition(Gdx.graphics.getWidth()/2f - exitButton.getWidth()/2, Gdx.graphics.getHeight()/2f - exitButton.getHeight()*7/2);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();
        bgScaleFactor = (float) winHeight / (float) bg.getHeight();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(
                bg,
                -((bg.getWidth() * bgScaleFactor) - winWidth) / 2,
                0,
                bg.getWidth() * bgScaleFactor,
                bg.getHeight() * bgScaleFactor);
        font.draw(game.batch, "PIAZZA PANIC 1", winWidth / 2f - winWidth/10f, winHeight / 2f + winHeight/5f, winWidth/5f, 1, false);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        playButton.setPosition(width/2f - playButton.getWidth()/2, height/2f + playButton.getHeight()/2);
        tutorialButton.setPosition(width/2f - tutorialButton.getWidth()/2, height/2f - tutorialButton.getHeight()/2);
        leaderboardButton.setPosition(width/2f - leaderboardButton.getWidth()/2, height/2f - leaderboardButton.getHeight()*3/2);
        creditsButton.setPosition(width/2f - creditsButton.getWidth()/2, height/2f - creditsButton.getHeight()*5/2);
        settingsButton.setPosition(width/2f - settingsButton.getWidth()/2, height/2f - settingsButton.getHeight()*7/2);
        exitButton.setPosition(width/2f - exitButton.getWidth()/2, height/2f - exitButton.getHeight()*9/2);
        stage.clear();
        stage.addActor(playButton);
        stage.addActor(tutorialButton);
        stage.addActor(leaderboardButton);
        stage.addActor(creditsButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);
        stage.getViewport().update(width, height);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        super.dispose();
        game.dispose();
        batch.dispose();
        font.dispose();
        bg.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
