
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
    Section prevSec;
    int offset = 1400*3/4;
    int bOffset[] = {offset, offset, offset, offset, offset, offset};
    double leftSpeed;
    MouseAdapter mAdapter = new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){
            cars.get(0).eBreak = true;
        }
        @Override
        public void mouseReleased(MouseEvent e){
            cars.get(0).eBreak = false;
        }
    };

    public void updateGUI(ArrayList<Car> cars) {
    }

    public void createAndShowGUI() {
        GUI2 gui = this; // Referenz auf die Instanz

        JFrame frame = new JFrame("Verkehrssimulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 580);

        cars = v.cars;
        sections = v.sec;
        prevSec = cars.get(0).section;


        JButton buttonBreak = new JButton("Emergency Break");
        buttonBreak.setBounds(10, 50, 150, 20);
        buttonBreak.addMouseListener(mAdapter);

        JButton buttonInsert = new JButton("insert new Car");
        buttonInsert.setBounds(10,80, 150, 20);
        buttonInsert.setVisible(true);
        buttonInsert.addActionListener(insertAction);

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                g.setColor(Color.decode("#7aad55"));
                g.fillRect(0, 0, panelWidth, panelHeight);
                

                double kWidth = 3; //3
                double kHeight = 2.3;
                double dHeight = 7;
                int offset = getWidth()*3/4;
                double prevLeftSpeed = leftSpeed;
                leftSpeed = cars.get(0).relPos*cars.get(0).section.length*getWidth()*kWidth;

                g.setColor(Color.WHITE);
                g.drawString("Zeit (sec): " + (int)(cars.get(0).timeGes*60*60), 10, 25);


                /*try{
                    BufferedImage background = ImageIO.read(GUI2.class.getResource("images/background.jpg"));
                    g.drawImage(background, (int)((offset - sections.get(0).length*getWidth()*kWidth) - (leftSpeed)), 0, this);
                } catch(IOException e){
                    System.out.println(e);
                }*/

                double[] xOffsets = {-800.0/1400.0, -300.0/1400.0, 150.0/1400.0, -250.0/1400.0, -600.0/1400.0, 200.0/1400.0};
                double[] yOffsets = {120.0/580.0, 70.0/580.0, 50.0/580.0, 350.0/580.0, 400.0/580.0, 375.0/580.0};
                String [] imgNames = {"1", "4", "2", "5", "4", "5"};
                for(int i=0; i<6; i++){
                    try{
                        BufferedImage img = ImageIO.read(GUI2.class.getResource("images/tree_" + imgNames[i] + ".png"));
                        if(cars.get(0).section != prevSec){
                            prevSec = cars.get(0).section;
                            for(int l=0; l<6; l++){bOffset[l] -= prevLeftSpeed;}
                        }if((int)(bOffset[i] - leftSpeed)+xOffsets[i]*getWidth()<-100) bOffset[i] = (int)(getWidth()+leftSpeed + 5)-(int)(xOffsets[i]*getWidth());
                        g.setColor(Color.BLACK);
                        g.drawImage(img, (int)(bOffset[i] - leftSpeed)+(int)(xOffsets[i]*getWidth()), (int)(yOffsets[i]*getHeight()), this);
                    }catch(IOException e){
                        System.out.println(e);
                    }
                }

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
                        cars.get(i).velocity = 0;
                        cars.get(i).relPos = cars.get(i-1).relPos - 0.0072/cars.get(i).section.length;
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


        drawingPanel.add(buttonBreak);
        drawingPanel.add(buttonInsert);
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

    ActionListener insertAction =  new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            insertionFrame();

        }
    };

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
                String nameOfCar = (String) nam.getSelectedItem();
                String colourOfCar = (String) col.getSelectedItem();
                double accOfCar = a.getValue();
                double dccOfCar = d.getValue();
                if(dccOfCar>0){
                    dccOfCar = -dccOfCar;
                }
                int posOfPrevCar = ((int) place.getValue())-1;

                /*System.out.println("name des Autos: "+ nameOfCar);
                System.out.println("farbe des Autos: "+ colourOfCar);
                System.out.println("Beschleunigung: "+accOfCar);
                System.out.println("Entschleunigung: "+dccOfCar);
                System.out.println("PosOFPrevCar in Array als Index: " + posOfPrevCar);*/

                Section s = v.cars.get(posOfPrevCar).section;

                Car n = new Car(nameOfCar,colourOfCar,accOfCar, dccOfCar, s,v);

                /* Wenn Auto an letzter Stelle hat es anfangs die gleiche Geschwindigkeit wie das vorherige,
                 * ansonsten hat das neue Auto die durchschnittliche Anfangsgeschwindigkeit von den beiden Autos vor und
                 * hinter ihm
                 */
                if(posOfPrevCar+1 == v.cars.size()){
                    n.velocity = v.cars.get(posOfPrevCar).velocity;
                }else {
                    n.velocity = (v.cars.get(0).velocity + v.cars.get(1).velocity) / 2;
                }

                //if-Abfrage true, wenn das Auto nicht an letzter Stelle eingefügt wird
                if(posOfPrevCar+1 != v.cars.size()){
                    if(v.cars.get(posOfPrevCar).section == v.cars.get(posOfPrevCar+1).section){
                        n.relPos = ((v.cars.get(posOfPrevCar).relPos - v.cars.get(posOfPrevCar+1).relPos)/2)+ v.cars.get(posOfPrevCar+1).relPos;
                    }else{
                        if(v.cars.get(posOfPrevCar).relPos > v.cars.get(posOfPrevCar+1).relPos){
                            n.relPos = v.cars.get(posOfPrevCar).relPos - (((v.cars.get(posOfPrevCar).relPos) + (1-v.cars.get(posOfPrevCar+1).relPos))/2);
                            n.section = v.cars.get(posOfPrevCar).section;
                            //n.nextSpeed = n.section.maxSpeed;
                        }else{
                            n.relPos = ((v.cars.get(posOfPrevCar).relPos + (1-v.cars.get(posOfPrevCar+1).relPos))/2) + v.cars.get(posOfPrevCar+1).relPos;
                            n.section = v.cars.get(posOfPrevCar+1).section;
                            //n.nextSpeed = n.section.maxSpeed;
                        }

                    }
                }else{
                    double dis = v.cars.get(posOfPrevCar).velocity/2;
                    n.relPos = v.cars.get(posOfPrevCar).relPos - (dis / v.cars.get(posOfPrevCar).section.getlength());
                }

                v.insertCar(posOfPrevCar+1, n);

                cars = v.cars;

                //System.out.println("Maximales Speed in dieser Section: " + n.section.getmaxSpeed());

                /*for(int i = 0; i < v.cars.size(); i++){
                    System.out.println(i + " Farbe: " +v.cars.get(i).colour + " Geschw: " +v.cars.get(i).velocity + " maxDcc: " + v.cars.get(i).maxDcc);
                }
                for(int i = 0; i < cars.size(); i++){
                    System.out.println(i + " Farbe: " +cars.get(i).colour + " Geschw: " +cars.get(i).velocity);
                }*/
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