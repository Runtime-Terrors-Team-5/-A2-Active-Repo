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
public class ExistingmachinesTest {
    ArrayList<Machine> machines = new ArrayList<>();

    TiledMap map;

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
     * Old way of testing machines before code refactor
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
    public String getMachineFromGame(String item, Integer layer, String disoutput){
        map = new TmxMapLoader().load("tilemaps/level1.tmx");
        PiazzaPanicGame A = new PiazzaPanicGame();
        ScenarioGameMaster game = new ScenarioGameMaster(A, map, 1, 1, 1);

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

                            // dispenses ingredient
                            if (Objects.equals(game.chefs.get(0).getInventory().peek(), "")){
                                mac.process(game.chefs.get(0));
                                return game.chefs.get(0).getInventory().pop();
                                // does ingredient processing and returns chef inventory contents
                            } else if(Objects.equals(game.chefs.get(0).getInventory().peek(), mac.getInput())){
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
                    }
                }
            }

        }
        return "";
    }


    /**
     * New method of testing machines after code refactor
     * tests if given the correct input in chef inventory the machines returns the correct output
     * @throws InterruptedException
     */
    @Test
    public void testNewAddToChefInventory() throws InterruptedException {
        // pizza grill
        assertEquals((getMachineFromGame("uncooked_pizza",9,"")),  "pizza");
        // making station turns meat -> patty
        assertEquals((getMachineFromGame("meat",5,"")),  "patty");

        assertEquals((getMachineFromGame("patty",6,"")),   "burger");
        assertEquals((getMachineFromGame("bun",6,"")),   "toastedbun");

        assertEquals((getMachineFromGame("tomato",4,"")),  "choppedtomato");
        assertEquals((getMachineFromGame("lettuce",4,"")),  "choppedlettuce");
        assertEquals((getMachineFromGame("onion",4,"")),  "choppedonion");

    }
    @Test
    public void testNewAddDispensedItemsToChefInventory() throws InterruptedException {

        // dispensed items
        assertEquals((getMachineFromGame("",10,"tomato")),  "tomato");
        assertEquals((getMachineFromGame("",10,"lettuce")),  "lettuce");
        assertEquals((getMachineFromGame("",10,"onion")),  "onion");
        assertEquals((getMachineFromGame("",10,"meat")),  "meat");
        assertEquals((getMachineFromGame("",10,"bun")),  "bun");
        assertEquals((getMachineFromGame("",10,"onion")),  "onion");



    }




}
