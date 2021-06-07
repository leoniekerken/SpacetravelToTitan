package NewtonRaphson;

import simulator.Simulator;
import simulator.Vector3d;

/**
 * This class computes the MULTIVARIABLE NEWTON'S THEOREM
 *
 * @author chiara
 */

public class MultivariableNewton {

    static double h = Simulator.h;     //Stepsize used to fill in the jacobian matrix
    double[][] jacobianMatrix;

    public MultivariableNewton() {
        jacobianMatrix = new double[3][3];
    }

    /**
     *  Method that actually performs the Multivariable Newton method
     *  V(k+1) = V(k) - D(g(V(k))inverse * g(V(k))
     *  @param g = g(V(k))
     *  @param vK = current velocity
     *  @return updated velocity V(k+1)
     */
    public titan.Vector3dInterface doMultivariableNewton(titan.Vector3dInterface g, titan.Vector3dInterface vK) {
        fillMatrix(g, vK);
        titan.Vector3dInterface product = MatrixOperations.multiplyVectorMatrix(MatrixOperations.inverse(jacobianMatrix), g);
        return vK.sub(product);
    }

    /**
     * Method to fill in the jacobianMatrix
     * g'(V(k)) = (gx(Vx + h) - gx(Vx - h))/2h
     * g'(V(k)) = (g(V + h) - gx(V - h))/2h
     */
    public void fillMatrix(titan.Vector3dInterface g, titan.Vector3dInterface v) {
        // First I compute all the vectors needed for the operations
        Vector3d xPlusH = new Vector3d(v.getX() + h, v.getY(), v.getZ());
        Vector3d xMinusH = new Vector3d(v.getX() - h, v.getY(), v.getZ());
        Vector3d yPlusH = new Vector3d(v.getX(), v.getY() + h, v.getZ());
        Vector3d yMinusH = new Vector3d(v.getX(), v.getY() - h, v.getZ());
        Vector3d zPlusH = new Vector3d(v.getX(), v.getY(), v.getZ() + h);
        Vector3d zMinusH = new Vector3d(v.getX(), v.getY(), v.getZ() - h);
        titan.Vector3dInterface gX = new Vector3d(g.getX(), g.getX(), g.getX());
        titan.Vector3dInterface gY = new Vector3d(g.getY(), g.getY(), g.getY());
        titan.Vector3dInterface gZ = new Vector3d(g.getZ(), g.getZ(), g.getZ());

        this.jacobianMatrix[0][0] =  (xPlusH.innerProduct(gX) - xMinusH.innerProduct(gX)) / 2*h;
        this.jacobianMatrix[0][1] =  (yPlusH.innerProduct(gX) - yMinusH.innerProduct(gX)) / 2*h;
        this.jacobianMatrix[0][2] =  (zPlusH.innerProduct(gX) - zMinusH.innerProduct(gX)) / 2*h;
        this.jacobianMatrix[1][0] =  (xPlusH.innerProduct(gY) - xMinusH.innerProduct(gY)) / 2*h;
        this.jacobianMatrix[1][1] =  (yPlusH.innerProduct(gY) - yMinusH.innerProduct(gY)) / 2*h;
        this.jacobianMatrix[1][2] =  (zPlusH.innerProduct(gY) - zMinusH.innerProduct(gY)) / 2*h;
        this.jacobianMatrix[2][0] =  (xPlusH.innerProduct(gZ) - xMinusH.innerProduct(gZ)) / 2*h;
        this.jacobianMatrix[2][1] =  (yPlusH.innerProduct(gZ) - yMinusH.innerProduct(gZ)) / 2*h;
        this.jacobianMatrix[2][2] =  (zPlusH.innerProduct(gZ) - zMinusH.innerProduct(gZ)) / 2*h;
    }
}