package geneticAlgorithm;

import simulator.ODESolver;
import simulator.ProbeSimulator;
import simulator.Vector3d;
import titan.Vector3dInterface;

/**
 * fitness is defined as the euclidean distance of the probe to titan
 *
 * @author Leo
 */

public class Fitness {

    double fitness;

    static Vector3d titanAtEnd;

    static boolean DEBUG = false;

    public Fitness(){
        titanAtEnd = ODESolver.titanPos[ODESolver.titanPos.length-1];
    }

    /**
     * run a simulation here to get fitness of an individual
     * @param individual
     * @return fitness (as distance from titan)
     */
    public double getFitness(Individual individual){

        //initial parameters to start the mission - 20 seems to be the limit for h
        double tf = 31536000; //final time point of the mission ins seconds (31636000s = one year)
        double h = 86400;  //step size with which everything is updated (86400s = 1 day)

        //run a simulation
        ProbeSimulator probeSimulator = new ProbeSimulator();
        Vector3dInterface[] trajectory = probeSimulator.trajectory(individual.posVector, individual.velVector, tf, h);

        //calculate fitness (as distance)
        fitness = trajectory[trajectory.length-1].dist(titanAtEnd);

        if(DEBUG){
            System.out.println("Fitness - start: " + individual.velVector + " fitness: " + fitness);
        }

        return fitness;
    }
}
