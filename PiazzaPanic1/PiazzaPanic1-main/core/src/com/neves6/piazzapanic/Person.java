package com.neves6.piazzapanic;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import org.javatuples.Quartet;
import org.javatuples.Sextet;

import java.util.*;

/**
 * Base person class.
 */
public class Person{
    private final String name;
    private int xCoord;
    private int yCoord;

    /**
     * Person constructor.
     * @param name Name of person.
     * @param xCoord logical x coordinate of person.
     * @param yCoord logical y coordinate of person.
     */
    public Person(String name, int xCoord, int yCoord){
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
    public void setxCoord(int xCoord){
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord){
        this.xCoord = yCoord;
    }
    public void alterxCoord(int xDelta){
        this.xCoord += xDelta;
    }
    public void alteryCoord(int yDelta){
        this.yCoord += yDelta;
    }

}

/**
 * Customer subclass.
 */
class Customer extends Person{

    private String order;
    private final Texture txUp;
    private final Texture txLeft;

    private float timer;
    private float maxTimer;
    private Texture orderTexture;

    /**
     * Customer constructor.
     * @param name Name of customer.
     * @param xCoord logical x coordinate of customer.
     * @param yCoord logical y coordinate of customer.
     * @param order Order of customer.
     */
    public Customer(String name, int xCoord, int yCoord, String order){
        super(name, xCoord, yCoord);
        this.order = order;
        this.timer = 20;
        this.maxTimer = timer;
        this.txUp = new Texture("people/cust1up.png");
        this.txLeft = new Texture("people/cust1left.png");

    }
    public Customer(String name, int xCoord, int yCoord, String order,float timer){
        super(name, xCoord, yCoord);
        this.order = order;
        this.timer = timer;
        this.maxTimer = timer;
        this.txUp = new Texture("people/cust1up.png");
        this.txLeft = new Texture("people/cust1left.png");
        if (Objects.equals(order, "salad")){
            this.orderTexture = new Texture("foods/salad.png");
        }
        else if (Objects.equals(order, "burger")) {
            this.orderTexture = new Texture("foods/hamburger.png");
        }
        else if (Objects.equals(order, "pizza")){
            this.orderTexture = new Texture("foods/pizza.png");
        }
    }

    public String getOrder(){
        return order;
    }

    public void setOrder(String inputOrder) {
        this.order = inputOrder;
    }

    public Texture getTxUp(){
        return txUp;
    }

    public Texture getTxLeft(){
        return txLeft;
    }

    public Texture getOrderTexture(){
        return orderTexture;
    }

    public void timerDecrease(float delta){this.timer -= delta;}


    public float getTimer() {return timer;}
    public float getMaxTimer() {return maxTimer;}

    public Quartet<Integer, Integer, String, Float> getCustomerData(){
        return new Quartet<>(getxCoord(),getyCoord(),getOrder(),getTimer());
    }

}

/**
 * Chef subclass.    made public
 */
class Chef extends Person {
    private boolean isStickied;
    private Stack<String> inventory;
    private String facing;
    private final Texture txUp;
    private final Texture txDown;
    private final Texture txLeft;
    private final Texture txRight;
    private final Texture uiIcon;
    private Texture txNow;
    private boolean isInteracting;
    private Machine machineInteractingWith;
    private int chefNumb;
    // holds textures so it can be run independently
    private FileHandle file;
    private List<Texture> invItems;

    /**
     * Chef constructor.
     * @param name
     * @param xCoord x coordinate of the chef
     * @param yCoord y coordinate of the chef
     * @param chopSpeed
     * @param frySpeed
     * @param bakeSpeed
     * @param isStickied stickys the chef so they cannot move when interacting with a machine
     * @param inventory
     * @param textureSet
     */
    public Chef(String name, int xCoord, int yCoord, int chopSpeed, int frySpeed, int bakeSpeed, boolean isStickied, Stack<String> inventory, int textureSet){
        super(name, xCoord, yCoord);
        this.isStickied = isStickied;
        this.inventory = inventory;
        this.chefNumb = textureSet;
        this.inventory = new Stack<String>();
        this.facing = "down";
        this.txUp =    new Texture("people/chef" + textureSet + "up.png");
        this.txDown =  new Texture("people/chef" + textureSet + "down.png");
        this.txLeft =  new Texture("people/chef" + textureSet + "left.png");
        this.txRight = new Texture("people/chef" + textureSet + "right.png");
        this.uiIcon = new Texture("icons/cheficons.png");
        this.txNow = txDown;
        this.invItems = new ArrayList<>();
    }

