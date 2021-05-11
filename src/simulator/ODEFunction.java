package simulator;

import titan.ODEFunctionInterface;
import titan.RateInterface;
import titan.StateInterface;

/**
 * class calculating acceleration using Newton's Law of Gravitation
 *
 * @author Leo
 */
public class ODEFunction implements ODEFunctionInterface{

    public static boolean DEBUG = false;
    private static final double G = 6.6743015e-11;  //Gravitational constant

    public Rate rate = new Rate();

    /**
     * calculating acceleration of an object using Newton's Law of Gravitation:
     *
     * F = Gm(i)m(j)*(pos(i)-pos(j)/|pos(i)-pos(j)|^3
     * because
     * m(i)a(i) = F(i)
     * we can eliminate m(i), so that
     * a(i) = Gm(j)*(pos(i)-pos(j)/|pos(i)-pos(j)|^3
     *
     * @param t
     * @param y =  state of the system
     *
     * @return rate of change that is acceleration for each objects
     */

    @Override
    public RateInterface call(double t, StateInterface y) {

        rate.initialize(Planet.planets.length);

        if(DEBUG){
            System.out.println("\nsimulator.ODEFunction - rate before update\n" + rate.toString());
        }

        //iterate over all objects
        for(int i = 0; i < Planet.planets.length; i++) {
            //all other objects
            for (int j = 0; j < Planet.planets.length; j++) {
                if (i != j) {
                    //calculate acceleration from the attraction of two objects
                    Vector3d acc = (Vector3d) (((((State) y).getPos(j).sub(((State) y).getPos(i))).mul(1 / (Math.pow(((State) y).getPos(i).dist(((State) y).getPos(j)), 3))))).mul(G * Planet.planets[j].mass);
                    //add calculated acc to total acceleration of object i
                    rate.add(i, acc);
                    if(DEBUG){
                        System.out.println("simulator.ODEFunction - acc " + acc.toString());
                        System.out.println("simulator.ODEFunction - rate " + rate.get(i).toString());
                    }
                }
            }
        }

        if(DEBUG){
            System.out.println("\nsimulator.ODEFunction - rate after update\n" + rate.toString());
        }

        return rate;
    }
}
