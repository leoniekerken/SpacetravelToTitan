package rocketThrust;

import titan.Vector3dInterface;

/**
 * This class computes the MULTIVARIABLE NEWTON'S THEOREM
 *
 * @author chiara
 */

public class MultivariableNewton {

    double[][] jacobianMatrix = new double[3][3];

    public MultivariableNewton() {
    }

    /**
     *  Method that actually performs the Multivariable Newton method
     *  V(k+1) = V(k) - D(g(V(k))inverse * g(V(k))
     *  @param g = g(V(k))
     *  @param vK = current velocity
     *  @return updated velocity V(k+1)
     */
    public Vector3dInterface doMultivariableNewton(Vector3dInterface g, Vector3dInterface vK) {
        Vector3dInterface product = VectorMatrixOperations.multiplyVectorMatrix(jacobianMatrix, g);
        return vK.sub(product);
    }

    /**
     * TO BE IMPLEMENTED!
     * Method to fill in the jacobianMatrix
     */
    public void fillMatrix() {
    }

    /**
     * TO BE IMPLEMENTED!
     * @return the inverse of the jacobianMatrix
     */
    public double[][] inverse() {
        double[][] inverse = new double[3][3];

        return inverse;
    }
}