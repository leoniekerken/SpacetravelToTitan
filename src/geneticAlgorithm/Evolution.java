package geneticAlgorithm;

import simulator.PlanetStart;
import simulator.ProbeSimulator;
import simulator.Vector3d;

/**
 * runs the genetic algorithm through several cycles or
 * until termination condition is reached
 *
 * @author Leo
 */
public class Evolution {

    static final int populationSize = 100;
    static final int tournamentSize = 2;

    static final double tf = 31536000;
    static final double h = 20;

    public static void main(String args[]){

        //initialize positions of all objects in solarSystem
        PlanetStart planetStart = new PlanetStart();

        System.out.println("FIRST SIMULATION");
        System.out.println();
        System.out.println();

        //one simulation run without probe
        ProbeSimulator probeSimulator = new ProbeSimulator();
        probeSimulator.trajectory(new Vector3d(), new Vector3d(), tf, h);
        probeSimulator = null;
        System.gc();

        Population population = new Population(populationSize, true);

        Population generation2 = new Population(populationSize/tournamentSize);

        Reproduction reproduction = new Reproduction();

        reproduction.crossover(reproduction.tournamentSelection(population, tournamentSize), reproduction.tournamentSelection(population, tournamentSize));

        System.out.println("done");

    }
}
