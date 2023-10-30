/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verkehrssimulation;

import java.util.ArrayList;

/**
 *
 * @author Felix
 */
public class Section {


    public static void main(String[] args) {
        
    }

    int maxSpeed;
    double length;

    public Section(int maximumSpeed, double l){
        maxSpeed = maximumSpeed;
        length = l;
    }

    public int getmaxSpeed(){
        return maxSpeed;
    }

    public double getlength(){
        return length;
    }

    /*public int getNr(ArrayList<Object> arraySection){
        int nr;

        for(int i = 0; i < arraySection.size(); i++){

        }

        return nr;
    }*/

    
    public double getLength(){
        return length;
    }
}
