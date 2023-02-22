package tests;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import com.neves6.piazzapanic.Machine;
import org.junit.Test;

import java.util.ArrayList;


// checks the oven / bakery outputs a pizza as a result
// and all the machine inputs & outputs involved in creating a pizza with machines
public class Pizza_cooking_machine {

    ArrayList<Machine> machines = new ArrayList<>();
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPizzaOutput() {
        // sample test comparing outputs
        machines.add(new Machine("chopping2onion", "onion", "choppedonion", 3, true));
        assertEquals(machines.get(0).getOutput(),  "choppedonion");


    }
}


// syntax for comparing an output and what u want in methods
// assertTrue(equals(triNations.getTeamScore("South Africa"),
//    createTeamScore("South Africa", 2, 0, 0, 8, 3, 13)));