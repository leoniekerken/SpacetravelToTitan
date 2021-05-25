package NewtonRaphson;

import simulator.Vector3d;
import titan.Vector3dInterface;

/**
 * CLASS TO PERFORM MATRIX OPERATIONS
 *
 * @author Oscar, Chiara
 */

public class MatrixOperations {

    public static int NOV;

    /**
     * INVERSE OF A MATRIX
     * @param og = matrix to be inverted
     * @return the inverse of matrix og
     */
    public static double[][] inverse (double[][] og) {
        final int N = og.length;
        NOV = N;
        double d = det(og, NOV);
        double[][] ad = new double[NOV][NOV];
        double[][] inverse = new double[NOV][NOV];
        adj(og, ad);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < NOV; j++) {
                inverse[i][j] = ad[i][j]/d;
            }
        }
        return inverse;
    }

    public static void factorize (double[][] og, double[][] temp, int p, int q, int n) {
        int i = 0;
        int j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col !=q) {
                    temp[i][j] = og[row][col];
                    j++;

                    if (j== n-1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public static double det (double[][] og, int n) {
        int delta = 0;
        if (n == 1) {
            return og[0][0];
        }

        double[][] temp = new double[NOV][NOV];

        int signum = 1;

        for (int i = 0; i < n; i++) {
            factorize(og, temp, 0, i, n);
            delta += signum * og[0][i] * det(temp, n-1);

            signum = -signum;
        }
        return delta;
    }

    public static void adj(double[][] og, double[][] ad) {
        if (NOV == 1) {
            ad[0][0] = 1;
            return;
        }
        int signum = 1;
        double[][] temp = new double[NOV][NOV];

        for (int i = 0; i < NOV; i++) {
            for (int j = 0; j < NOV; j++) {
                factorize(og, temp, i, j, NOV);

                if ((i + j) %2 == 0) {
                    signum = 1;
                } else {
                    signum = -1;
                }
                ad[j][i] = signum * det(temp, NOV -1);
            }
        }
    }

    /**
     * PRODUCT OF A MATRIX a AND A VECTOR3D b
     * @return the vector x resulting from the product Ab
     */
    public static Vector3dInterface multiplyVectorMatrix(double[][] a, Vector3dInterface b) {
        // Multiplication requirements not met
        // Size of vector b must be 3
        if(a[0].length != 3)
            throw new RuntimeException("Illegal matrix dimensions.");

        Vector3dInterface output = new Vector3d();

        output.setX(a[0][0]*b.getX() + a[0][1]*b.getY() + a[0][2]* b.getZ());
        output.setY(a[1][0]*b.getX() + a[1][1]*b.getY() + a[1][2]* b.getZ());
        output.setZ(a[2][0]*b.getX() + a[2][1]*b.getY() + a[2][2]* b.getZ());

        return output;
    }
}