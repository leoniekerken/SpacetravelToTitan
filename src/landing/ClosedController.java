package landing;

import java.util.LinkedList;

/**
 * CLOSED LOOP CONTROLLER
 *
 * @author Oscar
 */
public class ClosedController {
    // random velocity at the beginning
    public static boolean INITIAL_MOVING = false;
    public static boolean DEBUG = false;
    // Gravity of Titan 1.352 m s^-2
    public static final double GRAVITY = 1.352;

    // latest position updates
    public double xPos;
    public double yPos;
    public double angle;

    // timing holders
    public double time; // time taken in total
    public double step; // the next step size
    public LinkedList<Double> timeSteps; // used to have always the current timestep in mind.
    final double stepDefault = 1; // to have this saved  84600.00/1000
    // stores the Positions and angle
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

    // the End position of Landing module Best case
    public final double X_END = 0;
    public final double Y_END = 0;
    public final double ANGLE_END = 0;

    /**
     * constructor of the landing module
     */
    public ClosedController ()
    {
        if (DEBUG) System.out.println("Lander Instance Start");
        posX = new LinkedList<>();
        posY = new LinkedList<>();
        posDeg = new LinkedList<>();
        timeSteps = new LinkedList<>();
        // starting position adn velocity
        start();
        if (DEBUG) System.out.println("Lander Instance End: " + this.toString());
    }
    /**
     * Starting the positioning of the lander.
     * in case of Initial Movement the X Vel can be going towards or away from the landing site,
     * Y Vel can only be towards Titan.*/
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
            addY(posY.getFirst() - Math.random() * 50);
        }
        timeSteps.add(0.0);
        timeSteps.add(1.0);
    }

    /**changed the angle and now it is a new one
     * @param angle angle to be updated to */
    public void addAngle (double angle)
    {
        if (posDeg == null)
        {
            posDeg = new LinkedList<>();
        }
        this.angle = angle;
        posDeg.add(angle);
    }

    /**
     * updated the position X
     * @param x X Position that is now being called on.
     */
    public void addX (double x)
    {
        if (posX == null)
        {
            posX = new LinkedList<>();
        }

        xPos = x;
        posX.add(x);
    }

    /**
     * adding a new Y Position to the LIst and updating the posY to hear on the new value;
     * @param y YPosition*/
    public void addY (double y)
    {
        if (posY == null)
        {
            posY = new LinkedList<>();
        }

        yPos = y;
        posY.add(y);
    }



    /**
     * getting the acceleration form the position
     * second derivative from
     * @param posTime time step to be used (this is the Itterator NOT the TimeStep being Applied
     * @return the Acceleration at that time step.
     */
    public double getA (int posTime)
    {
        double vel12 = getV(posTime);
        double vel23 = getV(posTime - 1);
        return (vel12 - vel23) / ((posX.get(posTime) - posX.get(posTime - 2)) * (timeSteps.get(posTime) - timeSteps.get(posTime - 2)));
    }
    /**
     * getting the acceleration form the position
     * second derivative of Pos
     * @return the Acceleration at the most recent step.
     */
    public double getA ()
    {
        double vel12 = getV();
        double vel23 = getV(posX.size() -1);
        return (vel12 - vel23) / ((posX.getLast() - posX.get(posX.size() - 2)) * getStep());
    }

    /**
     * getting the velocity of the landing craft
     * first derivative of Position
     * @param posTime timeStep Itterator to be used
     * @return the Velocity at the timeStep.
     */
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
        return (posY.get(posTime) - posY.get(posTime - 1)) / ((posX.get(posTime) - posX.get(posTime - 1)) * getStep(posTime));
    }
    /**
     * getting the velocity of the landing craft
     * first derivative from position
     * @return the Velocity at the timeStep.
     */
    public double getV ()
    {
        if ((posX.getLast() - posX.get(posX.size() - 1)) == 0)
        {
            return getVY();
        }
        else if ((posY.getLast() - posY.get(posY.size() - 1)) == 0)
        {
            return getVX();
        }
        return (posY.getLast() - posY.get(posY.size() - 1)) / ((posX.getLast() - posX.get(posX.size() - 1)) * getStep());
    }

    /**
     * getting the velocity of the landing craft In the X direction
     * first derivative from position
     * @param posTime timeStep Itterator to be used
     * @return the X Velocity at the timeStep.
     */
    public double getVX (int posTime)
    {
        return (posX.get(posTime) - posX.get(posTime - 1)) / getStep(posTime);
    }
    /**
     * getting the x velocity of the landing craft
     * first derivative from
     * @return the X Velocity at the last version.
     */
    public double getVX ()
    {
        return (posX.get(posX.size() - 1) - posX.get( posX.size() - 2)) / getStep();
    }

    /**
     * getting the Y velocity of the landing craft
     * first derivative from
     * @param posTime timeStep Itterator to be used
     * @return the Y Velocity at the timeStep.
     */
    public double getVY (int posTime)
    {
        return (posY.get(posTime) - posY.get(posTime - 1)) / getStep();
    }
    /**
     * getting the velocity in Y direction of the landing craft
     * first derivative from
     * @return the Y Velocity at the latest step.
     */
    public double getVY ()
    {
        return (posY.get(posY.size() - 1) - posY.get(posY.size() - 2)) / getStep();
    }

    /**
     * getting the angular velocity of the landing craft
     * first derivative from
     * @param posTime timeStep Itterator to be used
     * @return the angular Velocity at the timestep.
     */
    public double getVA (int posTime)
    {
        return (posDeg.get(posTime) - posY.get(posTime - 1)) / getStep(posTime);
    }

    /**
     * getting the velocity of the landing craft
     * first derivative from
     * @return the Velocity at the timeStep.
     */
    public double getVA ()
    {
        return (posDeg.get(posDeg.size() - 1) - posDeg.get(posDeg.size() - 2)) / getStep();
    }

    /**
     * getting the XAcceleration of the landing craft
     * Second derivative from position
     * @param posTime timeStep Itterator to be used
     * @return the XAcceleration at the timeStep.
     */
    public double getAX (int posTime)
    {
        return Math.cos(angle) * getA((posTime));
    }

    /**
     * getting the Y_ACC of the landing craft
     * second derivative from position
     * @param posTime timeStep Itterator to be used
     * @return the Y-Acc at the timeStep.
     */
    public double getAY (int posTime)
    {
        return Math.sin(angle) * getA((posTime));
    }

    /**
     * getting the X_ACC of the landing craft
     * second derivative from position
     * @return the X-Acc at the most recent timeStep.
     */
    public double getAX ()
    {
        return Math.cos(angle) * getA();
    }
    /**
     * getting the Y_ACC of the landing craft
     * second derivative from position
     * @return the Y-Acc at the latest timeStep.
     */
    public double getAY ()
    {
        return Math.sin(angle) * getA();
    }

    /**
     * the most recent time step size
     * @return stepSize */
    public double getStep()
    {
        return timeSteps.getLast() - timeSteps.get(timeSteps.size() - 2);
    }
    /**
     * the time step at the itterator posTiem
     * @param posTime itterative value
     * @return StepSize
     * */
    public double getStep(int posTime)
    {
        return timeSteps.get(posTime) - timeSteps.get(posTime - 1);
    }

    /**Add a new time step to the system.
     * @param stepSize to be used
     */
    public void setStep(double stepSize)
    {
        timeSteps.add(timeSteps.getLast() + stepSize);
    }
    /**
     * adding the default step size
     */
    public void setStep()
    {
        timeSteps.add(timeSteps.getLast() + stepDefault);
    }

    /**
     * after changing the angle we need to go back to the normal stepsize.
     * wont be needed anymore...
     * */
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
        return Math.abs((posDeg.getLast() - posDeg.get(posDeg.size() - 2))/ getStep()) == ACCURACY_ANGLE1;
    }
    /**
     * simplify the calling of the test Accuracy mathods
     * @return ture if all came back true else false*/
    public boolean accuracy ()
    {
        return testX() && testTheta() && testXVel() && testYVel() && testAngleVel();
    }

    /** to string of the Positions
     * @return string of the Positions*/
    public String toString()
    {
        return "X: " + posX.getLast() + ", Y: " + posY.getLast() + ", Theta: " + posDeg.getLast();
    }
    /**
     * String of all nessisary information
     * @return pso and Vel of the Landing craft*/
    public String toFullString()
    {
        return "X: " + posX.getLast() + ", Y: " + posY.getLast() + ", Theta: " + posDeg.getLast() + ", X vel: "
                + getVX() + ", Y vel: " + getVY() + ", Angle Vel: " + getVA();
    }
}
