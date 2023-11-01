/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package verkehrssimulation;

import java.awt.Color;
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
    public Color color;

    public Section(int maxSpeed, double length, double offset, Color color) {
        this.maxSpeed = maxSpeed;
        this.length = length;
        this.offset = offset;
        this.color = color;
    }

    public int getmaxSpeed(){
        return maxSpeed;
    }

    public double getlength(){
        return length;
    }
}
