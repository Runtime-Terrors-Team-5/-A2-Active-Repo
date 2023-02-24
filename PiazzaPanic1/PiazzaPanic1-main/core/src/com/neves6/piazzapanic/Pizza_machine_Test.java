package com.neves6.piazzapanic;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import com.neves6.piazzapanic.Chef;
import com.neves6.piazzapanic.Machine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;



/**
 * checks the oven / bakery outputs a pizza as a result
 * and all the machine inputs & outputs involved in creating a pizza with machines
 *
 */
public class Pizza_machine_Test {

    ArrayList<Machine> machines = new ArrayList<>();

    ArrayList<Chef> chefs = new ArrayList<>();

    /**
     * constructs related pizza machines for tests and sample chef
     */
    public void Pizza_machine_Test(){
        machines.add(new Machine("Pizza", "uncooked_pizza", "pizza", 3, true));
        machines.add(new Machine("fridgecheese", "", "cheese", 0, false));
        machines.add(new Machine("fridgedough", "", "dough", 0, false));
        // adds chef
        chefs.add(new Chef("Chef", 6, 5, 1, 1, 1, false, new Stack<String>(), 1));


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
     * tests if cheese and dough machines output the correct foods
     * as well as if they are added to the chef stack correctly
     */
    @Test
    public void testNewFridgeOutput() {
        assertEquals(machines.get(1).getOutput(),  "cheese");
        assertEquals(machines.get(2).getOutput(),  "dough");
        machines.get(1).process(chefs.get(0));
        assertEquals(chefs.get(0).getInventory().pop(),  "cheese");
        machines.get(2).process(chefs.get(0));
        assertEquals(chefs.get(0).getInventory().pop(),  "dough");

    }







}


