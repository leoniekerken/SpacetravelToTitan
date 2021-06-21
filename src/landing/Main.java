package landing;

/**
 *
 *
 */
public class Main {
    // Run for testing purposes Landing module
    public static void main( String[] args){
        // wind();
        closed();

    }
    // This method gets called if we want to test the Closed Loop Controller
    public static void closed ()
    {
        // change this to false if you want to test with out any initial velocity.
        ClosedController.INITIAL_MOVING = true;
        Lander lander = new Lander();
        System.out.println("Closed Landing Start: " + lander.closedController.toFullString());
        lander.reachTitan();
        System.out.println("Closed Landing End: " + lander.closedController.toFullString());
    }

    public static void wind()
    {
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
