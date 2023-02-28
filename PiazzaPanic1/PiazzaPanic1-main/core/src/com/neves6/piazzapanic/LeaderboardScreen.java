package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LeaderboardScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    int winWidth;
    int winHeight;

    public LeaderboardScreen(PiazzaPanicGame game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    }

    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                game.setScreen(new TitleScreen(game));
                return true;
            }
        });

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
    }

    public void render(float delta) {
        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        font.draw(game.batch, "LEADERBOARD:", winWidth - (6*(winWidth/8f)), winHeight - 20, (3*(winWidth/8f)), -1, true);
        game.batch.end();
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
    }

    public void hide(){
        super.dispose();
        game.dispose();
        batch.dispose();
        font.dispose();
    }


}
