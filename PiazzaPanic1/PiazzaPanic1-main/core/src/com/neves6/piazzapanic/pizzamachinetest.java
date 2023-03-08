package com.neves6.piazzapanic;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;



/**
 * checks the oven / bakery outputs a pizza as a result
 * and all the machine inputs & outputs involved in creating a pizza with machines
 *
 */
public class pizzamachinetest {

    ArrayList<Machine> machines = new ArrayList<>();


    ArrayList<Chef> chefs = new ArrayList<>();

    public pizzamachinetest(){
        PiazzaPanicGame Game = new PiazzaPanicGame();
        //GameScreen NewGame = new GameScreen(Game, 1);
        chefs.add(new Chef("A",1,1, 1));
        //chefs.add(new Chef("Chef", 6, 5, 1, 1, 1, false, new Stack<String>(), 1));


    }


    /**
     * sample test comparing outputs of machine constructor
     */
    @Test
    public void testPizzaMachineOutput() {
        machines.add(new Machine("Pizza", "uncooked_pizza", "pizza", 3, true));
        assertEquals(machines.get(0).getOutput(),  "Pizza");
    }

    /**
     * tests if pizza is added to the chef stack
     */
    @Test
    public void testPizzaAddedToChefInventory() {
        machines.add(new Machine("Pizza", "uncooked_pizza", "pizza", 3, true));
        machines.get(0).process(chefs.get(0));
        assertEquals(chefs.get(0).getInventory().pop(),  "Pizza");


    }

    /**
     * tests if cheese and dough are added to the chef stack
     */
    @Test
    public void testCheeseDoughAddedToChefInventory() {
        chefs.add(new Chef("Chef", 6, 5, 1, 1, 1, false, new Stack<String>(), 1));

        machines.get(1).process(chefs.get(0));
        assertEquals(chefs.get(0).getInventory().pop(),  "cheese");
        machines.get(2).process(chefs.get(0));
        assertEquals(chefs.get(0).getInventory().pop(),  "dough");


    }

    /**
     * tests if cheese and dough machines output the correct foods
     * as well as if they are added to the chef stack correctly
     */
    @Test
    public void testNewFridgeOutput() {
        machines.add(new Machine("fridgecheese", "", "cheese", 0, false));
        machines.add(new Machine("fridgedough", "", "dough", 0, false));
        assertEquals(machines.get(1).getOutput(),  "cheese");
        assertEquals(machines.get(2).getOutput(),  "dough");

    }


    public void main(String[] args){
        pizzamachinetest test = new pizzamachinetest();
        test.testCheeseDoughAddedToChefInventory();
    }


}


