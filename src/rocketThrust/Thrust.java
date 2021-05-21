package rocketThrust;

import titan.Vector3dInterface;

/**
 * This class computes the formula
 * g(V(K)) = p(f) - p(k)
 * where
 * p(f) = final position that we want to reach (i.e. 300 km) from Titan
 * p(k) = current position of the probe
 *
 * @author chiara
 */
public class Thrust {

    public Thrust() {
    }

    /**
     * @return the vector resulting from p(f) - p(k)
     * Note that we should iterate this method until p(f) - p(k) = 0
     */
    public Vector3dInterface computeFormula(Vector3dInterface pf, Vector3dInterface pk) {
        return pf.sub(pk);
    }

    public Vector3dInterface updatePosition(Vector3dInterface pk) {
        return pk;
    }

    /**
     * @return true if we are close enough to the planet
     */
    public boolean closeEnough(Vector3dInterface p) {
        boolean check = false;

        if (p.getX() < 1 && p.getX() > 0) {
            check = true;

            if (p.getY() < 1 && p.getX() > 0) {
                check = true;

                if (p.getZ() < 1 && p.getZ() > 0) {
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
