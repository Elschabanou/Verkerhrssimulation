
package verkehrssimulation;

/**
 *
 * @author Arne
 */

public class Car {

    public int id;
    double velocity = 0;
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
    }


    public double getPos(){
        return relPos;
    }

    public Section getSection(){
        return section;
    }


}
