package geneticAlgorithm;

import simulator.*;
import titan.Vector3dInterface;

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
    static final int evolutionCycles = 100;
    static final double mutationRate = 0.3;
    static final double mutationFactor = 1e11;
    static final double randomness = 0.4;

    static final double tf = 31536000;
    static final double h = 30;

    public static Vector3d[] genePool;
    public static Vector3d[] titanPos;

    public static void main(String args[]) {

        if(DEBUG){
            PlanetStart2020 planetStart2020 = new PlanetStart2020();
            for(int i = 0; i < 100; i++){
                Vector3d targetPos = randomPosition.generateRandomPosition();
                Vector3d unitVector = (Vector3d) targetPos.sub(Planet.planets[3].posVector).mul(1/targetPos.dist(Planet.planets[3].posVector));
                Vector3d velVector = (Vector3d) unitVector.mul(60000);
                System.out.println("RANDOM targetPos: " + targetPos + " unitVector: " + unitVector + " velVector: " + velVector);
            }
        }

        if(RUN){

            //set visualization in simulator.ODESolver to false
            ODESolver.VISUALIZATION = false;

            //initialize positions of all objects in solarSystem (in 2019!!)
            PlanetStart2019 planetStart2019 = new PlanetStart2019();

            //new probeSimulator
            ProbeSimulator probeSimulator = new ProbeSimulator();

            //run one initial simulation to get positions of titan in its one year orbit from April 1st, 2019 to April 1st, 2020

            //calculate trajectory (without a probe)
            probeSimulator.trajectory(new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), tf, h);

            //retrieve positions of titan of 2019 as genePool for evolution
            genePool = probeSimulator.titanPos;

            if (PRINT) {
                System.out.println("START");
                System.out.println();
                System.out.println("POSITION OF TITAN AT APRIL 1st 2019 " + genePool[0]);
                System.out.println();
                System.out.println();
                System.out.println("POSITION OF TITAN AT APRIL 1st 2020 " + genePool[genePool.length - 1]);
                System.out.println();
                System.out.println();

            }

            //make sure to set probeSimulator to null to allow gc
            probeSimulator = null;
            System.gc();

            //run one initial simulation with the probe

            //make sure to initialize solar system with values from April 1st 2020
            PlanetStart2020 planetStart2020 = new PlanetStart2020();

            probeSimulator = new ProbeSimulator();

            double initVel = 60000;
            Vector3d titanAtStart = (Vector3d) Planet.planets[8].posVector;

            //initialize probe with starting values
            TakeOffPoint takeOffPoint = new TakeOffPoint();

            takeOffPoint.calculateTakeOffPoint(initVel, titanAtStart);
            Vector3dInterface p0 = takeOffPoint.startPos; //initial position here
            Vector3dInterface v0 = takeOffPoint.startVel; //initial velocity here

            //calculate trajectory of the probe
            Vector3dInterface[] trajectory = probeSimulator.trajectory(p0, v0, tf, h);

            //retrieve positions of titan
            titanPos = probeSimulator.titanPos;


            if (PRINT) {
                System.out.println();
                System.out.println();
                System.out.println("FIRST SIMULATION");
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
                System.out.println();
                System.out.println();
                System.out.println("probe at start: " + trajectory[0].toString());
                System.out.println("titan at start: " + titanPos[0]);
                System.out.println("euclidean distance probe to titan at start: " + trajectory[0].dist(titanPos[0]));
                System.out.println("distance vector probe to titan at start: " + trajectory[0].sub(titanPos[0]));
                System.out.println();
                System.out.println();
                System.out.println("probe at end: " + trajectory[trajectory.length - 1].toString());
                System.out.println("titan at end: " + titanPos[titanPos.length - 1]);
                System.out.println("euclidean distance probe to titan at end: " + trajectory[trajectory.length - 1].dist(titanPos[titanPos.length - 1]));
                System.out.println("distance vector probe to titan at end: " + trajectory[trajectory.length - 1].sub(titanPos[titanPos.length - 1]));
                System.out.println();
                System.out.println();
                System.out.println();
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
                    System.out.println("FITTEST PARENT POPULATION " + population.getFittest().velVector + "; initVel: " + population.getFittest().initVel + ", fitness: " + population.getFittest().getFitness() + ", distance: " + population.getFittest().distanceVector + ", position: " + population.getFittest().position);
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
