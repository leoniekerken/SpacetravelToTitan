package landing;

/**
 * STOCHASTIC WIND MODEL
 *
 * @author Chiara
 */
public class WindModel {

    final double MAX_ALTITUDE= 120.00;
    final double MIN_ALTITUDE= 7.00;
    final double A = -11.8796;
    final double B = 0.9978;

    //Constructor
    public WindModel() {
    }

    /**
     * The velocity is evaluated using the linear regression equation
     * derived from the data of the Cassini-Huygens mission to Titan
     * @param altitude is the given altitude we are at
     * @return the velocity at that altitude
     */
    public double evaluateVelocity(double altitude) {

        return A + B*altitude;
    }

    public double evalVel(double altitudeM)
    {
        // the system takes in values in km not m.
        double altitude = altitudeM/1000.00;

        if (altitude <= (MAX_ALTITUDE + 10) && altitude > MIN_ALTITUDE)
        {

            return A + B * altitude ;
        }
        else if (altitude <= MIN_ALTITUDE && altitude > 0.7)
        {
            return Math.random() * altitude/1000.00 ;
        }
        else if (altitude <= 0.7)
        {
            return 0.04;
        }
        else
        {
            return 0;
        }
    }

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
