
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
    private ArrayList<Car> cars = new ArrayList<Car>();
    private ArrayList<Section> sections = new ArrayList<Section>();
    private BufferedImage carImage;
    private Verkehrssimulation v;

    public void updateGUI(ArrayList<Car> cars) {
    }

    public void createAndShowGUI() {
        GUI2 gui = this; // Referenz auf die Instanz

        JFrame frame = new JFrame("Verkehrssimulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        cars = v.cars;
        sections = v.sec;

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                g.setColor(Color.GREEN);
                g.fillRect(0, 0, panelWidth, panelHeight);

                int gesLength = 0;
                double kDist = 50;
                int offset = getWidth()*3/4;

                for(int i=0; i<sections.size();i++){
                    gesLength += sections.get(i).length*getWidth()/5;
                }

                g.setColor(Color.BLACK);
                g.drawString("Zeit (sec): " + (int)(cars.get(0).timeGes*60*60), 10, 10);

                for (int i = 0; i < sections.size(); i++) {
                    g.setColor(Color.GRAY);
                    /*g.fillRect(, 
                        getHeight()/3, 
                        gesLength, 
                        getHeight()/5);*/ //x Wert muss noch mit relPos angepasst werden um die Sections zu bewegen
                }

                for (int i = 0; i < cars.size(); i++) {
                    try {
                        BufferedImage image = ImageIO.read(GUI2.class.getResource("images/Taycan_Topview_white_small.png"));
                        if(i!=0) g.drawImage(image, (int)(offset - i * cars.get(i).distance/5*getWidth() * kDist), (int)(getHeight()/3+getHeight()/5*0.2), this);
                        else g.drawImage(image, (int)(offset), (int)(getHeight()/3+getHeight()/5*0.2), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        frame.add(drawingPanel);

        try {
            carImage = ImageIO.read(GUI2.class.getResource("images/Taycan_Topview_white_small.png"));
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

    public GUI2(Verkehrssimulation ve){
        v = ve;
        SwingUtilities.invokeLater(() -> {
            //GUI2 gui = new GUI2();
            createAndShowGUI();
        });
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI2 gui = new GUI2();
            gui.createAndShowGUI();
        });
    }*/
}