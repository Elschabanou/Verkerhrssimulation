package verkehrssimulation;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import java.util.*;

/**
 *
 * @author Felix
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Simple SwingGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Set the background color to blue
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, panelWidth, panelHeight);

                // Load the image from a local file
                try {
         
                    BufferedImage image = ImageIO.read(getClass().getResource("Taycan_Topview_white.png"));
                    // Draw the resized image in the middle of the panel
                    int imageX = (panelWidth - image.getWidth()) / 2;
                    int imageY = (panelHeight - image.getHeight()) / 2;
                    g.drawImage(image, imageX, imageY, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        frame.add(drawingPanel);

        frame.setVisible(true);
    }
}
