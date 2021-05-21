package rocketThrust;

import titan.Vector3dInterface;

/**
 * This class computes the MULTIVARIABLE NEWTON'S THEOREM
 * V(k+1) = V(k) - inverse(D(g(V(k))) * g(V(k))
 *
 * @author chiara
 */

public class MultivariableNewton {

    double[][] jacobianMatrix = new double[3][3];

    public MultivariableNewton() {
    }

    /**
     *  MULTIVARIABLE NEWTON THEOREM
     *  Finds the updated velocity of the probe as it gets closer to Titan
     *
     *  @param g = g(V(k))
     *  @param vK = current velocity
     *  @return updated velocity V(k+1)
     */
    public Vector3dInterface doMultivariableNewton(Vector3dInterface g, Vector3dInterface vK) {
        Vector3dInterface product = MatrixOperations.multiplyVectorMatrix(jacobianMatrix, g);
        return vK.sub(product);
    }

    /**
     * TO BE IMPLEMENTED!
     * Method to fill in the jacobianMatrix
     */
    public void fillMatrix() {
    }

    /**
     * @return the inverse of the jacobianMatrix
     */
    public double[][] inverse() {
        double[][] inverse = MatrixOperations.inverse(jacobianMatrix);
        return inverse;
    }
}