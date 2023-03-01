package com.neves6.piazzapanic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

public class saveData implements Serializable {

    private ArrayList<Sextet> chefdata;
    private ArrayList<Septet> machinedata;
    private int level;
    private ArrayList<Triplet> customerdata;
    private int selectedChef;

    public saveData(ArrayList<Chef> chefs,int level, Stack<Customer> customers,int selectedChef,ArrayList<Machine> machines){
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
    }

    public ScenarioGameMaster loadGameMaster(PiazzaPanicGame game){
        ScenarioGameMaster gm = new ScenarioGameMaster(chefdata,customerdata,game,level,selectedChef,machinedata);
        return gm;
    }

}
