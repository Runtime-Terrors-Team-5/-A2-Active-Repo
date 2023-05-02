package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import org.javatuples.Pair;
import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LeaderboardScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    int winWidth;
    int winHeight;
    String text;


    ArrayList<Pair<String, Integer>> lbPairs = new ArrayList<Pair<String, Integer>>();

    /**
     * LeaderBoardScreen constructor.
     * @param game PiazzaPanicGame instance.
     */

    public LeaderboardScreen(PiazzaPanicGame game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    }
    /**
     * ScenarioGameMaster show method.
     * reads the leaderboard.txt file and displays it onto the screen
     */
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
        Utility.lbExistenceHandler();
        FileHandle handle = Gdx.files.local("leaderboard.txt");
        text = handle.readString(); //text contains the entire contents of leaderboard.txt
        String[] wordsArray = text.split("\\s+"); //this splits text by whitespace

        //this code removes empty strings
        ArrayList<String> temparray = new ArrayList<>();
        for (String s :
                wordsArray) {
            if (!s.equals("")) {
                temparray.add(s);
            }
        }

        //this loop places all the values in the temparray into a list of pairs
        for(int i=0; i<((temparray.size())-1); i+=2){
            Pair<String, Integer> addPair = new Pair<>(temparray.get(i), Integer.parseInt(temparray.get(i+1)));
            lbPairs.add(addPair);
        }

        //sorts the pairs based on the value in the pair
        for (int j = 0; j < lbPairs.size(); j++) {
            for (int i = 0; i < lbPairs.size() - 1; i++) {
                Pair<String, Integer> tempPair;
                if (lbPairs.get(i).getValue1() > lbPairs.get(i + 1).getValue1()) {
                    tempPair = lbPairs.get(i + 1);
                    lbPairs.set(i + 1, lbPairs.get(i));
                    lbPairs.set(i, tempPair);
                }
            }
        }
        text = "";
        for (int i = 0; i < lbPairs.size(); i++) {
            text = text + lbPairs.get(i).getValue0() + ": " + lbPairs.get(i).getValue1() + "\n";
        }
    }

    public void render(float delta) {
        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();



        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        font.draw(game.batch, "LEADERBOARD:\n" + text, winWidth - (6*(winWidth/8f)), winHeight - 20, (3*(winWidth/8f)), -1, true);
        game.batch.end();
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
    }

    public void hide(){
        batch.dispose();
        font.dispose();
    }

    @Override
    public void dispose(){
        super.dispose();
        game.dispose();
        batch.dispose();
        font.dispose();
    }
}
