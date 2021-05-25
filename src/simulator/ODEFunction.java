package simulator;

import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;

/**
 * this is the function representing the equations of motion
 *
 * first, we derive dx = v
 *
 * then, we derive dv = acceleration
 * acceleration is calculated based on the position of all objects relative to each other using Newton's Law of Gravitation:
 *
 * F = Gm(i)m(j)*(pos(i)-pos(j)/|pos(i)-pos(j)|^3
 * because
 * m(i)a(i) = F(i)
 * we can eliminate m(i), so that
 * a(i) = Gm(j)*(pos(i)-pos(j)/|pos(i)-pos(j)|^3
 *
 * @author Leo
 */

public class ODEFunction implements ODEFunctionInterface{

    static boolean DEBUG = false;
    private static final double G = 6.6743015e-11;  //Gravitational constant
    static int n = Planet.planets.length;

    public Rate rate = new Rate();

    /**
     * calling the function at time t
     *
     * @param t = time at which function is evaluated
     * @param y = state of the system at time t
     *
     * @return rate of change for each object
     */

    @Override
    public RateInterface call(double t, StateInterface y) {

        rate.initialize(n);

        if(DEBUG){
            System.out.println("\nsimulator.ODEFunction - rate before update\n" + rate.toString());
        }

        //get dx = v

        for(int i = 0; i < n; i++){
            Vector3d velocity = (Vector3d) ((State) y).getVel(i);
            rate.addVelocity(i, velocity);
        }

        //get dx = acceleration

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    //calculate acceleration from the attraction of two objects
                    Vector3d acc = (Vector3d) (((((State) y).getPos(j).sub(((State) y).getPos(i))).mul(1 / (Math.pow(((State) y).getPos(i).dist(((State) y).getPos(j)), 3))))).mul(G * Planet.planets[j].mass);
                    //add calculated acc to total acceleration of object i
                    rate.addAcceleration(i, acc);
                }
            }
        }

        if(DEBUG){
            System.out.println("\nsimulator.ODEFunction - rate after update\n" + rate.toString());
        }

        return rate;

    }
}
