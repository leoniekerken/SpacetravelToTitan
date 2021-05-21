package rocketThrust;

import simulator.ProbeSimulator;
import simulator.Vector3d;
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

    Vector3dInterface v0 = new Vector3d(30503.316321875955, -62503.59520115846, -621.7444409637209);                   // Initial velocity of the probe
    Vector3dInterface p0 = new Vector3d(-1.471868229554755E11, -2.8606557057938354E10, 8287486.0632270835);            // Initial position of the probe
    double tf = 31536000;   //final time point of the mission ins seconds (31636000s = one year)
    double h = 50;          //step size with which everything is updated (86400s = 1 day)
    int updates;            //for now only used to keep track of the position of the probe

    public Thrust() {
        updates = 0;
    }

    /**
     * @return the vector resulting from p(f) - p(k)
     * Note that we should iterate this method until p(f) - p(k) = 0
     */
    public Vector3dInterface computeFormula(Vector3dInterface pf, Vector3dInterface pk) {
        return pf.sub(pk);
    }

    /**
     * Updated the next position based on the trajectory computed by the Solvers
     *
     * @return updated position of the probe
     */
    public Vector3dInterface updateCurrentPosition() {
        //new probeSimulator
        ProbeSimulator probeSimulator = new ProbeSimulator();

        //set solver choice: 1 = EulerSolver; 2 = VerletSolver; 3 = RungeKuttaSolver
        probeSimulator.ODESolverChoice = 2;

        //calculate trajectory of the probe
        Vector3dInterface[] trajectory = probeSimulator.trajectory(p0, v0, tf, h);

        return trajectory[this.updates++];
    }

    /**
     * @return true if we are close enough to titan
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
