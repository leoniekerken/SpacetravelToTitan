package simulator;


//import titan.ProbeSimulatorInterface;
import titan.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;


/**
 * class to simulate trajectory of the probe
 *
 *
 * @author Leo
 */

public class ProbeSimulator implements ProbeSimulatorInterface {

    public static boolean DEBUG = false;
    public static boolean TESTING;
    public static boolean VISUALIZATION;
    static int visualizationTimeStamps = 100; // time step size for the visualization every 50th timestep we add to vis
    public boolean reachTitan = false; // marking if we reached the clossest poition yet
    public boolean reachEarth = false; // did we get back towards earth

    public State y0;
    public Vector3d[] trajectory;
    public Vector3d[] earthPos;
    public Vector3d earthPosAfterOneYear;
    public Vector3d[] titanPos;
    public Vector3d titanPosAfterOneYear;
    public LinkedList<State> states;
//    public State[] states;

    public int ODESolverChoice;
    public EulerSolver eulerSolver;
    public VerletSolver verletSolver;
    public RungeKuttaSolver rungeKuttaSolver;
    public String solverName;
    public ODEFunction f = new ODEFunction();
    public ProbeController probeController;

    /**
     * calculate trajectory of a probe
     *
     * @param p0 starting position
     * @param v0 starting velocity
     * @param ts time steps at which position is being updated
     * @return positions of the probe over a given time period
     */
    @Override
    public titan.Vector3dInterface[] trajectory(titan.Vector3dInterface p0, titan.Vector3dInterface v0, double[] ts) {
        probeController = new ProbeController();
        //starting conditions of the spacecraft
        Planet.planets[11].posVector = p0;
        Planet.planets[11].velVector = v0;

        //initial state of the system
        y0 = new State();
        y0.initializeState();

        if(ODESolverChoice == 1){
            //start solver
            eulerSolver = new EulerSolver();
            //states =
            // (State[]) eulerSolver.solve(f, y0, ts);
            //extract information
            solverName = "Euler";
            earthPos = eulerSolver.earthPos;
            earthPosAfterOneYear = eulerSolver.earthPosAfterOneYear;
            titanPos = eulerSolver.titanPos;
            titanPosAfterOneYear = eulerSolver.titanPosAfterOneYear;
        }
        else if(ODESolverChoice == 2){
            //start solver
            verletSolver = new VerletSolver();
            //states = (State[]) verletSolver.solve(f, y0, ts);
            //extract information
            solverName = "Verlet";
            earthPos = verletSolver.earthPos;
            earthPosAfterOneYear = verletSolver.earthPosAfterOneYear;
            titanPos = verletSolver.titanPos;
            titanPosAfterOneYear = verletSolver.titanPosAfterOneYear;
        }
        else if(ODESolverChoice == 3){
            //start solver
            rungeKuttaSolver = new RungeKuttaSolver();
            //states = (State[]) rungeKuttaSolver.solve(f, y0, ts);
            //extract information
            solverName = "RungeKutta";
            earthPos = rungeKuttaSolver.earthPos;
            earthPosAfterOneYear = rungeKuttaSolver.earthPosAfterOneYear;
            titanPos = rungeKuttaSolver.titanPos;
            titanPosAfterOneYear = rungeKuttaSolver.titanPosAfterOneYear;
        }

        trajectory = new Vector3d[ts.length];

        for (int i = 0; i < trajectory.length; i++) {
            trajectory[i] = (Vector3d) states.get(i).getPos(11);
//            trajectory[i] = (Vector3d) states[i].getPos(11);
        }

        //make sure to set ODESolver and State to null to allow garbage collection and clear memory
        eulerSolver = null;
        verletSolver = null;
        rungeKuttaSolver = null;
        states = null;
        System.gc();

        return trajectory;
    }

