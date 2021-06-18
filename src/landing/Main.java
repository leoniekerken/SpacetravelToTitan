package landing;

/**
 * Main class to test the wind model
 *
 * @author Chiara
 */
public class Main {

    public static void main( String[] args){
        WindModel wind = new WindModel();
        double altitude;
        double velocity;

        //Test to try to get an estimate of the velocity of the wind based on an arbitrary altitude
        for(int i=0; i< 100;i++) {
            altitude = Math.random() * 130;
            velocity = wind.evaluateVelocity(altitude);
            System.out.println("Altitude: " + altitude);
            System.out.println("Wind force at given altitude: " + velocity + " m/s");
            System.out.println();
        }
        System.out.println();
        System.out.println();

        //Estimate the relative error of the wind comparing it with the actual velocity of the wind
        //collected during the mission Cassini-Huygens
        double[][] correctData = {{7, 0}, {20, 4}, {30, 10}, {55, 30}, {120, 120}};
        for (int i = 0; i < correctData.length; i++) {
            System.out.println("Altitude: " + correctData[i][0]);
            System.out.println("Correct velocity: " + correctData[i][1]);
            System.out.println("Estimate velocity: " + wind.evaluateVelocity(correctData[i][0]));
            System.out.println("Relative error: " + wind.relativeError(wind.evaluateVelocity(correctData[i][0]), correctData[i][1]) + " %");
            System.out.println();
        }
    }
}
