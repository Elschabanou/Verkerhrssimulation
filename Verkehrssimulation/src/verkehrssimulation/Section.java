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
    public double offset;

    public Section(int maxSpeed, double length, double offset) {
        this.maxSpeed = maxSpeed;
        this.length = length;
        this.offset = offset;
    }

    public int getmaxSpeed(){
        return maxSpeed;
    }

    public double getlength(){
        return length;
    }
}
