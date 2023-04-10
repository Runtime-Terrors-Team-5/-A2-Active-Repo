package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.javatuples.Quartet;
import org.javatuples.Septet;
import org.javatuples.Sextet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

/**
 * GameMaster class.
 */
public class GameMaster {
    public GameMaster() {
    }
}

/**
 * ScenarioGameMaster subclass.
 */
class ScenarioGameMaster extends GameMaster {
    PiazzaPanicGame game;
    TiledMap map;
    TiledMapTileLayer collisionLayer;
    ArrayList<Chef> chefs = new ArrayList<>();
    Stack<Customer> customers = new Stack<>();
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<String> tray = new ArrayList<>();
    int selectedChef;
    float totalTimer;
    Sound grill;
    Sound chopping;
    Sound serving;
    Sound fridge;
    Sound forming;
    Sound trash;
    Sound ding;
    float soundVolume;
    ArrayList<String> settings;
    Random rand;
    int level;
    int repPoint;
    ArrayList<String> validOrder;
    int powerUpCount;
    Texture repIcon;
    int cusomerRemaining;
    private float customerSpawnTimer;


    /**
     * ScenarioGameMaster constructor.
     * @param game PiazzaPanicGame instance.
     * @param map TiledMap instance.
     * @param chefno Number of chefs.
     * @param custno Number of customers.
     */
    public ScenarioGameMaster(PiazzaPanicGame game, TiledMap map, int chefno, int custno,int level) {
        this.game = game;
        this.level = level;
        this.repPoint = 3;
        settings = Utility.getSettings();
        this.map = map;
        this.powerUpCount = 0;
        this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints3.png"));
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(3);

        if (this.level == 1){
            this.customerSpawnTimer = 5;
            validOrder = new ArrayList<>();
            validOrder.add("salad");
            validOrder.add("burger");
        }
        for (int i = 0; i < chefno; i++) {
            chefs.add(new Chef("Chef", 6+i, 5, 1, 1, 1, false, new Stack<String>(), i+1));
        }
        this.rand = new Random();

        String order = validOrder.get(rand.nextInt((validOrder.size())));
        customers.add(new Customer("Customer"+1, -1, -1, order));
        this.cusomerRemaining = custno-1;

        totalTimer = 0f;
        machines.add(new Machine("fridgemeat", "", "meat", 0, false));
        machines.add(new Machine("fridgetomato", "", "tomato", 0, false));
        machines.add(new Machine("fridgelettuce", "", "lettuce", 0, false));
        machines.add(new Machine("fridgeonion", "", "onion", 0, false));
        machines.add(new Machine("fridgebun", "", "bun", 0, false));
        machines.add(new Machine("grill1patty", "patty", "burger", 3, true));
        machines.add(new Machine("grill2patty", "patty", "burger", 3, true));
        machines.add(new Machine("grill1bun", "bun", "toastedbun", 3, true));
        machines.add(new Machine("grill2bun", "bun", "toastedbun", 3, true));
        machines.add(new Machine("forming1", "meat", "patty", 3, true));
        machines.add(new Machine("forming2", "meat", "patty", 3, true));
        machines.add(new Machine("chopping1tomato", "tomato", "choppedtomato", 3, true));
        machines.add(new Machine("chopping2tomato", "tomato", "choppedtomato", 3, true));
        machines.add(new Machine("chopping1lettuce", "lettuce", "choppedlettuce", 3, true));
        machines.add(new Machine("chopping2lettuce", "lettuce", "choppedlettuce", 3, true));
        machines.add(new Machine("chopping1onion", "onion", "choppedonion", 3, true));
        machines.add(new Machine("chopping2onion", "onion", "choppedonion", 3, true));

        // new machines for assessment 2
        machines.add(new Machine("Pizza", "uncooked_pizza", "pizza", 3, true));
        machines.add(new Machine("fridgecheese", "", "cheese", 0, false));
        machines.add(new Machine("fridgedough", "", "dough", 0, false));

        // disposal and tray/serving handled separately

        grill = Gdx.audio.newSound(Gdx.files.internal("sounds/grill.mp3"));
        chopping = Gdx.audio.newSound(Gdx.files.internal("sounds/chopping.mp3"));
        serving = Gdx.audio.newSound(Gdx.files.internal("sounds/serving.mp3"));
        fridge = Gdx.audio.newSound(Gdx.files.internal("sounds/fridge.mp3"));
        forming = Gdx.audio.newSound(Gdx.files.internal("sounds/forming.mp3"));
        trash = Gdx.audio.newSound(Gdx.files.internal("sounds/trash.mp3"));
        ding = Gdx.audio.newSound(Gdx.files.internal("sounds/ding.mp3"));

        switch (settings.get(1).strip()){
            case "full":
                soundVolume = 1f;
                break;
            case "half":
                soundVolume = 0.5f;
                break;
            case "none":
                soundVolume = 0f;
                break;
        }
    }

