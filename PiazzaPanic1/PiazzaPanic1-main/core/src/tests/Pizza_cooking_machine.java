package tests;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;



// checks the oven / bakery outputs a pizza as a result
// and all the machine inputs & outputs involved in creating a pizza with machines
public class Pizza_cooking_machine {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPizzaOutput() {

        // insert tests here
    }
}


// syntax for comparing an output and what u want in methods
// assertTrue(equals(triNations.getTeamScore("South Africa"),
//    createTeamScore("South Africa", 2, 0, 0, 8, 3, 13)));