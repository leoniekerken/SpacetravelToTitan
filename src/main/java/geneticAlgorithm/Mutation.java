package geneticAlgorithm;

import simulator.Vector3d;

/**
 * introducing small random changes to the parameters
 *
 * @author Leo
 */
public class Mutation {

    static double mutationFactor = Evolution.mutationFactor;
    static boolean subtleMutation = true;

    static boolean DEBUG = false;

    public static void Mutation(){

    }

    /**
     * generate random position vector
     * @param subtleMutation - true if we want the mutation only to occur either in x,y,z direction, false if we want
     *                       it to occur in all directions simultaneously
     * @return mutation vector
     */
    public static Vector3d mutate(){
        Vector3d mutation = new Vector3d();

        double randomX = 0;
        double randomY = 0;
        double randomZ = 0;

        if(subtleMutation) {

            if(DEBUG){
                System.out.println("MUTATION DEBUG1");
            }

            double random = Math.random();

            if (random <= 0.3) {
                if (Math.random() < 0.5) {
                    randomX = Math.random() * (-mutationFactor);
                } else {
                    randomX = Math.random() * mutationFactor;
                }
            } else if (random > 0.3 && random <= 0.6) {
                if (Math.random() < 0.5) {
                    randomY = Math.random() * (-mutationFactor);
                } else {
                    randomY = Math.random() * mutationFactor;
                }
            } else if (random > 0.6) {
                if (Math.random() < 0.5) {
                    randomZ = Math.random() * (-mutationFactor / 10);
                } else {
                    randomZ = Math.random() * mutationFactor / 10;
                }
            }
        }

        else{


            if(DEBUG){
                System.out.println("MUTATION DEBUG2");
            }

            if (Math.random() < 0.5) {
                randomX = Math.random() * (-mutationFactor);
            } else {
                randomX = Math.random() * mutationFactor;
            }
            if (Math.random() < 0.5) {
                randomY = Math.random() * (-mutationFactor);
            } else {
                randomY = Math.random() * mutationFactor;
            }
            if (Math.random() < 0.5) {
                randomZ = Math.random() * (-mutationFactor / 10);
            } else {
                randomZ = Math.random() * mutationFactor / 10;
            }
        }

        mutation.setX(randomX);
        mutation.setY(randomY);
        mutation.setZ(randomZ);

        return mutation;
    }
}
