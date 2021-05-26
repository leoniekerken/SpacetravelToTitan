package rocketThrust;

import simulator.Vector3d;
import simulator.Planet;
import simulator.Simulator;
import titan.Vector3dInterface;

public class MassFlowRate {
    /** @param mass must be one of the parameters. In this case, the initial mass of the spacecraft is sufficient
     *  @param acceleration is required. The acceleration can be found from the vector3d class
     *  @return mass flow rate by calculating subject of the formula F=ma, f= dm+ V_e +(pe -p0)Ae {pe-p0=0}
     *  */

    final static double MASS_SPACECRAFT=78000; // value of the spacecraft only, without any fuel.
    double remainingFuel;
    static double eV=2e4;
    static double power = 3e7;
    static double mass;
    static Vector3dInterface acceleration;


    //At first had the params as Vector3d velocity values, but treating in scalar, so assumed that starting v is 0 and final v is 60kms for the moment.
    public static Vector3dInterface getAcceleration( Vector3dInterface initialVelocity, Vector3dInterface finalVelocity, double time_interval){
        acceleration =  (finalVelocity.sub(initialVelocity)).mul(1/time_interval);
        // acceleration.mul(power);
        return acceleration;
    }

//    public static Vector3d getThrust(Vector3d velocity, double massFlowrate){
//        //Problem, unsure how to find the massFlowrate here.
//        //Also, the Thrust class is slightly misleading, as it finds the velocity, not the net force, since we don't have the mass flow rate yet.
//
//        return velocity.mul(massFlowrate);
//    }

    //These methods may possibly need to go to the simulation class instead.

    public Vector3dInterface calculateNetForce(Vector3dInterface finalVelocity){
        Vector3dInterface accel= getAcceleration(Planet.planets[11].posVector, finalVelocity, Simulator.h);
        this.mass=MASS_SPACECRAFT + remainingFuel;

        this.acceleration= accel;

        return accel.mul(mass);


    //removed the thrust parameter since it's not required here.
    /** @return rate of mass, calculated from F=ma and F_t= Ve * (dm/dt) --> make dm/dt the subject of the formula.*/
    public double getMassFlowRate(Vector3dInterface finalVelocity){
        Vector3dInterface unitFinal = finalVelocity.mul(1/finalVelocity.norm());
        Vector3dInterface force = calculateNetForce(unitFinal.mul(eV));

        return force.mul(1/unitFinal.norm());
    }
        
    public void setfuelMassLoss(double MassFlowrate, double time, double h){
        for( int i=0; i<time; i+=h){
            System.out.println(Massflowrate.mul(i));
        }
    }
       
    //In simularion, need a method that returns the mass required for fuel. I think that we could multiply the mass flowrate
    // by the time interval of one year to solve this, but I don't know if this is correct.


}
