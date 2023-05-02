package com.neves6.piazzapanic;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.javatuples.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RunWith(GdxTestRunner.class)
public class testSaveLoad {
    TiledMap map;

    /**
     * Tests that the saved instance of the game is equal to the load version
     */
    @Test
    public void testGameScreen(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        PiazzaPanicGame B = new PiazzaPanicGame();
        // game before save
        //GameScreen game = new GameScreen(A,1);
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 3, 5, 1);
        SaveAndLoadHandler.setSave(game);
        System.out.println("Here");

        //game after save
        //GameScreen LoadGame = new GameScreen(A);

        saveData data = SaveAndLoadHandler.getSave(); // loads the save file
        ScenarioGameMaster gm = data.loadGameMaster(A);
        assertEquals(game.generateSaveData(),  gm.generateSaveData());

    }
}
