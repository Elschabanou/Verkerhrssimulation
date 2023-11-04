
package verkehrssimulation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;
import java.net.URL;


/**
 *
 * @author Arne
 */

public class Car {

    public String name;
    public double velocity = 0;
    double acceleration = 0;
    double accStep = 0.1;
    double maxAcc;
    double maxDcc;
    public String colour;
    public Section section;
    public double relPos = 0;
    double distance = 0;
    Verkehrssimulation v;
    PI_Regler regler;
    public double timeGes = 0;
    double lastTime = 0;
    double brakingDist = 0;
    public boolean eBreak = false;
    public String filepath;
    public double nextSpeed;


    public Car(String name, String colour, double maxAcc, double maxDcc, Section section, Verkehrssimulation v){
        this.name = name;
        this.colour = colour;
        this.maxAcc = maxAcc;
        this.maxDcc = maxDcc;
        this.section = section;
        this.v = v;
        this.regler = new PI_Regler(0.0000008, 0.00000000000000002);
        //kp: wie schnell wird angestrebte Beschleunigung erreicht (je kleiner, desto langsamer) (0.000005) (menschl. 0.0000008)
        // ki: wie stark schwankt er nach oben aus (0.000000000005) (menschl. 0.000000000000002)
    }

    public static void main(String[] args){

    }

    void updateAcceleration(double setpoint, double timeStep) {

        double controlSignal = regler.calculate(setpoint, acceleration, timeStep);
        acceleration += controlSignal;
    }

    void calcAcceleration(double timeStep){

        Car PrevCar = v.getPrev(this);
        if(PrevCar != null){
            if(PrevCar.section == section){
                distance = (PrevCar.relPos - relPos) * section.length;
            }else{
                distance = (PrevCar.relPos * PrevCar.section.length) + ((1-relPos) * section.length);
            }
        }else distance = 10;

        try{
            nextSpeed = v.getNewSection(section).maxSpeed;
        }catch(Exception e){
            nextSpeed = section.maxSpeed;
        }

        brakingDist = (velocity/10)*(velocity/10) - (nextSpeed/9)*(nextSpeed/9);
        //if(eBreak)System.out.println(eBreak);
        if((eBreak && velocity>0) || (velocity>0 && ((distance*1000) < velocity/2+10 || velocity > section.maxSpeed+1 || ((brakingDist >= (1-relPos)*section.length*1000) && velocity > nextSpeed)))){
            regler.setParameters(0.00002, 0.0000000000005);
            updateAcceleration(maxDcc, timeStep);
        }else if(((distance*1000) > (velocity/2+10) && distance*1000 > 10) && velocity <= section.maxSpeed){
            regler.setParameters(0.0000008, 0.000000000000002);
            updateAcceleration(maxAcc, timeStep);
        }else{
            regler.setParameters(0.0000008, 0.000000000000002);
            updateAcceleration(0.0, timeStep);
        }

    }

    void move(double timeStep){
        velocity += acceleration * timeStep;
        relPos += (velocity * timeStep)/section.length;
        timeGes += timeStep;
        if(timeGes*60*60 >= lastTime*60*60 + 0.3){
            //System.out.println("Max: " + section.maxSpeed + " Cur: " + velocity + /*" Strecke: " + relPos*section.length + " Time: " + timeGes*60*60 */ " BrWeg: " + brakingDist);
            lastTime = timeGes;
        }
    }

    boolean update(double timeStep){
        if(relPos >= 1){
            section = v.getNewSection(section);
            if(section == null){
                return false;
            }
            relPos = 0;
        }
        calcAcceleration(timeStep);
        move(timeStep);
        return true;
    }
    
    public static void playSound(String filePath) {
        try {
            
            URL resourceUrl = Car.class.getResource(filePath);

            if (resourceUrl != null) {
                String Path = resourceUrl.getFile();
                System.out.println("Dateipfad der WAV-Datei: " + Path);
                File soundFile = new File(Path);

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
            
                clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                }
            });
            
            clip.open(audioInputStream);
            clip.start();
            
            
            } else {
                System.out.println("Die WAV-Datei wurde nicht gefunden.");
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


  
   


