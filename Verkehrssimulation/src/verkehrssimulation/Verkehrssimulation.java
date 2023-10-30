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

    ArrayList<Object> sec = new ArrayList<>();//Dynamischer Array, in dem wir die Sections abspeichern

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.print("Hallo Arne");
    }


    public static Car getPrev(){
        return new Car(0, null, 0, 0, 0, null); //Muss geändert werden
    }

    public void sectionsErstellen(){
        //irgendwelche Sections werden erstellt
        Section a = new Section(1, 30, 20);
        Section b = new Section(2, 50,10);
        Section c = new Section(3, 100, 50);
        Section d = new Section(4, 60,2);
        Section e = new Section(5, 130,60);
        Section f = new Section(6, 70,15);

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

    public void verkehrssimulationStarten(){


    }
    
}