    public ScenarioGameMaster(saveData data, PiazzaPanicGame game) {
        this.game = game;
        this.repPoint = data.getRepPoint();
        settings = Utility.getSettings();
        this.level = data.getLevel();
        if (this.level == 1) {
            this.map = new TmxMapLoader().load("tilemaps/level1.tmx");}
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(3);
        this.rand = new Random();
        for (Sextet chef: data.getChefdata()) {
            this.chefs.add(new Chef("Chef",chef));
        }

        for (Quartet customer: data.getCustomerdata()) {
            this.customers.add(new Customer("Customer", (int) customer.getValue0(),
                (int) customer.getValue1(), (String) customer.getValue2(),
                (Float) customer.getValue3()));
        }
        this.cusomerRemaining = data.getCustumerRemaining();
        this.selectedChef = data.getSelectedChef();

        this.customerSpawnTimer = data.getCustomerSpawnTimer();

        totalTimer = 0f;

        for (Septet machine: data.getMachinedata()) {
            this.machines.add(new Machine(machine,chefs));
        }

        this.tray = data.getTrayContent();
        this.totalTimer = data.getTimeElapled();

        // disposal and tray/serving handled separately

        grill = Gdx.audio.newSound(Gdx.files.internal("sounds/grill.mp3"));
        chopping = Gdx.audio.newSound(Gdx.files.internal("sounds/chopping.mp3"));
        serving = Gdx.audio.newSound(Gdx.files.internal("sounds/serving.mp3"));
        fridge = Gdx.audio.newSound(Gdx.files.internal("sounds/fridge.mp3"));
        forming = Gdx.audio.newSound(Gdx.files.internal("sounds/forming.mp3"));
        trash = Gdx.audio.newSound(Gdx.files.internal("sounds/trash.mp3"));
        ding = Gdx.audio.newSound(Gdx.files.internal("sounds/ding.mp3"));

        switch (settings.get(1).strip()){
            case "full":
                soundVolume = 1f;
                break;
            case "half":
                soundVolume = 0.5f;
                break;
            case "none":
                soundVolume = 0f;
                break;
        }
    }

    public saveData generateSaveData(){
        return new saveData(chefs, level, customers,selectedChef,machines,tray,totalTimer,
            repPoint,cusomerRemaining, customerSpawnTimer);
    }
    public void setSelectedChef(int selectedChef) {
        this.selectedChef = selectedChef - 1;
    }
    public int getSelectedChef() {
        return selectedChef + 1;
    }

    public Chef getChef(int i){
        return chefs.get(i-1);
    }

    /**
     * Attempts to move the currently selected chef in a specified direction.
     * @param direction Direction to move.
     */
    public void tryMove(String direction){
        Chef chef = chefs.get(selectedChef);
        switch (direction) {
            case "up":
                if (wouldNotCollide(chef.getxCoord(), chef.getyCoord() + 1, selectedChef)) {
                    chef.alteryCoord(+1);
                }
                chef.setFacing("up");
                break;
            case "down":
                if (wouldNotCollide(chef.getxCoord(), chef.getyCoord() - 1, selectedChef)) {
                    chef.alteryCoord(-1);
                }
                chef.setFacing("down");
                break;
            case "left":
                if (wouldNotCollide(chef.getxCoord() - 1, chef.getyCoord(), selectedChef)) {
                    chef.alterxCoord(-1);
                }
                chef.setFacing("left");
                break;
            case "right":
                if (wouldNotCollide(chef.getxCoord() + 1, chef.getyCoord(), selectedChef)) {
                    chef.alterxCoord(+1);
                }
                chef.setFacing("right");
                break;
        }
    }

    /**
     * Checks collision with the collision layer, other chefs, and being stickied.
     * @param x x coordinate to check.
     * @param y y coordinate to check.
     * @param chefno chef number to check.
     * @return true if the chef would not collide, false otherwise.
     */
    private boolean wouldNotCollide(int x, int y, int chefno) {
        if (chefs.get(chefno).getIsStickied()) {
            return false;
        }
        if ((chefno == 0 && chefs.get(1).getxCoord() == x && chefs.get(1).getyCoord() == y) ||
                (chefno == 0 && chefs.get(2).getxCoord() == x && chefs.get(2).getyCoord() == y) ||
                (chefno == 1 && chefs.get(0).getxCoord() == x && chefs.get(0).getyCoord() == y) ||
                (chefno == 1 && chefs.get(2).getxCoord() == x && chefs.get(2).getyCoord() == y) ||
                (chefno == 2 && chefs.get(0).getxCoord() == x && chefs.get(0).getyCoord() == y) ||
                (chefno == 2 && chefs.get(1).getxCoord() == x && chefs.get(1).getyCoord() == y)) {
            return false;
        }
        int tempCellTileID = collisionLayer.getCell(x, y).getTile().getId();
        return tempCellTileID != 37 && tempCellTileID != 39;
    }

