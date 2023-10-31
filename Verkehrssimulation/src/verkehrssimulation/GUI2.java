
package verkehrssimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class GUI2 {
    private static final int ANIMATION_DELAY = 10;
    private ArrayList<String> cars = new ArrayList<>();
    private BufferedImage carImage;

    public void updateGUI(ArrayList<String> cars) {
        // Aktualisieren Sie hier Ihre cars-ArrayList
    }

    public void createAndShowGUI() {
        GUI2 gui = this; // Referenz auf die Instanz

        JFrame frame = new JFrame("Verkehrssimulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        cars.add("Taycan");

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                g.setColor(Color.BLUE);
                g.fillRect(0, 0, panelWidth, panelHeight);


                for (int i = 0; i < cars.size(); i++) {
                    //Car car = cars.get(i);
                    try {
                        BufferedImage image = ImageIO.read(GUI2.class.getResource("images/Taycan_Topview_white.png"));
                        g.drawImage(image, 0, 0, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        frame.add(drawingPanel);

        try {
            carImage = ImageIO.read(GUI2.class.getResource("images/Taycan_Topview_white.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.updateGUI(cars);
                drawingPanel.repaint();
            }
        });
        timer.start();

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI2 gui = new GUI2();
            gui.createAndShowGUI();
        });
    }
}