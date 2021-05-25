package simulator;

/**
 * class to calculate optimal initial conditions for the probe to launch
 *
 * @author Oscar, Leo
 */

public class TakeOffPoint {

    public static boolean DEBUG = true;

    public Vector3d unitVector;
    public Vector3d startPos;
    public Vector3d startVel;

    public Vector3d titanAtStart;
    public Vector3d titanAtEnd;
    public Vector3d titanAtHalf;

    public Vector3d targetPos;

    public TakeOffPoint(){
    }

    /**
     * calculates ideal take off point of the probe
     * @param initVel initial (undirected) velocity in m/s
     * @param targetPos target position
     */
    public void calculateTakeOffPoint(double initVel, Vector3d targetPos){
        posTitan();
        unitVector(targetPos);
        startPos();
        startVel(initVel);
    }

    /**
     * initializing position of titan at three different time points
     * start, end (after one year) and half (after half a year)
     * to compute the unitVector and hence the initial orientation of the probe
     */
    public void posTitan(){
        titanAtStart = (Vector3d) Planet.planets[8].posVector;
        titanAtEnd = new Vector3d(8.789395128516927E11, -1.2040036104665325E12, -1.435613681023351E10);
        titanAtHalf = new Vector3d(7.582476634152103E11, -1.2884184289416096E12, -7.369670796140033E9);
    }

    /**
     * calculate unitVector from earth to titan by
     * taking the difference between earth.positionVector and titan.positionVector and dividing it by
     * the euclidean distance between earth.positionVector and titan.positionVector
     * @return the unitVector from earth to titan
     */
    public Vector3d unitVector(Vector3d posVectorTitan){
        //unitVector = (Vector3d) posVectorTitan.sub(Planet.planets[3].posVector).mul(1/posVectorTitan.dist(Planet.planets[3].posVector));
        Vector3d velVector  = new Vector3d(23355.353441491174, -55266.284768020276, -406.48931488639954);
        unitVector = (Vector3d) velVector.mul((1.0/60000.0));
        return unitVector;
    }
    /**
     * positioning the take off point somewhere on earth's surface and configuring take off point
     * so that it is directed towards titan by
     * multiplying unitVector earth to titan with radius of earth - this way we get a new vector, with the same direction
     * as the unitVector (so we are pointing towards titan), but with a different magnitude (so that we are on earth's surface)
     * @return starting positionVector of the probe
     */
    public Vector3d startPos(){
        startPos = (Vector3d) Planet.planets[3].posVector.addMul(Planet.planets[3].radius, unitVector);
        return startPos;
    }

    /**
     * calculating starting velocity of the probe by
     * multiplying unitVector earth to titan with the (undirected) initialVelocity in m/s
     * this way we get a new (directed) velocityVector towards titan
     * @param initVel initialVelocity in m/s
     * @return starting (directed) velocityVector of the probe
     */
    public Vector3d startVel(double initVel){
        startVel = (Vector3d) unitVector.mul(initVel);
        return startVel;
    }
}
