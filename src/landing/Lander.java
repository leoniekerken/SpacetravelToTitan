package landing;

import java.util.LinkedList;

public class Lander {
    public static boolean INITIAL_MOVING = false;
    // Gravity of Titan 1.352 m s^-2
    public static final double GRAVITY = 1.352;

    // latest position updates
    public double xPos;
    public double yPos;
    public double angle;

    // timing holders
    public double time; // time taken in total
    public double step; // the next step size
    public LinkedList<Double> timeSteps; // used to have alwasy the current timestep in mind.
    final double stepDefault = 86400.00/1000; // to have this saved

    public LinkedList<Double> posX;
    public LinkedList<Double> posY;
    public LinkedList<Double> posDeg;

    // Accuracy specified by the Examiners of the Titan Landing site
    public final double ACCURACY_ANGLE1 = 0.01; // derivative of Angle
    public final double ACCURACY_ANGLE = 0.02; // |angle mod 2pi|
    public final double ACCURACY_X = 0.1; // x value
    public final double ACCURACY_X1 = 0.01; // derivative of x
    public final double ACCURACY_Y1 = 0.01; // derivative of y

    public final double DRAG = 4;
    public final double DRAG_C = 1.00/300;

    public final double MASS = 6000; // KG

    // the End position of Landing module
    public final double X_END = 0;
    public final double Y_END = 0;
    public final double ANGLE_END = 0;

    public Lander ()
    {
        posX = new LinkedList<>();
        posY = new LinkedList<>();
        posDeg = new LinkedList<>();
        timeSteps = new LinkedList<>();
        // starting position adn velocity
        start();
    }

    private void start()
    {
        if (!INITIAL_MOVING)
        {
            double randX = 160000 + (Math.random() * 15000);
            double randY = 270000 + (Math.random() * 15000);
            addAngle(0);
            addAngle(0);
            addX(randX);
            addX(randX);
            addY(randY);
            addY(randY);

        }
        else
        {

            addAngle(0);
            addAngle(0);
            addX(160000 + (Math.random() * 15000));
            addX(160000 + (Math.random() * 15000));
            addY(270000 + (Math.random() * 15000));
            addY(posY.getFirst() - Math.random() * 5);
        }
        timeSteps.add(0.0);
        timeSteps.add(1.0);
    }

