package com.neves6.piazzapanic;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
     * Reputation starts at 3 initially
     */
    @Test
    public void testHeartRep(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        Texture texture = new Texture(Gdx.files.internal("icons/repIcon.png"));
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);

        new PowerUp("rep",1,1,1,texture );
        game.IncreasePowerUpCount();
        game.chefs.get(0).setxCoord(1);
        game.chefs.get(0).setyCoord(1);
        game.getPowerUp();
        game.powerUpEffect();
        //game.repIncrease();
        assertEquals(game.getRepPoint(),  3);


    }
    /**
     * Tests that speed is increased
     */
    @Test
    public void testFastIcon(){

    }
    /**
     * Tests that money is increased for each customer served
     */
    @Test
    public void testMoneyIcon(){

    }


    /**
     * Tests that money is doubled for each customer served
     */
    @Test
    public void testDoubleMoney(){

    }
    /**
     * Tests that time is freezed for a set time
     */
    @Test
    public void testFreezeTime(){

        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);
        

    }














}
