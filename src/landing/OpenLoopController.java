package landing;

/**
 * OPEN LOOP CONTROLLER
 *
 * @author Aditi
 */

public class OpenLoopController {

    public final double g_Titan = 1.352; // acceleration due to gravity on Titan

    // tolerance values for each parameter
    public final double ToleranceX = 0.1;
    public final double ToleranceTheta = 0.02;
    public final double ToleranceXDerivate = 0.1;
    public final double ToleranceYDerivative = 0.1;
    public final double ToleranceThetaDeriv = 0.01;

    //Constructor
    public OpenLoopController() {
    }

    /**
     * Test coordinate x against its tolerance value
     * @param x is the x-coordinate of the probe
     * @return true if x is allowed for its tolerance and false if not
     */
    public boolean testForX(double x) {
        if(Math.abs(x) <= ToleranceX) {
            return true;
        }
        return false;
    }

    /**
     * Test theta against its tolerance value
     * @param theta
     * @return true if theta is allowed for its tolerance and false if not
     */
    public boolean testForTheta(double theta) {
        if(Math.abs(theta % 2*Math.PI) <= ToleranceTheta) {
            return true;
        }
        return false;
    }

    /**
     * Test x-coordinate of the velocity against its tolerance value
     * @param velOfX x-coordinate of the velocity of the probe
     * @return true if the x-coordinate is allowed for its tolerance and false if not
     */
    public boolean testForXDerivative(double velOfX) {
        if(Math.abs(velOfX) <= ToleranceXDerivate) {
            return true;
        }
        return false;
    }

    /**
     * Test y-coordinate of the velocity against its tolerance value
     * @param velOfY y-coordinate of the velocity of the probe
     * @return true if the y-coordinate is allowed for its tolerance and false if not
     */
    public boolean testForYDerivative(double velOfY) {
        if(Math.abs(velOfY) <= ToleranceYDerivative) {
            return true;
        }
        return false;
    }

    /**
     * Test the derivative of theta against its tolerance value
     * @param thetaDerivative
     * @return true if the derivative of theta is allowed for its tolerance and false if not
     */
    public boolean testForThetaDerivative(double thetaDerivative) {
        if (Math.abs(thetaDerivative) == ToleranceThetaDeriv) {
            return true;
        }
        return false;
    }
}