package geneticAlgorithm;

import simulator.Planet;
import simulator.Vector3d;

public class randomPosition {


    final static double c = 1e11;

    /**
     * generates a random position vector relative to earth's position
     * @return random position vector
     */
    public static Vector3d generateRandomPosition(){

        double x = Math.random() * c;
        double y = Math.random() * c;
        double z = Math.random() * 1e9;

        double[] randomPos = {x, y, z};
        double[] earthPosValues = {Planet.planets[3].posVector.getX(),Planet.planets[3].posVector.getY(), Planet.planets[3].posVector.getZ()};

        double[] posVectorValues = new double[3];

        for(int i = 0; i < 3; i++){
            double sign = Math.random();
            if(sign < 0.5){
                posVectorValues[i] = earthPosValues[i] - randomPos[i];
            }
            else{
                posVectorValues[i] = earthPosValues[i] + randomPos[i];
            }
        }

        Vector3d targetPos = new Vector3d(posVectorValues[0], posVectorValues[1], posVectorValues[2]);

        return targetPos;
    }
}
