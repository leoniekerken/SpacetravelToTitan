package geneticAlgorithm;

/**
 * takes care of the process of reproduction
 * two individuals are selected and then crossed
 *
 * @author Leo
 */

public class Reproduction {

    static boolean DEBUG = false;
    static boolean sexySon = false;
    static boolean RANDOM = false;

    static double randomness = 1 - Evolution.randomness;

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
            System.out.println();
            System.out.println();
            System.out.println("CROSSOVER DEBUG");
            System.out.println();
            System.out.println();
            System.out.println("PARENT 1 targetPos: " + individual1.targetPos);
            System.out.println();
            System.out.println("PARENT 2 targetPos: " + individual2.targetPos);
            System.out.println();
            System.out.println("CHILD targetPos: " + child.targetPos);
            System.out.println();
            System.out.println();
            System.out.println("PARENT 1 unitVector: " + individual1.unitVector);
            System.out.println();
            System.out.println("PARENT 2 unitVector: " + individual2.unitVector);
            System.out.println();
            System.out.println("CHILD unitVector: " + child.unitVector);
            System.out.println();
            System.out.println();
            System.out.println("PARENT 1 initVel: " + individual1.initVel);
            System.out.println();
            System.out.println("PARENT 2 initVel: " + individual2.initVel);
            System.out.println();
            System.out.println("CHILD initVel: " + child.initVel);
            System.out.println();
            System.out.println();
            System.out.println("PARENT 1 velVector: " + individual1.velVector);
            System.out.println();
            System.out.println("PARENT 2 velVector: " + individual2.velVector);
            System.out.println();
            System.out.println("CHILD velVector: " + child.velVector);
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

        //add randomness - not always the fittest wins the tournament -
        //but sometimes also the best dancer in the crowd!
        if(RANDOM && Math.random() > randomness){
            int randomID = (int) Math.round(Math.random() * (population.populationSize-1));
            Individual luckyDancer = population.getIndividual(randomID);
            return luckyDancer;
        }

        Population tournament = new Population(tournamentSize);

        //fill tournament with random individuals
        for(int i = 0; i < tournamentSize; i++){
            int randomID = (int) Math.round(Math.random() * (population.populationSize-1));
            tournament.addIndividual(i, population.getIndividual(randomID));
            if(i == tournamentSize-1 && sexySon){ //make sure sexy son is in there
                tournament.addIndividual(i, population.getIndividual(population.populationSize-1));
            }
        }

        Individual fittest = tournament.getFittest();

        if(DEBUG){
            System.out.println();
            System.out.println();
            System.out.println("TOURNAMENT");
            System.out.println();
            System.out.println();
            System.out.println("CONTESTANTS:");
            for(int i = 0; i < tournament.populationSize; i++){
                System.out.println("individual " + (i+1) + ": " + tournament.individuals[i].velVector + " fitness: " + tournament.individuals[i].getFitness());
            }
            System.out.println();
            System.out.println();
            System.out.println("WINNER: " + fittest.velVector + "; " + fittest.getFitness());
            System.out.println();

        }

        return fittest;
    }
}
