package rocketThrust;

public class JacobianMatrix extends Vector3d{
    /**
     * @author Laurence.
     * @param h represents the step size.
     * @param 2 dimensional 3 by 3 matrix to calculate the partial derivatives of the 3 Dimensional vectors
     * @param x,y,z represents the coordinates in 3D.
     * @return 3 dimensional matrix with partial derivative values representing each vector in 3d.
     */

    double[][] matrix = new double[3][3];
    //i represents the rows.
    //j represents the columns.
    double row = matrix.length;
    double column = matrix[0].length;
    // maybe turn this into an instance
    vector3d vectorValue;


    public static double[][] calcJacobianDifferential(double h, Vector3d vectors) {
        double[][] A = new double[3][3];
        //x value of vector
        double dvx = Vector3d.getX();
        dvx += h;
        //y value of vector
        double dvy = Vector3d.getY();
        dvy += h;
        //z value of vector
        double dvz = Vector3d.getZ();
        dvz += z;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i < row && j < column) {
                    //temporarily store dvx vector
                    A[i][0] = dvx;
                    A[i][1] = dvy;
                    A[i][2] = dvz;


                }
            }
        }
        // once calculations are complete, return the newly formed matrix
        return A;
    }
    // What is the function g? because at the moment it looks like it's a vector.
    // Vectors are not functions.
    // Once function g is obtained, how to contruct the partial derivatives?
    // is it the differential / differential or a differential equation?

    public static double getDerivativeG(Vector3d vector, double h) {
        double dgx = 1.0;


        // WRITE CODE HERE


        return dgx;
    }
}




}
