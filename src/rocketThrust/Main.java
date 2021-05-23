package rocketThrust;

import simulator.Vector3d;
import titan.Vector3dInterface;

/**
 * PROBE SIMULATOR
 *
 * @author chiara
 */
public class Main {

    public static void main(String[] args) {

        Thrust t = new Thrust();
        MultivariableNewton newton = new MultivariableNewton();

        Vector3dInterface p0 = new Vector3d(-1.471868229554755E11, -2.8606557057938354E10, 8287486.0632270835); //initial position here
        Vector3dInterface v0 = new Vector3d(30503.316321875955, -62503.59520115846, -621.7444409637209);        //initial velocity here

        Vector3dInterface updatedvelocity = new Vector3d();
        Vector3dInterface currentVelocity = new Vector3d();
        //Vector3dInterface v0 = new Vector3d();                  //Initial velocity of the probe

        Vector3dInterface pf = new Vector3d(6.332873118527889e+11,  -1.357175556995868e+12,  2.134637041453660e+09);                  //Final position to be reached (300 km from titan = titan's orbit)
        Vector3dInterface pk = p0;                  //Current position of the probe (Initialize it with the starting position of the probe)

        Vector3dInterface g = new Vector3d();                   //pf - pk (Should be as close to a zero vector as possible

        System.out.println("ROCKET SIMULATION");
        System.out.println();
        System.out.println();
        System.out.println("initial position of the probe: " + pk.toString());
        System.out.println("initial velocity of the probe: " + v0.toString());
        System.out.println("final destination of the probe: " + pf.toString());

        //First evaluation of the formula g
        g = t.computeFormula(pf, pk);

        //First evaluation of the updated velocity with the initial velocity of the probe
        updatedvelocity = newton.doMultivariableNewton(g, v0);

        System.out.println("g(Vk: " + g.toString());
        System.out.println();
        System.out.println("updated velocity: " + updatedvelocity.toString());
        System.out.println();
        System.out.println();

        //While we are close to Titan keep updating data
        while(t.closeEnough(g) == false) {
            pk = t.updateCurrentPosition();
            g = t.computeFormula(pf, pk);
            updatedvelocity = newton.doMultivariableNewton(g, currentVelocity);

            System.out.println("updated position of the probe: " + pk.toString());
            System.out.println("final destination of the probe: " + pf.toString());
            System.out.println("g(Vk): " + g.toString());
            System.out.println("updated velocity V(k+1): " + updatedvelocity.toString());
            System.out.println();
            System.out.println();
        }
    }
}