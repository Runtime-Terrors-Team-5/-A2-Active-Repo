package com.neves6.piazzapanic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Septet;
import org.javatuples.Sextet;

public class saveData implements Serializable {

    private final ArrayList<Sextet> chefdata;
    private HashMap machinedata;
    private int level;
    private ArrayList<Quartet> customerdata;
    private int selectedChef;
    private ArrayList<String> trayContent;
    private float timeElapled;

    private int repPoint;

    private int customerRemaining;
    private float customerSpawnTimer;
    private ArrayList validOrder;

    private ArrayList powerUPs;

    private int money;

    private int customerPersonalTimer;
    private float finalspawnTimer;

    /**
     * class implements Serializable it will ccontain all the variables and data which will be saved
     * to a file which can be loaded later
     *
     * @param chefs              array containing the chefs
     * @param level              int to show which level it is 1 easy, 2 medium, 3 hard, 4 endless
     * @param customers          stack containing all the customers which has been spawned
     * @param selectedChef       int which shows which chef is currently selected and controlled by
     *                           the player
     * @param machines           dictionary containing the machines and their location stored as an
     *                           array and a pair containing 2 integers respectively
     * @param trayContent        array containing strings to denote the contents of the tray
     * @param timeElapsed        float to show how long since the game has started
     * @param repPoint           int showing how much repoints the player has before they lose the
     *                           game
     * @param customerRemaining  int for how many customers left to spawn
     * @param customerSpawnTimer float for how long before the next customer is spawned
     * @param validOrder         array containing what orders will be spawned with the customers
     * @param powerUps           array containing the spawned power ups
     * @param money
     */
    public saveData(ArrayList<Chef> chefs,int level, Stack<Customer> customers,int selectedChef,
        HashMap<Pair<Integer, Integer>,ArrayList<Machine>> machines, ArrayList<String> trayContent,
        float timeElapsed, int repPoint,
        int customerRemaining, float customerSpawnTimer, ArrayList validOrder, ArrayList powerUps,
        int money, int customerPersonalTimer, float finalSpawnTimer){

        this.chefdata = new ArrayList<>();
        for (Chef i:chefs) {
            //System.out.println(i.getChefInfo());
            chefdata.add(i.getChefInfo());
        }
        this.level = level;
        //System.out.println(level);
        this.customerdata = new ArrayList<>();
        for (Customer i:customers) {
            customerdata.add(i.getCustomerData());
        }
        this.selectedChef = selectedChef;
        this.machinedata = new HashMap();

        ArrayList<Septet> tempMachineList;
        for (Pair key: machines.keySet()){
            tempMachineList = new ArrayList();
            for (Machine mac:machines.get(key)){
                tempMachineList.add(mac.getMachineInfo());
            }
            machinedata.put(key,tempMachineList);
        }

        this.trayContent = trayContent;
        this.timeElapled = timeElapsed;

        this.repPoint = repPoint;
        this.customerRemaining = customerRemaining;
        this.customerSpawnTimer = customerSpawnTimer;
        this.customerPersonalTimer = customerPersonalTimer;
        this.finalspawnTimer = finalSpawnTimer;

        this.validOrder = validOrder;

        this.powerUPs = powerUps;
        this.money = money;
    }

    public ScenarioGameMaster loadGameMaster(PiazzaPanicGame game){
        return new ScenarioGameMaster(this,game);
    }

    public HashMap<Pair,ArrayList<Septet>> getMachinedata() {return machinedata;}

    public int getLevel() {return level;}

    public ArrayList<Quartet> getCustomerdata() {return customerdata;}

    public ArrayList<Sextet> getChefdata() {return chefdata;}

    public int getSelectedChef() {return selectedChef;}

    public ArrayList<String> getTrayContent() {return trayContent;}

    public float getTimeElapled() {return timeElapled;}

    public int getRepPoint() {return repPoint;}

    public int getCustomerRemaining() {return customerRemaining;}

    public float getCustomerSpawnTimer() {return customerSpawnTimer;}

    public ArrayList getValidOrder(){return validOrder;}

    public ArrayList getPowerUPs() {return powerUPs;}

    public int getMoney() {return money;}

    public int getcustomerPersonalTimer(){return customerPersonalTimer;}

    public float getFinalspawnTimer() {
        return finalspawnTimer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof saveData)) {
            return false;
        }
        saveData saveData = (saveData) o;
        return getLevel() == saveData.getLevel() && getSelectedChef() == saveData.getSelectedChef()
            && Float.compare(saveData.getTimeElapled(), getTimeElapled()) == 0
            && getRepPoint() == saveData.getRepPoint()
            && getCustomerRemaining() == saveData.getCustomerRemaining()
            && Float.compare(saveData.getCustomerSpawnTimer(), getCustomerSpawnTimer()) == 0
            && Objects.equals(getChefdata(), saveData.getChefdata())
            && Objects.equals(getMachinedata(), saveData.getMachinedata())
            && Objects.equals(getCustomerdata(), saveData.getCustomerdata())
            && Objects.equals(getTrayContent(), saveData.getTrayContent())
            && Objects.equals(getValidOrder(), saveData.getValidOrder())
            && Objects.equals(getPowerUPs(), saveData.getPowerUPs())
            && Objects.equals(getMoney(), saveData.getMoney());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChefdata(), getMachinedata(), getLevel(), getCustomerdata(),
            getSelectedChef(), getTrayContent(), getTimeElapled(), getRepPoint(),
            getCustomerRemaining(), getCustomerSpawnTimer(), getValidOrder(), getPowerUPs(), getMoney());
    }
}
