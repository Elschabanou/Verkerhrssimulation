
package verkehrssimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;



public class GUI2 {
    private static final int ANIMATION_DELAY = 10;
    private ArrayList<Car> cars = new ArrayList<Car>();
    private ArrayList<Section> sections = new ArrayList<Section>();
    private Verkehrssimulation v;
    boolean start = true;

    public void updateGUI(ArrayList<Car> cars) {
    }

    public void createAndShowGUI() {
        GUI2 gui = this; // Referenz auf die Instanz

        JFrame frame = new JFrame("Verkehrssimulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 580);
        
        cars = v.cars;
        sections = v.sec;

        JButton buttonBreak = new JButton("Emergency Break");
        buttonBreak.setBounds(10, 50, 150, 20);
        buttonBreak.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){ 
                cars.get(0).eBreak = true;
            }
            @Override
            public void mouseReleased(MouseEvent e){
                cars.get(0).eBreak = false;
            }
        });

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                g.setColor(Color.decode("#7aad55"));
                g.fillRect(0, 0, panelWidth, panelHeight);
                

                double kWidth = 3;
                double kHeight = 2.3;
                double dHeight = 7;
                int offset = getWidth()*3/4;
                double leftSpeed = cars.get(0).relPos*cars.get(0).section.length*getWidth()*kWidth;

                g.setColor(Color.WHITE);
                g.drawString("Zeit (sec): " + (int)(cars.get(0).timeGes*60*60), 10, 25);

                
                add(buttonBreak);         
                   
                /*try{
                    BufferedImage background = ImageIO.read(GUI2.class.getResource("images/background.jpg"));
                    g.drawImage(background, (int)((offset - sections.get(0).length*getWidth()*kWidth) - (leftSpeed)), 0, this);
                } catch(IOException e){
                    System.out.println(e);
                }*/

                for (int i = 0; i < sections.size(); i++){
                   
                    if(i == v.getSectionNr(cars.get(0).section)){
                        g.setColor(Color.WHITE);
                        g.drawString("Limit: " + sections.get(i).maxSpeed, (int)((offset) - (leftSpeed)), getHeight()/3 - 20);
                        g.setColor(sections.get(i).color);
                        g.fillRect((int)((offset) - (leftSpeed)), 
                        (int)(getHeight()/kHeight), 
                        (int)(sections.get(i).length*getWidth()*kWidth), 
                        (int)(getHeight()/dHeight));
                    }
                    else if(i < v.getSectionNr(cars.get(0).section)){
                        g.setColor(Color.WHITE);
                        if(i+1 == v.getSectionNr(cars.get(0).section))g.drawString("Limit: " + sections.get(i).maxSpeed, (int)((offset - sections.get(i).length*getWidth()*kWidth) - (leftSpeed)), getHeight()/3 - 20);
                        g.setColor(sections.get(i).color);
                        g.fillRect((int)((offset - sections.get(i).length*getWidth()*kWidth) - (leftSpeed)), 
                        (int)(getHeight()/kHeight),
                        (int)(sections.get(i).length*getWidth()*kWidth), 
                        (int)(getHeight()/dHeight));
                    }
                    else if(i > v.getSectionNr(cars.get(0).section)){
                        g.setColor(Color.WHITE);
                        if(i == 1+ v.getSectionNr(cars.get(0).section)) g.drawString("Limit: " + sections.get(i).maxSpeed, (int)((offset + cars.get(0).section.length*getWidth()*kWidth) - (leftSpeed)), getHeight()/3 - 20);
                        g.setColor(sections.get(v.getSectionNr(v.getNewSection(cars.get(0).section))).color);
                        g.fillRect((int)((offset + cars.get(0).section.length*getWidth()*kWidth) - (leftSpeed)), 
                        (int)(getHeight()/kHeight),
                        (int)(sections.get(i).length*getWidth()*kWidth), 
                        (int)(getHeight()/dHeight));
                    }
                        //System.out.print((int)((offset + sections.get(i).offset*getWidth()) - (leftSpeed)));
                        //System.out.print((cars.get(0).relPos + v.getSectionNr(cars.get(0).section))/(sections.size()-1));
                        //System.out.println(" " + (int)(sections.get(i).length*getWidth()));
                }


                double cOffset = 0;
                

                for (int i = 0; i < cars.size(); i++) {
                    if(cars.get(i).distance*getWidth()*kWidth <= 30 && v.getSectionNr(cars.get(i).section) != 0){
                        g.setFont(g.getFont().deriveFont(50.0f));
                        g.setColor(Color.RED);
                        g.drawString("CRASH!", getWidth()/2, getHeight()/4);
                        //cars.get(i).relPos = cars.get(i-1).relPos - 30/getWidth()/kWidth;
                    }
                    try {
                        g.setColor(Color.WHITE);
                        
                        BufferedImage image = ImageIO.read(GUI2.class.getResource("images/Taycan_Topview_" + cars.get(i).colour + "_small.png"));
                        
                        g.setFont(g.getFont().deriveFont(100.0f));
                        if(i!=0){
                            
                            g.drawImage(image, (int)(offset - (cOffset + cars.get(i).distance)*getWidth() * kWidth), (int)(getHeight()/kHeight+getHeight()/5*0.2), this);
                            //g.drawString(".", (int)(offset - (cOffset + cars.get(i).distance)*getWidth() * kWidth), (int)(getHeight()/3+getHeight()/5*0.2));
                            //g.drawRect((int)(offset - (cOffset + cars.get(i).distance)*getWidth() * kWidth), (int)(getHeight()/3+getHeight()/5*0.2), 30, 13);
                            g.setFont(g.getFont().deriveFont(15.0f));
                            g.drawString(/* "Speed: " +*/ String.valueOf((int)(cars.get(i).velocity)), (int)(offset - (cOffset + cars.get(i).distance)*getWidth() * kWidth), (int)(getHeight()/kHeight+getHeight()/5*0.2) + 80);
                        }else {
                            g.drawImage(image, (int)(offset), (int)(getHeight()/kHeight+getHeight()/5*0.2), this);
                            //g.drawString(".", (int)(offset), (int)(getHeight()/3+getHeight()/5*0.2));
                            
                            //g.drawRect((int)(offset), (int)(getHeight()/3+getHeight()/5*0.2), 30, 13);
                            g.setFont(g.getFont().deriveFont(15.0f));
                            g.drawString(/* "Speed: " +*/ String.valueOf((int)(cars.get(i).velocity)), (int)(offset), (int)(getHeight()/kHeight+getHeight()/5*0.2) + 80);
                        }
                        if(i>0)cOffset += cars.get(i).distance;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        frame.add(drawingPanel);


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