    /**
     * constructor for use when loading the game from a file
     * @param name
     * @param chef tuple containing the data for the chef
     */

    public Chef(String name,Sextet chef){
        super(name, (int) chef.getValue0(), (int) chef.getValue1());
        this.isStickied = (boolean) chef.getValue4();
        this.inventory = (Stack<String>) chef.getValue3();

        this.facing = (String) chef.getValue2();
        this.chefNumb = (int) chef.getValue5();
        this.txUp =    new Texture("people/chef" + chef.getValue5() + "up.png");
        this.txDown =  new Texture("people/chef" + chef.getValue5() + "down.png");
        this.txLeft =  new Texture("people/chef" + chef.getValue5() + "left.png");
        this.txRight = new Texture("people/chef" + chef.getValue5() + "right.png");
        this.uiIcon = new Texture("icons/cheficons.png");

        if (Objects.equals(this.facing, "up")){this.txNow = txUp;}
        else if (Objects.equals(this.facing, "down")){this.txNow = txDown;}
        else if (Objects.equals(this.facing, "left")){this.txNow = txLeft;}
        else if (Objects.equals(this.facing, "right")){this.txNow = txRight;}
        this.invItems = new ArrayList<>();
    }



    public boolean getIsStickied(){
        return isStickied;
    }
    public Texture getTxNow(){
        return txNow;
    }
    public Texture getUiIcon(){return uiIcon;}
    public void setIsStickied(boolean flag){
        this.isStickied = flag;
    }
    public void setMachineInteractingWith(Machine machine){
        this.machineInteractingWith = machine;
    }
    public Machine getMachineInteractingWith(){
        return machineInteractingWith;
    }

    public List<Texture> getInvItems(){return invItems;}
    //Inventory management
    public Stack<String> getInventory(){
        return inventory;
    }
    public void addToInventory(String item){
        this.inventory.push(item);
    }
    public void removeTopFromInventory(){
        this.inventory.pop();
    }

    //Facing
    public String getFacing(){
        return facing;
    }

    /**
     * Sets the facing direction of the chef, then changes the current texture to match.
     * @param facing sprite facing direction.
     */
    public void setFacing(String facing){
        this.facing = facing;
        switch (facing){
            case "up":
                this.txNow = txUp;
                break;
            case "down":
                this.txNow = txDown;
                break;
            case "left":
                this.txNow = txLeft;
                break;
            case "right":
                this.txNow = txRight;
                break;
        }
    }

    public void setInventoryTextures(){
        if(!invItems.isEmpty()) {
            invItems.clear();
        }
        List<String> items = Arrays.asList(new String[]{"bun", "burger", "completed burger", "choppedlettuce", "lettuce",  "meat", "onion", "choppedonion", "patty", "pizza", "completed salad", "toastedbun", "tomato", "choppedtomato","cheese","dough","uncooked_pizza"});
        for (int i=0;i<(this.inventory.size()) && i<3;i++){
            for(int j = 0; j < items.size(); j++){
                if (Objects.equals(this.inventory.get(i), items.get(j))){
                    invItems.add(new Texture("foods/" + (items.get(j)) +".png"));
                }
            }
        }
    }

    public int getChefNumb(){return chefNumb;}

    /**
     * Takes the important variables from the chef and compiles into tuple for saveData
     * @return Tuple of Chef variables
     */
    public Sextet<Integer, Integer, String, Stack<String>, Boolean, Integer> getChefInfo(){
        return new Sextet<>(getxCoord(),getyCoord(),getFacing(),getInventory(),getIsStickied(),getChefNumb());
    }
}
