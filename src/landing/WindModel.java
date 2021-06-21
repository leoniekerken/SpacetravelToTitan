package landing;

/**
 * STOCHASTIC WIND MODEL
 *
 * @author Chiara
 */
public class WindModel {

    //The following data is in KM
    final double MAX_ALTITUDE = 120.00;
    final double MIN_ALTITUDE = 10.00;
    //The values of A and B results from the data collected from the Cassini-Huygen mission to Titan in 2005
    final double A = -11.8796;
    final double B = 0.9978;
    //Random component to be added to the formula to evaluate the velocity of the wind
    double epsilon = 0;

    //Constructor
    public WindModel() {
    }

    /**
     * The velocity is evaluated using the linear regression equation
     * derived from the data of the Cassini-Huygens mission to Titan
     *
     * @param altitudeKM is the given altitude we are at in km
     * @return the velocity at that altitude
     */
    public double evaluateVelocity(double altitudeKM) {
        epsilon = Math.random() * 10;
        return (A + B * altitudeKM) + epsilon;
    }

    /**
     * The velocity is evaluated taking into account the different changes that happen in Titan's atmosphere
     *
     * @param altitudeM is the altitude in meters
     * @return the velocity in m/s at given altitude
     */
    public double evalVel(double altitudeM)
    {
        //Altitude is converted from meters to kilometers
        double altitude = fromMtoKM(altitudeM);

        //First layer: above the maximum altitude and closer to the surface the velocity is close to zero
        if (altitude > MAX_ALTITUDE || altitude < MIN_ALTITUDE)
        {
            return 0.3;
        }

        //Second layer: between 100 and 60 the velocity of the wind is very slow
        else if (altitude < 100 && altitude > 60)
        {
            return evaluateVelocity(altitude)/2;
        }

        //Everything else that does not belong to the previous two layers is evaluated thanks to the previous formula
        //since the wind velocity is linearly dependent on those layers
        else
        {
            return evaluateVelocity(altitude);
        }
    }

    /**
     * @param m is the value in meters to be converted
     * @return value of m in km
     */
    public double fromMtoKM(double m) { return m/1000.00; }

    /**
     * @param km is the value in kilometers to be converted
     * @return value of km in m
     */
    public double fromKMtoM(double km) { return km*1000.00; }

    /**
     * @param windSpeed
     * @return kmh^-1 from ms^-1
     */
    public double convertToKmH( double windSpeed){ return (windSpeed*3600)/1000; }

    /**
     * @param windSpeed
     * @return ms^-1 from mkh^-1
     */
    public double convertToMS( double windSpeed){ return (windSpeed*1000)/3600; }

    /**
     * Evaluate the relative error
     * @return the relative error
     */
    public double relativeError(double estimate, double correct) {
        return (Math.abs(estimate-correct)/Math.abs(correct)) * 100;
    }
}
