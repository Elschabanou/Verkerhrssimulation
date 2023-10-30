
package verkehrssimulation;


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
        if(Verkehrssimulation.getPrev().getSection() == section){
            distance = (Verkehrssimulation.getPrev().getPos() - relPos) * section.getLength();
        } 
    }


    public double getPos(){
        return relPos;
    }

    public Section getSection(){
        return section;
    }


}
