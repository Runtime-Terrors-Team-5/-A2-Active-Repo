package com.neves6.piazzapanic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

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

    public saveData(ArrayList<Chef> chefs,int level, Stack<Customer> customers,int selectedChef,
        HashMap<Pair<Integer, Integer>,ArrayList<Machine>> machines, ArrayList<String> trayContent,
        float timeElapsed, int repPoint,
        int customerRemaining, float customerSpawnTimer, ArrayList validOrder){

        this.chefdata = new ArrayList<>();
        for (Chef i:chefs) {
            System.out.println(i.getChefInfo());
            chefdata.add(i.getChefInfo());
        }
        this.level = level;
        System.out.println(level);
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

        this.validOrder = validOrder;
    }

    public ScenarioGameMaster loadGameMaster(PiazzaPanicGame game){
        ScenarioGameMaster gm = new ScenarioGameMaster(this,game);
        return gm;
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
}
