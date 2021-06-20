package Landing;

import Landing.Lander;
import Landing.PIDController;

public class ClosedLoopController {
<<<<<<< HEAD
    public final double GRAVITY = 1.352; // Gravity of Titan

    public final double ACCURACY_ANGLE_VEL = 0.01; // derivative of Angle - angular velocity
    public final double ACCURACY_ANGLE = 0.02; // |angle mod 2pi|
    public final double ACCURACY_X = 0.1; // x value
    public final double ACCURACY_X_VEL = 0.01; // derivative of x - x velocity
    public final double ACCURACY_Y_VEL = 0.01; // derivative of y - y velocity

    public ClosedLoopController ()
    {
        // Constructor
    }

    public boolean testX (double x)
    {
        return Math.abs(x) <= ACCURACY_X;
    }
    public boolean testTheta (double angle)
    {
        return Math.abs(angle) <= ACCURACY_ANGLE;
    }
    public boolean testXVel (double xVel)
    {
        return Math.abs(xVel) <= ACCURACY_X_VEL;
    }
    public boolean testYVel (double yVel)
    {
        return Math.abs(yVel) <= ACCURACY_Y_VEL;
    }

    // did we already reach the final state?
    /**
     * test angular velocity against the derivative given
     * @param angleVel angular velocity
     * @return true if condition reached
     */
    public boolean testThetaDerivative (double angleVel)
    {
        return Math.abs(angleVel) <= ACCURACY_ANGLE_VEL;
    }


}


/*
* double nextStep = 86400.00/1000.00;
    Lander lander;
    PIDController pidController;

    public double dist()
    {
        double pythagoras = (Math.pow(lander.posX.getLast(), 2) + Math.pow(lander.posY.getLast(), 2));
        return Math.sqrt(pythagoras);
    }

    //
    // rotation the Probe to have the main thruster pushing the landing module.
    public double rotate( double desAngle)
    {
        double updatedAngle;
        double angle = lander.posDeg.getLast();

        if (angle == desAngle)
        {
            return angle;
        }
        // this is for the final time step to make sure to get it to correct angle
        else if (angle != Math.abs(lander.ANGLE_END + lander.ACCURACY_ANGLE) && Math.abs(lander.posX.getLast()) <= (lander.X_END + lander.ACCURACY_X))
        {
            lander.step = Math.sqrt(Math.abs(angle/2)/lander.getA(lander.posX.size() - 1));
            updatedAngle = angle/2 ; // do something on the angle
            lander.addAngle(updatedAngle);
        }
        else {
            lander.step = Math.sqrt(Math.abs(angle/2)/lander.getA(lander.posX.size() - 1));
            updatedAngle = angle;
            lander.addAngle(updatedAngle);
        }

        return updatedAngle;
    }

    public void nextStep (double desAngle)
    {
        double angle = rotate(desAngle);

        lander.addX(lander.xPos + (lander.xPos * Math.sin(angle)));

        lander.addY(lander.yPos + (lander.yPos * Math.cos(angle)));
    }



    public void one ()
    {
        // rotational velocity and time needed for it.
        // v = ((thetaDesired - theta)/ (timescale^2)) - theta'/timscale

    }
*/
=======

    double nextStep = 86400.00/1000.00;
    Lander lander;
    PIDController pidController;

    public double dist()
    {
        double pythagoras = (Math.pow(lander.posX.getLast(), 2) + Math.pow(lander.posY.getLast(), 2));
        return Math.sqrt(pythagoras);
    }

    //
    // rotation the Probe to have the main thruster pushing the landing module.
    public double rotate( double desAngle)
    {
        double updatedAngle;
        double angle = lander.posDeg.getLast();

        if (angle == desAngle)
        {
            return angle;
        }
        // this is for the final time step to make sure to get it to correct angle
        else if (angle != Math.abs(lander.ANGLE_END + lander.ACCURACY_ANGLE) && Math.abs(lander.posX.getLast()) <= (lander.X_END + lander.ACCURACY_X))
        {
            lander.step = Math.sqrt(Math.abs(angle/2)/lander.getA(lander.posX.size() - 1));
            updatedAngle = angle/2 ; // do something on the angle
            lander.addAngle(updatedAngle);
        }
        else {
            lander.step = Math.sqrt(Math.abs(angle/2)/lander.getA(lander.posX.size() - 1));
            updatedAngle = angle;
            lander.addAngle(updatedAngle);
        }

        return updatedAngle;
    }

    public void nextStep (double desAngle)
    {
        double angle = rotate(desAngle);

        lander.addX(lander.xPos + (lander.xPos * Math.sin(angle)));

        lander.addY(lander.yPos + (lander.yPos * Math.cos(angle)));
    }



    public void one ()
    {
        // rotational velocity and time needed for it.
        // v = ((thetaDesired - theta)/ (timescale^2)) - theta'/timscale

    }







}
>>>>>>> 8290e5f (Add files via upload)
