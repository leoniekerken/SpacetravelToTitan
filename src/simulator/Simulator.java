package simulator;

import titan.Vector3dInterface;
import visualization.StartVisualization;

public class Simulator {

    static boolean PRINT = true;
    static boolean DETAIL = false;
    static boolean VISUALIZATION = true;

    //initial parameters to start the mission - 20 seems to be the limit for h
    static double tf = 31536000; //final time point of the mission ins seconds (31636000s = one year)
    static double h = 30;  //step size with which everything is updated (86400s = 1 day)

    static double initVel = 60000; //initial (undirected) velocity of the probe in m/s

    public static void main(String args[]){

        //initialize positions of all objects in solarSystem
        PlanetStart2020 planetStart2020 = new PlanetStart2020();

        //new probeSimulator
        ProbeSimulator probeSimulator = new ProbeSimulator();

        //calculate take off point of the probe
        TakeOffPoint takeOffPoint = new TakeOffPoint();
        takeOffPoint.calculateTakeOffPoint(initVel, (Vector3d) Planet.planets[8].posVector);

        Vector3dInterface p0 = takeOffPoint.startPos; //initial position here
        Vector3dInterface v0 = takeOffPoint.startVel; //initial velocity here

        //calculate trajectory of the probe
        Vector3dInterface[] trajectory = probeSimulator.trajectory(p0, v0, tf, h);

        //retrieve positions of earth and titan
        Vector3d[] titanPos = probeSimulator.titanPos;

        if(PRINT){

            System.out.println();
            System.out.println();
            System.out.println("START OF MISSION TO TITAN");
            System.out.println();
            System.out.println();
            System.out.println("tf = " + tf);
            System.out.println("step size = " + h);
            System.out.println();
            System.out.println();
            System.out.println("position of earth at start: " + Planet.planets[3].posVector);
            System.out.println("position of titan at start: " + titanPos[0]);
            System.out.println();
            System.out.println();
            System.out.println("probe launched at: \nposition: " + takeOffPoint.startPos.toString() + "\nvelocity: " + initVel + ", " + takeOffPoint.startVel.toString());
            System.out.println("distance probe to earth: " + takeOffPoint.startPos.sub(Planet.planets[3].posVector) + ", euclidean: " + takeOffPoint.startPos.dist(Planet.planets[3].posVector));
            System.out.println("radius earth: " + Planet.planets[3].radius);
            System.out.println();
            System.out.println();
            System.out.println("probe at start: " + trajectory[0].toString());
            System.out.println("titan at start: " + titanPos[0]);
            System.out.println("euclidean distance probe to titan at start: " + trajectory[0].dist(titanPos[0]));
            System.out.println("distance vector probe to titan at start: " + trajectory[0].sub(titanPos[0]));
            System.out.println();
            System.out.println();
            System.out.println("probe at end: " + trajectory[trajectory.length-1].toString());
            System.out.println("titan at end: " + titanPos[titanPos.length-1]);
            System.out.println("euclidean distance probe to titan at end: " + trajectory[trajectory.length-1].dist(titanPos[titanPos.length-1]));
            System.out.println("distance vector probe to titan at end: " + trajectory[trajectory.length-1].sub(titanPos[titanPos.length-1]));
            System.out.println();
            System.out.println();

            if(DETAIL) {
                System.out.println("DETAILED TRAJECTORY");
                System.out.println();
                System.out.println();

                for (int i = 0; i < trajectory.length; i++) {

                    if (i % 1000 == 0) { //print every 1 days
                        System.out.println("probe at " + i + ": " + trajectory[i].toString());
                        System.out.println("titan at " + i + ": " + titanPos[i]);
                        System.out.println("euclidean distance probe to titan: " + trajectory[i].dist(titanPos[i]));
                    }
                }
            }
        }

        if(VISUALIZATION){
            StartVisualization.start();
        }
    }
}
