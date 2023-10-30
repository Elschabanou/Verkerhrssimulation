/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package verkehrssimulation;

import java.util.*;

/**
 *
 * @author Felix
 */
public class Verkehrssimulation {

    ArrayList<Section> sec = new ArrayList<>();//Dynamischer Array, in dem wir die Sections abspeichern
    ArrayList<Car> cars = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }

    private void init(){
        makeSections();
        makeCars();
    }

    private void makeCars(){
        cars.add(new Car("Taycan 4s","white",35.316,-60, sec.get(0),this));
        cars.add(new Car("Taycan s","blue",30.316,-60, sec.get(0),this));
        cars.add(new Car("Taycan GTS","black",40.316,-65, sec.get(0),this));
        cars.add(new Car("Taycan","red",25.316,-55, sec.get(0),this));
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
        return Car; //Übergabewerte müssen geändert werden
    }

    public void makeSections(){
        //irgendwelche Sections werden erstellt
        Section a = new Section(30, 20);
        Section b = new Section(50,10);
        Section c = new Section(100, 50);
        Section d = new Section(60,2);
        Section e = new Section(130,60);
        Section f = new Section(70,15);

        /* es wäre evtl praktischer, wenn wir die Eigenschaft nr bei Section weglassen würden und
         * die Stelle der Section mit einer Methode über den DynArray bestimmen würden (dynamischer)
         */
        sec.add(a);
        sec.add(b);
        sec.add(c);
        sec.add(d);
        sec.add(e);
        sec.add(f);
    }

    public void Section(){

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
        s = sec.get(j + 1);
        return s;
    }

    /*public void makeCars(int id, String colour){
        Car porsche911 = new Car(id, colour, );
        Car porscheTaycanTurboS = new Car(id,);
        Car porscheMacan = new Car();
        Car porscheGT3RS = new Car();
    }

    public void make911(String name, int id, String colour){
        Car name = new Car(id, colour, 71.32, -35.32, 2000, );

    }*/

    public void verkehrssimulationStarten(){


    }
    
}
