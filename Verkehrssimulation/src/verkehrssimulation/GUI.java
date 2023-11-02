package verkehrssimulation;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */




/**
 *
 * @author Felix
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class GUI {

    
    private static final int ANIMATION_DELAY = 10; // Zeitverzögerung zwischen Frames in Millisekunden
    public ArrayList<Car> cars = new ArrayList<>();
    private int imageX = 0; // Anfangsposition des Bildes auf der x-Achse
    private BufferedImage image;
    Verkehrssimulation v = new Verkehrssimulation();
    ArrayList<Car> car = new ArrayList<>();


    public static void main(String[] args) {
        new GUI();
    }
    public GUI(){
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    public void lists(){
        for(int i = 0; i < v.cars.size(); i++){
            System.out.println(v.cars.get(i));
        }

    }

    public void updateGUI(ArrayList<Car> cars) {
        // Hier können Sie Ihre GUI basierend auf der Liste der Autos aktualisieren
           
        
    }
    
    public static void start(int cars_count){
         
            
        }

    public static void createAndShowGUI() {
        //GUI gui = new GUI();
        
        JFrame frame = new JFrame("Verkehrssimulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel drawingPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int pos_start = 0;
                

                // Set the background color to blue
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, panelWidth, panelHeight);

                // Zeichnen des Bildes an seiner aktuellen Position (imageX)
                g.setColor(new Color(105, 109, 110));
                g.fillRect(0, panelHeight / 2 - 25, 500, 150);
                
                for (int i = 0; i < 4; i++){
                    try{
                        BufferedImage image = ImageIO.read(GUI.class.getResource("images/Taycan_Topview_white.png"));
                        g.drawImage(image, pos_start, panelHeight /2, this);
                        System.out.print("halo");
                        pos_start += 300;
                        
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        frame.add(drawingPanel);

        try {
            // Laden des Bildes aus dem Projektressourcenverzeichnis
            gui.image = ImageIO.read(GUI.class.getResource("images/Taycan_Topview_white.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(ANIMATION_DELAY, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Bewegung des Bildes um 1 Pixel pro Frame in positiver x-Richtung
                gui.imageX++;
                /*if (gui.imageX > panelWidth) {
                    // Wenn das Bild das Panel verlässt, von links nach rechts, zurücksetzen
                    gui.imageX = -gui.image.getWidth();
                }*/
                gui.updateGUI(gui.cars);
                drawingPanel.repaint(); // Das Panel neu zeichnen, um die Änderungen anzuzeigen
            }
        });
        timer.start();

        frame.setVisible(true);
    }
}