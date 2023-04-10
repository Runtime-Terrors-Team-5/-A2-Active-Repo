package com.neves6.piazzapanic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.runner.RunWith;

/**
 * Tests whether the interacted power ups collected by
 * the user are applied to the correct chef
 * heart - increase rep by 1
 * fastIcon - increases cooking/interact speed
 * MoneyIcon - lump increase in money
 * doubleMoney - Doubles money recieved by customers
 * frzTime - stops customer waiting time
 *
 * assertEquals(game.chefs.get(0).getInventory().pop(),  "pizza");
 */


@RunWith(GdxTestRunner.class)
public class poweruptest {

    TiledMap map;

    /**
     * Tests that reputation is increased by 1
     */
    public void testHeartRep(){

        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);
        game.repIncrease();
        assertEquals(game.getRepPoint(),  1);


    }
    /**
     * Tests that speed is increased
     */
    public void testFastIcon(){

    }
    /**
     * Tests that money is increased for each customer served
     */
    public void testMoneyIcon(){

    }


    /**
     * Tests that money is doubled for each customer served
     */
    public void testDoubleMoney(){

    }
    /**
     * Tests that time is freezed for a set time
     */
    public void testFreezeTime(){

    }














}
