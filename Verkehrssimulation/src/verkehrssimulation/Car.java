
package verkehrssimulation;

/**
 *
 * @author Arne
 */

public class Car {

    public int id;
    double velocity = 0;
    double acceleration = 0;
    double accStep = 0.1;
    double maxAcc;
    double maxDcc;
    int mass;
    String colour;
    Section section;
    double relPos = 0;
    double distance = 0;

    public Car(int id, String colour, double maxAcc, double maxDcc, int mass, Section section) {
        id = id;
        colour = colour;
        maxAcc = maxAcc;
        maxDcc = maxDcc;
        mass = mass;
        section = section;

    }

    public static void main(String[] args){

    }

    void calcVelocity(){

        Car PrevCar = Verkehrssimulation.getPrev();

        if(PrevCar.getSection() == section){
            distance = (PrevCar.getPos() - relPos) * section.length;
        }else{
            distance = (PrevCar.getPos() * PrevCar.getSection().length) + ((1-relPos) * section.length);
        }

        if(distance <= velocity/2){
            if(acceleration >= 0) acceleration = 0;
            else if(acceleration >= maxDcc + accStep) acceleration -= accStep;
        }

    }


    public double getPos(){
        return relPos;
    }

    public Section getSection(){
        return section;
    }


}
