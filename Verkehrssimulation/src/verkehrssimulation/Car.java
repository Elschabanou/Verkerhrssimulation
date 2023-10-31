
package verkehrssimulation;

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
    double timeGes = 0;
    double lastTime = 0;
    double brakingDist = 0;

    public Car(String name, String colour, double maxAcc, double maxDcc, Section section, Verkehrssimulation v){
        this.name = name;
        this.colour = colour;
        this.maxAcc = maxAcc;
        this.maxDcc = maxDcc;
        this.section = section;
        this.v = v;
        this.regler = new PI_Regler(0.000005, 0.000000000005); //kp: wie schnell wird angestrebte Beschleunigung erreicht (je kleiner desto langsamer)  |  ki: wie stark schwank er nach oben aus
    }

    public static void main(String[] args){

    }

    void updateAcceleration(double setpoint, double timeStep) {

        double controlSignal = regler.calculate(setpoint, acceleration, timeStep);
        acceleration += controlSignal;
        //System.out.println(acceleration);
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

        brakingDist = (velocity/10)*(velocity/10) - (v.getNewSection(section).maxSpeed/100)*(v.getNewSection(section).maxSpeed/100);

        if((distance*1000) < velocity/2 || velocity > section.maxSpeed || (brakingDist >= (1-relPos)*section.length) && velocity > v.getNewSection(section).maxSpeed){
            //acceleration = maxDcc;
            //regler.setParameters(0.4, 0.01);
            updateAcceleration(maxDcc, timeStep);
        }else if((distance*1000) > (velocity/2 + 0.005) && velocity < section.maxSpeed){
            //acceleration = maxAcc;
            //regler.setParameters(0.2, 0.0005);
            updateAcceleration(maxAcc, timeStep);
        }else{
            //acceleration = 0;
            //regler.setParameters(0.5, 0.0005);
            updateAcceleration(0.0, timeStep);
        }

    }

    void move(double timeStep){
        velocity += acceleration * timeStep;
        relPos += (velocity * timeStep)/section.length;
        timeGes += timeStep;
        if(timeGes*60*60 >= lastTime*60*60 + 0.3){
            System.out.println("Max: " + section.maxSpeed + " Cur: " + velocity + /*" Strecke: " + relPos*section.length + */" Time: " + timeGes*60*60);
            lastTime = timeGes;
        }
    }

    void update(double timeStep){
        if(relPos >= 1){
            section = v.getNewSection(section);
            relPos = 0;
        }
        calcAcceleration(timeStep);
        move(timeStep);
    }

    

}
