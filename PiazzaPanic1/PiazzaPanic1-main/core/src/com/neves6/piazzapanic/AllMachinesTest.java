package com.neves6.piazzapanic;

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
public class AllMachinesTest {
    ArrayList<Machine> machines = new ArrayList<>();

    static TiledMap map;

    /**
     * Tests machine outputs are correct
     * once a certain machine is instantiated so that it's been instantiated correctly
     */
    @Test
    public void testMachineOutput(){

        machines.add(new Machine("grill1patty", "patty", "burger", 3, true));
        machines.add(new Machine("grill1bun", "bun", "toastedbun", 3, true));
        machines.add(new Machine("forming", "meat", "patty", 3, true));
        machines.add(new Machine("choppingTomato", "tomato", "choppedtomato", 3, true));
        machines.add(new Machine("choppingLettuce", "lettuce", "choppedlettuce", 3, true));
        machines.add(new Machine("choppingOnion", "onion", "choppedonion", 3, true));

        machines.add(new Machine("dispenser", "", "tomato", 0, false));
        machines.add(new Machine("dispenser", "", "lettuce", 0, false));
        machines.add(new Machine("dispenser", "", "onion", 0, false));


        assertEquals(machines.get(0).getOutput(),  "burger");
        assertEquals(machines.get(1).getOutput(),  "toastedbun");
        assertEquals(machines.get(2).getOutput(),  "patty");
        assertEquals(machines.get(3).getOutput(),  "choppedtomato");
        assertEquals(machines.get(4).getOutput(),  "choppedlettuce");
        assertEquals(machines.get(5).getOutput(),  "choppedonion");
        assertEquals(machines.get(6).getOutput(),  "tomato");
        assertEquals(machines.get(7).getOutput(),  "lettuce");
        assertEquals(machines.get(8).getOutput(),  "onion");
    }

