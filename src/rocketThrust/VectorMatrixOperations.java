package rocketThrust;

import simulator.Vector3d;
import titan.Vector3dInterface;

/**
 * This class performs operations between a matrix and a vector
 *
 * @author chiara
 */
public class VectorMatrixOperations {


    public VectorMatrixOperations() {
    }

    /**
     * Multiplication of a matrix A and a Vector3d b
     * @return the vector x resulting from the product Ab
     */
    public static Vector3dInterface multiplyVectorMatrix(double[][] A, Vector3dInterface b) {
        // Multiplication requirements not met
        // Size of vector b must be 3
        if(A[0].length != 3)
            throw new RuntimeException("Illegal matrix dimensions.");

        Vector3dInterface output = new Vector3d();

        output.setX(A[0][0]*b.getX() + A[0][1]*b.getY() + A[0][2]* b.getZ());
        output.setY(A[1][0]*b.getX() + A[1][1]*b.getY() + A[1][2]* b.getZ());
        output.setZ(A[2][0]*b.getX() + A[2][1]*b.getY() + A[2][2]* b.getZ());

        return output;
    }
}
