package geneticAlgorithm;

/**
 * creates a population of individuals
 *
 * @author Leo
 */
public class Population {

    Individual[] individuals;
    int populationSize;

    static final boolean DEBUG = false;
    static final boolean sexySon = false;

    /**
     * initialize a population with random individuals
     * @param populationSize
     * @param init
     */
    public Population(int populationSize, boolean init){

        this.populationSize = populationSize;
        this.individuals = new Individual[populationSize];

        for(int i = 0; i < populationSize; i++){
            Individual individual = new Individual();
            individuals[i] = individual.generateIndividual();
            if(sexySon && i == populationSize-1){
                individuals[i] = individual.generateSexySon();
            }
        }
    }

    /**
     * creates new empty population
     * @param populationSize
     */
    public Population(int populationSize){
        this.individuals = new Individual[populationSize];
        this.populationSize = populationSize;
    }

    /**
     * adding individual to population at position i
     * @param i
     * @param individual
     */
    public void addIndividual(int i, Individual individual){
        individuals[i] = individual;
    }

    /**
     * overwrite individuals of a population to next generation
     * @param nextGeneration
     */
    public void takeOver(Population nextGeneration){
        for(int i = 0; i < populationSize; i++){
            this.individuals[i] = nextGeneration.individuals[i];
        }
    }

    /**
     * resets population
     */
    public void resetEmpty(){
        for(int i = 0; i < populationSize; i++){
            this.individuals[i] = null;
        }
    }

    public Individual getFittest(){
        Individual fittest = individuals[0];
        for(int i = 0; i < populationSize; i++){
            if(individuals[i].getFitness() < fittest.getFitness()){
                fittest = individuals[i];
            }
        }
        return fittest;
    }

    public Individual getIndividual(int i){
        return individuals[i];
    }

    public void print(){
        for(int i = 0; i < populationSize; i++){
            System.out.println("INDIVIDUAL: " + individuals[i].velVector + ", initVel: " + individuals[i].initVel + ", fitness " + individuals[i].fitness + ", distance: " + individuals[i].distanceVector + ", position: " + individuals[i].position);
        }
    }

}
