package landing;

/**
 * Class used to perform a landing on Titan
 *
 * @author Oscar
 */

public class Lander {
    public ClosedController closedController;
    public WindModel wind;
    double g = 1.352;
    double dragCoefficient = 1.00/300;
    double  torque = 50;
    double desiredAngle;
    double force = 1e-20;
    double external;
    boolean flag = false;
    public boolean reachedEnd = false;
    public static boolean DEBUG = false;

    /** Constructor of the Lander (closed Loop process)*/
    public Lander()
    {
        closedController = new ClosedController();
        wind = new WindModel();
    }
    /**Loop over the Closed Loop and gets onto titan*/
    public void reachTitan ()
    {
        int count = 0;
        while (!reachedEnd) {
            experimental();
            if (DEBUG) {
                System.out.println("Loop " + (count++) + ": " + closedController.toString());
            }
        }
    }

    /** Executed if we need to rotate
     * No Param since this is teh default
     * @return time needed to rotate the craft*/
    private double rotation (){
        return  angularAccelerate(desiredAngle);
    }

    /**
     * calcs time needed to rotate landing module
     * @param desAngle angle that is requested
     * @return time needed to execute*/
    private double angularAccelerate(double desAngle) {
        // perform two time steps where 1 step = time
        // after 1 time step the craft is rotated halfway
        double angle = Math.abs(closedController.posDeg.getLast() - desAngle);

        double time =  2 * Math.sqrt(angle / torque);
        //change time step to time
        closedController.addAngle(desiredAngle);
        printTest();
        return time;
    }


    /**
     * calculate the external forces that act on the system
     */
    private void externalForce ()
    {
        double windA = wind.evalVel(closedController.posY.getLast()) / force;

        if(closedController.getVX() > 0 ){
            external = -Math.pow((closedController.getVX() - windA),2) * closedController.DRAG_C;
        } else {
            external = Math.pow((closedController.getVX() - windA),2) * closedController.DRAG_C;
        }
    }

    /**All major cases that need to be handled and the exit conditions are in this method*/
    private void backup()
    {

        if (closedController.posX.getLast() > 0 && closedController.getVX() > 0)
        {
            closedController.addX(closedController.posX.getLast() - (closedController.getVX() * closedController.getStep()));
        }

        if (closedController.posY.getLast() > 0 && closedController.getVY() > 0)
        {
            closedController.addY(closedController.posY.getLast() - (closedController.getVY() * closedController.getStep()));
        }

        if (closedController.posY.getLast() < 150000)
        {
            double grav = g *10;
            force = -(grav * (1 - Math.pow(Math.E, (closedController.posY.getLast()/grav))));
        }
        if (closedController.posY.getLast() <= closedController.ACCURACY_Y1)
        {

            closedController.addAngle(desiredAngle);
            reachedEnd = true;
        }

        //stepSize reduction
        if (closedController.posY.getLast() > 100 && closedController.step != closedController.stepDefault)
        {
            closedController.resetStep();
            if (DEBUG) System.out.println("Step" + closedController.getStep());
        }
        else if (closedController.posY.getLast() <= 10)
        {
            closedController.step = 0.0125;
            closedController.setStep(closedController.step);
            if (DEBUG) System.out.println("Step" + closedController.getStep());
        }
        else if (closedController.posY.getLast() <= 20)
        {
            closedController.step = 0.025;
            closedController.setStep(closedController.step);
            if (DEBUG) System.out.println("Step" + closedController.getStep());
        }
        else if (closedController.posY.getLast() <= 50)
        {
            closedController.step = 0.125;
            closedController.setStep(closedController.step);
            if (DEBUG) System.out.println("Step" + closedController.getStep());
        }
        else if (closedController.posY.getLast() <= 100)
        {
            closedController.step = 0.25;
            closedController.setStep(closedController.step);
            if (DEBUG) System.out.println("Step" + closedController.getStep());
        }
        else if (closedController.posY.getLast() <= 200)
        {
            closedController.step = 0.5;
            closedController.setStep(closedController.step);
            if (DEBUG) System.out.println("Step" + closedController.getStep());
        }
        casE();
    }
    /**in case of DEBUG it prints the current infos*/
    private void printTest()
    {
        if (DEBUG)
        {
            System.out.println("External: "+ external);
            System.out.println("step" + closedController.getStep());
            System.out.println("VX:" +closedController.getVX());
        }
    }
    /**Main execution method*/
    public void experimental(){
        backup();
        if(reachedEnd)
        {
            return;
        }
        // else we try to get to 0
        double thrust = 1;
        double sign = Math.signum(closedController.posX.getLast()) * - 1;
        // external forces = wind, drag
        externalForce();
        if(closedController.posY.getLast() == 0 && closedController.accuracy())
        {
            closedController.addAngle(desiredAngle);
            reachedEnd = true;
            return;
        }
        else if (closedController.posY.getLast() == 0)
        {
            reachedEnd = true;
            if (DEBUG) System.out.println("reached the ground but not the Landing Site");
            return;
        }

        if(Math.abs(closedController.posX.getLast()) > 4000 ){
            desiredAngle = 80 * sign;
        } else if (Math.abs(closedController.posX.getLast()) > 5 && Math.abs(closedController.posX.getLast()) <= 4000){
            desiredAngle = sign * (Math.abs(closedController.posX.getLast()) / 100) + 5;
        } else {
            desiredAngle = Math.abs(closedController.posX.getLast()) * sign;
        }

        if(closedController.posY.getLast() > 7000 && closedController.getVY() > -50){
            thrust = g/Math.cos(closedController.posDeg.getLast()) / 2;
        } else if(closedController.posY.getLast() < 7000 || closedController.getVY() <= -50){
            thrust = g/Math.cos(closedController.posDeg.getLast());
        }

        if(Math.abs(closedController.posX.getLast()) <= closedController.ACCURACY_X){
            desiredAngle = 0;
            if(closedController.getVY() <= -50) {
                thrust = (Math.pow(closedController.getVY(), 2) / (2 * closedController.posY.getLast())) + g;
            } else {
                thrust = 0;
            }
        }
        //update them in case
        externalForce();

        timestep(thrust);

    }

