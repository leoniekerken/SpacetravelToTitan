package simulator;

import titan.Vector3dInterface;

import java.util.ArrayList;

/**
 * class to store all relevant information about an object in the solar system
 *
 * @author Leo, Oscar
 */
public class Planet
{
    public String name;
    public int step;

    public double mass;
    public double radius;
    public double gravity;

    //velocity
    public double velocityX;
    public double velocityY;
    public double velocityZ;

    //position
    public double positionX;
    public double positionY;
    public double positionZ;

    //vectors
    public Vector3dInterface posVector;
    public Vector3dInterface velVector;
    public Vector3dInterface accVector;

    //arrayList to store positions over time (for visualization)
    ArrayList<Vector3dInterface> orbit = new ArrayList<Vector3dInterface>();

    //array to store x-Coordinates over time (for visualization)
    double[] orbitX;

    //array to store y-Coordinates over time (for visualization)
    double[] orbitY;

    public static Planet[] planets; //Array of all planets of the solar system

    /**
     *constructor
     *@param namePlanet represents the name of the planet to be created
     */
    public Planet (String namePlanet)
    {
        name = namePlanet;
        step = 0;
    }

    /**
     * create the vector3d objects.
     */
    public void vectors ()
    {
        posVector = new Vector3d(positionX, positionY, positionZ);
        velVector = new Vector3d(velocityX, velocityY, velocityZ);
        accVector = new Vector3d(0,0,0);

        //adds initial position to orbit
        orbit.add(posVector);
    }

    /**
     *  resets the acceleration of all objects
     */
    public static void accReset ()
    {
        for (int i = 0; i < planets.length; i++)
        {
            planets[i].accVector = new Vector3d(0,0,0);
        }
    }

    /**
     * conversion from meters to AU
     * @param m values in meters
     * @return in AU
     */
    public double fromMToAU(double m) {
        double AU = m * 6.6846e-12;
        return AU;
    }

    /**
     * @return array holding x-coordinates over time (in AU, important for visualization!)
     */
    public double[] getOrbitX(){

        orbitX = new double[orbit.size()];

        for(int i = 0; i < orbit.size(); i++){
            orbitX[i] = fromMToAU(orbit.get(i).getX());
        }
        return orbitX;
    }

    /**
     * @return array holding y-coordinates over time (in AU, important for visualization!)
     */
    public double[] getOrbitY(){

        orbitY = new double[orbit.size()];

        for(int i = 0; i < orbit.size(); i++){
            orbitY[i] = fromMToAU(orbit.get(i).getY());
        }
        return orbitY;
    }

    /**
     *
     * @return initial x-position of object
     */
    public double getInitX(){

        return fromMToAU(orbit.get(1).getX());
    }

    /**
     *
     * @return initial y-position of object
     */
    public double getInitY(){

        return fromMToAU(orbit.get(1).getY());
    }


}