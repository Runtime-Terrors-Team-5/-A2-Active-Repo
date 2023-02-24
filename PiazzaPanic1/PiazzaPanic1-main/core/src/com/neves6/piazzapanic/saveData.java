package com.neves6.piazzapanic;

import java.util.ArrayList;
import java.util.Stack;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

public class saveData {

    private ArrayList<Sextet> chefdata;
    private int level;
    private ArrayList<Triplet> customerdata;
    private int selectedChef;

    public saveData(ArrayList<Chef> chefs,int level, Stack<Customer> customers,int selectedChef){
        this.chefdata = new ArrayList<>();
        for (Chef i:chefs) {
            chefdata.add(i.getChefInfo());
        }
        this.level = level;
        this.customerdata = new ArrayList<>();
        for (Customer i:customers) {
            customerdata.add(i.getCustomerData());
        }
        this.selectedChef = selectedChef;
    }

    public ScenarioGameMaster loadGameMaster(PiazzaPanicGame game){
        ScenarioGameMaster gm = new ScenarioGameMaster(chefdata,customerdata,game,level,selectedChef);
        return gm;
    }

}
