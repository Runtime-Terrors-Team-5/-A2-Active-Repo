package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testShipAssetExists() {
        assertTrue("This test will only pass when the save.txt asset exists.", Gdx.files.internal("save.txt").exists());
    }
}