    /**
     * case Emergency -> if the Code runs into an issue this it what is to be done.
     * */
    private void casE()
    {
        // in the end there is a possibility that it goes mad and changes the vals because it just wants to.
        if (closedController.posY.getLast() < 0 || closedController.posY.getLast().isNaN() || closedController.posY.getLast().isInfinite())
        {
            int i = closedController.posY.size() -1;
            while (closedController.posY.get(i).isNaN() || closedController.posY.get(i).isInfinite()) {
                i --;
            }
            closedController.addY(closedController.posY.get(i));
            closedController.addY(closedController.posY.get(i));
        }

        if (closedController.posDeg.getLast().isNaN() || closedController.posDeg.getLast().isInfinite())
        {
            int i = closedController.posDeg.size() -1;
            while (closedController.posDeg.get(i).isNaN() || closedController.posDeg.get(i).isInfinite()) {
                i--;
            }
            closedController.addAngle(closedController.posDeg.get(i - 1));
            closedController.addAngle(closedController.posDeg.get(i));
        }

        if (closedController.posX.getLast().isNaN() || closedController.posX.getLast().isInfinite() || closedController.posX.getLast()> 1e8)
        {
            //closedController.addX(0);
            //closedController.addX(0);
            int i = closedController.posX.size() -1;
            while (closedController.posX.get(i).isNaN() || closedController.posX.get(i).isInfinite()) {
                i--;
            }
            closedController.addX(0);
            closedController.addX(0);
        }
    }

    /**This is the time step to execute the acceleration of the craft.
     * @param thrust thrust amount (force)
     */
    public void timestep(double thrust){
        double timestep = closedController.getStep();
        double rot = rotation();
        if(rot > timestep){
            timestep = rot;
        }

        if (DEBUG) System.out.println("thrust : " + thrust);
        //update x
        double accX = Math.sin(closedController.posDeg.getLast()) * thrust + external;
        double velX = accX * timestep + closedController.getVX();
        closedController.addX(velX * timestep + closedController.posX.getLast());
        if(DEBUG)
        {
            System.out.println("acc : " + accX);
            System.out.println("vel : " + velX);
        }
        //update y
        double accY = Math.cos(closedController.posDeg.getLast()) * thrust - g;
        double velY = closedController.getVY() + (accY * timestep);
        closedController.addY(closedController.posY.getLast() + velY * closedController.getStep());
    }
}