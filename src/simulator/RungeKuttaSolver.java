package simulator;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;

/**
 * 4th order Runge-Kutta solver (RK4) to solve equations of motion
 *
 * @author Aditi, Sebas, Leo
 */

public class RungeKuttaSolver implements ODESolverInterface {

    static boolean DEBUG = true;
    public static boolean TESTING = ProbeSimulator.TESTING;
    public  static boolean VISUALIZATION = ProbeSimulator.VISUALIZATION;
    static int visualizationTimeStamps = 50;

    public ProbeController probeController = new ProbeController();

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
            if(i < 10 && states[i].getVel(11) != probeController.vL){
                Vector3d newVelocity = probeController.accelerate((Vector3d) states[i].getVel(11), probeController.vL, h);
                states[i].addVel(11, newVelocity);
                if(DEBUG){
                    System.out.println("SOLVER: velocity updated: " + newVelocity);
                    System.out.println();
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

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

        Rate k1, k2, k3, k4;

        //k1 = h * f(t, y)
        k1 = ((Rate) f.call(t, y)).mul(h);
        //k2 = h + f(t+0.5*h, y+0.5*k1)
        k2 = ((Rate) f.call(t + 0.5 * h, y.addMul(0.5, k1))).mul(h);
        //k3 = h + f(t+0.5*h, y+0.5*k2)
        k3 = ((Rate) f.call(t + 0.5 * h, y.addMul(0.5, k2))).mul(h);
        //k4 = h + f(t*h, y+k3)
        k4 = ((Rate) f.call(t * h, y.addMul(1, k3))).mul(h);

        //newRate = k1 + 2 * k2 + 2 * k3 + k4
        Rate newRate = k1.add(k2.mul(2)).add(k3.mul(2)).add(k4);

        //newState = y + 1/6 * newRate
        State newState = (State) y.addMul(1.0/6.0, newRate);

        return newState;
    }
}