package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;

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

    public PowerUp(String PowerUpType, int xCoord, int yCoord, int time, Texture texture){
    this.powerUpType = PowerUpType;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.time = time;
    this.texture = texture;
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
    public void clearxCoord() {this.xCoord = 1000;}
    public void clearyCoord() {this.yCoord = 1000;}

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

