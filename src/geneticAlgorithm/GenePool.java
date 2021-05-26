package geneticAlgorithm;

import simulator.ProbeSimulator;
import simulator.Vector3d;

/**
 * selects certain positions based on titan's orbit as gene pool for evolution
 */

public class GenePool {

    public static Vector3d[] genePool;

    public static void genePool(){

        //new probeSimulator
        ProbeSimulator probeSimulator = new ProbeSimulator();

        //run one initial simulation to get positions of titan in its one year orbit from April 1st, 2019 to April 1st, 2020

        //calculate trajectory (without a probe)
        probeSimulator.trajectory(new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), Evolution.tf, Evolution.h);

        //retrieve positions of titan of 2019 as genePool for evolution
        genePool = probeSimulator.titanPos;

        //make sure to set probeSimulator to null to allow gc
        probeSimulator = null;
        System.gc();
    }
}
