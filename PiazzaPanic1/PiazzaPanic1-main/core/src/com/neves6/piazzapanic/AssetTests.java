package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import org.junit.Test;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

/**
 * Checks assets exist
 */
@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testLevelSelectorExists() {
        assertTrue("levelselector.atlas", Gdx.files.internal("buttons/levelselector/levelselector.atlas").exists());
        assertTrue(" levelselector.png", Gdx.files.internal("buttons/levelselector/levelselector.png").exists());
    }
    @Test
    public void testTitleAssetsExists() {
        String[] myStrArr = {"buttons/title/black_alpha_low.png", "buttons/title/black_alpha_mid.png", "buttons/title/black_alpha_square.png", "buttons/title/levelselector.tpproj" };
        for (int i=0; i<myStrArr.length;i++) {
            assertTrue(myStrArr[i], Gdx.files.internal(myStrArr[i]).exists());
        }

    }
    @Test
    public void testFontsExists() {
        String[] myStrArr = {"fonts/IBM_Plex_Mono_SemiBold.fnt ","fonts/IBM_Plex_Mono_SemiBold.png" ,"fonts/IBM_Plex_Mono_SemiBold_Black.fnt" ,"fonts/IBM_Plex_Mono_SemiBold_Black.png"};
        for (String s : myStrArr) {
            assertTrue(s, Gdx.files.internal(s.substring(6)).exists());
        }
    }

    @Test
    public void testFoodsExists() {
        String[] myStrArr = {"foods/bakedPotato.png\n" ,
                "foods/bun.png\n" ,
                "foods/burger.png\n",
                "foods/cheese.png\n" ,
                "foods/choppedlettuce.png\n" ,
                "foods/choppedonion.png\n" ,
                "foods/choppedtomato.png\n" ,
                "foods/completed burger.png\n" ,
                "foods/completed salad.png\n" ,
                "foods/dough.png\n" ,
                "foods/hamburger.png\n" ,
                "foods/lettuce.png\n" ,
                "foods/meat.png\n" ,
                "foods/onion.png\n" ,
                "foods/patty.png\n" ,
                "foods/pizza.png\n" ,
                "foods/salad.png\n" ,
                "foods/toastedbun.png\n" ,
                "foods/tomato.png\n" ,
                "foods/uncooked_pizza.png"};
        for (String s : myStrArr) {
            assertTrue(s, Gdx.files.internal(s.substring(5, s.length())).exists());
        }
    }
}