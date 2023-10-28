/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verkehrssimulation;

/**
 *
 * @author Felix
 */
public class Section {
    public int nr;//Variable für die Abschnittsnumer
    int maxSpeed;
    double length;

    public Section(int nummer, int maximumSpeed, double l){
        nr = nummer;
        maxSpeed = maximumSpeed;
        length = l;
    }

    public int getmaxSpeed(){
        return maxSpeed;
    }

    public double getlength(){
        return length;
    }

    public int getNr(){
        return nr;
    }

    
}
