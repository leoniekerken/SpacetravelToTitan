package simulator;

/**
 * class to calculate optimal initial conditions for the probe to launch
 *
 * @author Oscar, Leo
 */

public class TakeOffPoint {

    public static boolean DEBUG = true;

    Vector3d unitVector;
    Vector3d startPos;
    Vector3d startVel;

    Vector3d titanAtStart;
    Vector3d titanAtEnd;
    Vector3d titanAtHalf;

    /**
     * constructor
     * @param initVel initial velocity in m/s
     */
    public TakeOffPoint(double initVel){

       posTitan();
       unitVector(titanAtStart);
       startPos();
       startVel(initVel);

       if(DEBUG){
           System.out.println("UNITVECTOR: " + unitVector);
           System.out.println(("UNITVECTOR NORM: " + unitVector.norm()));
       }
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
        unitVector = (Vector3d) posVectorTitan.sub(Planet.planets[3].posVector).mul(1/posVectorTitan.dist(Planet.planets[3].posVector));
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
