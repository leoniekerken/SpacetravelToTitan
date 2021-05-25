package simulator;

import NewtonRaphson.MultivariableNewton;
import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

/**
 * class implementing Euler's method to calculate motion of objects in the solar system
 *
 * @author Leo
 */
public class EulerSolver implements ODESolverInterface {

    static boolean DEBUG = false;
    public  static boolean VISUALIZATION = Simulator.VISUALIZATION;
    static int visualizationTimeStamps = 50;

    Vector3dInterface p0 = new Vector3d(-1.471868229554755E11, -2.8606557057938354E10, 8287486.0632270835);         //initial position here
    Vector3dInterface v0 = new Vector3d(30503.316321875955, -62503.59520115846, -621.7444409637209);                //initial velocity
    Vector3dInterface pf = new Vector3d(6.332873118527889e+11,  -1.357175556995868e+12,  2.134637041453660e+09);    //Final position to be reached (300 km from titan = titan's orbit)

    public State[] states;

    public Vector3d[] earthPos;
    public Vector3d[] titanPos;

    public ProbeController controller;
    public MultivariableNewton newton;
    public Vector3d[] positions;        //All the positions of the probe
    public Vector3d[] velocities;       //All the velocities of the probe
    public Vector3d[] gValues;          //All the values of g

    /**
     * CONSTRUCTOR
     */
    public EulerSolver() {
        controller = new ProbeController();
        newton = new MultivariableNewton();
    }

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

        //Initialize arrays
        positions = new Vector3d[ts.length];
        velocities = new Vector3d[ts.length];
        gValues = new Vector3d[ts.length];

        //Set first element of the arrays
        positions[0] = (Vector3d) p0;                                              //Current position of the probe (Initialize it with the starting position of the probe)
        gValues[0] = (Vector3d) controller.computeFormula(pf, positions[0]);       //pf - pk (Should be as close to a zero vector as possible
        velocities[0] = (Vector3d) newton.doMultivariableNewton(gValues[0], v0);   //First evaluation of the updated velocity with the initial velocity of the probe

        //updating positions for one step
        for(int i = 1; i < states.length; i++){
            states[i] = (State) step(f, ts[i], states[i-1], (ts[i]-ts[i-1]));
            titanPos[i] = (Vector3d) states[i].getPos(8); //add current position of titan to static storage
            updatedProbePosition(states[i].getPos(11), i);  //Make sure that 11 is the position of the probe!!!!!!
            //add current position of object to short version of orbit (for visualization)
            if(VISUALIZATION && i % visualizationTimeStamps == 0)
            for(int j = 0; j < Planet.planets.length; j++){
                Planet.planets[j].addOrbit(states[i].getPos(j));
            }
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

        //Initialize arrays
        positions = new Vector3d[ts.length];
        velocities = new Vector3d[ts.length];
        gValues = new Vector3d[ts.length];

        //Set first element of the arrays
        positions[0] = (Vector3d) p0;                                              //Current position of the probe (Initialize it with the starting position of the probe)
        gValues[0] = (Vector3d) controller.computeFormula(pf, positions[0]);       //pf - pk (Should be as close to a zero vector as possible
        velocities[0] = (Vector3d) newton.doMultivariableNewton(gValues[0], v0);   //First evaluation of the updated velocity with the initial velocity of the probe

        //updating positions for one step
        for(int i = 1; i < states.length; i++){
            states[i] = (State) step(f, ts[i], states[i-1], (ts[i]-ts[i-1]));
            if(DEBUG){
                System.out.println("STATE AT " + i + ": " + states[i]);
            }
            titanPos[i] = (Vector3d) states[i].getPos(8);   //add current position of titan to extra storage
            updatedProbePosition(states[i].getPos(11), i);  //Make sure that 11 is the position of the probe!!!!!!
            //add current position of object to short version of orbit (for visualization)
            if(VISUALIZATION && i % visualizationTimeStamps == 0)
                for(int j = 0; j < Planet.planets.length; j++){
                    Planet.planets[j].addOrbit(states[i].getPos(j));
                }
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
        State newState = (State) y.addMul(h, f.call(t, y));
        return newState;
    }

    /**
     * PROBE CONTROLLER
     * Fill in the arrays of positions, velocities and values of g
     *
     * @param updatedPos is the updated position of the probe that is evaluated by the solver
     * @param i is the index where to add the computed value
     */
    public void updatedProbePosition(Vector3dInterface updatedPos, int i) {
        positions[i] = (Vector3d) controller.updateCurrentPosition(updatedPos);
        gValues[i] = (Vector3d) controller.computeFormula(pf, positions[i]);
        velocities[i] = (Vector3d) newton.doMultivariableNewton(gValues[i], velocities[i-1]);

        //While we are close to Titan keep updating data
        //while (controller.closeEnough(gValues[i]) == true) {
    }
}
