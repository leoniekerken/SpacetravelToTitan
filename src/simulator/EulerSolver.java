package simulator;

import titan.*;
/**
 * class implementing Euler's method to calculate motion of objects in the solar system
 *
 * @author Leo
 */
public class EulerSolver implements ODESolverInterface {

    static boolean DEBUG = true;
    public static boolean TESTING = ProbeSimulator.TESTING;
    public  static boolean VISUALIZATION = ProbeSimulator.VISUALIZATION;
    static int visualizationTimeStamps = 50;

    public State[] states;

    public Vector3d[] earthPos;
    public Vector3d earthPosAfterOneYear;
    public Vector3d[] titanPos;
    public Vector3d titanPosAfterOneYear;

    public ProbeController probeController = new ProbeController();

    public Vector3d pf = new Vector3d(8.994491235691361E11, -1.246880800663044E12, 5.261491970119961E9);

    public Vector3d g;

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
        ts[0] = 0;
        for(int i = 1; i < ts.length; i++){
            ts[i] = ts[i-1] + h;
            if(i == ts.length-1){
                ts[i] = tf;
            }
        }

        //create array storing states at different timestamps
        states = new State[(int) (Math.round(tf/h)+1)];
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
            if(i == 1){
                states[i].addVel(11, probeController.setVelocity((Vector3d) states[i-1].getPos(11), (Vector3d) states[i-1].getVel(11)));
                if(DEBUG){
                    System.out.println("VEL AFTER UPDATE: " + states[i].getVel(11));
                }
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

        State newState = (State) y.addMul(h, f.call(t, y));

        return newState;
    }
}