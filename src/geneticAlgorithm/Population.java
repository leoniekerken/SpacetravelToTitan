package geneticAlgorithm;

/**
 * creates a population of individuals
 *
 * @author Leo
 */
public class Population {

    Individual[] individuals;
    int populationSize;

    static final boolean DEBUG = true;

    public Population(int populationSize, boolean init){

        this.populationSize = populationSize;
        this.individuals = new Individual[populationSize];

        for(int i = 0; i < populationSize; i++){
            Individual individual = new Individual();
            if(i == populationSize-1){
                individuals[i] = individual.generateSexySon();
            }
            individuals[i] = individual.generateIndividual();
        }
    }

    public Population(int populationSize){
        this.individuals = new Individual[populationSize];
        this.populationSize = populationSize;
    }

    public void addIndividual(int i, Individual individual){
        individuals[i] = individual;
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

}
