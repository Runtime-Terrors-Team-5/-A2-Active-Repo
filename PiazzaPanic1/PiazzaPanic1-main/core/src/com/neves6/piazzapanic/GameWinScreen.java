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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JOptionPane;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameWinScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture bg;
    int winWidth;
    int winHeight;
    float bgScaleFactor;
    Stage stage;
    TextButton creditsButton;
    TextButton titleButton;
    TextButton.TextButtonStyle buttonStyle;
    Skin skin;
    TextureAtlas atlas;
    int completionTime;
    String lbText;


    public GameWinScreen(PiazzaPanicGame game, int completionTime) {
        this.game = game;
        this.completionTime = completionTime;
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        String username = JOptionPane.showInputDialog("What is your username (for the leaderboard)");
        output_to_leaderboard(username);


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
        creditsButton = new TextButton("Credits", buttonStyle);
        creditsButton.setPosition(Gdx.graphics.getWidth()/2f - creditsButton.getWidth()/2, Gdx.graphics.getHeight()/3f + creditsButton.getHeight()/2);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditsScreen(game));
            }
        });
        stage.addActor(creditsButton);
        titleButton = new TextButton("Title", buttonStyle);
        titleButton.setPosition(Gdx.graphics.getWidth()/2f - titleButton.getWidth()/2, Gdx.graphics.getHeight()/3f - titleButton.getHeight()/2);
        titleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TitleScreen(game));
            }
        });
        stage.addActor(titleButton);

    }

    public void output_to_leaderboard(String username) {
        lbText = "\n" + username + " " + completionTime;
        try {
            Files.write(Paths.get("./leaderboard.txt"), lbText.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
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
        font.draw(game.batch, "CONGRATULATIONS!\nYou completed the game in " + completionTime + " seconds!", winWidth / 2f - winWidth/10f, winHeight / 2f + winHeight/5f, winWidth/5f, 1, false);
        game.batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        creditsButton.setPosition(width/2f - creditsButton.getWidth()/2, height/3f + creditsButton.getHeight()/2);
        titleButton.setPosition(width/2f - titleButton.getWidth()/2, height/3f - titleButton.getHeight()/2);
        stage.clear();
        stage.addActor(creditsButton);
        stage.addActor(titleButton);
        stage.getViewport().update(width, height);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void hide(){
        batch.dispose();
        font.dispose();
        bg.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
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
