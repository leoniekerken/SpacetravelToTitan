package testing;

import simulator.PlanetStart2020;
import simulator.ProbeSimulator;
import simulator.Vector3d;
import titan.Vector3dInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * class to test different solver methods with different step sizes
 */

public class Testing {

    static PlanetStart2020 planetStart2020 = new PlanetStart2020();

    static Vector3dInterface p0 = new Vector3d(0,0,0); //initial position here
    static Vector3dInterface v0 = new Vector3d(0,0,0); //initial velocity here

    static Vector3d titanPosNasa =  new Vector3d(8.789384100968744E+11, -1.204002291074617E+12,-1.435729928774685E+10);
    static Vector3d earthPosNasa = new Vector3d(-1.477129564888797E+11, -2.821064238692064E+10,2.033107664331608E+07);

    static double tf = 31536000; //final time point of the mission ins seconds (31536000s = one year; 315576000s = one julian astronomical year)
    static double[] stepSizes = {86400, 8640, 864, 86, 50, 30, 25, 20}; //step sizes that we want to test
    static double h;

    static ProbeSimulator probeSimulator;

    static FileWriter writer;
    static BufferedWriter bufferedWriter;

    public static void main(String args[]){

        System.out.println();
        System.out.println();
        System.out.println("TESTING");
        System.out.println();
        System.out.println();

        //setting up CSV file
        File outputFile = new File("testing_SolverAccuracy.csv");

        try{
            //setting up bufferedWriter
            writer = new FileWriter(outputFile);
            bufferedWriter = new BufferedWriter(writer);

            //write header
            bufferedWriter.write("solver,stepsize,dist_earthPos,dist_titanPos");
            bufferedWriter.newLine();

            //start simulations
            for(int i = 1; i < 4; i++) {

                for(int j = 0; j < stepSizes.length; j++) {

                    ProbeSimulator.TESTING = true;
                    ProbeSimulator.VISUALIZATION = false;

                    probeSimulator = new ProbeSimulator();

                    probeSimulator.ODESolverChoice = i;

                    h = stepSizes[j];

                    probeSimulator.trajectory(p0, v0, tf, h);

                    double distEarthPos = earthPosNasa.dist(probeSimulator.earthPosAfterOneYear);
                    double distTitanPos = titanPosNasa.dist(probeSimulator.titanPosAfterOneYear);

                    bufferedWriter.write(probeSimulator.solverName+","+h+","+distEarthPos+","+distTitanPos);
                    bufferedWriter.newLine();

                    System.out.println();
                    System.out.println();
                    System.out.println("TEST DONE: " + probeSimulator.solverName + ", " + h);
                    System.out.println();
                    System.out.println();

                    probeSimulator = null;
                    System.gc();
                }
            }

            bufferedWriter.close();

            } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println("DONE");
        System.out.println();
        System.out.println();

    }
}
