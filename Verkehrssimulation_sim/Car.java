
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

    public Car(String name, String colour, double maxAcc, double maxDcc, Section section, Verkehrssimulation v){
        this.name = name;
        this.colour = colour;
        this.maxAcc = maxAcc;
        this.maxDcc = maxDcc;
        this.section = section;
        this.v = v;
        this.regler = new PI_Regler(0.00005, 0.001); //kp: wie schnell wird angestrebte Beschleunigung erreicht  |  ki: wie stark schwank er nach oben aus
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
        }else distance = 1;
        
        if(distance*1000 < velocity/2 || velocity > section.maxSpeed){
            //acceleration = maxDcc;
            //regler.setParameters(0.4, 0.01);
            updateAcceleration(maxDcc, timeStep);
        }else if(distance*1000 > (velocity/2 + 0.005) && velocity < section.maxSpeed){
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
        System.out.println("Max: " + section.maxSpeed + " Cur: " + velocity + " Strecke: " + relPos*section.length);
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
