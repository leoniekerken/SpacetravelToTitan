package simulator;

import NewtonRaphson.MultivariableNewton;

import titan.Vector3dInterface;

/**
 * CLASS TO CONTROL THE TRAJECTORY OF THE PROBE
 * (change direction, velocity)
 *
 * This class computes the formula
 * g(V(K)) = p(f) - p(k)
 * where
 * p(f) = final position that we want to reach (i.e. 300 km) from Titan
 * p(k) = current position of the probe
 *
 * @author chiara
 */
public class ProbeController {

    static boolean DEBUG = true;
    public double massFuel  = 2e6; //mass of the fuel this gives us upto 200sec of thrust
    public static final double DRY_MASS= 78000; //dry mass of space rocket without fuel
    public double vE = 20000; //exhaust velocity (in m/s)
    public double F = 3e7; //maximum thrust

    public double massFlowRate = -(F/vE); // decay amount
    public double kickOut = 31536000; // when to end if possible
    public double kickEarth = kickOut * 2; // when to end in worst case
    public double distProbeEarth = 1e18;// max return jorney -> decreases to the acual value of 8e8
    public double distProbeTitan = 1e18; // dinstance of the probe to titan in eucledian distance form

    public static Vector3d pF = new Vector3d(8.994491235691361E11, -1.246880800663044E12, 5.261491970119961E9); //target position

    public Vector3d p0; //initial position
    public Vector3d v0; //initial velocity
    public Vector3d vL = new Vector3d(41583.09221214755, -56163.23800237314, -540.5163786866699); //launch velocity - velocity we want to approximate after launching
    public Vector3d pK; //current position
    public Vector3d vK; //current velocity

    public Vector3d g; // pF - pK
    public Vector3d updatedVelocity;
    public Vector3d diffVelocity;
    public Vector3d acceleration;

    MultivariableNewton multivariableNewton = new MultivariableNewton();


    /**
     * method to compute initial directed velocity
     **/
    public Vector3d setVelocity(Vector3d pK, Vector3d vK) {

        g = (Vector3d) pF.sub(pK);
        updatedVelocity = (Vector3d) multivariableNewton.doMultivariableNewton(g, vK);

        //call method to compute fuel consumption

        return updatedVelocity;
    }

    /**
     * method to set launch velocity based on initial conditions from GA
     *
     * approximate it in steps given properties of the rocket engine
     *
     */
    public Vector3d setVelocityLaunch(){

        //return a fraction of vL that is possible to achieve in one time step
        return vL;

    }

    /**
     * fire the rocket to accelerate in a certain direction
     *
     * return acceleration
     *
     */
    public Vector3d accelerate(Vector3d vK, Vector3d vF, double h){

        diffVelocity = (Vector3d) vF.sub(vK);
        massFuel -= 5000 * h;
        acceleration = (Vector3d) diffVelocity.mul(1/h);

        //maximal acceleration a = F/m

        double maxAcceleration = F/DRY_MASS;

        if(acceleration.norm() <= maxAcceleration){

            return acceleration;
        }

        else{
            System.out.println("EXIT ACC TOO HIGH");
        }

        if(DEBUG){
            System.out.println();
            System.out.println();
            System.out.println("PROBECONTROLLER DEBUG");
            System.out.println();
            System.out.println();
            System.out.println("oldVelocity: " + vK);
            System.out.println();
            System.out.println();
            System.out.println("vF: " + vF);
            System.out.println();
            System.out.println();
            System.out.println("diffVelocity: " + diffVelocity);
            System.out.println();
            System.out.println();
            System.out.println("acceleration: " + acceleration);
            System.out.println();
            System.out.println();
        }

        return null;
    }



    /**
     * Currently checks whether every coordinate is closed enough to the desired destination
     * @param pProbe = current position of the probe
     * @param pTitan = current position of the t
     * @return true if we are the closest to titan
     */
    public boolean closeEnough(double i, Vector3dInterface pProbe, Vector3dInterface pTitan) {

        // 31536000
        // If the probe is close enough
        if (i < kickOut && distProbeTitan > pProbe.dist(pTitan))
        {
            distProbeTitan = pProbe.dist(pTitan);
            return false;
        }
        kickOut = i;
        return true;
    }

    /**

     * @param i = current state
     * @param pProbe = current position of the probe
     * @param pEarth = current position of the earth
     * Checks whether the distance between earth and the probe is decreasing
     */
    public boolean closeToEarth(double i, Vector3dInterface pProbe, Vector3dInterface pEarth) {
        if (i > kickOut && distProbeEarth > pProbe.dist(pEarth))
        {
            kickEarth = i;
            distProbeEarth = pProbe.dist(pEarth);
            return false;
        }

        System.out.println(distProbeEarth);
        return true;
    }

    /**
     * reverse the acceleration that we use previously to get back towards earth and Reverts the direction of the spaceprobe
     * @param i
     * @param state current state
     * @param h = step size
    */
    public Vector3dInterface reverseThrust(double i,State state, double h) {
        // Using this we stop at 8e8 from earth.
        // kick out initiated.
        kickOut = i;
        Vector3d returnVector = new Vector3d (-vL.getX(), -vL.getY(), -vL.getZ());
        Vector3d backToEarth = accelerate((Vector3d)(state.getVel(11)), returnVector, h);
        // states[i-1].addVel(11, backToEarth.mul(h/2));
        if (DEBUG) {
            //System.out.println("IMPORTANT_______________________________________________________________________________________________________________");
            System.out.println("SOLVER: velocity updated: " + state.getVel(11));
            System.out.println();
        }
        return backToEarth;
     }


    /** fuel decay function
     * @param massFlowrate
     * @param time interval
     * @param h stepsize
     * @return rate of loss of mass of fuel
     */
    public void setfuelMassLoss(double massFlowrate, double time, double h){
        for( int i=0; i<time; i+=h){
            System.out.println(massFlowrate*i);
        }
    }
}