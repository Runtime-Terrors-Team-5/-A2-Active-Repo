package com.neves6.piazzapanic;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class WinScreenTest {

    /**
     * Tests the gamewinscreen's variables
     */
    @Test
    public void testWinScreenVars(){
        PiazzaPanicGame A = new PiazzaPanicGame();
        GameWinScreen winscreen = new GameWinScreen(A, 50, 4);
        assertEquals(50, winscreen.returnVars().getValue(0));
        assertEquals(4, winscreen.returnVars().getValue(1));
    }

    /**
     * tests the output message functionality
     */
    @Test
    public void testOutputFunctionality(){
        PiazzaPanicGame A = new PiazzaPanicGame();
        GameWinScreen winscreen = new GameWinScreen(A, 50, 4);
        assertEquals("CONGRATULATIONS!\nYou completed the game in "
                + (int)winscreen.returnVars().getValue(0) + " seconds!\n" + "You served "
                + (int)winscreen.returnVars().getValue(1) + " customers.", winscreen.winMsg());
    }
}
