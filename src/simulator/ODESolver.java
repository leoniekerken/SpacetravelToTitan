package simulator;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

/**
 * class implementing Euler's method to calculate motion of objects in the solar system
 *
 * @author Leo
 */
public class ODESolver implements ODESolverInterface {

    public static boolean DEBUG = false;

    public State[] planetStates;
    public State[] probeStates;

    public static Vector3d[] titanPos;

    /**
     * Euler solver
     *
     * @param f simulator.ODEFunction implementing Newton's law
     * @param y0 initial state of the system
     * @param ts time steps at which position is being updated
     * @return state of the system at different time points
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {

        //create array storing states at different timestamps
        planetStates = new State[ts.length];
        planetStates[0] = (State) y0;

        //create array storing positions of titan
        titanPos = new Vector3d[ts.length];
        titanPos[0] = (Vector3d) planetStates[0].getPos(8);

        //updating positions for one step
        for(int i = 1; i < planetStates.length; i++){
            planetStates[i] = (State) step(f, ts[i], planetStates[i-1], (ts[i]-ts[i-1]));
            titanPos[i] = (Vector3d) planetStates[i].getPos(8); //add current position of titan to extra storage
        }

        return planetStates;
    }

    /**
     * Euler solver
     *
     * @param f simulator.ODEFunction implementing Newton's law
     * @param y0 initial state of the system
     * @param tf final time point
     * @param h step size
     * @return states of the planets over a given time period
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {

        //get array storing separate timestamps
        double[] ts = new double[(int) (Math.round((tf/h)+1))];
        ts[0] = 0;
        for(int i = 1; i < ts.length; i++){
            ts[i] = ts[i-1] + h;
        }

        //create array storing states at different timestamps
        planetStates = new State[(int) (Math.round(tf/h)+1)];
        planetStates[0] = (State) y0;

        if(DEBUG){
            System.out.println("simulator.ODESolver - states " + planetStates.length);
            System.out.println("simulator.ODESolver - state at 0\n" + y0.toString());
        }

        //create array storing positions of titan
        titanPos = new Vector3d[ts.length];
        titanPos[0] = (Vector3d) planetStates[0].getPos(8);

        //updating positions for one step
        for(int i = 1; i < planetStates.length; i++){
            planetStates[i] = (State) step(f, ts[i], planetStates[i-1], (ts[i]-ts[i-1]));
            titanPos[i] = (Vector3d) planetStates[i].getPos(8); //add current position of titan to extra storage
        }

        return planetStates;
    }

    /**
     * this method does the same, except we are now only updating positions of the probes for testing
     * states of the planets are already calculated and can be retrieved
     * @param f simulator.ODEFunction implementing Newton's law
     * @param y0 initial state of the system
     * @param tf final time point
     * @param h step size
     * @param testing
     *
     * @return States of the probes for testing
     */
    public StateInterface[] solve(ODEFunction f, StateInterface y0, double tf, double h, boolean testing){

        //get array storing separate timestamps
        double[] ts = new double[(int) (Math.round((tf/h)+1))];
        ts[0] = 0;
        for(int i = 1; i < ts.length; i++){
            ts[i] = ts[i-1] + h;
        }

        //create array storing states at different timestamps
        probeStates = new State[(int) (Math.round(tf/h)+1)];
        probeStates[0] = (State) y0;

        //updating positions for one step
        for(int i = 1; i < probeStates.length; i++){
            probeStates[i] = (State) step(f, ts[i], probeStates[i-1], planetStates[i-1], (ts[i]-ts[i-1]), testing);
        }

        if(DEBUG){
            for(int i = 0; i < (probeStates.length); i++){
                if(i % 10 == 0){ //print every day
                    System.out.println("simulator.ODESolver - state at " + (i) + "\n" + probeStates[i].toString());
                }
            }
        }

        return probeStates;

    }

    /**
     * calculating one step
     *
     * @param f simulator.ODEFunction implementing Newton's law
     * @param t time
     * @param y state
     * @param h step size
     * @return new state of the system after one update step
     */
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

        State newState = (State) y.addMul(h, f.call(h, y));

        return newState;
    }

    /**
     * does the same except for that we are retrieving positions from the already calculated states of all planets
     * @param f simulator.ODEFunction implementing Newton's law
     * @param t time
     * @param y state
     * @param planetState state of the planets at that time
     * @param h step size
     * @param testing
     * @return new state of the system after update
     */
    public StateInterface step(ODEFunction f, double t, State y, State planetState, double h, boolean testing) {

        State newState = (State) y.addMul(h, f.call(h, y, planetState, testing));

        return newState;
    }
}
