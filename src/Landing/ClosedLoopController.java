package Landing;

import Landing.Lander;
import Landing.PIDController;

public class ClosedLoopController {

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
