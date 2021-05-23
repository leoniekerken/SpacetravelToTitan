package simulator;


import titan.Vector3dInterface;

/**
 * CLASS USED TO PERFORM OPERATIONS ON Vector3dInterface OBJECTS AND ARRAYLISTS OF Vector3dInterface OBJECTS
 * Note: this methods could also be added to the Vector3d class
 *
 * @author chiara
 */
public class VectorOperations {

    public VectorOperations() {
    }

    /**
     * Sum all Vector3d objects of an ArrayList together
     *
     * @return the sum of all vectors as another vector3dInterface
     */
    public static Vector3dInterface sumAll(Vector3dInterface[] vectors) {
        Vector3dInterface sum = vectors[0];

        for (int i = 1; i < vectors.length; i++) {
            sum = sum.add(vectors[i]);
        }
        return sum;
    }

    /**
     *  Given two vectors, this method evaluates the direction of the force that they perform on each other
     *
     *  @param v1 is the first vector
     *  @param v2 is the second vector
     *  @return The direction of the force resulting from the two vectors
     */
    public static Vector3dInterface approximateDirection(Vector3dInterface v1, Vector3dInterface v2) {
        Vector3dInterface resultantForce = new Vector3d();
        resultantForce.setX(v1.getX() + v2.getX());
        resultantForce.setY(v1.getY() + v2.getY());
        resultantForce.setZ(v1.getZ() + v2.getZ());

        //Finish method to get a better approximation of the direction of the force!

        return resultantForce;
    }

    /**
     * Find the angle between vectors
     * //Note: check whether it is possible to be evaluated without the dotProduct
     *
     * Formula:
     * //Insert here (it is equal to the dot product of vectors divided by the product of the magnitudes of the vectors)
     *
     * @param v1 is the first vector
     * @param v2 is the second vector
     * @retrun the angle between the vectors
     */
    public double getAngle(Vector3dInterface v1, Vector3dInterface v2) {
        double magnitudesProduct = getMagnitude(v1) * getMagnitude(v2);

        double dotProduct = getDotProduct(v1, v2);
        double angle = dotProduct / magnitudesProduct;
        return angle;
    }

    /**
     * Evaluate the magnitude of vector v
     *
     * Formula:
     * //Insert here (it is equal to the square root of the sum of the three dimensions)
     *
     * @param v is the vector
     * @return the magnitude of vector v
     */
    public double getMagnitude(Vector3dInterface v) {
        double magnitude = Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2));
        return magnitude;
    }

    /**
     * Evaluate the dot product of the vectors
     * Note: check whether the angle needs to be added to this formula for a better result!
     *
     * Formula:
     * //Insert here
     *
     * @param v1 is the first vector
     * @param v2 is the second vector
     * @raturn the dot product of the vectors
     */
    public double getDotProduct(Vector3dInterface v1, Vector3dInterface v2) {
        double dotProd = (v1.getX() * v2.getX()) + (v1.getY() * v2.getY()) + (v1.getZ() * v2.getZ());
        return dotProd;
    }
}
