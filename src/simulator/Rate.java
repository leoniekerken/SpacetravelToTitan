package simulator;

import titan.RateInterface;

/**
 * data structure to store the rate of change (acceleration) for all objects in the system
 * used to update the state of the system
 *
 * @author Leo
 */
public class Rate implements RateInterface {

    public static boolean DEBUG = false;

    //storing acc values for all objects
    public Vector3d[] rate;

    public Rate(){
    }

    public void initialize(int size){
        rate = new Vector3d[size];
        for(int i = 0; i < size; i++){
            rate[i] = new Vector3d(0, 0, 0);
        }
    }


    public void add(int i, Vector3d other){
        if(DEBUG){
            System.out.println("rate - i " + i);
            System.out.println("rate - other " + other.toString());
            System.out.println("rate - before adding " + rate[i].toString());
        }

        rate[i] = (Vector3d) rate[i].add(other);

        if(DEBUG){
            System.out.println("rate - after adding " + rate[i].toString());
        }
    }

    public Vector3d get(int i){
        return rate[i];
    }

    public String toString(){
        String str = "";
        for(int i = 0; i < rate.length; i++){
            str += i + " " + rate[i] + "\n";
        }
        return str;
    }

}
