package Landing;

import java.util.LinkedList;

public class Lander {

    // latest position updates
    public double xPos;
    public double yPos;
    public double angle;

    // timing holders
    public double time; // time elapsed up until now
    public double step; // the next step size
    // public LinkedList<Double> stepSizes; // dont think i need this but safty first
    final double stepDefault = 86400/1000; // to have this saved

    public LinkedList<Double> posX;
    public LinkedList<Double> posY;
    public LinkedList<Double> posDeg;

    // public LinkedList<Double> velX;
    // public LinkedList<Double> velY;

    // Gravity of Titan 1.352 m s^-2
    public final double GRAVITY = 1.352;

    // Accuracy specified by the Examiners of the Titan Landing site
    public final double ACCURACY_ANGLE1 = 0.01; // derivative of Angle
    public final double ACCURACY_ANGLE = 0.02; // |angle mod 2pi|
    public final double ACCURACY_X = 0.1; // x value
    public final double ACCURACY_X1 = 0.01; // derivative of x
    public final double ACCURACY_Y1 = 0.01; // derivative of y

    // the End position of Landing module
    public final double X_END = 0;
    public final double Y_END = 0;
    public final double ANGLE_END = 0;

    // after changing the angle we need to go back to the normal stepsize.
    public void resetStep ()
    {
        step = stepDefault;
    }

    // changed the angle and now it is a new one
    public void addAngle (double angle)
    {
        if (posDeg == null)
        {
            posDeg = new LinkedList<>();
        }

        this.angle = angle;
        posDeg.add(angle);
    }

    // updated the position
    public void addX (double x)
    {
        if (posX == null)
        {
            posX = new LinkedList<>();
        }
        // stepSizes.add(step);
        xPos = x;
        posX.add(x);
    }

    // position update
    public void addY (double y)
    {
        if (posY == null)
        {
            posY = new LinkedList<>();
        }

        yPos = y;
        posY.add(y);
    }

    // getting the acceleration form the position
    // second derivative from
    // *NEED VERIFICATION ON RETURN*
    public double getA (int posTime)
    {
        double vel12 = getV(posTime);
        double vel23 = getV(posTime - 1);
        // one of them is correct but I am not fully sure.
        return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * step);
        // return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * Math.pow(step, 2)); // one of these it should be.
        // return (vel12 - vel23) / (posX.get(posTime) - posX.get(posTime - 2)); // highly doubt its this one
    }
    // getting the velocity of the landing craft
    public double getV (int posTime)
    {
        if ((posX.get(posTime) - posX.get(posTime - 1)) == 0)
        {
            return (posY.get(posTime) - posY.get(posTime - 1)) / step;
        }
        else if ((posY.get(posTime) - posY.get(posTime - 1)) == 0)
        {
            return (posX.get(posTime) - posX.get(posTime - 1)) / step;
        }
        return (posY.get(posTime) - posY.get(posTime - 1)) / ((posX.get(posTime) - posX.get(posTime - 1)) * step);
    }

    public double getVX (int posTime)
    {
        return (posX.get(posTime) - posX.get(posTime - 1)) / step;
    }

    public double getVY (int posTime)
    {
        return (posY.get(posTime) - posY.get(posTime - 1)) / step;
    }

    public double getAX (int posTime)
    {
        return Math.cos(angle) * getA((posTime));
    }

    public double getAY (int posTime)
    {
        return Math.sin(angle) * getA((posTime));
    }
}
