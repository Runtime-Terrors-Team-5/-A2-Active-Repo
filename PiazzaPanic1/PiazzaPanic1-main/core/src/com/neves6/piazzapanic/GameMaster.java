package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.javatuples.*;

import java.util.*;

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
    ArrayList<String> pizzatray = new ArrayList<>();
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
    ArrayList validOrder;
    int powerUpCount;
    Texture repIcon;
    int cusomerRemaining;
    private float customerSpawnTimer;
    private float finalSpawnTimer;
    int customerPersonalTimer; //this sets the timer of the customer til they reduce the players reputation points

    boolean goldGrillUnlocked = false; //defaults to true until we figure out how to "unlock" it

    boolean formingStationUnlocked = false; //defaults to true til we figure out how to unlock it

    boolean pizzaStationUnlocked = false;

    boolean endless;
    HashMap<Pair<Integer, Integer>, ArrayList<Machine>> machineLocation;
    int money = 0;
    List<Texture> trayTextures;


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
        this.trayTextures = new ArrayList<>();

        if (this.level == 1){

            this.customerSpawnTimer = 15;
            this.finalSpawnTimer = customerSpawnTimer;
            customerPersonalTimer = 40;
            validOrder = new ArrayList<>();
            validOrder.add("salad");
            validOrder.add("burger");
            //validOrder.add("pizza");
            endless = false;
        }

        if (this.level == 2){
            this.customerSpawnTimer = 10;
            this.finalSpawnTimer = customerSpawnTimer;
            customerPersonalTimer = 30;
            validOrder = new ArrayList<>();
            validOrder.add("salad");
            validOrder.add("burger");
            endless = false;
        }

        if (this.level == 3){
            this.customerSpawnTimer = 5;
            this.finalSpawnTimer = customerSpawnTimer;
            customerPersonalTimer = 20;
            validOrder = new ArrayList<>();
            validOrder.add("salad");
            validOrder.add("burger");
            endless = false;
        }

        if (this.level == 4){
            this.customerSpawnTimer = 5;
            customerPersonalTimer = 40;
            validOrder = new ArrayList<>();
            validOrder.add("salad");
            validOrder.add("burger");
            endless = true;
        }
        
        for (int i = 0; i < chefno; i++) {
            chefs.add(new Chef("Chef", 6+i, 5, 1, 1, 1, false, new Stack<String>(), i+1));
        }
        this.rand = new Random();

        String order = (String) validOrder.get(rand.nextInt((validOrder.size())));
        customers.add(new Customer("Customer"+1, -1, -1, order, customerPersonalTimer));

        if (!endless) {
            this.cusomerRemaining = custno - 1;
        } else {
            this.cusomerRemaining = custno;
        }

        machineLocation = new HashMap<>();

        ArrayList<Machine> tempArray = new ArrayList<>();


        // Cutting board from map
        // subsequent loops work in similar fashion
        // gets the layer from the tilemap, then loop through and when a tile is not empty
        // use the x and y to form the key and add the machines into the dictionary as the values
        // in an arraylist
        TiledMapTileLayer workingLayer = (TiledMapTileLayer) map.getLayers().get(4);
        for (int i = 0; i < workingLayer.getHeight(); i++) {
            for (int j = 0; j < workingLayer.getWidth(); j++) {
                if (workingLayer.getCell(j,i) != null){
                    tempArray.add(new Machine("choppingTomato", "tomato", "choppedtomato", 3, true));
                    tempArray.add(new Machine("choppingLettuce", "lettuce", "choppedlettuce", 3, true));
                    tempArray.add(new Machine("choppingOnion", "onion", "choppedonion", 3, true));
                    machineLocation.put(new Pair<>(j,i),tempArray);
                    tempArray = new ArrayList<>();
                }
            }
        }

        // making station turns meat -> patty
        workingLayer = (TiledMapTileLayer) map.getLayers().get(5);
        for (int i = 0; i < workingLayer.getHeight(); i++) {
            for (int j = 0; j < workingLayer.getWidth(); j++) {
                if (workingLayer.getCell(j,i) != null){
                    tempArray.add(new Machine("forming", "meat", "patty", 3, true));
                    machineLocation.put(new Pair<>(j,i),tempArray);
                    tempArray = new ArrayList<>();
                }
            }
        }


        // grilling station
        workingLayer = (TiledMapTileLayer) map.getLayers().get(6);
        for (int i = 0; i < workingLayer.getHeight(); i++) {
            for (int j = 0; j < workingLayer.getWidth(); j++) {
                if (workingLayer.getCell(j,i) != null){
                    tempArray.add(new Machine("grill1patty", "patty", "burger", 3, true));
                    tempArray.add(new Machine("grill1bun", "bun", "toastedbun", 3, true));
                    machineLocation.put(new Pair<>(j,i),tempArray);
                    tempArray = new ArrayList<>();
                }
            }
        }

        //pizza grill


        // loops through the dispenser layers to get the corresponding ingredient
        for (int k=10;k<=16;k++){
            workingLayer = (TiledMapTileLayer) map.getLayers().get(k);
            for (int i = 0; i < workingLayer.getHeight(); i++) {
                for (int j = 0; j < workingLayer.getWidth(); j++) {
                    if (workingLayer.getCell(j,i) != null){
                        tempArray.add(new Machine("dispenser", "", workingLayer.getName(), 0, false));
                        machineLocation.put(new Pair<>(j,i),tempArray);
                        tempArray = new ArrayList<>();
                    }
                }
            }
        }
        //System.out.println(machineLocation);

        totalTimer = 0f;


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
    /**
     * signiture for loading feature
     * loads all SCenarioGameMaster variables from data that is passed in
     */
    public ScenarioGameMaster(saveData data, PiazzaPanicGame game) {
        this.game = game;
        this.repPoint = data.getRepPoint();
        if (repPoint == 3) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints3.png"));}
        else if (repPoint == 2) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints2.png"));}
        else if (repPoint == 1) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints1.png"));}
        else if (repPoint == 0) {this.repIcon =  new Texture(Gdx.files.internal("icons/repPoints0.png"));}

        settings = Utility.getSettings();
        this.level = data.getLevel();
        if (this.level == 1) {
            this.map = new TmxMapLoader().load("tilemaps/level1.tmx");}
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(3);
        this.rand = new Random();

        chefs = new ArrayList<>();
        for (Sextet chef: data.getChefdata()) {
            this.chefs.add(new Chef("Chef",chef));
        }

        customers = new Stack<>();
        for (Quartet customer: data.getCustomerdata()) {
            this.customers.add(new Customer("Customer", (int) customer.getValue0(),
                (int) customer.getValue1(), (String) customer.getValue2(),
                (Float) customer.getValue3()));
        }
        this.cusomerRemaining = data.getCustomerRemaining();
        this.selectedChef = data.getSelectedChef();

        this.customerSpawnTimer = data.getCustomerSpawnTimer();

        totalTimer = 0f;

        machineLocation = new HashMap<>();
        HashMap<Pair,ArrayList<Septet>> machineData = data.getMachinedata();
        ArrayList<Machine> tempMachine;
        for (Pair key : machineData.keySet()) {
            tempMachine = new ArrayList<Machine>();
            for (Septet mac : machineData.get(key)) {
                tempMachine.add(new Machine(mac,chefs));
            }
            machineLocation.put(key,tempMachine);

        }

        ArrayList<Quintet> tempPowerUP = data.getPowerUPs();
        for (Quintet power:tempPowerUP){
            new PowerUp((String) power.getValue0(), (Integer) power.getValue1(),
                (Integer) power.getValue2(),(Integer) power.getValue3(),
                new Texture(Gdx.files.internal(String.format("icons/%sIcon.png",power.getValue0())))
                ,(Boolean)power.getValue4());
        }


        this.tray = data.getTrayContent();
        this.validOrder = data.getValidOrder();
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

    /**
     * Takes all important variables from GameMaster and creates a saveData using it
     * @return saveData containing all variables to be used in save and loading
     */
    public saveData generateSaveData(){
        return new saveData(chefs, level, customers,selectedChef,machineLocation,tray,totalTimer,
            repPoint,cusomerRemaining, customerSpawnTimer, validOrder, PowerUp.generatePowerData());
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
    public String generateMoneyText() {
        String comp = "";
        comp += "";
        comp += (int) money;
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
    public int getCustomerRemainingTime() { return Math.round(getFirstCustomer().getTimer()/getFirstCustomer().getMaxTimer() * 60); }

    public Customer getFirstCustomer() {
        return customers.get(0);
    }

    public int getChefsLength(){return chefs.size();}

    /**
     * Spawns a customer when called
     * customer is added to the customer stack
     * resets the customerSpawnTimer to finalSpawnTimer when called
     */
    public void spawnCustomer(){
        if (cusomerRemaining <= 0){return;}
        String order = (String) validOrder.get(rand.nextInt((validOrder.size())));
        customers.add(new Customer("Customer"+1, -1, -1, order, customerPersonalTimer));
        if (!endless) {
            this.cusomerRemaining -= 1;
        }
        this.customerSpawnTimer = finalSpawnTimer;
        for (int i = 0; i < customers.size(); i++) {
            System.out.print(customers.get(i).getOrder());
            System.out.print(", ");
        }
        System.out.println("");
    }

    /**
     * Updates timers on all machines and the total timer.
     * To be called every frame render.
     * @param delta time since last frame.
     */
    public void tickUpdate(float delta) {
        // loops through the dictionary to update the runtime of the machines which are active
        for (Pair<Integer, Integer> tempPair: machineLocation.keySet()){
            for (Machine mac :
                machineLocation.get(tempPair)) {
                if (mac.getActive()) {
                    mac.incrementRuntime(delta);
                    for(PowerUp inst: PowerUp.PowerUps) {
                        if (Objects.equals(inst.powerUpType, "fast") && inst.active) {
                            mac.incrementRuntime(delta);
                        }
                    }
                    mac.attemptGetOutput(selectedChef);
                }
            }
        }
        //for customer timer increase
        boolean pauseTime = false;
        for (int i = 0; i < customers.size(); i++) {
            for(PowerUp inst: PowerUp.PowerUps){
                if (Objects.equals(inst.powerUpType, "frzTime") && inst.active){
                    pauseTime = true;
                }
            }
            //System.out.println(customers.get(i).getTimer());
            if (customers.get(i).getTimer() < 0 ){
                repDecrease();
                customers.remove(i);
            }
        }

        if (!pauseTime && customers.size() > 0){ customers.get(0).timerDecrease(delta); }

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
     * calls every time E is pressed
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



        String invTop;
        if (chef.getInventory().empty()) {
            invTop = "null";
        }

        else{
            invTop = chef.getInventory().peek();
        }

        // key to query the dictionary with the location of the machine
        Pair<Integer, Integer> target = new Pair<>(targetx,targety);
        try{
        ArrayList<Machine> targetMachines = machineLocation.get(target);
        //System.out.println(targetMachines);
        for (Machine mac :
            targetMachines) {
            // does ingredient processing
            if (mac.getInput().equals(invTop)){
                mac.process(chef);
            // dispenses ingredient
            } else if (mac.getInput().equals("")) {
                mac.process(chef);
            }
        }
        }
        catch (Exception e){
            System.out.println("error");
        }


        if (targetx == 1 && targety == 5 && !Objects.equals(invTop, "null")) {
            chef.removeTopFromInventory();
            trash.play(soundVolume);
        } else if (targetx == 12 && targety == 3) {
            addToTray(false);
        } else if (targetx == 8 && targety == 3) {
            if (Objects.equals(invTop, "completed burger")) {
                customers.remove(0);
                money += 5;
                chef.getInventory().pop();
                repIncrease();
                serving.play(soundVolume);
                if (customers.size() == 0 && cusomerRemaining == 0){
                    game.setScreen(new GameWinScreen(game, (int) totalTimer));
                }
            } else if (Objects.equals(invTop, "completed salad")) {
                customers.remove(0);
                money += 5;
                chef.getInventory().pop();
                repIncrease();
                serving.play(soundVolume);
                if (customers.size() == 0 && cusomerRemaining == 0){
                    game.setScreen(new GameWinScreen(game, (int) totalTimer));
                }
            } else if (Objects.equals(invTop, "pizza")) {
                customers.remove(0);
                money += 5;
                chef.getInventory().pop();
                repIncrease();
                serving.play(soundVolume);
                if (customers.size() == 0 && cusomerRemaining == 0){
                    game.setScreen(new GameWinScreen(game, (int) totalTimer));
                }
            }
        } else if (targetx == 11 && targety == 3) {
            addToTray(true);
        }
        setTrayTextures();

    }

    private void setTrayTextures(){
        if(!trayTextures.isEmpty()) {
            trayTextures.clear();
        }
        List<String> items = Arrays.asList(new String[]{"bun", "burger", "completed burger", "choppedlettuce", "lettuce",  "meat", "onion", "choppedonion", "patty", "pizza", "completed salad", "toastedbun", "tomato", "choppedtomato","cheese","dough","uncooked_pizza"});
        for(int i=0;i<tray.size();i++){
            for(int j=0;j<items.size();j++){
                if(this.tray.get(i) == items.get(j)){
                    trayTextures.add(new Texture("foods/" + (items.get(j)) +".png"));
                }
            }
        }
    }

    /**
     * Adds the top item from the currently selected chef's inventory to the tray.
     * Returns the completed item to Chefs inventory if all required items are in the tray
     */
    private void addToTray(boolean isItPizza) {
        Chef chef = chefs.get(selectedChef);
        Stack<String> inv = chef.getInventory();
        if (!isItPizza) {
            if (Objects.equals(customers.get(0).getOrder(), "burger")) {
                if (Objects.equals(inv.peek(), "burger")) {
                    inv.pop();
                    tray.add("burger");
                } else if (Objects.equals(inv.peek(), "toastedbun")) {
                    inv.pop();
                    tray.add("toastedbun");
                }
                if (tray.contains("burger") && tray.contains("toastedbun")) {
                    //customers.remove(0);
                    tray.clear();
                    //repIncrease();
                    inv.push("completed burger");

                    //serving.play(soundVolume);
                }
            } else if (Objects.equals(customers.get(0).getOrder(), "salad")) {
                if (Objects.equals(inv.peek(), "choppedtomato")) {
                    inv.pop();
                    tray.add("choppedtomato");
                } else if (Objects.equals(inv.peek(), "choppedlettuce")) {
                    inv.pop();
                    tray.add("choppedlettuce");
                } else if (Objects.equals(inv.peek(), "choppedonion")) {
                    inv.pop();
                    tray.add("choppedonion");
                }
                if (tray.contains("choppedtomato") && tray.contains("choppedlettuce") && tray.contains("choppedonion")) {
                    //customers.remove(0);
                    tray.clear();
                    //repIncrease();
                    inv.push("completed salad");

                    //serving.play(soundVolume);
                }
            }
        } else {
            if (Objects.equals(customers.get(0).getOrder(), "pizza")) {
                if (Objects.equals(inv.peek(), "cheese")) {
                    inv.pop();
                    pizzatray.add("cheese");
                } else if (Objects.equals(inv.peek(), "dough")) {
                    inv.pop();
                    pizzatray.add("dough");
                }
                if (pizzatray.contains("cheese") && pizzatray.contains("dough")) {
                    pizzatray.clear();
                    inv.push("uncooked_pizza");
                }
            }
        }
    }

    /**
     * used for testing as the method above is private
     * @param isitpizza if pizza being tested
     */
    public void UseAddToTray(boolean isitpizza){
        if (isitpizza){
            addToTray(true);
        }else{
            addToTray(false);
        }
    }
    public void generatePowerUp(){
        if(powerUpCount < 2){
            boolean powerUpCollisionCheck = false;
            int tempX = 0;
            int tempY = 0;
            Random rand = new Random();
            int randomInt = rand.nextInt(600);
            if (randomInt == 0){
                int time = 300;
                String powerUpType = "";
                Texture texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));

                randomInt = rand.nextInt(100);
                if (randomInt < 30){
                    powerUpType = "fast";
                    texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));
                }
                else if (randomInt < 45){
                    powerUpType = "rep";
                    texture = new Texture(Gdx.files.internal("icons/repIcon.png"));
                }
                else if (randomInt < 55){
                    powerUpType = "minusRep";
                    texture = new Texture(Gdx.files.internal("icons/minusRepIcon.png"));
                }
                else if (randomInt < 85){
                    powerUpType = "frzTime";
                    texture = new Texture(Gdx.files.internal("icons/frzTimeIcon.png"));
                }
                else if (randomInt < 100){
                    powerUpType = "money";
                    texture = new Texture(Gdx.files.internal("icons/moneyIcon.png"));
                }

                while (!powerUpCollisionCheck) {
                    tempX = rand.nextInt(13) + 1;
                    tempY = rand.nextInt(3) + 4;
                    if ((tempX == chefs.get(0).getxCoord() && tempY == chefs.get(0).getyCoord()) ||
                            (tempX == chefs.get(1).getxCoord() && tempY == chefs.get(1).getyCoord()) ||
                            (tempX == chefs.get(2).getxCoord() && tempY == chefs.get(2).getyCoord()) ||
                            (tempX == 1 && tempY == 5))
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

    /**
     * Used to generate the power ups non randomly, so they can be accessed by the chef
     * during testing.
     * @param powerupno
     */
    public void generatePowerUpTest(int powerupno){
        if (powerupno == 0){
            Texture texture = new Texture(Gdx.files.internal("icons/repIcon.png"));
            new PowerUp("rep",6,5,1,texture );
        }
        else if (powerupno == 1){
            Texture texture = new Texture(Gdx.files.internal("icons/fastIcon.png"));
            new PowerUp("cookSpeed",6,1,1,texture );
        }
        else if (powerupno == 2){
            Texture texture = new Texture(Gdx.files.internal("icons/moneyIcon.png"));
            new PowerUp("money",6,5,1,texture );
        }
        else if (powerupno == 3) {
            Texture texture = new Texture(Gdx.files.internal("icons/minusRepIcon.png"));
            new PowerUp("minusRep",6,5,1,texture );

        }
        else if (powerupno == 4){
            Texture texture = new Texture(Gdx.files.internal("icons/frzTimeIcon.png"));
            new PowerUp("pauseTime",6,5,1,texture );
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
                if(Objects.equals(inst.powerUpType, "pauseTime") ||
                    Objects.equals(inst.powerUpType, "cookSpeed")){
                    inst.setTime();
                }
                inst.setActive();
                ding.play(1);
            }
        }
    }

    public void powerUpEffect(){
        for(PowerUp inst: PowerUp.PowerUps){
            if(inst.active){
                if (Objects.equals(inst.powerUpType, "rep")){
                    repIncrease();
                    inst.clearTime();
                }
                else if (Objects.equals(inst.powerUpType, "minusRep")){
                    repDecrease();
                    inst.clearTime();
                }
                else if (Objects.equals(inst.powerUpType, "money")){
                    money += 5;
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

    /**
     * Used for testing
     * @return int money value
     */
    public int getMoney(){return this.money;}

    /**
     * Test method for unlocking machines
     * @param value
     */
    public void setMoney(int value){this.money += value;}


    public float getTotalTimer(){return this.totalTimer;}

//    public void scrambleOrders() {
//        Random rand = new Random();
//        int custSize = customers.size();
//        int n;
//        for (int i = 1; i < custSize; i++) {
//            n = rand.nextInt(100);
//            if (n < 35) {
//                customers.get(i).setOrder("pizza");
//            }
//        }
//    }

    /**
     * unlock machines once game conditions met
     * @param machine
     */


    public void unlockMachine (int machine) {
        if (machine == 1 && money >= 5) {
            if (!goldGrillUnlocked) {
                System.out.println("gold grill unlocked!");
                goldGrillUnlocked = true;
                money -= 5;
                TiledMapTileLayer workingLayer = (TiledMapTileLayer) map.getLayers().get(7);
                ArrayList<Machine> tempArray = new ArrayList<>();
                for (int i = 0; i < workingLayer.getHeight(); i++) {
                    for (int j = 0; j < workingLayer.getWidth(); j++) {
                        if (workingLayer.getCell(j,i) != null){
                            tempArray.add(new Machine("grill3bun", "bun", "toastedbun", 3, true)); //machine 20
                            tempArray.add(new Machine("grill3patty", "patty", "burger", 3, true)); //machine 21
                            machineLocation.put(new Pair<>(j,i),tempArray);
                            tempArray = new ArrayList<>();
                        }
                    }
                }
            }
        }
        if (machine == 2 && money >=5) {
            if (!formingStationUnlocked) {
                System.out.println("forming station unlocked!");
                formingStationUnlocked = true;
                money -= 5;
                TiledMapTileLayer workingLayer = (TiledMapTileLayer) map.getLayers().get(8);
                ArrayList<Machine> tempArray = new ArrayList<>();
                for (int i = 0; i < workingLayer.getHeight(); i++) {
                    for (int j = 0; j < workingLayer.getWidth(); j++) {
                        if (workingLayer.getCell(j,i) != null){
                            tempArray.add(new Machine("forming3", "meat", "patty", 3, true));
                            machineLocation.put(new Pair<>(j,i),tempArray);
                            tempArray = new ArrayList<>();
                        }
                    }
                }
            }
        }
        if (machine == 3 && money >=5) {
            if (!pizzaStationUnlocked) {
                System.out.println("pizza station unlocked!");
                validOrder.add("pizza");
                pizzaStationUnlocked = true;
                //scrambleOrders();
                money -= 5;
                TiledMapTileLayer workingLayer = (TiledMapTileLayer) map.getLayers().get(9);
                ArrayList<Machine> tempArray = new ArrayList<>();
                for (int i = 0; i < workingLayer.getHeight(); i++) {
                    for (int j = 0; j < workingLayer.getWidth(); j++) {
                        if (workingLayer.getCell(j, i) != null) {
                            tempArray.add(new Machine("Pizza", "uncooked_pizza", "pizza", 3, true));
                            machineLocation.put(new Pair<>(j, i), tempArray);
                            tempArray = new ArrayList<>();
                        }
                    }
                }
            }
        }



    }
}