    // changed the angle and now it is a new one
    public void addAngle (double angle)
    {
        if (posDeg == null)
        {
            posDeg = new LinkedList<>();
        }
        if (timeSteps == null)
        {
            timeSteps = new LinkedList<>();
        }
//        if (timeSteps.getLast() != time)
//        {
//            timeAdd();
//        }

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
        // timeSteps.add(step);
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
        // return ((Math.sqrt(Math.pow(vel12,2)) + Math.pow(vel23,2)) / (timeSteps.get(posTime) - timeSteps.get(posTime - 1)));
        return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * (timeSteps.get(posTime) - timeSteps.get(posTime - 2)));
        // return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * Math.pow((timeSteps.get(posTime) - timeSteps.get(posTime - 1)), 2)); // one of these it should be.
        // return (vel12 - vel23) / (posX.get(posTime) - posX.get(posTime - 2)) / ((timeSteps.get(posTime) - timeSteps.get(posTime - 1))/2); // highly doubt its this one
    }
    public double getA ()
    {
        int posTime = this.timeSteps.size() - 1;
        double vel12 = getV(posTime);
        double vel23 = getV(posTime - 1);
        // one of them is correct but I am not fully sure.
        // return ((Math.sqrt(Math.pow(vel12,2)) + Math.pow(vel23,2)) / (timeSteps.get(posTime) - timeSteps.get(posTime - 1)));
        return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * (timeSteps.get(posTime) - timeSteps.get(posTime - 2)));
        // return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * Math.pow((timeSteps.get(posTime) - timeSteps.get(posTime - 1)), 2)); // one of these it should be.
        // return (vel12 - vel23) / (posX.get(posTime) - posX.get(posTime - 2)) / ((timeSteps.get(posTime) - timeSteps.get(posTime - 1))/2); // highly doubt its this one
    }

    // getting the velocity of the landing craft
    public double getV (int posTime)
    {
        if ((posX.get(posTime) - posX.get(posTime - 1)) == 0)
        {
            return getVX(posTime) ;
        }
        else if ((posY.get(posTime) - posY.get(posTime - 1)) == 0)
        {
            return getVY(posTime);
        }
        //return //((Math.sqrt(Math.pow((posY.get(posTime) - posY.get(posTime - 1)),2)) + Math.pow((posX.get(posTime) - posX.get(posTime - 1)),2)) / (timeSteps.get(posTime) - timeSteps.get(posTime - 1)));
        return (posY.get(posTime) - posY.get(posTime - 1)) / ((posX.get(posTime) - posX.get(posTime - 1)) * step);
    }

    public double getV ()
    {
        int posTime = this.timeSteps.size() - 1;
        if ((posX.get(posTime) - posX.get(posTime - 1)) == 0)
        {
            return getVY();
        }
        else if ((posY.get(posTime) - posY.get(posTime - 1)) == 0)
        {
            return getVX();
        }
        return (posY.get(posTime) - posY.get(posTime - 1)) / ((posX.get(posTime) - posX.get(posTime - 1)) * step);
    }

    public double getVX (int posTime)
    {
        return (posX.get(posTime) - posX.get(posTime - 1)) / getStep(posTime);
    }

    public double getVX ()
    {
        return (posX.get(timeSteps.size() - 1) - posX.get( timeSteps.size() - 2)) / getStep();
    }

    public double getVY (int posTime)
    {
        return (posY.get(posTime) - posY.get(posTime - 1)) / getStep();
    }

    public double getVY ()
    {
        return (posY.get(timeSteps.size() - 1) - posY.get(timeSteps.size() - 2)) / getStep();
    }

    public double getAX (int posTime)
    {
        return Math.cos(angle) * getA((posTime));
    }

    public double getAY (int posTime)
    {
        return Math.sin(angle) * getA((posTime));
    }

    public double getAX ()
    {
        return Math.cos(angle) * getA(timeSteps.size() - 1);
    }

    public double getAY ()
    {
        return Math.sin(angle) * getA((timeSteps.size() - 1));
    }

    private double timeAdd()
    {
        time = timeSteps.getLast() + step;
        timeSteps.add(time);
        return timeSteps.getLast();
    }

    public double getStep()
    {
        return timeSteps.getLast() - timeSteps.get(timeSteps.size() - 2);
    }

    public double getStep(int posTime)
    {
        return timeSteps.get(posTime) - timeSteps.get(posTime - 1);
    }

    public void setStep(double stepSize)
    {
        timeSteps.add(timeSteps.getLast() + stepSize);
    }

    public void setStep()
    {
        timeSteps.add(timeSteps.getLast() + stepDefault);
    }

    // after changing the angle we need to go back to the normal stepsize.
    // wont be needed anymore...
    public void resetStep ()
    {
    step = stepDefault;
    }

    /**
     * Test coordinate x against its tolerance value
     * @return true if x is allowed for its tolerance and false if not
     */
    public boolean testX() {
        return Math.abs(posX.getLast()) <= ACCURACY_X;
    }

    /**
     * Test theta against its tolerance value
     * @return true if theta is allowed for its tolerance and false if not
     */
    public boolean testTheta() {
        return Math.abs(posDeg.getLast()) <= ACCURACY_ANGLE;
    }

    /**
     * Test x-coordinate of the velocity against its tolerance value
     * @return true if the x-coordinate is allowed for its tolerance and false if not
     */
    public boolean testXVel() {
        return Math.abs(getVX()) <= ACCURACY_X1;
    }

    /**
     * Test y-coordinate of the velocity against its tolerance value
     * @return true if the y-coordinate is allowed for its tolerance and false if not
     */
    public boolean testYVel() {
        return Math.abs(getVY()) <= ACCURACY_Y1;
    }

    /**
     * Test the derivative of theta against its tolerance value
     * @return true if the derivative of theta is allowed for its tolerance and false if not
     */
    public boolean testAngleVel() {
        return Math.abs(posDeg.getLast()) == ACCURACY_ANGLE1;
    }

    public boolean accuracy ()
    {
        return testX() && testTheta() && testXVel() && testYVel() && testAngleVel();
    }

}
