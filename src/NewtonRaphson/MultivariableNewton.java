package NewtonRaphson;

import simulator.Simulator;
import simulator.Vector3d;
import titan.Vector3dInterface;

/**
 * MULTIVARIABLE NEWTON'S THEOREM
 *
 * @author chiara
 */

public class MultivariableNewton {

    static double h = Simulator.h;     //Stepsize used to fill in the jacobian matrix
    double[][] jacobianMatrix;
    titan.Vector3dInterface v0 = new Vector3d(0,0,0); //initial velocity

    public MultivariableNewton() {
        jacobianMatrix = new double[3][3];
    }

    /**
     * Only used to get the current velocity which corresponds to the value of g(vK)
     * @param v is the current velocity
     * @return v
     */
    public Vector3dInterface computeG(Vector3dInterface v) {
        return v;
    }

    /**
     * Compute formula g given the two positions of the probe
     * @param pk is the current position of the probe
     * @param pf is the final position of the probe
     * @return the value of g(vK) = pf-pk
     */
    public Vector3dInterface computeDistance(Vector3dInterface pk, Vector3dInterface pf) {
        return pf.sub(pk);
    }

    /**
     *  Method that actually performs the Multivariable Newton method
     *  V(k+1) = V(k) - D(g(V(k))inverse * g(V(k))
     *  @param g = g(V(k))
     *  @param vK = current velocity
     *  @return updated velocity V(k+1)
     */
    public titan.Vector3dInterface doMultivariableNewton(titan.Vector3dInterface g, titan.Vector3dInterface vK) {
        fillMatrix(vK);
        titan.Vector3dInterface product = MatrixOperations.multiplyVectorMatrix(MatrixOperations.inverse(jacobianMatrix), g);
        return vK.sub(product);
    }

    /**
     * Method to fill in the jacobianMatrix
     * g'(V(k)) = (g(V + h) - g(V - h))/2h
     */
    public void fillMatrix(titan.Vector3dInterface v) {
        // First I compute all the vectors needed for the operations
        Vector3d xPlusH = new Vector3d(v.getX() + h, v.getY(), v.getZ());
        Vector3d xMinusH = new Vector3d(v.getX() - h, v.getY(), v.getZ());
        Vector3d yPlusH = new Vector3d(v.getX(), v.getY() + h, v.getZ());
        Vector3d yMinusH = new Vector3d(v.getX(), v.getY() - h, v.getZ());
        Vector3d zPlusH = new Vector3d(v.getX(), v.getY(), v.getZ() + h);
        Vector3d zMinusH = new Vector3d(v.getX(), v.getY(), v.getZ() - h);

        this.jacobianMatrix[0][0] =  (computeG(xPlusH).sub(computeG(xMinusH))).getX() / 2*h;
        this.jacobianMatrix[0][1] =  (computeG(xPlusH).sub(computeG(xMinusH))).getY() / 2*h;
        this.jacobianMatrix[0][2] =  (computeG(xPlusH).sub(computeG(xMinusH))).getZ() / 2*h;
        this.jacobianMatrix[1][0] =  (computeG(yPlusH).sub(computeG(yMinusH))).getX() / 2*h;
        this.jacobianMatrix[1][1] =  (computeG(yPlusH).sub(computeG(yMinusH))).getY() / 2*h;
        this.jacobianMatrix[1][2] =  (computeG(yPlusH).sub(computeG(yMinusH))).getZ() / 2*h;
        this.jacobianMatrix[2][0] =  (computeG(zPlusH).sub(computeG(zMinusH))).getX() / 2*h;
        this.jacobianMatrix[2][1] =  (computeG(zPlusH).sub(computeG(zMinusH))).getX() / 2*h;
        this.jacobianMatrix[2][2] =  (computeG(zPlusH).sub(computeG(zMinusH))).getX() / 2*h;
    }

    /**
     * Print the jacobian matrix
     */
    public void print(double[][] M) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }
    }
}
