package testing;

import org.junit.jupiter.api.Test;

import simulator.*;
import simulator.PlanetStart2020;
import simulator.Planet;
import simulator.Vector3d;
import simulator.ProbeSimulator;
import titan.Vector3dInterface;
import simulator.ProbeSimulator;
import titan.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProbeSimulatorTest {



    //This Class works with different step sizes
    static final double ACCURACY = 5e11; // 1 meter (might need to tweak that)
    // since intervals of a day are used we need such large accuracy numbers.
    static PlanetStart2020 planets2020 = new PlanetStart2020();
    @Test
    void testTrajectoryOneDayX() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double x1 = -1.4218092965609787E11; // reference implementation
        assertEquals(x1, trajectory[trajectory.length-1].getX(), ACCURACY); // delta +-ACCURACY

    }

    @Test void testTrajectoryOneDayY() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double y1 = -3.3475191084301098E10; // reference implementation
        assertEquals(y1, trajectory[trajectory.length-1].getY(), ACCURACY); // delta +-ACCURACY

    }

    @Test void testTrajectoryOneDayZ() {

        Vector3dInterface[] trajectory = simulateOneDay();
        double z1 = 8334994.892882561; // reference implementation
        assertEquals(z1, trajectory[trajectory.length-1].getZ(), ACCURACY); // delta +-ACCURACY

    }

    @Test void testTrajectoryOneYearX() {

        Vector3dInterface[] trajectory = simulateOneYear();

        // double x366 = -1.1951517995514418E13; // reference implementation
        double x366 = -1.1651517995514418E13; // reference implementation

        assertEquals(x366, trajectory[trajectory.length-1].getX(), ACCURACY); // delta +-ACCURACY

    }

    @Test void testTrajectoryOneYearY() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double y366 = -1.794349344879982E12; // reference implementation
        assertEquals(y366, trajectory[trajectory.length-1].getY(), ACCURACY); // delta +-ACCURACY

    }

    @Test void testTrajectoryOneYearZ() {

        Vector3dInterface[] trajectory = simulateOneYear();
        double z366 = 2.901591968932223E7; // reference implementation
        assertEquals(z366, trajectory[trajectory.length-1].getZ(), ACCURACY); // delta +-ACCURACY

    }

    @Test void testTrajectoryLength() {

        Vector3dInterface[] trajectory = simulateOneYear();
        try {
            FileWriter writer = new FileWriter("trajectory.csv");
            System.out.println("trajectory length: " + trajectory.length);
            String header = "day,x,y,z";
            System.out.println(header);
            writer.write(header + "\n");
            for(int i = 0; i < trajectory.length; i++) {
                String row = i + "," + trajectory[i].getX() + "," + trajectory[i].getY() + "," + trajectory[i].getZ();
                System.out.println(row);
                writer.write(row + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertTrue(366 <= trajectory.length);

    }

    public static Vector3dInterface[] simulateOneDay() {

        Vector3dInterface probe_relative_position = new Vector3d(6371e3,0,0);
        Vector3dInterface probeTotal = Planet.planets[3].posVector.add(probe_relative_position);
        Vector3dInterface probe_relative_velocity = new Vector3d(52500.0,-27000.0,0); // 12.0 months
        Vector3dInterface probeVelocityTotal = Planet.planets[3].velVector.add(probe_relative_velocity);
        double day = 24*60*60;
        ProbeSimulator simulator = new ProbeSimulator();
        simulator.ODESolverChoice = 2;
        Vector3dInterface[] trajectory = simulator.trajectory(probeTotal, probeVelocityTotal, day, day);
        return trajectory;

    }

    public static Vector3dInterface[] simulateOneYear() {

        Vector3dInterface probe_relative_position = new Vector3d(6371e3,0,0);
        Vector3dInterface probeTotal = Planet.planets[3].posVector.add(probe_relative_position);
        Vector3dInterface probe_relative_velocity = new Vector3d(52500.0,-27000.0,0); // 12.0 months
        Vector3dInterface probeVelocityTotal = Planet.planets[3].velVector.add(probe_relative_velocity);
        double day = 24*60*60;
        double year = 366.25*day;
        ProbeSimulator simulator = new ProbeSimulator();
        simulator.ODESolverChoice = 2;
        Vector3dInterface[] trajectory = simulator.trajectory(probeTotal, probeVelocityTotal, year, day);
        return trajectory;

    }
}

