package NewtonRaphson;

import simulator.PlanetStart2020;
import simulator.ProbeSimulator;
import simulator.TakeOffPoint;
import simulator.Vector3d;
import titan.Vector3dInterface;

/**
 * This class is a test of the Multivariable Newton method
 */
public class Main {

    //initial parameters to start the mission - 20 seems to be the limit for h
    public static double tf = 31136000; //final time point of the mission ins seconds (31536000s = one year)
    public static double h = 86400 / 10;  //step size with which everything is updated (86400s = 1 day)

    static double initVel = 60000; //initial (undirected) velocity of the probe in m/s

    public static void main(String[] args) {

        //initialize positions of all objects in solarSystem
        PlanetStart2020 planetStart2020 = new PlanetStart2020();

        //new probeSimulator
        ProbeSimulator probeSimulator = new ProbeSimulator();

        TakeOffPoint takeOffPoint = new TakeOffPoint();

        //take off point of the probe
        titan.Vector3dInterface p0 = new Vector3d(-1.471868229554755E11, -2.8606557057938354E10, 8287486.0632270835); //initial position here
        titan.Vector3dInterface v0 = new Vector3d(0,0,0); //initial velocity here

        titan.Vector3dInterface pf = new Vector3d(6.332873118527889e+11,-1.357175556995868e+12,-2.134637041453660e+09); //initial velocity here

        takeOffPoint.calculateTakeOffPoint(initVel, takeOffPoint.titanAtEnd);

        //set solver choice: 1 = EulerSolver; 2 = VerletSolver; 3 = RungeKuttaSolver
        probeSimulator.ODESolverChoice = 3;

        //calculate trajectory of the probe
        titan.Vector3dInterface[] trajectory = probeSimulator.trajectory(p0, v0, tf, h);

        //retrieve positions of earth and titan
        Vector3d[] titanPos = probeSimulator.titanPos;

        MultivariableNewton newton = new MultivariableNewton();

        //Initialize an empty matrix
        double[][] jacobian = newton.jacobianMatrix;

        //Print empty matrix
        newton.print(jacobian);

        //Fill matrix with the initial velocity
        newton.fillMatrix(v0);

        //Print matrix after first fill in
        newton.print(jacobian);

        Vector3dInterface currentVelocity = v0;
        Vector3dInterface g = newton.computeDistance(p0, pf);
        //First update
        Vector3dInterface updatedVelocity = newton.doMultivariableNewton(g, currentVelocity);
        currentVelocity = updatedVelocity;

        System.out.println();
        System.out.println("MISSION TO TITAN");
        System.out.println();
        System.out.println("INITIAL DATA: ");
        System.out.println();
        System.out.println("Initial velocity: " + v0.toString());
        System.out.println("Initial position: " + p0.toString());
        System.out.println("Final position: "  + pf.toString());
        System.out.println("Initial value of g: " + g.toString());
        System.out.println();
        System.out.println("First update of the velocity: " + updatedVelocity.toString());
        System.out.println();

        //For every position we compute and print the updated velocity and the new matrix
        for (int i = 1; i < trajectory.length; i++) {
            g = newton.computeDistance(trajectory[i], pf);
            System.out.println("Updated value of g " + g.toString());
            updatedVelocity = newton.doMultivariableNewton(g, currentVelocity);
            System.out.println("Updated velocity: " + updatedVelocity.toString());
            System.out.println();
            currentVelocity = updatedVelocity;
            newton.fillMatrix(currentVelocity);
            newton.print(jacobian);
            System.out.println();
            System.out.println();
        }
    }
}
