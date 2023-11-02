
package verkehrssimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

        JButton buttonInsert = new JButton("insert new Car");
        buttonInsert.setBounds(10,100, 50, 20);
        buttonInsert.setVisible(true);
        buttonInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertionFrame();

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
                add(buttonInsert);

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
//String name, String colour, double maxAcc, double maxDcc, Section section, Verkehrssimulation v
    public void insertionFrame(){

        JFrame insCar = new JFrame("Insert Car");

        insCar.setSize(200, 350);

        JPanel panel = new JPanel();

        String[] choicesColour = {"beige","blue","green","grey","white"};
        String[] choicesName = {"Taycan","Taycan 4s","Taycan GTS","Taycan s"};

        JLabel name = new JLabel("Name: ");
        JLabel colour = new JLabel("Colour: ");
        JLabel acc = new JLabel("Maximal Acceleration: ");
        JLabel dcc = new JLabel("Maximal Decceleration: ");
        JLabel position = new JLabel("behind car number:");

        //JTextField na = new JTextField("Taycan GTS", 15);
        final JComboBox<String> nam = new JComboBox<String>(choicesName);
        final JComboBox<String> col = new JComboBox<String>(choicesColour);
        //JTextField co = new JTextField("green", 15);
        //JTextField ac = new JTextField("99000", 15);
        JSlider a = new JSlider(90000,150000, 99000);
        a.setMinorTickSpacing(1000);
        a.setMajorTickSpacing(5000);
        a.createStandardLabels(1000000);
        a.setPaintTicks(true);
        //a.setPaintLabels(true);
        JLabel readAcc = new JLabel("Current Acceleration: " + a.getValue());

        a.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int chosenA = a.getValue();
                readAcc.setText("Acceleration: " + chosenA);
            }
        });

        //JTextField dc = new JTextField("-100000", 15);
        JSlider d = new JSlider(-150000,-80000,-100000);
        d.setMinorTickSpacing(1000);
        d.setMajorTickSpacing(5000);
        d.createStandardLabels(1000000);
        d.setPaintTicks(true);
        //d.setPaintLabels(true);
        JLabel readDcc = new JLabel("Current Decceleration: " + d.getValue());

        d.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int chosenD = d.getValue();
                readDcc.setText("Decceleration: " + chosenD);
            }
        });

        //JTextField po = new JTextField("1", 15);
        SpinnerModel p = new SpinnerNumberModel(1, 1, v.cars.size(),1);
        JSpinner place = new JSpinner(p);

        JButton okay = new JButton("Okay");
        okay.setBounds(10,150, 50, 20);
        okay.setVisible(true);

        okay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameOfCar = nam.toString();
                String colourOfCar = col.toString();
                int accOfCar = a.getValue();
                int dccOfCar = d.getValue();
                int posOfPrevCar = ((int) place.getValue())-1;

                Section s = v.cars.get(posOfPrevCar).section;

                Car n = new Car(nameOfCar,colourOfCar,accOfCar,-dccOfCar, s,v);

                if(posOfPrevCar+1 == v.cars.size()){
                    n.velocity = v.cars.get(posOfPrevCar).velocity;
                }else {
                    n.velocity = (v.cars.get(0).velocity + v.cars.get(1).velocity) / 2;
                }

                if(posOfPrevCar+1 != v.cars.size()){
                    if(v.cars.get(posOfPrevCar).section == v.cars.get(posOfPrevCar+1).section){
                        n.relPos = ((v.cars.get(posOfPrevCar).relPos - v.cars.get(posOfPrevCar+1).relPos)/2)+ v.cars.get(posOfPrevCar+1).relPos;
                    }else{
                        if(v.cars.get(posOfPrevCar).relPos > v.cars.get(posOfPrevCar+1).relPos){
                            n.relPos = v.cars.get(posOfPrevCar).relPos - (((v.cars.get(posOfPrevCar).relPos) + (1-v.cars.get(posOfPrevCar+1).relPos))/2);
                            n.section = v.cars.get(posOfPrevCar).section;
                        }else{
                            n.relPos = ((v.cars.get(posOfPrevCar).relPos + (1-v.cars.get(posOfPrevCar+1).relPos))/2) + v.cars.get(posOfPrevCar+1).relPos;
                            n.section = v.cars.get(posOfPrevCar+1).section;
                        }

                    }
                }else{
                    double dis = v.cars.get(posOfPrevCar).velocity/2;
                    n.relPos = v.cars.get(posOfPrevCar).relPos - (dis / v.cars.get(posOfPrevCar).section.getlength());
                }

                v.insertCar(posOfPrevCar, n);

                cars = v.cars;
            }
        });


        panel.add(name);
        panel.add(nam);
        panel.add(colour);
        panel.add(col);
        panel.add(acc);
        panel.add(a);
        panel.add(readAcc);
        panel.add(dcc);
        panel.add(d);
        panel.add(readDcc);
        panel.add(position);
        panel.add(place);

        panel.add(okay);

        insCar.add(panel);
        insCar.setVisible(true);

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