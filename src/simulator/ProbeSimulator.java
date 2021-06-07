package simulator;


//import titan.ProbeSimulatorInterface;
import titan.*;


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

    public State y0;
    public Vector3d[] trajectory;
    public Vector3d[] earthPos;
    public Vector3d earthPosAfterOneYear;
    public Vector3d[] titanPos;
    public Vector3d titanPosAfterOneYear;
    public State[] states;

    public int ODESolverChoice;
    public EulerSolver eulerSolver;
    public VerletSolver verletSolver;
    public RungeKuttaSolver rungeKuttaSolver;
    public String solverName;
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
    public titan.Vector3dInterface[] trajectory(titan.Vector3dInterface p0, titan.Vector3dInterface v0, double[] ts) {

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
            solverName = "Euler";
            earthPos = eulerSolver.earthPos;
            earthPosAfterOneYear = eulerSolver.earthPosAfterOneYear;
            titanPos = eulerSolver.titanPos;
            titanPosAfterOneYear = eulerSolver.titanPosAfterOneYear;
        }
        else if(ODESolverChoice == 2){
            //start solver
            verletSolver = new VerletSolver();
            states = (State[]) verletSolver.solve(f, y0, ts);
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
            states = (State[]) rungeKuttaSolver.solve(f, y0, ts);
            //extract information
            solverName = "RungeKutta";
            earthPos = rungeKuttaSolver.earthPos;
            earthPosAfterOneYear = rungeKuttaSolver.earthPosAfterOneYear;
            titanPos = rungeKuttaSolver.titanPos;
            titanPosAfterOneYear = rungeKuttaSolver.titanPosAfterOneYear;
        }

        trajectory = new Vector3d[ts.length];

        for (int i = 0; i < trajectory.length; i++) {
            trajectory[i] = (Vector3d) states[i].getPos(11);
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
        else if(ODESolverChoice == 3){
            //start solver
            rungeKuttaSolver = new RungeKuttaSolver();
            states = (State[]) rungeKuttaSolver.solve(f, y0, tf, h);
            //extract information
            solverName = "RungeKutta";
            earthPos = rungeKuttaSolver.earthPos;
            earthPosAfterOneYear = rungeKuttaSolver.earthPosAfterOneYear;
            titanPos = rungeKuttaSolver.titanPos;
            titanPosAfterOneYear = rungeKuttaSolver.titanPosAfterOneYear;
        }


        trajectory = new Vector3d[(int) Math.round((tf/h) + 1)];

        for(int i = 0; i < trajectory.length; i++){
            trajectory[i] = (Vector3d) states[i].getPos(11);
        }

        //make sure to set ODESolver and State to null to allow garbage collection and clear memory
        eulerSolver = null;
        verletSolver = null;
        rungeKuttaSolver = null;
        states = null;
        System.gc();


        return trajectory;
    }
}
