package com.neves6.piazzapanic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

public class saveData implements Serializable {

    private ArrayList<Sextet> chefdata;
    private ArrayList<Septet> machinedata;
    private int level;
    private ArrayList<Quartet> customerdata;
    private int selectedChef;
    private ArrayList<String> trayContent;
    private float timeElapled;

    private int repPoint;

    private  int custumerRemaining;
    private float customerSpawnTimer;

    public saveData(ArrayList<Chef> chefs,int level, Stack<Customer> customers,int selectedChef,
        ArrayList<Machine> machines, ArrayList<String> trayContent, float timeElapsed, int repPoint,
        int custumerRemaining, float customerSpawnTimer){
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
        this.machinedata = new ArrayList<>();
        for (Machine i:machines){
            machinedata.add(i.getMachineInfo());
        }
        this.trayContent = trayContent;
        this.timeElapled = timeElapsed;

        this.repPoint = repPoint;
        this.custumerRemaining = custumerRemaining;
        this.customerSpawnTimer = customerSpawnTimer;
    }

    public ScenarioGameMaster loadGameMaster(PiazzaPanicGame game){
        ScenarioGameMaster gm = new ScenarioGameMaster(this,game);
        return gm;
    }

    public ArrayList<Septet> getMachinedata() {return machinedata;}

    public int getLevel() {return level;}

    public ArrayList<Quartet> getCustomerdata() {return customerdata;}

    public ArrayList<Sextet> getChefdata() {return chefdata;}

    public int getSelectedChef() {return selectedChef;}

    public ArrayList<String> getTrayContent() {return trayContent;}

    public float getTimeElapled() {return timeElapled;}

    public int getRepPoint() {return repPoint;}

    public int getCustumerRemaining() {return custumerRemaining;}

    public float getCustomerSpawnTimer() {return customerSpawnTimer;}
}
