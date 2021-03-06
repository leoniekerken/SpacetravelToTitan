package simulator;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

/**
 * Verlet solver to solve equations of motion
 *
 * @author Chiara, Leo
 */

public class VerletSolver implements ODESolverInterface {

    static boolean DEBUG = false;
    public static boolean TESTING = ProbeSimulator.TESTING;
    public  static boolean VISUALIZATION = ProbeSimulator.VISUALIZATION;
    static int visualizationTimeStamps = 50;

    public State[] states;
    public Vector3d[] earthPos;
    public Vector3d earthPosAfterOneYear;
    public Vector3d[] titanPos;
    public Vector3d titanPosAfterOneYear;

    /**
     * solve ODE
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

        if(!TESTING){
            //create array storing positions of titan
            titanPos = new Vector3d[ts.length];
            titanPos[0] = (Vector3d) states[0].getPos(8);
            //create array storing positions of earth
            earthPos = new Vector3d[ts.length];
            earthPos[0] = (Vector3d) states[0].getPos(3);
        }

        //updating positions for one step
        for(int i = 1; i < states.length; i++){
            states[i] = (State) step(f, ts[i], states[i-1], (ts[i]-ts[i-1]));
            if(!TESTING){
                titanPos[i] = (Vector3d) states[i].getPos(8); //add current position of titan to static storage
                earthPos[i] = (Vector3d) states[i].getPos(3); //add current position of earth to extra storage
            }
            //add current position of object to short version of orbit (for visualization)
            if(VISUALIZATION && i % visualizationTimeStamps == 0)
                for(int j = 0; j < Planet.planets.length; j++){
                    Planet.planets[j].addOrbit(states[i].getPos(j));
                }
        }
        earthPosAfterOneYear = (Vector3d) states[states.length - 1].getPos(3);
        titanPosAfterOneYear = (Vector3d) states[states.length - 1].getPos(8);

        return states;
    }

    /**
     * solve ODE
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
        if(DEBUG){
            System.out.println("ts array: " + ts.length);
        }
        ts[0] = 0;
        for(int i = 1; i < ts.length; i++){
            ts[i] = ts[i-1] + h;
            if(i == ts.length-1){
                ts[i] = tf;
                if(DEBUG){
                    System.out.println();
                    System.out.println();
                    System.out.println("ts[last] = " + ts[i] + " at " + i);
                    System.out.println();
                    System.out.println();
                }
            }
        }

        //create array storing states at different timestamps
        states = new State[(int) (Math.round(tf/h)+1)];
        states[0] = (State) y0;

        if(DEBUG){
            System.out.println("simulator.ODESolver - states " + states.length);
            System.out.println("simulator.ODESolver - state at 0\n" + y0.toString());
        }

        if(!TESTING){
            //create array storing positions of titan
            titanPos = new Vector3d[ts.length];
            titanPos[0] = (Vector3d) states[0].getPos(8);
            //create array storing positions of earth
            earthPos = new Vector3d[ts.length];
            earthPos[0] = (Vector3d) states[0].getPos(3);
        }

        //updating positions for one step
        for(int i = 1; i < states.length; i++){
            states[i] = (State) step(f, ts[i], states[i-1], (ts[i]-ts[i-1]));
            if(DEBUG && (i == 1 || i == states.length - 1)){
                System.out.println("SOLVER DEBUG - STEPSIZE: " + (ts[i]-ts[i-1]));
                System.out.println("SOLVER DEBUG - TIME: " + ts[i] + " at " + i);
            }
            if(!TESTING){
                titanPos[i] = (Vector3d) states[i].getPos(8); //add current position of titan to static storage
                earthPos[i] = (Vector3d) states[i].getPos(3); //add current position of earth to extra storage
            }
            //add current position of object to short version of orbit (for visualization)
            if(VISUALIZATION && i % visualizationTimeStamps == 0)
                for(int j = 0; j < Planet.planets.length; j++){
                    Planet.planets[j].addOrbit(states[i].getPos(j));
                }
        }
        earthPosAfterOneYear = (Vector3d) states[states.length - 1].getPos(3);
        titanPosAfterOneYear = (Vector3d) states[states.length - 1].getPos(8);

        return states;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

        if(DEBUG){
            System.out.println();
            System.out.println();
            System.out.println("VERLET SOLVER DEBUG");
            System.out.println();
            System.out.println();
            System.out.println("y: " + y);
            System.out.println();
            System.out.println();
        }

        //half-step velocity: v(t+h) = v(t) + 1/2 a(t) * h
        State halfStepVel = ((State) y).updateVelocity((0.5*h), f.call(t, y));

        if(DEBUG){
            System.out.println("a: " + f.call(t, y));
            System.out.println();
            System.out.println();
            System.out.println("halfStepVel: " + halfStepVel);
            System.out.println();
            System.out.println();
        }

        //update position: x(t+h) = x(t) + halfStepVel
        State updatePos = ((State) y).updatePosition(h, f.call(t + 0.5 * h, halfStepVel));

        if(DEBUG){
            System.out.println("updatePos: " + updatePos);
            System.out.println();
            System.out.println();
        }

        //update halfStepVel: v(t+h) = halfStepVel + 1/2 a(t+h) * h
        State updateVel = halfStepVel.updateVelocity((0.5*h), f.call(t + h, updatePos));

        if(DEBUG){
            System.out.println("updateVel: " + updateVel);
            System.out.println();
            System.out.println();
        }

        //putting it together: newState with updatePos and updateVel
        State newState = new State();
        newState.addAllPos(updatePos);
        newState.addAllVel(updateVel);

        if(DEBUG){
            System.out.println("newState: " + newState);
            System.out.println();
            System.out.println();
        }

        return newState;
    }
}
