package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;
import org.javatuples.Quintet;

import java.util.ArrayList;
import java.util.List;

public class PowerUp {

    public String powerUpType;
    private int xCoord;
    private int yCoord;
    private int time;
    public boolean active;
    public Texture texture;
    public static List<PowerUp> PowerUps = new ArrayList<>();


    /**
     * Power up constructor.
     * @param PowerUpType a string declaring which power up the power up is
     * @param xCoord the int of the xCoord the power up collectable is rendered at
     * @param yCoord the int of the xCoord the power up collectable is rendered at
     * @param time int that ticks down, once time reaches 0 the power up is no longer collectable or active
     * @param texture the texture of the power up collectable
     */
    public PowerUp(String PowerUpType, int xCoord, int yCoord, int time, Texture texture) {
        this.powerUpType = PowerUpType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.time = time;
        this.texture = texture;
        this.active = false;
        PowerUps.add(this);
    }

    /**
     * constructor used when loading to respawn the powerups
     * @param PowerUpType a string declaring which power up the power up is
     * @param xCoord the int of the xCoord the power up collectable is rendered at
     * @param yCoord the int of the xCoord the power up collectable is rendered at
     * @param time int that ticks down, once time reaches 0 the power up is no longer collectable or active
     * @param texture the texture of the power up collectable
     * @param active boolean for whether the powerup effect is active or not
     */
    public PowerUp(String PowerUpType, int xCoord, int yCoord, int time, Texture texture, boolean active) {
        this.powerUpType = PowerUpType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.time = time;
        this.texture = texture;
        this.active = active;
        PowerUps.add(this);
    }

    public String getType() {
        return this.powerUpType;
    }

    public int getTime() {
        return this.time;
    }

    public int getxCoord() {
        return this.xCoord;
    }

    public int getyCoord() {
        return this.yCoord;
    }

    public void clearxCoord() {
        this.xCoord = 1000;
    }

    public void clearyCoord() {
        this.yCoord = 1000;
    }

    public void incrementTime() {
        this.time -= 1;
    }

    public void setTime() {
        this.time = 1000;
    }

    public void clearTime() {
        this.time = 0;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive() {
        this.active = true;
    }

    /**
     * pull data from all powerups and returns it to be used when saving and loading data
     * @return array of a tuple where tuples contain the data for the powerup
     */
    public static ArrayList<Quintet> generatePowerData() {
        ArrayList<Quintet> returnList = new ArrayList<>();
        for (PowerUp inst : PowerUp.PowerUps) {
            returnList.add(new Quintet(inst.getType(),inst.getxCoord(),inst.getyCoord(),
                inst.getTime(),inst.getActive()));
        }
        return returnList;
    }
}