    /**
     * Generates the display text for the chefs' inventories.
     * @return String containing the display text.
     */
    public String generateHoldingsText() {
        String comp = "";
        comp += "Chef 1 is holding:\n";
        comp += chefs.get(0).getInventory().toString();
        comp += "\nChef 2 is holding:\n";
        comp += chefs.get(1).getInventory().toString();
        comp += "\nChef 3 is holding:\n";
        comp += chefs.get(2).getInventory().toString();
        return comp;
    }

    /**
     * Generates the display text for the customers' tray and order.
     * @return String containing the display text.
     */
    public String generateCustomersTrayText() {
        String comp = "";
        comp += "Customers remaining: ";
        comp += customers.size();
        if (customers.size() > 0) {
            comp += "\nOrder: ";
            comp += customers.get(0).getOrder();

        }
        comp += "\nTray contents: ";
        comp += tray.toString();
        return comp;
    }

    /**
     * Generates the display text for the timer.
     * @return String containing the display text.
     */
    public String generateTimerText(){
        String comp = "";
        comp += "Time elapsed: ";
        comp += (int) totalTimer;
        comp += " s";
        return comp;
    }

    /**
     * Generates the display text for the chef's timer.
     * @param chefno chef number to check.
     * @return String containing the display text.
     */
    public String getMachineTimerForChef(int chefno) {
        Chef chef = chefs.get(chefno);
        if (chef.getMachineInteractingWith() != null) {
            Machine machine = chef.getMachineInteractingWith();
            return ((int) (machine.getProcessingTime() - machine.getRuntime() + 1)) + "";
        } else {
            return "";
        }
    }

    public String getMachineTimerForChefDone(int chefno) {
        Chef chef = chefs.get(chefno);
        if (chef.getMachineInteractingWith() != null) {
            Machine machine = chef.getMachineInteractingWith();
            return ((int) (machine.getProcessingTime() - machine.getRuntime() + 1)*(-1)) + "";
        } else {
            return "";
        }
    }

    public int getCustomersSize(){
        return customers.size();
    }

    public Customer getFirstCustomer() {
        return customers.get(0);
    }

    public void spawnCustomer(){
        if (cusomerRemaining <= 0){return;}
        String order = validOrder.get(rand.nextInt((validOrder.size())));
        customers.add(new Customer("Customer"+1, -1, -1, order));
        this.cusomerRemaining -= 1;

        switch (this.level) {
            case 1:
                this.customerSpawnTimer = 5;
                break;
            case 2:
                this.customerSpawnTimer = 5;
                break;
            case 3:
                this.customerSpawnTimer = 5;
                break;
            case -1:
                this.customerSpawnTimer = 5;
                break;
        }
    }

    /**
     * Updates timers on all machines and the total timer.
     * To be called every frame render.
     * @param delta time since last frame.
     */
    public void tickUpdate(float delta) {
        for (Machine machine : machines) {
            if (machine.getActive()) {
                machine.incrementRuntime(delta);
                machine.attemptGetOutput(selectedChef);
            }
        }
        //for customer timer increase
        boolean pauseTime = false;
        for (int i = 0; i < customers.size(); i++) {
            for(PowerUp inst: PowerUp.PowerUps){
                if (inst.powerUpType == "pauseTime" && inst.active){
                    pauseTime = true;
                }
            }
            if (pauseTime == false){ customers.get(i).timerDecrease(delta); }
            if (customers.get(i).getTimer() < 0 ){
                repDecrease();
                customers.remove(i);
            }
        }

        this.customerSpawnTimer -= delta;
        if (customerSpawnTimer < 0){
            if (cusomerRemaining >=3){
                if (rand.nextInt(100) > 90){
                    //System.out.println("SPawn 3");
                    spawnCustomer();
                    spawnCustomer();
                    spawnCustomer();
                }
                else{spawnCustomer();}
            }
            else if (cusomerRemaining >=2){
                if (rand.nextInt(100) > 50){
                    //System.out.println("SPawn 2");
                    spawnCustomer();
                    spawnCustomer();
                }
                else{spawnCustomer();}
            }
            else{spawnCustomer();}
        }
        
        totalTimer += delta;
    }

