package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;
import org.javatuples.Septet;
import org.javatuples.Sextet;

/**
 * Machine class.
 * Represents a machine or station in the game.
 */
public class Machine{
    private final String type;
    private final String input;
    private final String output;
    private final float processingTime;
    private final Boolean sticky;
    private Boolean active;
    private float runtime;
    private Chef operator;

    /**
     * Machine constructor.
     * @param type Type of machine.
     * @param input Input ingredient.
     * @param output Output ingredient.
     * @param processingTime Processing time.
     * @param sticky Whether or not the machine locks the chef in place during use.
     */
    public Machine(String type, String input, String output, float processingTime, Boolean sticky){
        this.type = type;
        this.input = input;
        this.output = output;
        this.processingTime = processingTime;
        this.sticky = sticky;
        this.active = false;
    }

    public Machine(Septet machine, ArrayList<Chef> chefs) {
        this.type = "";
        this.input = (String) machine.getValue0();
        this.output = (String) machine.getValue1();
        this.processingTime = (float) machine.getValue2();
        this.sticky = (Boolean) machine.getValue3();
        this.active = (Boolean) machine.getValue5();
        this.operator = chefs.get((Integer) machine.getValue4()-1);
        this.runtime = (float) machine.getValue6();
    }

    /**
     * Begins the machine processing of the ingredient.
     * @param chef Which chef is using the machine.
     */
    public void process(Chef chef){
        if (input == "" && processingTime == 0) {
            chef.addToInventory(output);
        } else if (chef.getInventory().peek() == input) {
            active = true;
            chef.getInventory().pop();
            chef.setIsStickied(sticky);
            chef.setMachineInteractingWith(this);
            operator = chef;
        }
    }

    /**
     * Checks if the machine is done processing and adds the output to the chef's inventory if it is.
     * Handles unsticking the chef.
     */
    public void attemptGetOutput(int SelectedChef){
        Chef chef = operator;
        if (active && runtime >= processingTime) {
            if (Gdx.input.isKeyJustPressed(Keys.E) && SelectedChef == (chef.getChefNumb()-1)){
                chef.addToInventory(output);
                chef.setIsStickied(false);
                chef.setMachineInteractingWith(null);
                active = false;
                runtime = 0;
            }
        }
        if (active && runtime >= processingTime*3){
            chef.addToInventory("Junk");
            chef.setIsStickied(false);
            chef.setMachineInteractingWith(null);
            active = false;
            runtime = 0;
        }
    }
    public void attemptGetOutput(){
        Chef chef = operator;
        if (active && runtime >= processingTime) {
            chef.addToInventory(output);
            chef.setIsStickied(false);
            chef.setMachineInteractingWith(null);
            active = false;
            runtime = 0;
            }
        }


    public void incrementRuntime(float delta){
        this.runtime += delta;
    }

    public float getRuntime(){
        return runtime;
    }
    public boolean getActive(){
        return active;
    }
    public float getProcessingTime(){
        return processingTime;
    }

    public String getInput() {return input;}

    public String getOutput(){
        return output;
    }  // game test

    public Septet getMachineInfo(){
        if (this.operator == null){return new Septet(input, output, processingTime, sticky, 1, active,
            runtime);}
        else {
            return new Septet(input, output, processingTime, sticky, operator.getChefNumb(), active,
                runtime);
        }
    }
    public void fastForwardTime(Boolean active, float runtime){
        this.active = active;
        this.runtime = runtime;

    }
}
