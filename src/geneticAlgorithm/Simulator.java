package geneticAlgorithm;

import simulator.*;
import titan.Vector3dInterface;

/**
 * runs the simulation to test different initial conditions of the probe
 *
 * @author Leo
 */
public class Simulator {

    static final boolean DEBUG = false;

    public double tf = Evolution.tf;
    public double h = Evolution.h;

    public ODEFunction f = new ODEFunction();
    public ODESolver solver = new ODESolver();

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
            getBestPos(trajectory, individuals[i]);
            probeSimulator = null;
            System.gc();
        }

    }

    /**
     * finds the best position (closest to titan) of a probe during one year flight
     * sets: fitness, best position, distance vector of individual
     */
    public void getBestPos(Vector3dInterface[] trajectory, Individual individual) {

        //find best position
        double distance = trajectory[trajectory.length-1].dist(Evolution.titanPos[Evolution.titanPos.length-1]);
        int position = trajectory.length - 1;
        Vector3d distanceVector = (Vector3d) trajectory[trajectory.length-1].sub(Evolution.titanPos[Evolution.titanPos.length-1]);
        for (int i = trajectory.length-1; i >= 0; i--){
            if (trajectory[i].dist(Evolution.titanPos[i]) < distance) {
                distance = trajectory[i].dist(Evolution.titanPos[i]);
                position = i;
                distanceVector = (Vector3d) trajectory[i].sub(Evolution.titanPos[i]);
            }
        }
        individual.setFitness(distance);
        individual.setPosition(position);
        individual.setDistanceVector(distanceVector);
    }
}
