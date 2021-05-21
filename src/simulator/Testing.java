package simulator;

import titan.Vector3dInterface;

/**
 * class to test different solver methods with different step sizes
 */

public class Testing {

    public static void main(String args[]){

        System.out.println();
        System.out.println();
        System.out.println("TESTING");
        System.out.println();
        System.out.println();

        //initialize positions of all objects in solarSystem
        PlanetStart2020 planetStart2020 = new PlanetStart2020();

        //take off point of the probe
        Vector3dInterface p0 = new Vector3d(0,0,0); //initial position here
        Vector3dInterface v0 = new Vector3d(0,0,0); //initial velocity here

        ProbeSimulator probeSimulator = new ProbeSimulator();








    }
}
