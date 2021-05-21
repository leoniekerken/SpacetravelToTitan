package simulator;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;

/**
 * class to simulate trajectory of the probe
 *
 * @author Leo
 */

public class ProbeSimulator implements ProbeSimulatorInterface {

    public static boolean DEBUG = false;

    public State y0;
    public Vector3d[] trajectory;
    public Vector3d[] earthPos;
    public Vector3d[] titanPos;
    public State[] states;

    public int ODESolverChoice;
    public EulerSolver eulerSolver;
    public VerletSolver verletSolver;
    public RungeKuttaSolver rungeKuttaSolver;
    public ODEFunction f = new ODEFunction();

    /**
     * calculate trajectory of a probe
     *
     * @param p0 starting position
     * @param v0 starting velocity
     * @param ts time steps at which position is being updated
     * @return positions of the probe over a given time period
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {

        //starting conditions of the spacecraft
        Planet.planets[11].posVector = p0;
        Planet.planets[11].velVector = v0;

        //initial state of the system
        y0 = new State();
        y0.initializeState();


        if(ODESolverChoice == 1){
            //start solver
            eulerSolver = new EulerSolver();
            states = (State[]) eulerSolver.solve(f, y0, ts);
            //extract information
            earthPos = eulerSolver.earthPos;
            titanPos = eulerSolver.titanPos;
        }
        else if(ODESolverChoice == 2){
            //start solver
            verletSolver = new VerletSolver();
            states = (State[]) verletSolver.solve(f, y0, ts);
            //extract information
            earthPos = verletSolver.earthPos;
            titanPos = verletSolver.titanPos;
        }
        else if(ODESolverChoice == 3){
            //start solver
            rungeKuttaSolver = new RungeKuttaSolver();
            states = (State[]) rungeKuttaSolver.solve(f, y0, ts);
            //extract information
            earthPos = rungeKuttaSolver.earthPos;
            titanPos = rungeKuttaSolver.titanPos;
        }

        trajectory = new Vector3d[ts.length];

        for (int i = 0; i < trajectory.length; i++) {
            trajectory[i] = (Vector3d) states[i].getPos(11);
        }

        if (DEBUG) {
            System.out.println("ProbeSimulator - titanPos at 0 " + titanPos[0]);
        }

        //make sure to set ODESolver and State to null to allow garbage collection and clear memory
        eulerSolver = null;
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
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {

        //starting conditions of the spacecraft
        Planet.planets[11].posVector = p0;
        Planet.planets[11].velVector = v0;

        //initial state of the system
        y0 = new State();
        y0.initializeState();

        if(ODESolverChoice == 1){
            //start solver
            eulerSolver = new EulerSolver();
            states = (State[]) eulerSolver.solve(f, y0, tf, h);
            //extract information
            earthPos = eulerSolver.earthPos;
            titanPos = eulerSolver.titanPos;
        }
        else if(ODESolverChoice == 2){
            //start solver
            VerletSolver verletSolver = new VerletSolver();
            states = (State[]) verletSolver.solve(f, y0, tf, h);
            //extract information
            earthPos = verletSolver.earthPos;
            titanPos = verletSolver.titanPos;
        }
        else if(ODESolverChoice == 3){
            //start solver
            rungeKuttaSolver = new RungeKuttaSolver();
            states = (State[]) rungeKuttaSolver.solve(f, y0, tf, h);
            //extract information
            earthPos = rungeKuttaSolver.earthPos;
            titanPos = rungeKuttaSolver.titanPos;
        }


        trajectory = new Vector3d[(int) Math.round((tf/h) + 1)];

        for(int i = 0; i < trajectory.length; i++){
            trajectory[i] = (Vector3d) states[i].getPos(11);
        }

        //make sure to set ODESolver and State to null to allow garbage collection and clear memory
        eulerSolver = null;
        rungeKuttaSolver = null;
        states = null;
        System.gc();

        if(DEBUG){
            System.out.println("ProbeSimulator - titanPos at 0 " + titanPos[0]);
        }


        return trajectory;
    }
}
