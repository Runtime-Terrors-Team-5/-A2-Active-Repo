package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class ExistingmachinesTest {
    ArrayList<Machine> machines = new ArrayList<>();

    TiledMap map;
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

}
