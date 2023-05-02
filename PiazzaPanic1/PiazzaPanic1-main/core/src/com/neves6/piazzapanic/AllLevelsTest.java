package com.neves6.piazzapanic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.javatuples.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class AllLevelsTest {

    static TiledMap map;

    /**
     * Tests level 1's starting variables
     */
    @Test
    public void testLevelOne(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        ArrayList thislist = new ArrayList<>();
        thislist.add("salad");
        thislist.add("burger");
        thislist.add("potato");
        Quintet thisQuintet = new Quintet<Float, Float, Integer, ArrayList, Boolean>((float)15.0, (float)15.0, 40, thislist, false);
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);
        assertEquals(thisQuintet, game.returnStartingVars());
    }
    /**
     * Tests level 2's starting variables
     */
    @Test
    public void testLevelTwo(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        ArrayList thislist = new ArrayList<>();
        thislist.add("salad");
        thislist.add("burger");
        thislist.add("potato");
        Quintet thisQuintet = new Quintet<Float, Float, Integer, ArrayList, Boolean>((float)10.0, (float)10.0, 30, thislist, false);
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 2);
        assertEquals(thisQuintet, game.returnStartingVars());
    }
    /**
     * Tests level 3's starting variables
     */
    @Test
    public void testLevelThree(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        ArrayList thislist = new ArrayList<>();
        thislist.add("salad");
        thislist.add("burger");
        thislist.add("potato");
        Quintet thisQuintet = new Quintet<Float, Float, Integer, ArrayList, Boolean>((float)5.0, (float)5.0, 20, thislist, false);
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 3);
        assertEquals(thisQuintet, game.returnStartingVars());
    }
    /**
     * Tests level 4's starting variables
     */
    @Test
    public void testLevelFour(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        ArrayList thislist = new ArrayList<>();
        thislist.add("salad");
        thislist.add("burger");
        thislist.add("potato");
        Quintet thisQuintet = new Quintet<Float, Float, Integer, ArrayList, Boolean>((float)3.0, (float)3.0, 40, thislist, true);
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 4);
        assertEquals(thisQuintet, game.returnStartingVars());
    }
}
