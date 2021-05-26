package simulator;

import NewtonRaphson.MultivariableNewton;

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
    public double massFlowRate = -(F/vE);


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
     * method to compute adjustments (late phase)
     * dv = s/d * v
     *
     * s still needs to be determined!
     */
    public Vector3d setAdjustments(Vector3d pK, Vector3d vK){

        //updatedVelocity = s/d * vK

        return updatedVelocity;
    }

    /**
     * method to compute acceleration
     *
     * a = F/m - both F and m are given
     *
     */




    /**
     *
     * integrate all of that
     *
     */

    /**
     * CURRENTLY SUBTRACT A VALUE FROM EVERY COORDINATE OF THE CURRENT VELOCITY
     * MAKE IT MORE EFFICIENT BY GRADUALLY SLOWING IT DOWN BY CALLING THIS METHOD IN A LOOP (?)
     * @param vK is the current velocity of the probe
     * @return the updated velocity of the probe (slowed down)
     */
//    public Vector3d slowDown(Vector3d vK) {
//        return new Vector3d(vK.getX() - vK.getX()/2, vK.getY() - vK.getY()/2, vK.getZ() - vK.getZ()/2);
//    }
//
//    /**
//     * CURRENTLY ADD A VALUE TO EVERY COORDINATE OF THE CURRENT VELOCITY
//     * MAKE IT MORE EFFICIENT BY GRADUALLY SLOWING IT DOWN BY CALLING THIS METHOD IN A LOOP (?)
//     * @param vK is the current velocity of the probe
//     * @return the updated velocity of the probe (faster)
//     */
//    public Vector3d goFaster(Vector3d vK) {
//        return new Vector3d(vK.getX() + vK.getX()/2, vK.getY() + vK.getY()/2, vK.getZ() + vK.getZ()/2);
//    }

    /**
     * MAKE IT MORE EFFICIENT! (reduce if-statements if possible!)
     * Currently checks whether every coordinate is closed enough to the desired destination
     * @return true if we are close enough to titan
     */
    public boolean closeEnough(Vector3d p) {
        boolean check = false;

        if (p.getX() < 1e7 && p.getX() > 0) {
            check = true;

            if (p.getY() < 1e7 && p.getY() > 0) {
                check = true;

                if (p.getZ() < 1e7 && p.getZ() > 0) {
                    check = true;

                } else {
                    check = false;
                }

            } else {
                check = false;
            }

        } else {
            check = false;
        }
        return check;
    }


    public double getTotalMass(){
        return massFuel+DRY_MASS;
    }

    public void setfuelMassLoss(double massFlowrate, double time, double h){
        for( int i=0; i<time; i+=h){
            System.out.println(massFlowrate*i);
        }
    }
}