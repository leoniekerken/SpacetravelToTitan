package rocketThrust;

import simulator.Vector3d;

public class MassFlowRate {
    /** @param mass must be one of the parameters. In this case, the initial mass of the spacecraft is sufficient
     *  @param acceleration is required. The acceleration can be found from the vector3d class
     *  @return mass flow rate by calculating subject of the formula F=ma, f= dm+ V_e +(pe -p0)Ae {pe-p0=0}
     *  */

    final static double MASS_SPACECRAFT=78000; // value of the spacecraft only, without any fuel.
    static double eV=20000;


    //At first had the params as Vector3d velocity values, but treating in scalar, so assumed that starting v is 0 and final v is 60kms for the moment.
    public static double getAcceleration( double initialVelocity, double finalVelocity, double time_interval){
        return (finalVelocity- initialVelocity)/time_interval;
    }

    public static double getThrust(double velocity, double massFlowrate){
        //Problem, unsure how to find the massFlowrate here.
        //Also, the Thrust class is slightly misleading, as it finds the velocity, not the net force, since we don't have the mass flow rate yet.

        return velocity* massFlowrate;
    }

    //These methods may possibly need to go to the simulation class instead.

    public double calculateNetForce(double mass, double acceleration){
        double massVal= MASS_SPACECRAFT;
        double accel= getAcceleration();
        this.mass=massVal;
        this.acceleration= accel;

        return massVal*accel;
    }

    //removed the thrust parameter since it's not required here.
    /** @return rate of mass, calculated from F=ma and F_t= Ve * (dm/dt) --> make dm/dt the subject of the formula.*/
    public double getMassFlowRate(double exhaustVelocity, double force){

        double velocity=eV;
        this.exhaustiveVelocity= velocity;
        double netForce= calculateNetForce();
        this.force= netForce;

        return  netForce/velocity;
    }
    //In simularion, need a method that returns the mass required for fuel. I think that we could multiply the mass flowrate
    // by the time interval of one year to solve this, but I don't know if this is correct.


}
