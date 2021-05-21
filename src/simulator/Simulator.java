package simulator;

import titan.Vector3dInterface;
import visualization.StartVisualization;

public class Simulator {

    static boolean PRINT = true;
    static boolean DETAIL = false;
    static boolean VISUALIZATION = false;


    //initial parameters to start the mission - 20 seems to be the limit for h
    static double tf = 31536000; //final time point of the mission ins seconds (31636000s = one year)
    static double h = 50;  //step size with which everything is updated (86400s = 1 day)

    static double initVel = 60000; //initial (undirected) velocity of the probe in m/s

    public static void main(String args[]){

        //initialize positions of all objects in solarSystem
        PlanetStart2020 planetStart2020 = new PlanetStart2020();

        //new probeSimulator
        ProbeSimulator probeSimulator = new ProbeSimulator();

        //take off point of the probe
        Vector3dInterface p0 = new Vector3d(-1.471868229554755E11, -2.8606557057938354E10, 8287486.0632270835); //initial position here
        Vector3dInterface v0 = new Vector3d(30503.316321875955, -62503.59520115846, -621.7444409637209); //initial velocity here

        //set solver choice: 1 = EulerSolver; 2 = RungeKuttaSolver; 3 = Verlet
        probeSimulator.ODESolverChoice = 2;

        //calculate trajectory of the probe
        Vector3dInterface[] trajectory = probeSimulator.trajectory(p0, v0, tf, h);

        //retrieve positions of earth and titan
        Vector3d[] titanPos = probeSimulator.titanPos;

        //find best position
        double distance = trajectory[trajectory.length-1].dist(titanPos[titanPos.length-1]);
        int position = trajectory.length - 1;
        Vector3d distanceVector = (Vector3d) trajectory[trajectory.length-1].sub(titanPos[titanPos.length-1]);
        Vector3d probeAtBest = (Vector3d) trajectory[trajectory.length-1];
        Vector3d titanAtBest = titanPos[titanPos.length-1];
        for (int i = trajectory.length-1; i >= 0; i--){
            if (trajectory[i].dist(titanPos[i]) < distance) {
                distance = trajectory[i].dist(titanPos[i]);
                position = i;
                distanceVector = (Vector3d) trajectory[i].sub(titanPos[i]);
                probeAtBest = (Vector3d) trajectory[i];
                titanAtBest = titanPos[i];
            }
        }

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
            System.out.println("probe launched at: \nposition: " + p0 + "\nvelocity: " + initVel + ", " + v0);
            System.out.println("distance probe to earth: " + p0.sub(Planet.planets[3].posVector) + ", euclidean: " + p0.dist(Planet.planets[3].posVector));
            System.out.println("radius earth: " + Planet.planets[3].radius);
            System.out.println();
            System.out.println();
            System.out.println("probe at start: " + trajectory[0].toString());
            System.out.println("titan at start: " + titanPos[0]);
            System.out.println("euclidean distance probe to titan at start: " + trajectory[0].dist(titanPos[0]));
            System.out.println("distance vector probe to titan at start: " + trajectory[0].sub(titanPos[0]));
            System.out.println();
            System.out.println();
            System.out.println("probe at best: " + probeAtBest);
            System.out.println("titan at best: " + titanAtBest);
            System.out.println("euclidean distance probe to titan at best: " + distance);
            System.out.println("distance vector probe to titan at best: " + distanceVector);
            System.out.println("at position: " + position);
            System.out.println();
            System.out.println();
            System.out.println("probe at end: " + trajectory[trajectory.length-1].toString());
            System.out.println("titan at end: " + titanPos[titanPos.length-1]);
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