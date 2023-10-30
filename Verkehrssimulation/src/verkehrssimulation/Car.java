
package verkehrssimulation;

/**
 *
 * @author Arne
 */

public class Car {

    public String name;
    double velocity = 0;
    double acceleration = 0;
    double accStep = 0.1;
    double maxAcc;
    double maxDcc;
    public String colour;
    public Section section;
    public double relPos = 0;
    double distance = 0;
    Verkehrssimulation v;

    public Car(String name, String colour, double maxAcc, double maxDcc, Section section, Verkehrssimulation v){
        this.name = name;
        this.colour = colour;
        this.maxAcc = maxAcc;
        this.maxDcc = maxDcc;
        this.section = section;
        this.v = v;

    }

    public static void main(String[] args){

    }

    void calcVelocity(){

        Car PrevCar = v.getPrev(this);

        if(PrevCar.getSection() == section){
            distance = (PrevCar.getPos() - relPos) * section.length;
        }else{
            distance = (PrevCar.getPos() * PrevCar.getSection().length) + ((1-relPos) * section.length);
        }

        if(distance < velocity/2){
            acceleration = maxDcc;
        }else if(distance > (velocity/2 + 0.005) && velocity < section.maxSpeed){
            acceleration = maxAcc;
        }else{
            acceleration = 0;
        }

    }

    void changeVelocity(double timeStep){
        velocity = acceleration * timeStep;
        relPos = relPos + (velocity * timeStep)/section.length;
    }

    void update(double timeStep){
        if(relPos >= 1){
            section = v.getNewSection(section);
            relPos = 0;
        }
        calcVelocity();
        changeVelocity(timeStep);
    }


    public double getPos(){
        return relPos;
    }

    public Section getSection(){
        return section;
    }


}
