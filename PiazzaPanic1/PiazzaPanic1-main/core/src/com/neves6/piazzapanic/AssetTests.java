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
        assertTrue("levelselector.atlas", Gdx.files.internal("levelselector.atlas").exists());
        assertTrue(" levelselector.png", Gdx.files.internal(" levelselector.png").exists());
    }
    @Test
    public void testTitleAssetsExists() {
        String[] myStrArr = {"black_alpha_low.png", "black_alpha_mid.png", "black_alpha_square.png", "levelselector.tpproj" , "title.tpproj"};

        assertTrue("save.txt", Gdx.files.internal("save.txt").exists());

    }
    @Test
    public void testFontsExists() {
        assertTrue("save.txt", Gdx.files.internal("save.txt").exists());
    }
}