    /**
     * Attempts to cause an interaction between the currently selected chef and the machine in front of them.
     */
    public void tryInteract() {
        Chef chef = chefs.get(selectedChef);
        if (chef.getIsStickied()) {
            return;
        }
        int targetx;
        int targety;
        switch (chef.getFacing()) {
            case "up":
                targetx = chef.getxCoord();
                targety = chef.getyCoord() + 1;
                break;
            case "down":
                targetx = chef.getxCoord();
                targety = chef.getyCoord() - 1;
                break;
            case "left":
                targetx = chef.getxCoord() - 1;
                targety = chef.getyCoord();
                break;
            case "right":
                targetx = chef.getxCoord() + 1;
                targety = chef.getyCoord();
                break;
            default:
                targetx = chef.getxCoord();
                targety = chef.getyCoord();
                break;
        }
        //System.out.println("Target: " + targetx + ", " + targety + "\nFacing: " + chef.getFacing());

        if (targetx == 1 && targety == 10) {
            machines.get(0).process(chef);
            fridge.play(soundVolume);
        } else if (targetx == 2 && targety == 10) {
            machines.get(1).process(chef);
            fridge.play(soundVolume);
        } else if (targetx == 3 && targety == 10) {
            machines.get(2).process(chef);
            fridge.play(soundVolume);
        } else if (targetx == 4 && targety == 10) {
            machines.get(3).process(chef);
            fridge.play(soundVolume);
        } else if (targetx == 1 && targety == 8) {
            machines.get(4).process(chef);
            fridge.play(soundVolume);
        }

        String invTop = chef.getInventory().peek();
        if (targetx == 6 && targety == 7) {


            if (Objects.equals(invTop, "patty")) {
                machines.get(5).process(chef);
                grill.play(soundVolume);
            } else if (Objects.equals(invTop, "bun")) {
                machines.get(7).process(chef);
                grill.play(soundVolume);
            }
        } else if (targetx == 7 && targety == 7) {

            if (Objects.equals(invTop, "patty")) {
                machines.get(6).process(chef);
                grill.play(soundVolume);
            } else if (Objects.equals(invTop, "bun")) {
                machines.get(8).process(chef);
                grill.play(soundVolume);
            }
        } else if (targetx == 9 && targety == 7) {
            machines.get(9).process(chef);
            forming.play(soundVolume);
        } else if (targetx == 10 && targety == 7) {
            machines.get(10).process(chef);
            forming.play(soundVolume);
        } else if (targetx == 11 && targety == 7) {
            if (Objects.equals(invTop, "tomato")) {
                machines.get(11).process(chef);
                chopping.play(soundVolume);
            } else if (Objects.equals(invTop, "lettuce")) {
                machines.get(13).process(chef);
                chopping.play(soundVolume);
            } else if (Objects.equals(invTop, "onion")) {
                machines.get(15).process(chef);
                chopping.play(soundVolume);
            }
        } else if (targetx == 12 && targety == 7) {
            if (Objects.equals(invTop, "tomato")) {
                machines.get(12).process(chef);
            } else if (Objects.equals(invTop, "lettuce")) {
                machines.get(14).process(chef);
            } else if (Objects.equals(invTop, "onion")) {
                machines.get(16).process(chef);
            }
        } else if (targetx == 1 && targety == 5) {
            chef.removeTopFromInventory();
            trash.play(soundVolume);
        } else if (targetx == 8 && targety == 3) {
            addToTray();
        }
    }

    /**
     * Adds the top item from the currently selected chef's inventory to the tray.
     */
    private void addToTray() {
        Chef chef = chefs.get(selectedChef);
        Stack<String> inv = chef.getInventory();
        if (Objects.equals(customers.get(0).getOrder(), "burger")){
            if (Objects.equals(inv.peek(), "burger")){
                inv.pop();
                tray.add("burger");
            } else if (Objects.equals(inv.peek(), "toastedbun")){
                inv.pop();
                tray.add("toastedbun");
            }
            if (tray.contains("burger") && tray.contains("toastedbun")){
                customers.remove(0);
                tray.clear();

                serving.play(soundVolume);
            }
        } else if (Objects.equals(customers.get(0).getOrder(), "salad")){
            if (Objects.equals(inv.peek(), "choppedtomato")){
                inv.pop();
                tray.add("choppedtomato");
            } else if (Objects.equals(inv.peek(), "choppedlettuce")){
                inv.pop();
                tray.add("choppedlettuce");
            } else if (Objects.equals(inv.peek(), "choppedonion")){
                inv.pop();
                tray.add("choppedonion");
            }
            if (tray.contains("choppedtomato") && tray.contains("choppedlettuce") && tray.contains("choppedonion")){
                customers.remove(0);
                tray.clear();
                serving.play(soundVolume);
            }
        }
        if (customers.size() == 0 && cusomerRemaining == 0){
            game.setScreen(new GameWinScreen(game, (int) totalTimer));
        }
    }

