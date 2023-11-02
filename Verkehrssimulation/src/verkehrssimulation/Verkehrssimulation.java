/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package verkehrssimulation;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Felix
 */
public class Verkehrssimulation {
    Verkehrssimulation v;
    ArrayList<Section> sec = new ArrayList<>();
    ArrayList<Car> cars = new ArrayList<>();

    public Verkehrssimulation(){
        GUI2 gui = new GUI2(this);
        init();
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Verkehrssimulation v = new Verkehrssimulation();
    }

    private void init(){
        makeSections();
        makeCars();
        run();
    }

    /* 
    available colours:
    
    ⚪ "white"
    ⚪ "green"
    ⚪ "blue"
    ⚪ "grey"
    ⚪ "beige"    
    
    */
    
    
    private void makeCars(){
        cars.add(new Car("Taycan 4s","white",150000,-150000, sec.get(0),this));
        cars.add(new Car("Taycan s","blue",91000,-100000, sec.get(0),this));
        cars.add(new Car("Taycan GTS","green",91000,-100000, sec.get(0),this));
        cars.add(new Car("Taycan","beige",91000,-100000, sec.get(0),this));
        cars.add(new Car("Taycan 4s","white",91000,-120000, sec.get(0),this));
        cars.add(new Car("Taycan s","blue",91000,-120000, sec.get(0),this));
        cars.add(new Car("Taycan GTS","green",91000,-90000, sec.get(0),this));
        cars.add(new Car("Taycan","beige",91000,-90000, sec.get(0),this));
    }
    public Car getPrev(Car driving){
        if(driving == cars.get(0)){
            return null;
        }else{
            int index = 0;
            for (Car i:cars) {
                if(i == driving){
                    break;
                }else{
                    index++;
                }
            }
            return cars.get(index-1);
        }

    }

    public void makeSections(){
        sec.add(new Section(50, 0.1, Color.GRAY));
        sec.add(new Section(200,0.9, Color.LIGHT_GRAY));
        sec.add(new Section(30, 0.1, Color.GRAY));
        sec.add(new Section(600, 2, Color.LIGHT_GRAY));
        sec.add(new Section(30,0.1, Color.GRAY));
        sec.add(new Section(100, 0.5, Color.LIGHT_GRAY));
        sec.add(new Section(60,0.1, Color.GRAY));
    }


    /*Die auf die übergebene Section folgende Section wird über das Array bestimmt und
     *zurückgegeben
     */
    public Section getNewSection(Section s){
        int j = 0;
        for(int i = 0; i < sec.size(); i++){
            if(s == sec.get(i)){
                j = i;
                i = sec.size();
            }
        }
        if(j+1 != sec.size())s = sec.get(j + 1);
        else s = null;
        return s;
    }

    public int getSectionNr(Section section){
        int nr = 0;

        for(int i = 0; i < sec.size(); i++){
            if(section == sec.get(i)){
                nr = i;
                i = sec.size();
            }
        }

        return nr;
    }
 
    private void run(){
        boolean end = false;
        while(!end){
            for(int i = 0; i<cars.size();i++)
            {
                end = !cars.get(i).update(0.00000000005); //realtime: 0.000000000018
                /*try {
                    Thread.sleep(0);
                  } catch (InterruptedException e) {
                    System.out.println("Fehler: " + e.getMessage());
                    Thread.currentThread().interrupt();
                  }*/
            }
            if(cars.get(0).section == sec.get(sec.size()-1) && cars.get(0).relPos == 1){
                end = true;
            }
        }

    }
}
