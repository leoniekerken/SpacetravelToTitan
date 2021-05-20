//package simulator;
import java.util.Arrays;
public class InvertedMatrix {

//    public static double[][] inverseOfMatrix (double[][] matrixOG)
//    {
//        int n = matrixOG.length;
//        double[][] matrixFin = new double [n][n];
//        double[][] matrixI = new double [n][n];
//        int[] index = new int[n];
//        for (int i = 0; i < n; i++)
//        {
//            index[i] = i;
//            matrixI[i][i] = 1;
//        }
//        inverseDevide1(matrixOG, index);
//
//        for (int i=0; i < n-1; i++)
//        {
//            for (int j = i + 1; j < n; j++)
//            {
//                for (int k = 0; k < n; k++)
//                {
//                    matrixI[index[j]][k] -= matrixOG[index[j]][i] * matrixI[index[i]][k];
//                }
//            }
//        }
//        for (int i = 0; i < n; i ++)
//        {
//            matrixFin[n-1][i] = matrixI[index[n-1]][i]/matrixOG[index[n-1]][n-1];
//            for (int j=n-2; j>=0; j--)
//            {
//                matrixFin[j][i] = matrixI[index[j]][i];
//                for (int k=j+1; k<n; k++)
//                {
//                    matrixFin[j][i] -= matrixOG[index[j]][k] * matrixFin[k][i];
//                }
//                matrixFin[j][i] /= matrixOG[index[j]][j];
//            }
//        }
//        return matrixFin;
//    }
//
//    public static void inverseDevide1(double[][] matrixOG, int[] index)
//    {
//        int n = matrixOG.length;
//        double[] c = new double [n];
//
//        for (int i = 0; i < n; i ++)
//        {
//            double c1 = 0;
//            for (int j=0; j<n; ++j)
//            {
//                double c0 = Math.abs(matrixOG[i][j]);
//                if (c0 > c1)
//                {
//                    c1 = c0;
//                }
//            }
//            c[i] = c1;
//        }
//        int p = 0;
//        for (int j = 0; j < (n-1); j++)
//        {
//            double pJ0 = 0;
//
//            for (int i = j; i < n; i++)
//            {
//                double pJ1 = Math.abs(matrixOG[index[i]][j]);
//                pJ1 /= c[index[i]];
//                if (pJ1 > pJ0)
//                {
//                    pJ0 = pJ1;
//                    p = i;
//                }
//            }
//
//            int indJ = index[j];
//            index[j] = index[p];
//            index[p] = indJ;
//
//            for (int i = j + 1; i < n; i++)
//            {
//                double pJ2 = matrixOG[index[i]][j]/matrixOG[index[j]][i];
//                matrixOG [index[i]][j] = pJ2;
//                for (int k = j+1; k < n; k++)
//                {
//                    matrixOG[index[i]][k] -= pJ2 * matrixOG[index[j]][k];
//                }
//            }
//        }
//    }

    public static int NOV;
    public static double[][] inverse (double[][] og)
    {
        final int N = og.length;
        NOV = N;
        double d = det(og, NOV);
        double[][] ad = new double[NOV][NOV];
        double[][] inverse = new double[NOV][NOV];
        adj(og, ad);

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < NOV; j++)
            {
                inverse[i][j] = ad[i][j]/d;
            }
        }
        return inverse;
    }

    public static void factorize (double[][] og, double[][] temp, int p, int q, int n)
    {
        int i = 0;
        int j = 0;

        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                if (row != p && col !=q)
                {
                    temp[i][j] = og[row][col];
                    j++;

                    if (j== n-1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public static double det (double[][] og, int n)
    {
        int delta = 0;
        if (n == 1)
        {
            return og[0][0];
        }

        double[][] temp = new double[NOV][NOV];

        int signum = 1;

        for (int i = 0; i < n; i++)
        {
            factorize(og, temp, 0, i, n);
            delta += signum * og[0][i] * det(temp, n-1);

            signum = -signum;
        }
        return delta;
    }

    public static void adj(double[][] og, double[][] ad)
    {
        if (NOV == 1)
        {
            ad[0][0] = 1;
            return;
        }
        int signum = 1;
        double[][] temp = new double[NOV][NOV];

        for (int i = 0; i < NOV; i++)
        {
            for (int j = 0; j < NOV; j++)
            {
                factorize(og, temp, i, j, NOV);

                if ((i + j) %2 == 0)
                {
                    signum = 1;
                }
                else
                {
                    signum = -1;
                }

                ad[j][i] = signum * det(temp, NOV -1);
            }
        }
    }

    public static void main(String[] args) {
//        double[][] testMatrix = new double[][]{{1,2},{3,4}};
//        System.out.println(Arrays.deepToString(inverseOfMatrix(testMatrix)));
        double[][] og = {{1,2},{3,4}};
        System.out.println(Arrays.deepToString(inverse(og)));
    }
}

























