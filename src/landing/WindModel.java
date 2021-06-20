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
<<<<<<< HEAD
        if (altitude <= MAX_ALTITUDE && altitude > MIN_ALTITUDE)
        {

            return A + (Math.random() * B) * altitude;
        }
        else
        {
            return 0;
        }
=======
        return A + B*altitude;
>>>>>>> 8290e5f (Add files via upload)
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
<<<<<<< HEAD

=======
>>>>>>> 8290e5f (Add files via upload)
    }
}
