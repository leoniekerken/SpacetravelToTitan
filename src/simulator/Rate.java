package simulator;

import titan.RateInterface;

/**
 * data structure to store the rate of change for all objects in the system
 *
 * [i][0] = dx = velocity of object i
 * [i][1] = dv = acceleration of object i
 *
 * @author Leo
 */
public class Rate implements RateInterface {

    public static boolean DEBUG = false;
    static int n = Planet.planets.length;

    //storing acc values for all objects
    public Vector3d[][] rate;

    public Rate() {
    }

    /**
     * initializes empty rate array with size
     * @param size
     */
    public void initialize(int size) {
        rate = new Vector3d[size][size];
        for(int i = 0; i < size; i++){
            rate[i][0] = new Vector3d(0, 0, 0);
            rate[i][1] = new Vector3d(0, 0, 0);
        }
    }

    /**
     * adds to array storing values for dx
     * @param i
     * @param other
     */
    public void addVelocity(int i, Vector3d other){
        rate[i][0] = (Vector3d) rate[i][0].add(other);
    }

    /**
     * adds to array storing values for dv
     * @param i
     * @param other
     */
    public void addAcceleration(int i, Vector3d other){
        rate[i][1] = (Vector3d) rate[i][1].add(other);
    }

    /**
     * adds two rates
     * @param other
     * @return sum
     */
    public Rate add(Rate other) {
        Rate newRate = new Rate();
        newRate.initialize(n);

        for(int i = 0; i < rate.length; i++){
            newRate.rate[i][0] = (Vector3d) this.rate[i][0].add(other.getVelocity(i));
            newRate.rate[i][1] = (Vector3d) this.rate[i][1].add(other.getAcceleration(i));
        }
        return newRate;
    }

    /**
     * multiplies two rates
     * @param h
     * @return product
     */
    public Rate mul(double h){

        Rate newRate = new Rate();
        newRate.initialize(n);

        for(int i = 0; i < rate.length; i++){
            newRate.rate[i][0] = (Vector3d) this.rate[i][0].mul(h);
            newRate.rate[i][1] = (Vector3d) this.rate[i][1].mul(h);
        }
        return newRate;
    }

    public Vector3d getVelocity(int i){
        return rate[i][0];
    }

    public Vector3d getAcceleration(int i){
        return rate[i][1];
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < rate.length; i++){
            str += i + " " + rate[i] + "\n";
        }
        return str;
    }
}