    /**
     * calculate trajectory of a probe
     *
     * @param p0 starting position
     * @param v0 starting velocity
     * @param tf final time point
     * @param h step size
     * @return positions of the probe over a given time period
     */
    @Override
    public titan.Vector3dInterface[] trajectory(titan.Vector3dInterface p0, titan.Vector3dInterface v0, double tf, double h) {
        //double itterator = 0;
        probeController = new ProbeController();
        //starting conditions of the spacecraft
        Planet.planets[11].posVector = p0;
        Planet.planets[11].velVector = v0;

        //initial state of the system
        y0 = new State();
        y0.initializeState();
//        states = new State[(int) Math.floor(tf/h) + 1];
//        states[0] = y0;
        states = new LinkedList<>();
        states.add(y0);
        
        if(ODESolverChoice == 1) {
            eulerSolver = new EulerSolver();
        }
        else if(ODESolverChoice == 2) {
            verletSolver= new VerletSolver();
        }
        else  {
            rungeKuttaSolver = new RungeKuttaSolver();
        }


         /*if(ODESolverChoice == 1){
            //start solver
            eulerSolver = new EulerSolver();
            states = (State[]) eulerSolver.solve(f, y0, tf, h);
            //extract information
            if(DEBUG){
                System.out.println("probeSimulator DEBUG");
                System.out.println();
            }
            solverName = "Euler";
            earthPos = eulerSolver.earthPos;
            earthPosAfterOneYear = eulerSolver.earthPosAfterOneYear;
            titanPos = eulerSolver.titanPos;
            titanPosAfterOneYear = eulerSolver.titanPosAfterOneYear;
        }
        else if(ODESolverChoice == 2){
            //start solver
            verletSolver = new VerletSolver();
            states = (State[]) verletSolver.solve(f, y0, tf, h);
            //extract information
            solverName = "Verlet";
            earthPos = verletSolver.earthPos;
            earthPosAfterOneYear = verletSolver.earthPosAfterOneYear;
            titanPos = verletSolver.titanPos;
            titanPosAfterOneYear = verletSolver.titanPosAfterOneYear;
        }
        else





        if(ODESolverChoice == 3){
            //start solver
            rungeKuttaSolver = new RungeKuttaSolver();
            //states.addAll(Arrays.asList((State[])rungeKuttaSolver.solve(f, y0, tf, h)));
            states = (State[]) rungeKuttaSolver.solve(f, y0, tf, h);
            //extract information
            solverName = "RungeKutta";
            earthPos = rungeKuttaSolver.earthPos;
            earthPosAfterOneYear = rungeKuttaSolver.earthPosAfterOneYear;
            titanPos = rungeKuttaSolver.titanPos;
            titanPosAfterOneYear = rungeKuttaSolver.titanPosAfterOneYear;
        }*/


        rungeKuttaSolver = new RungeKuttaSolver();

        //while (!reachTitan && !reachEarth && itterator < tf) {

        for (int itterator = 0; itterator < tf; itterator += h) {
            if( itterator <= 1 && states.getLast().getVel(11) != probeController.vL) {
                Vector3d newAcceleration = probeController.accelerate((Vector3d) (states.getLast().getVel(11)), probeController.vL, h);
//                System.out.println("Accelerate first: " + newAcceleration.mul(h).toString());
//                System.out.println("Accelerate Earth: " + states.getLast().getVel(3).toString());
                State stateLast = states.getLast();
                stateLast.addVel(11, newAcceleration.mul(h));
                states.removeLast();
                states.add(stateLast);
                if (DEBUG) {
                    System.out.println("SOLVER: velocity updated: " + states.getLast().getVel(11));
                    System.out.println();
                }
            }
            if (VISUALIZATION && (itterator % visualizationTimeStamps) == 0) {
                for (int j = 0; j < Planet.planets.length; j++) {
                    Planet.planets[j].addOrbit(states.get((int) (itterator/h)).getPos(j));
                }
            }

            states.add(step(states, itterator, h, f));

            if (!reachTitan && probeController.closeEnough(itterator, states.getLast().getPos(11), states.getLast().getPos(8))) {
                 states.getLast().addVel(11,probeController.reverseThrust(itterator, states.getLast(), h));
                 reachTitan = true;
            }

            if (!reachEarth && reachTitan && probeController.closeToEarth(itterator, states.getLast().getPos(11), states.getLast().getPos(3))) {
                reachEarth = true;
                finalizeTrajectory (states);
                eulerSolver = null;
                verletSolver = null;
                rungeKuttaSolver = null;
                states = null;
                System.gc();
//                System.out.println("itterator HERE:   " + itterator + "   ___-----------------------------------");
                return trajectory;

            }

            //itterator += h;
        }

        // safty if we hit the time limit.
        finalizeTrajectory(states);

        //make sure to set ODESolver and State to null to allow garbage collection and clear memory
        eulerSolver = null;
        verletSolver = null;
        rungeKuttaSolver = null;
        states = null;
        System.gc();

        //System.out.println(itterator);
        return trajectory;
    }


    private State step (LinkedList<State> states, double itterator, double h, ODEFunction f)
    {
        if (itterator > (3*h))
        {
            State stateThis;
            if(ODESolverChoice == 1) {
                stateThis = (State) eulerSolver.step(f, itterator, states.getLast(),h);
            }
            else if(ODESolverChoice == 2) {
                stateThis = (State) verletSolver.step(f, itterator, states.getLast(),h);
            }
            else  {
                stateThis = (State) rungeKuttaSolver.step(f, itterator, states.getLast(),h);
            }

            AdamMoulton adamMoulton = new AdamMoulton();
            return adamMoulton.corrector(f, states, itterator, h, stateThis);
        }
        else
        {
            if(ODESolverChoice == 1) {
                return (State) eulerSolver.step(f, itterator, states.getLast(),h);
            }
            else if(ODESolverChoice == 2) {
                return (State) verletSolver.step(f, itterator, states.getLast(),h);
            }
            else {
                return (State) rungeKuttaSolver.step(f, itterator, states.getLast(),h);
            }

        }
    }

    private void finalizeTrajectory (LinkedList<State> states)
    {
        trajectory = new Vector3d[states.size()];
        titanPos = new Vector3d[states.size()];
        earthPos = new Vector3d[states.size()];

        for(int i = 0; i < trajectory.length; i++) {
            if (states.get(i) != null) {
                trajectory[i] = (Vector3d) states.get(i).getPos(11);
                titanPos[i] = (Vector3d) states.get(i).getPos(8);
                earthPos[i] = (Vector3d) states.get(i).getPos(3);
            }
        }
    }

    private void finalizeTrajectory (State[] states)
    {
        trajectory = new Vector3d[states.length];
        titanPos = new Vector3d[states.length];
        earthPos = new Vector3d[states.length];

        for(int i = 0; i < trajectory.length; i++) {
            if (states[i] != null) {
                trajectory[i] = (Vector3d) states[i].getPos(11);
                titanPos[i] = (Vector3d) states[i].getPos(8);
                earthPos[i] = (Vector3d) states[i].getPos(3);
            }
        }
    }
}
