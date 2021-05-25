package simulator;

import titan.ODEFunctionInterface;
import titan.ODESolverInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

public class VerletSolver implements ODESolverInterface {

    private final PlanetStart2019 p = new PlanetStart2019();
    private final double grav = 6.674E-11;
    StateInterface[] states;                    //Array of all the states to be returned
    Vector3dInterface[] positions;
    Vector3dInterface[] prevPositions;
    Vector3dInterface[] velocities;
    Vector3dInterface[] acceleration;
    Vector3d[] earthPos;
    Vector3d[] titanPos;
    int index = 0;
    double[] masses;

    /**
     * CONSTRUCTOR
     * Initialize all the arrays
     */
    public VerletSolver() {
        positions = new Vector3d[p.planets.length];
        prevPositions = new Vector3d[p.planets.length];
        earthPos = new Vector3d[100000];
        titanPos = new Vector3d[100000];
        masses = new double[p.planets.length];
        velocities = new Vector3d[p.planets.length];

        //Initialize both positions and previousPositions arrays with the initial positions of all planets
        for (int i = 0; i < p.planets.length; i++) {
            positions[i] = p.planets[i].posVector;
            prevPositions[i] = p.planets[i].posVector;
        }

        earthPos[index] = (Vector3d) positions[4];
        titanPos[index] = (Vector3d) positions[8];
        index++;

        //Initialize array with initial masses of all planets
        for (int i = 0; i < p.planets.length; i++) {
            masses[i] = p.planets[i].mass;
        }

        //Initialize the velocity array with the initial velocity of all planets
        for (int i = 0; i < p.planets.length; i++) {
            velocities[i] = p.planets[i].velVector;
        }

        //Initialize acceleration vector
        acceleration = new Vector3dInterface[positions.length];

        //Update accelerations
        updateAcceleration();
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        states  = new StateInterface[ts.length];
        states[0] = y0;

        nextPosVelocityVerlet(ts[1]);                                                  //First update of the positions with Velocity Verlet
        states[1] = step(f, ts[1], y0, ts.length/ts[ts.length-1]);                  //This is the first state to be added after the Velocity Verlet

        for (int i = 2; i < states.length; i++) {
            nextPosPositionVerlet(ts[i]);
            states[i] = step(f, i * ts[i], states[i-1], ts.length/ts[ts.length-1]);
        }
        return this.states;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        states  = new StateInterface[(int) (Math.round(tf/h)+1)];
        states[0] = y0; //here I add the initial state

        nextPosVelocityVerlet(h);                          //First update of the positions with Velocity Verlet
        states[1] = step(f, tf/h, y0, h);               //This is the first state to be added after the Velocity Verlet

        int ts = (int) (tf/h);

        for (int i = 2; i < ts; i++) {
            nextPosPositionVerlet(h);
            states[i] = step(f, i * h, states[i-1], h);
        }
        return states;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        State newState = new State();
            for (int i = 0; i < positions.length; i++) {
                newState.addPos(i, this.positions[i]);
                newState.addVel(i, this.velocities[i]);
            }
        return newState;
    }

    /**
     *  SIMPLE POSITION VERLET
     *
     *  1. The current positions are stored in the ArrayList storing the previous positions
     *  2. The current positions are updated
     *  3. The acceleration is updated
     */
    public void nextPosPositionVerlet(double h) {
        for (int i = 0; i < positions.length; i++) {

            Vector3dInterface pos = positions[i].mul(2);
            Vector3dInterface prevPos = prevPositions[i].mul(-1);
            Vector3dInterface acc = acceleration[i].mul(h * h);

            Vector3dInterface[] vs = new Vector3d[3];
            vs[0] = pos;
            vs[1] = prevPos;
            vs[2] = acc;

            prevPositions[i] = positions[i];             //ArrayList of previous positions becomes equal to the current positions
            positions[i] = VectorOperations.sumAll(vs);                  //ArrayList of current positions is updated with updated positions
        }
        earthPos[index] = (Vector3d) positions[4];
        titanPos[index] = (Vector3d) positions[8];
        index++;
        updateAcceleration();
    }

    /**
     *  VELOCITY VERLET
     *
     *  1. The current positions are stored in the ArrayList storing the previous positions
     *  2. The current positions are updated
     *  3. The acceleration is updated
     *
     */
    public void nextPosVelocityVerlet(double h) {

        //Update position
        for (int i = 0; i < positions.length; i++) {

            Vector3dInterface pos = positions[i];
            Vector3dInterface vel = velocities[i].mul(h);
            Vector3dInterface acc = acceleration[i].mul(h * h * (0.5));

            Vector3dInterface[] vs = new Vector3d[3];
            vs[0] = (pos);
            vs[1] = (vel);
            vs[2] = (acc);

            positions[i] = VectorOperations.sumAll(vs);
        }
        earthPos[index] = (Vector3d) positions[4];
        titanPos[index] = (Vector3d) positions[8];
        index++;
        updateAcceleration();
    }

    /**
     * The acceleration is updated
     */
    public void updateAcceleration() {

        for (int i = 0; i < positions.length; i++) {

            Vector3dInterface body1 = positions[i];
            double mass1 = masses[i];                       //Copy array of masses

            Vector3dInterface[] forces = new Vector3d[positions.length];

            for (int j = 0; j < positions.length; j++) {
                if (j != i) {
                    Vector3dInterface body2 = positions[j];
                    double mass2 = masses[j];
                    forces[j] = gravitationalForce(mass1, mass2, body1, body2);
                }
            }
            acceleration[i] = VectorOperations.sumAll(forces).mul(1/mass1);
        }
    }

    public Vector3d[] getEarthPos() { return earthPos; }

    public Vector3d[] getTitanPos() { return titanPos; }

    /**
     * Evaluate the change in the direction of the objects due to the forces that act on them
     *
     * Forces taken into account:
     * - force that one object performs on the other (evaluated by the directionVector(p1, p2) method)
     * - gravitational force
     *
     * @param m1 is the mass of the first object
     * @param m2 is the mass of the second object
     * @param v1 is the direction represented by the vector with the position of the first object
     * @param v2 is the direction represented by the vector with the positions of the second object
     * @return positions after all the forces influenced it
     */
    private Vector3dInterface gravitationalForce(double m1, double m2, Vector3dInterface v1, Vector3dInterface v2) {
        double distance = v2.dist(v1);
        Vector3dInterface forceDirection = VectorOperations.approximateDirection(v1, v2);       //Direction of force resulting from the two vectors
        double force = grav * m1 * m2 / Math.pow(distance, 2);                                  //Actual force resulting from the two vectors
        return forceDirection.mul(force);
    }
}
