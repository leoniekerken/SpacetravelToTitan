package simulator;

import simulator.PlanetStart2020;
import simulator.ProbeSimulator;
import simulator.Vector3d;
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

    public ProbeController() {
    }

    /**
     * @return the vector resulting from p(f) - p(k)
     * Note that we should iterate this method until p(f) - p(k) = 0
     */
    public Vector3dInterface computeFormula(Vector3dInterface pf, Vector3dInterface pk) {
        return pf.sub(pk);
    }

    /**
     * Method to be called inside the solvers to change the direction and velocity of the probe
     *
     * @param pK is the current position of the probe
     * @return the position of the probe after changes have been made on it
     */
    public Vector3dInterface updateCurrentPosition(Vector3dInterface pK) {
        return pK;
    }

    /**
     * CURRENTLY SUBTRACT A VALUE FROM EVERY COORDINATE OF THE CURRENT VELOCITY
     * MAKE IT MORE EFFICIENT BY GRADUALLY SLOWING IT DOWN BY CALLING THIS METHOD IN A LOOP (?)
     * @param vK is the current velocity of the probe
     * @return the updated velocity of the probe (slowed down)
     */
    public Vector3dInterface slowDown(Vector3dInterface vK) {
        return new Vector3d(vK.getX() - vK.getX()/2, vK.getY() - vK.getY()/2, vK.getZ() - vK.getZ()/2);
    }

    /**
     * CURRENTLY ADD A VALUE TO EVERY COORDINATE OF THE CURRENT VELOCITY
     * MAKE IT MORE EFFICIENT BY GRADUALLY SLOWING IT DOWN BY CALLING THIS METHOD IN A LOOP (?)
     * @param vK is the current velocity of the probe
     * @return the updated velocity of the probe (faster)
     */
    public Vector3dInterface goFaster(Vector3dInterface vK) {
        return new Vector3d(vK.getX() + vK.getX()/2, vK.getY() + vK.getY()/2, vK.getZ() + vK.getZ()/2);
    }

    /**
     * MAKE IT MORE EFFICIENT! (reduce if-statements if possible!)
     * Currently checks whether every coordinate is closed enough to the desired destination
     * @return true if we are close enough to titan
     */
    public boolean closeEnough(Vector3dInterface p) {
        boolean check = false;

        if (p.getX() < 1e7 && p.getX() > 0) {
            check = true;

            if (p.getY() < 1e7 && p.getX() > 0) {
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
}
