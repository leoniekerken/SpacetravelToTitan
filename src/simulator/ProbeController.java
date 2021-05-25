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

    public double m = 78000; //mass
    public double vE = 20000; //exhaust velocity (in m/s)
    public double F = 3e7; //maximum thrust

    public static Vector3d pF = new Vector3d(8.994491235691361E11, -1.246880800663044E12, 5.261491970119961E9); //target position

    public Vector3d p0; //initial position
    public Vector3d v0; //initial velocity
    public  Vector3d pK; //current position
    public Vector3d vK; //current velocity

    public Vector3d g; // pF - pK
    public Vector3d updatedVelocity;

    MultivariableNewton multivariableNewton = new MultivariableNewton();


    /**
     * method to compute initial directed velocity
     **/
    public Vector3d setInitialVelocity(Vector3d pK, Vector3d vK) {

        g = (Vector3d) pF.sub(pK);
        updatedVelocity = (Vector3d) multivariableNewton.doMultivariableNewton(g, vK);

        //call method to compute fuel consumption

        return updatedVelocity;
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
     * method to compute dm/dt = F/vE
     *
     *
     */

    /**
     *
     * integrate all of that
     *
     */
}