    public void generatePowerUp(){
        if(powerUpCount < 2){
            boolean powerUpCollisionCheck = false;
            int tempX = 0;
            int tempY = 0;
            Random rand = new Random();
            int randomInt = rand.nextInt(500);
            if (randomInt == 49){
                int time = 200;
                String powerUpType = "";
                Texture texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));

                randomInt = rand.nextInt(5);
                if (randomInt == 0){
                    powerUpType = "cookSpeed";
                    texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));
                }
                else if (randomInt == 1){
                    powerUpType = "rep";
                    texture = new Texture(Gdx.files.internal("icons/repIcon.png"));
                }
                else if (randomInt == 2){
                    powerUpType = "minusRep";
                    texture = new Texture(Gdx.files.internal("icons/minusRepIcon.png"));
                }
                else if (randomInt == 3){
                    powerUpType = "pauseTime";
                    texture = new Texture(Gdx.files.internal("icons/frzTimeIcon.png"));
                }
                else if (randomInt == 4){
                    powerUpType = "money";
                    texture = new Texture(Gdx.files.internal("icons/moneyIcon.png"));
                }

                while (powerUpCollisionCheck == false) {
                    tempX = rand.nextInt(13) + 1;
                    tempY = rand.nextInt(3) + 4;
                    if ((tempX == chefs.get(0).getxCoord() && tempY == chefs.get(0).getyCoord()) ||
                            (tempX == chefs.get(1).getxCoord() && tempY == chefs.get(1).getyCoord()) ||
                            (tempX == chefs.get(2).getxCoord() && tempY == chefs.get(2).getyCoord()))
                    { powerUpCollisionCheck = false; }
                    else { powerUpCollisionCheck = true; }
                }

                int xCoord = tempX;
                int yCoord = tempY;
                new PowerUp(powerUpType,xCoord,yCoord,time,texture);
                powerUpCount += 1;
            }
        }
    }

    public void clearPowerUp(){
        List<PowerUp> found = new ArrayList<>();
        for(PowerUp inst: PowerUp.PowerUps)
        {
            inst.incrementTime();
            if(inst.getTime() < 0){
                found.add(inst);
                powerUpCount -= 1;
            }
        }
        PowerUp.PowerUps.removeAll(found);
    }

    public void getPowerUp(){
        List<PowerUp> found = new ArrayList<>();
        for(PowerUp inst: PowerUp.PowerUps){
            if(inst.getxCoord() == chefs.get(getSelectedChef()-1).getxCoord() &&
                    inst.getyCoord() == chefs.get(getSelectedChef()-1).getyCoord()){
                inst.clearxCoord();
                inst.clearyCoord();
                if(inst.powerUpType == "pauseTime"){
                    inst.setTime();
                }
                inst.setActive();
                ding.play(1);
            }
        }
    }

    public void powerUpEffect(){
        for(PowerUp inst: PowerUp.PowerUps){
            if(inst.active == true){
                if (inst.powerUpType == "cookSpeed"){}
                else if (inst.powerUpType == "rep"){
                    repIncrease();
                    inst.clearTime();
                }
                else if (inst.powerUpType == "minusRep"){
                    repDecrease();
                    inst.clearTime();
                }
                else if (inst.powerUpType == "money"){
                    inst.clearTime();
                }

            }
        }
    }

    //rep decrease()
    public void repDecrease(){
        repPoint -= 1;
        if (repPoint == 3) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints3.png"));}
        else if (repPoint == 2) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints2.png"));}
        else if (repPoint == 1) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints1.png"));}
        else if (repPoint == 0) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints0.png"));}
    }

    public void repIncrease(){
        if (repPoint < 3) {repPoint += 1;}
        if (repPoint == 3) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints3.png"));}
        else if (repPoint == 2) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints2.png"));}
        else if (repPoint == 1) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints1.png"));}
        else if (repPoint == 0) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints0.png"));}
    }

    public int getRepPoint(){
        return this.repPoint;
    }

    public void IncreasePowerUpCount(){this.powerUpCount += 1;}

    public float getTotalTimer(){return this.totalTimer;}
}