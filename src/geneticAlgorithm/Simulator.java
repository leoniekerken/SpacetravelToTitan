package geneticAlgorithm;

import simulator.*;
import titan.Vector3dInterface;

/**
 * runs the simulation to test different initial conditions of the probe
 *
 * @author Leo
 */
public class Simulator {

    static final boolean DEBUG = true;

    public static final double tf = 31536000;
    public static final double h = 20;

    public ODEFunction f = new ODEFunction();
    public ODESolver solver = new ODESolver();

    public static Vector3d[] titanPos = ODESolver.titanPos;

    public int populationSize;
    public Individual[] individuals;
    public State y0;

    public Vector3dInterface[] trajectory;


    public Simulator(){
    }

    /**
     * initializes a new State with the starting values of the subset of possible values that we want to test
     * runs a simulation with a new probeSimulator
     */
    public void run(Population population){

        this.populationSize = population.populationSize;
        this.individuals = population.individuals;

        for(int i = 0; i < individuals.length; i++){
            ProbeSimulator probeSimulator = new ProbeSimulator();
            trajectory = probeSimulator.trajectory(individuals[i].posVector, individuals[i].velVector, tf, h);
            individuals[i].setFitness(getBestPos(trajectory));
            probeSimulator = null;
            System.gc();
        }

    }

    /**
     * finds the best position (closest to titan) of a probe during one year flight
     * @return double[][] bestPos - bestPosition (euclidean distance to titan) and associated time point
     */
    public double getBestPos(Vector3dInterface[] trajectory) {

        double distance = trajectory[0].dist(titanPos[0]);
        int bestPos = 0;
        for (int i = 0; i < trajectory.length; i++){
            if (trajectory[i].dist(titanPos[i]) < distance) {
                distance = trajectory[i].dist(titanPos[i]);
                bestPos = i;
            }
        }
        return distance;
    }
}