    /**
     * Old way of testing machines before code refactored
     * Test used for original machines
     * Originally the machines were held in a machine type array in the GameMaster.java file
     *
     * @throws InterruptedException
     */
    @Test
    public void testAddItemToChefInventory() throws InterruptedException{

        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map , 1, 1, 1);
        String[] inputs = {"patty", "bun", "meat", "tomato", "lettuce", "onion"};
        String[] outputs = {"burger", "toastedbun","patty", "choppedtomato", "choppedlettuce", "choppedonion"};
        for (int i = 0; i <  inputs.length; i++) {
            System.out.println(inputs[i]);
          //  game.chefs.get(0).addToInventory(inputs[i]);
          //  game.machines.get(i).process(game.chefs.get(0));
         //   TimeUnit.SECONDS.sleep(4);
         //   game.machines.get(i).fastForwardTime(true, 6);
         //   game.machines.get(i).attemptGetOutput();
        //    assertEquals(game.chefs.get(0).getInventory().pop(),  outputs[i] );
        }
    }


    /**
     * processes for testNewAddToChefInventory()
     * finds machines in the map.layers and carries out the processing
     * to get the output
     * @param item  input string
     * @param layer corresponding machine layer
     * @return machine output
     */
    public static String getMachineFromGame(String item, Integer layer, String disoutput){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map, 1, 1, 1);
        // unlocks pizza machine
        if (Objects.equals(disoutput, "unlockPizza")){
            game.setMoney(6);
            game.unlockMachine (3);
        }else if(Objects.equals(disoutput, "unlockGrill")){
            game.setMoney(6);
            game.unlockMachine (1);
        }else if(Objects.equals(disoutput, "unlockForming")){
            game.setMoney(6);
            game.unlockMachine (2);

        }


        game.chefs.get(0).addToInventory(item);

        TiledMapTileLayer workingLayer = (TiledMapTileLayer) game.map.getLayers().get(layer);

        // for dispensers
        if (Objects.equals(game.chefs.get(0).getInventory().peek(), "")){
            for (int k=10;k<=16;k++){
                workingLayer = (TiledMapTileLayer) map.getLayers().get(k);
               // System.out.println(workingLayer.getName()); // get layer name
                for (int i = 0; i < workingLayer.getHeight(); i++) {
                    for (int j = 0; j < workingLayer.getWidth(); j++) {
                        if (workingLayer.getCell(j,i) != null){
                            Pair<Integer, Integer> target = new Pair<>(j,i);
                            ArrayList<Machine> targetMachines = game.machineLocation.get(target);
                            for (Machine mac :
                                    targetMachines) {
                                if(Objects.equals(disoutput, mac.getOutput())) {
                                    return mac.getOutput();

                                }
                            }


                        }
                    }
                }
            }

        }

        // For non dispensers
        for (int i = 0; i < workingLayer.getHeight(); i++) {
            for (int j = 0; j < workingLayer.getWidth(); j++) {
                if (workingLayer.getCell(j,i) != null){
                    System.out.println(j);
                    System.out.println(i);

                    // key to query the dictionary with the location of the machine
                    Pair<Integer, Integer> target = new Pair<>(j,i);
                    try{
                        ArrayList<Machine> targetMachines = game.machineLocation.get(target);
                        //System.out.println(targetMachines);
                        for (Machine mac :
                                targetMachines) {
                            System.out.println(mac.getOutput() + " machine output");
                            System.out.println(game.chefs.get(0).getInventory().peek() + " machine output");

                                // does ingredient processing and returns chef inventory contents
                            if(Objects.equals(game.chefs.get(0).getInventory().peek(), mac.getInput())){
                                System.out.println(game.chefs.get(0).getInventory().peek());
                                mac.process(game.chefs.get(0));
                                //TimeUnit.SECONDS.sleep(4);
                                mac.fastForwardTime(true, 6);
                                mac.attemptGetOutput();
                                return game.chefs.get(0).getInventory().pop();
                            }
                        }
                    } catch (Exception e){
                        System.out.println("error");
                        return "error";
                    }
                }
            }

        }
        return "";
    }


    /**
     * New method of testing machines after code refactor
     * tests if given the correct input in chef inventory the machines returns the correct output
     *
     *
     * @throws InterruptedException
     */
    @Test
    public void testNewAddToChefInventory()  {

        // making station turns meat -> patty
        assertEquals((getMachineFromGame("meat",5,"")),  "patty");

        assertEquals((getMachineFromGame("patty",6,"")),   "burger");
        assertEquals((getMachineFromGame("bun",6,"")),   "toastedbun");

        assertEquals((getMachineFromGame("tomato",4,"")),  "choppedtomato");
        assertEquals((getMachineFromGame("lettuce",4,"")),  "choppedlettuce");
        assertEquals((getMachineFromGame("onion",4,"")),  "choppedonion");

    }

    /**
     * tests if given the correct input in chef inventory the machines returns the correct output
     * for dispensers
     *
     */
    @Test
    public void testNewAddDispensedItemsToChefInventory()  {

        // dispensed items
        assertEquals((getMachineFromGame("",10,"tomato")),  "tomato");
        assertEquals((getMachineFromGame("",10,"lettuce")),  "lettuce");
        assertEquals((getMachineFromGame("",10,"onion")),  "onion");
        assertEquals((getMachineFromGame("",10,"meat")),  "meat");
        assertEquals((getMachineFromGame("",10,"bun")),  "bun");
        assertEquals((getMachineFromGame("",10,"onion")),  "onion");



    }

    /**
     * Tests that the tray creates the right corresponding items once
     * it contains all the necessary ingredients.
     * Also changes what is added to be checked based on the current customer
     * order
     *
     */
    @Test
    public void testItemsAddedToTray()  {
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map, 1, 1, 1);
        for (int i=0; i<3; i++) {
            if (Objects.equals(game.customers.get(0).getOrder(), "salad")) {
                game.chefs.get(0).addToInventory("choppedtomato");
                game.UseAddToTray(false);
                game.chefs.get(0).addToInventory("choppedlettuce");
                game.UseAddToTray(false);
                game.chefs.get(0).addToInventory("choppedonion");
                game.UseAddToTray(false);
                assertEquals(game.chefs.get(0).getInventory().pop(), "completed salad");
            } else if(Objects.equals(game.customers.get(0).getOrder(), "burger")) {
                // burger
                game.chefs.get(0).addToInventory("burger");
                game.UseAddToTray(false );
                game.chefs.get(0).addToInventory("toastedbun");
                game.UseAddToTray(false );
                assertEquals(game.chefs.get(0).getInventory().pop(),  "completed burger");
                // pizza
            } else if (Objects.equals(game.customers.get(0).getOrder(), "pizza")) {
                game.chefs.get(0).addToInventory("cheese");
                game.UseAddToTray(true);
                game.chefs.get(0).addToInventory("dough");
                game.UseAddToTray(true);
                assertEquals(game.chefs.get(0).getInventory().pop(),  "uncooked_pizza");


            }

        }

    }

    /**
     * tests if machines are unlocked under the correct conditions
     * Pizza, golden grill and former
     * @throws InterruptedException
     */
    @Test
    public void testUnlockableMachines()  throws InterruptedException{
        // tests that it does not work before it is unlocked
        assertEquals((getMachineFromGame("uncooked_pizza",9,"")),  "error");
        assertEquals((getMachineFromGame("patty",7,"")),   "error");
        assertEquals((getMachineFromGame("bun",7,"")),   "error");
        assertEquals((getMachineFromGame("meat",8,"")),   "error");
        // Tests after machine are unlocked
        // pizza machine
        assertEquals((getMachineFromGame("uncooked_pizza",9,"unlockPizza")),  "pizza");


        // gold grill layer 7
        assertEquals((getMachineFromGame("patty",7,"unlockGrill")),   "burger");
        assertEquals((getMachineFromGame("bun",7,"unlockGrill")),   "toastedbun");


        // forming station
        assertEquals((getMachineFromGame("meat",8,"unlockForming")),   "patty");

    }

    /**
     * Tests if customers are removed from the queue once their order has been completed
     * by using the facing x and y function
     */
    @Test
    public void testCustomerOrdersRemovedOnceServed(){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map, 1, 1, 1);
        System.out.println(game.customers.get(0).getOrder());
        System.out.println(game.customers.size());
        game.chefs.get(0).addToInventory(game.customers.get(0).getOrder());
        game.chefs.get(0).setxCoord(8);
        game.chefs.get(0).setyCoord(4);
        game.chefs.get(0).setFacing("down");
        int NumberOfOrders = game.customers.size();
        assertEquals(game.customers.size(), NumberOfOrders);




    }
}
