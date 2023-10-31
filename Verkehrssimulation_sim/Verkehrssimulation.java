import java.util.*;

/**
 *
 * @author Felix
 */
public class Verkehrssimulation {
    Verkehrssimulation v;
    ArrayList<Section> sec = new ArrayList<>();
    ArrayList<Car> cars = new ArrayList<>();

    public Verkehrssimulation(){
        //v = new Verkehrssimulation();
        //v.init();
        init();
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

    }

    void init(){
        makeSections();
        makeCars();
        run();
    }

    void makeCars(){
        cars.add(new Car("Taycan 4s","white",1000,-1800, sec.get(0),this));
        //cars.add(new Car("Taycan s","blue",30.316,-60, sec.get(0),this));
        //cars.add(new Car("Taycan GTS","black",40.316,-65, sec.get(0),this));
        //cars.add(new Car("Taycan","red",25.316,-55, sec.get(0),this));
    }
    public Car getPrev(Car driving){
        if(driving == cars.get(0)){
            return null;
        }else{
            int index = 0;
            for (Car i:cars) {
                if(i == driving){
                    break;
                }else{
                    index++;
                }
            }
            return cars.get(index-1);
        }

    }

    public void makeSections(){
        sec.add(new Section(30, 20));
        sec.add(new Section(50,10));
        sec.add(new Section(100, 50));
        sec.add(new Section(60,2));
        sec.add(new Section(130,60));
        sec.add(new Section(70,15));
    }


    /*Die auf die übergebene Section folgende Section wird über das Array bestimmt und
     *zurückgegeben
     */
    public Section getNewSection(Section s){
        int j = 0;
        for(int i = 0; i < sec.size(); i++){
            if(s == sec.get(i)){
                j = i;
                i = sec.size();
            }
        }
        s = sec.get(j + 1);
        return s;
    }

    public int getSectionNr(Section section){
        int nr = 0;

        for(int i = 0; i < sec.size(); i++){
            if(section == sec.get(i)){
                nr = i;
                i = sec.size();
            }
        }

        return nr;
    }

    private void run(){
        boolean end = false;
        while(!end){
            for(int i = 0; i<cars.size();i++)
            {
                cars.get(i).update(0.0000005);
            }
            if(cars.get(0).section == sec.get(sec.size()-1) && cars.get(0).relPos == 1){
                end = true;
            }
        }

    }
    
}
