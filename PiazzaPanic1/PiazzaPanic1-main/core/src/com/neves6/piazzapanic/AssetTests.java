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
        assertTrue("black_alpha_low.png", Gdx.files.internal("buttons/black_alpha_low.png").exists());
        assertTrue("black_alpha_mid.png", Gdx.files.internal("buttons/black_alpha_mid.png").exists());
        assertTrue("black_alpha_square.png", Gdx.files.internal("buttons/black_alpha_square.png").exists());
        assertTrue("levelselector.tpproj", Gdx.files.internal("buttons/levelselector.tpproj").exists());

    }
    @Test
    public void testFontsExists() {
        String[] myStrArr = {"fonts/IBM_Plex_Mono_SemiBold.fnt ","fonts/IBM_Plex_Mono_SemiBold.png" ,"fonts/IBM_Plex_Mono_SemiBold_Black.fnt" ,"fonts/IBM_Plex_Mono_SemiBold_Black.png"};
        for (String s : myStrArr) {
            assertTrue(s.substring(6), Gdx.files.internal(s).exists());
        }
    }

    @Test
    public void testFoodsExists() {
        String[] myStrArr = {"foods/bakedPotato.png" ,
                "foods/bun.png" ,
                "foods/burger.png",
                "foods/cheese.png" ,
                "foods/choppedlettuce.png" ,
                "foods/choppedonion.png" ,
                "foods/choppedtomato.png" ,
                "foods/completed burger.png" ,
                "foods/completed salad.png" ,
                "foods/dough.png" ,
                "foods/hamburger.png" ,
                "foods/lettuce.png" ,
                "foods/meat.png" ,
                "foods/onion.png" ,
                "foods/patty.png" ,
                "foods/pizza.png" ,
                "foods/salad.png" ,
                "foods/toastedbun.png" ,
                "foods/tomato.png" ,
                "foods/uncooked_pizza.png"};
        for (String s : myStrArr) {
            assertTrue(s.substring(6), Gdx.files.internal(s).exists());
        }
    }

    @Test
    public void testIconsExists() {
        String[] myStrArr = {"icons/bar1.png",
               "icons/bar2.png",
               "icons/cheficons1.png",
               "icons/cheficons2.png",
              "icons/cheficons3.png",
                "icons/fastIcon.png",
              "icons/frzTimeIcon.png",
                "icons/locked.png",
              "icons/minusRepIcon.png",
               "icons/moneyIcon.png",
             "icons/moneyUI.png",
               "icons/repIcon.png",
                "icons/repPoints0.png",
                "icons/repPoints1.png",
                "icons/repPoints2.png",
                "icons/repPoints3.png"};
        for (String s : myStrArr) {
            assertTrue(s.substring(6), Gdx.files.internal(s).exists());
        }
    }


    @Test
    public void testChefAndCustomerExists() {
        String[] myStrArr = {"people/chef1/chef1.atlas",
                "people/chef1/chef1.png",
                "people/chef2/chef2.atlas",
                "people/chef2/chef2.png",
                "people/cust1/cust1.atlas",
                "people/cust1/cust1.png",
                "people/chef1down.png",
                "people/chef1left.png",
                "people/chef1right.png",
                "people/chef1up.png",
                "people/chef2down.png",
                "people/chef2left.png",
                "people/chef2right.png",
                "people/chef2up.png",
                "people/chef3down.png",
                "people/chef3left.png",
              "people/chef3right.png",
                "people/chef3up.png",
                "people/cust1down.png",
                "people/cust1left.png",
                "people/cust1right.png",
                "people/cust1up.png",
                "people/selected.png"};
        for (String s : myStrArr) {
            assertTrue(s.substring(6), Gdx.files.internal(s).exists());
        }
    }

    @Test
    public void testSoundsExists() {
        String[] myStrArr = {
                "sounds/background.mp3",
                "sounds/chopping.mp3",
                "sounds/ding.mp3",
                "sounds/forming.mp3",
                "sounds/fridge.mp3",
                "sounds/grill.mp3",
                "sounds/serving.mp3",
                "sounds/trash.mp3",
               };
        for (String s : myStrArr) {
            assertTrue(s.substring(6), Gdx.files.internal(s).exists());
        }
    }

    @Test
    public void testTilemapsExists() {
        String[] myStrArr = {
                "tilemaps/tiles/cafeteria_floor.png",
                "tilemaps/tiles/choppingtable.png",
               "tilemaps/tiles/cleaver.png",
               "tilemaps/tiles/collide.png",
              "tilemaps/tiles/disposal.png",
                "tilemaps/tiles/formingtable.png",
                "tilemaps/tiles/formingtable_vert.png",
                "tilemaps/tiles/free.png",
               "tilemaps/tiles/freezer_floor.png",
                "tilemaps/tiles/fridgebun.png",
                "tilemaps/tiles/fridgeCheese.png",
                "tilemaps/tiles/fridgeclosed.png",
                "tilemaps/tiles/fridgeDough.png",
                "tilemaps/tiles/fridgelettuce.png",
                "tilemaps/tiles/fridgemeat.png",
                "tilemaps/tiles/fridgeonion.png",
                "tilemaps/tiles/fridgeopen.png",
                "tilemaps/tiles/fridgepotato.png",
                "tilemaps/tiles/fridgetomato.png",
                "tilemaps/tiles/fryeroff.png",
                "tilemaps/tiles/fryeron.gif",
                "tilemaps/tiles/goldgrill.png",
                "tilemaps/tiles/grilloff.png",
                "tilemaps/tiles/grillon.gif",
                "tilemaps/tiles/letthatsinkin.png",
                "tilemaps/tiles/machine.png",
                "tilemaps/tiles/pizzagrill.png",
                "tilemaps/tiles/pizzatable.png",
                "tilemaps/tiles/plastic_floor.png",
                "tilemaps/tiles/servingtable.png",
                "tilemaps/tiles/tableleft.png",
                "tilemaps/tiles/tablemid.png",
                "tilemaps/tiles/tableparts.pdn",
                "tilemaps/tiles/tableright.png",
                "tilemaps/tiles/wallbottom.png",
                "tilemaps/tiles/wallbottomleft.png",
                "tilemaps/tiles/wallbottomright.png",
                "tilemaps/tiles/wallfull.png",
                "tilemaps/tiles/wallinnerbottomleft.png",
                "tilemaps/tiles/wallinnerbottomright.png",
                "tilemaps/tiles/wallinnerleftbottom.png",
                "tilemaps/tiles/wallinnertopbottomleft.png",
                "tilemaps/tiles/wallinnertopbottomright.png",
                "tilemaps/tiles/wallleft.png",
                "tilemaps/tiles/wallleftright.png",
                "tilemaps/tiles/wallouterbottomrightinnerleft.png",
                "tilemaps/tiles/wallouterright.png",
                "tilemaps/tiles/walloutertopleftinnerbottomright.png",
                "tilemaps/tiles/wallright.png",
                "tilemaps/tiles/walltop.png",
                "tilemaps/tiles/walltopbottom.png",
                "tilemaps/tiles/walltopleft.png",
                "tilemaps/tiles/walltopright.png",
                "tilemaps/tiles/white_floor.png",


        };
        for (String s : myStrArr) {
            assertTrue(s.substring(14), Gdx.files.internal(s).exists());
        }
    }
    @Test
    public void testTiles(){
        String[] myStrArr = {
        "tilemaps/level1.tmx",
                "tilemaps/piazzapanic.tiled-project",
                "tilemaps/silver_wall.png",
                "tilemaps/tiles.tsx",};
        String[] list2 = {
                "badlogic.jpg",
                "controls.png",
                "credits.png",
                "intro_sheet.png",
                "leaderboard.txt",
                "levellocked.png",
                "recipes.png",
                "Runtime_Terrors_Logo.png",
                "save.txt",
                "settings.txt",
                "title_screen_large-min.png",
                "tutorial.pdn",
                "tutorial.png"};

        for (String s : myStrArr) {
            assertTrue(s.substring(9), Gdx.files.internal(s).exists());
        }
        for (String s : list2) {
            assertTrue(s, Gdx.files.internal(s).exists());
        }

    }




}