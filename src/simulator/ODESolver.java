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

    public State[] states;

    public Vector3d[] earthPos;
    public Vector3d[] titanPos;

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
        states = new State[ts.length];
        states[0] = (State) y0;

        //create array storing positions of titan
        titanPos = new Vector3d[ts.length];
        titanPos[0] = (Vector3d) states[0].getPos(8);

        //updating positions for one step
        for(int i = 1; i < states.length; i++){
            states[i] = (State) step(f, ts[i], states[i-1], (ts[i]-ts[i-1]));
            titanPos[i] = (Vector3d) states[i].getPos(8); //add current position of titan to static storage
        }

        return states;
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
        states = new State[(int) (Math.round(tf/h)+1)];
        states[0] = (State) y0;

        if(DEBUG){
            System.out.println("simulator.ODESolver - states " + states.length);
            System.out.println("simulator.ODESolver - state at 0\n" + y0.toString());
        }

        //create array storing positions of titan
        titanPos = new Vector3d[ts.length];
        titanPos[0] = (Vector3d) states[0].getPos(8);

        //updating positions for one step
        for(int i = 1; i < states.length; i++){
            states[i] = (State) step(f, ts[i], states[i-1], (ts[i]-ts[i-1]));
            titanPos[i] = (Vector3d) states[i].getPos(8); //add current position of titan to extra storage
        }

        return states;
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
}
