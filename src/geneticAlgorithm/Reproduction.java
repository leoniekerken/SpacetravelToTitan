package geneticAlgorithm;

import simulator.Vector3d;

import java.util.Arrays;

/**
 * takes care of the process of reproduction
 * two individuals are selected and then crossed
 *
 * @author Leo
 */

public class Reproduction {

    static boolean DEBUG = true;

    public Simulator simulator;

    public Reproduction(){
        simulator = new Simulator();
    }

    /**
     * performing crossover between
     * @param individual1
     * @param individual2
     * @return offspring of both parents
     */
    public Individual crossover(Individual individual1, Individual individual2){

        Individual child = individual1.getChild(individual2);

        if(DEBUG){
            System.out.println("Reproduction - crossover: parent1 " + individual1.velVector + individual1.getFitness());
            System.out.println("Reproduction - crossover: parent2 " + individual2.velVector + individual2.getFitness());
            System.out.println("Reproduction - crossover: child " + child.velVector + child.getFitness());
            System.out.println();
        }
        return child;
    }

    /**
     * tournament means a subset of the whole population which participates in the selection process
     * @param population the whole population
     * @param tournamentSize size of the tournament
     * @return fittest individual from the tournament
     */
    public Individual tournamentSelection(Population population, int tournamentSize){

        Population tournament = new Population(tournamentSize);
        //fill tournament with random individuals
        for(int i = 0; i < tournamentSize; i++){
            int randomID = (int) Math.round(Math.random() * (population.populationSize-1));
            if(i == tournamentSize-1){ //make sure sexy son is in there
                tournament.addIndividual(i, population.getIndividual(population.populationSize-1));
            }
            tournament.addIndividual(i, population.getIndividual(randomID));
        }

        //run a simulation and get fittest individual
        simulator.run(tournament);
        Individual fittest = tournament.getFittest();

        if(DEBUG){
            System.out.println("Reproduction - tournament");
            System.out.println();
            System.out.println("fittest " + fittest.velVector + fittest.getFitness());
            System.out.println();
        }
        return fittest;
    }
}
