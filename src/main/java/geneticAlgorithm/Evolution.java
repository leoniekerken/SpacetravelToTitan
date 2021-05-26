package geneticAlgorithm;

import simulator.*;

/**
 * runs the genetic algorithm through several cycles or
 * until termination condition is reached
 *
 * @author Leo
 */
public class Evolution {

    static final boolean PRINT = true;
    static final boolean RUN = true;
    static final boolean DEBUG = false;
    static final boolean GENEPOOL = false;

    /**
     * play around with those parameters and look what happens
     * @param populationSize - size of a population of individuals, init with random values, then for every generation
     *                       the whole population will be replaced by new individuals
     * @param tournamentSize - size of a tournament, in a tournament, n individuals are randomly selected and then the
     *                       fittest wins and is selected for crossover
     * @param evolutionCycles - for how many generations we want to run the evolution
     * @param mutationRate - the rate at which mutations - random changes to the values occur
     * @param mutationFactor - here we can determine the magnitude of a mutation, how large or subtle the mutation should be
     * @param randomness - sometimes we want not the fittest individual of a tournament to win and pass on the genes,
     *                   but rather want to pick a random individual to increase diversity in our population - diversity
     *                   is most important!
     */

    static final int populationSize = 90;
    static final int tournamentSize = 3;
    static final int evolutionCycles = 1000;
    static final double mutationRate = 0.3;
    static final double mutationFactor = 1e11;
    static final double randomness = 0.4;

    static final double tf = 31536000;
    static final double h = 86400 / 10;

    static int ODESolverChoice = 3;

    public static Vector3d[] genePool;
    public static Vector3d[] titanPos;

    public static void main(String args[]) {

        if(DEBUG){
            PlanetStart2020 planetStart2020 = new PlanetStart2020();
            for(int i = 0; i < 100; i++){
                Vector3d targetPos = RandomPosition.generateRandomPosition();
                Vector3d unitVector = (Vector3d) targetPos.sub(Planet.planets[3].posVector).mul(1/targetPos.dist(Planet.planets[3].posVector));
                Vector3d velVector = (Vector3d) unitVector.mul(60000);
                System.out.println("RANDOM targetPos: " + targetPos + " unitVector: " + unitVector + " velVector: " + velVector);
            }
        }

        if(GENEPOOL){
            GenePool.genePool();
        }

        if(RUN){
            if (PRINT) {
                System.out.println();
                System.out.println();
                System.out.println("RUNNING INITIAL SIMULATION");
                System.out.println();
                System.out.println();
            }

            //initialize positions of the planets
            PlanetStart2020 planetStart2020 = new PlanetStart2020();

            ProbeSimulator probeSimulator = new ProbeSimulator();

            probeSimulator.ODESolverChoice = ODESolverChoice;

            probeSimulator.trajectory(new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), tf, h);

            titanPos = probeSimulator.titanPos;

            if (PRINT) {
                System.out.println();
                System.out.println();
                System.out.println("EVOLUTION STARTS HERE");
                System.out.println();
                System.out.println();
            }

            //initialize new population
            Population population = new Population(populationSize, true);

            //initialize simulator
            Simulator simulator = new Simulator();

            //initialize reproduction class
            Reproduction reproduction = new Reproduction();

            //next generation
            Population nextGeneration = new Population(populationSize);

            //parents
            Individual individual1 = new Individual();
            Individual individual2 = new Individual();

            //child
            Individual child = new Individual();

            for (int i = 0; i < evolutionCycles; i++) {
                if (PRINT) {
                    System.out.println("------------------------------------------------------------------------------------");
                    System.out.println();
                    System.out.println("RUNNING CYCLE " + i);
                    System.out.println();
                    System.out.println("------------------------------------------------------------------------------------");
                }
                simulator.run(population);
                if (PRINT) {
                    System.out.println("POPULATION: " + i);
                    System.out.println();
                    population.print();
                    System.out.println();
                    System.out.println("FITTEST PARENT POPULATION " + population.getFittest().velVector + "; position: " + population.getFittest().posVector + "; initVel: " + population.getFittest().initVel + ", fitness: " + population.getFittest().getFitness() + ", distance: " + population.getFittest().distanceVector + ", position: " + population.getFittest().position);
                }
                for (int j = 0; j < nextGeneration.populationSize; j++) {

                    individual1 = reproduction.tournamentSelection(population, tournamentSize);
                    individual2 = reproduction.tournamentSelection(population, tournamentSize);

                    //recombination from two same individuals is not allowed
                    while(individual1.targetPos == individual2.targetPos){
                        individual2 = reproduction.tournamentSelection(population, tournamentSize);
                    }

                    child = reproduction.crossover(individual1, individual2);
                    nextGeneration.addIndividual(j, child);

                }
                //next generation takes over
                population.takeOver(nextGeneration);
                //make space for new generation
                nextGeneration.resetEmpty();
            }

            simulator.run(population);
            if (PRINT) {
                System.out.println("------------------------------------------------------------------------------------");
                System.out.println();
                System.out.println("FITTEST AFTER " + evolutionCycles + " CYCLES: " + population.getFittest().velVector + ", fitness: " + population.getFittest().getFitness() + ", distance: " + population.getFittest().distanceVector + ", position: " + population.getFittest().position);
                System.out.println();
                System.out.println("------------------------------------------------------------------------------------");
                System.out.println();
                System.out.println("END");
            }
        }
    }
}
