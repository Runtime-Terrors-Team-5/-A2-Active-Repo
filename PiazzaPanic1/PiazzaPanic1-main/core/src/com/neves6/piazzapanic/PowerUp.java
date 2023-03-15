package com.neves6.piazzapanic;

import java.util.ArrayList;
import java.util.List;

public class PowerUp {
    private String powerUpType;
    private int xCoord;
    private int yCoord;
    private int time;
    private boolean active;
    public static List<PowerUp> PowerUps = new ArrayList<>();

    public PowerUp(String PowerUpType, int xCoord, int yCoord, int time){
    this.powerUpType = PowerUpType;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.time = time;
    this.active = false;
    PowerUps.add(this);
    }

    public String getType(){
        return this.powerUpType;
    }

    public int getTime(){
        return this.time;
    }

    public int getxCoord(){
        return this.xCoord;
    }

    public int getyCoord(){
        return this.yCoord;
    }

    public void incrementTime(){
        this.time -= 1;
    }

    public boolean getActive(){
        return this.active;
    }

    public void setActive(){
        this.active = true;
    }

    public void powerUpEffect(){
        for(PowerUp inst: PowerUp.PowerUps){
            if(inst.active == true){
                System.out.println("yay");
            }
        }
    }

}

