package com.neves6.piazzapanic;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

/**
 * Tests whether the interacted power ups collected by
 * the user are applied to the correct chef
 *
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

        game.generatePowerUpTest(0);
        game.getPowerUp();
        game.powerUpEffect();
        assertEquals(game.getRepPoint(),  3);


    }
    /**
     * Tests that speed is increased by checking the processing time of the machine once the power up is collected
     * the time waited is 3 seconds compared to 6 seconds for the same pizza test in
     * pizzamachineTest.testPizzaAddedToChefInventory()s
     * this means that the power up worked as it took half the time to process the pizza
     */
    @Test
    public void testFastIcon() throws InterruptedException {

        Texture texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);

        game.generatePowerUpTest(1);

        game.getPowerUp();
        game.powerUpEffect();



        assertEquals((AllMachinesTest.getMachineFromGame("uncooked_pizza",9,"")),  "pizza");


    }
    /**
     * Tests that money is increased for each customer served, +5 in int money value
     */
    @Test
    public void testMoneyIcon(){

        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);
        Texture texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));
        game.generatePowerUpTest(2);
        game.getPowerUp();
        game.powerUpEffect();

        assertEquals(game.getMoney(),  5);


    }


    /**
     * Tests that the reputation point is decreased if the power up is collected ( rep -1)
     */
    @Test
    public void testMinusReputation(){

        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);

        game.generatePowerUpTest(3);


        game.getPowerUp();
        game.powerUpEffect();

        assertEquals(game.getRepPoint(),  2);

    }
    /**
     * Tests that time is freezed for a set time
     */
    @Test
    public void testFreezeTime() throws InterruptedException {

        Texture texture = new Texture(Gdx.files.internal("icons/frzTimeIcon.png"));
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);
        // insert time freeze here

        game.generatePowerUpTest(4);

        game.getPowerUp();
        game.powerUpEffect();

        float lastTime = game.getTotalTimer();

        TimeUnit.SECONDS.sleep(4);

        assertEquals(game.getTotalTimer(),  lastTime, 0.1);


    }














}
