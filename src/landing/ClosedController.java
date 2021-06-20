package landing;

public class ClosedController {
    Lander lander;
    WindModel wind;
    double g = 1.352;
    double dragCoefficient = 1.00/300;
    double  torque = 50;
    double desiredAngle;
    double external;
    boolean flag = false;
    public boolean reachedEnd = false;
    public static boolean DEBUG = true;

    public ClosedController()
    {
        lander = new Lander();
        wind = new WindModel();
    }

    public void reachTitan ()
    {

        int count = 0;
        while (!reachedEnd)
        {

            ifLoop();
            if(DEBUG)
            {
                System.out.println("Loop " + (count++) + ": " + lander.toString());
                //count = printLoop(count);
            }
        }
        if (DEBUG) System.out.println("final " + "Loop " + count + ": " + lander.toFullString());
    }


    public void check(){
        if(Math.signum(lander.posX.getLast()) * Math.signum(lander.getVX()) == -1 ){
            flag = true;
        } else {
            flag = false;
        }
   }


    public void ifLoop() {
        backup();
        desiredAngle = 0;
        angleCalc();
        if (DEBUG && reachedEnd) System.out.println("END: " +lander.toString());
        externalForce();
        if(lander.posY.getLast() == 0 && lander.accuracy())
        {
            lander.addAngle(desiredAngle);
            reachedEnd = true;
            return;
        }
        else if (lander.posY.getLast() == 0)
        {
            reachedEnd = true;
            if (DEBUG) System.out.println("reached the ground but not the Landing Site");
            return;
        }

        // WIND MODEL FOR Y > 7 KM

        if (Math.abs(lander.posX.getLast()) > lander.ACCURACY_X){
            //find difference in angle to angle we want
            //adjust angle
            //calculate thrust
            //calculate velocity it will reach with given thrust
            //calculate distance needed to rotate to 0 and stop x velocity
            /* t0 -> t1 calculate time needed to perform rotation
            *   velocity - wind velocity
            *   velocity divided by 300 = acceleration
            *   actual velocity after rotation will be accelartion * time * velocity at t0
            *   distance traveled is vt0 - vt1 * t / 2
            *   t1 -> t2
            *   average velocity - wind velocity
            *   velocity divided by 300 = acceleration
            *  time needed = -v1 / a
            *  distance needed = v1 * t / 2
            * */
           // if (DEBUG) System.out.println("ACCURACY X");
            if ((lander.posDeg.getLast() - desiredAngle) != 0)
            {
                rotation();
            }
//            else
//            {
//                lander.addAngle(lander.posDeg.getLast());
//            }
            if(Math.abs(lander.posX.getLast()) - distanceToZero()[0] <= 100 && flag){
                // decelerate and rotate to 0
                double timeNeeded = distanceToZero()[1];
                lander.setStep(timeNeeded);
                lander.addAngle(0);
                lander.addX(0);
                lander.addX(0);
                lander.addY(lander.posY.getLast() + (lander.getVY() * lander.getStep()));
                lander.setStep();
                printTest();
                return;

            }
            if (DEBUG) System.out.println("WARNING---------------------: "+ lander.toString());
            horizontal();

        } else {
            // lander X is equal to 0
            if((lander.posDeg.getLast())== 0){
                // calculate thrust needed to slow down to zero until reaching the ground
                // subtract the gravitational force and the drag
                // have wind checked for next itteration

                // only do y directional movement
                vertical();

            } else {
                //find difference in angle to angle we want
                //adjust angle
                desiredAngle = 0;
                rotation();

            }
        }
    }

    private void angleCalc(){
        double sign = Math.signum(lander.posX.getLast()) * - 1;
        if(lander.posY.getLast() >= 45000){
            desiredAngle = 45 * sign;
        } else {
           desiredAngle = lander.posY.getLast() / 1000 * sign;
        }
    }

    private void rotation (){
       if (DEBUG) System.out.println("ROTATION");
        double timeStep = angularAccelerate(desiredAngle);
        lander.setStep(timeStep);
        horizontal();
        printTest();
    }

    private double angularAccelerate(double desAngle){
        // perform two time steps where 1 step = time
        // after 1 time step the craft is rotated halfway
        double angle = Math.abs(lander.posDeg.getLast() - desAngle);

        double time =  2 * Math.sqrt(angle / torque);
        //change time step to time
        lander.addAngle(desiredAngle);
        return time;
    }

    private void vertical()
    {
        double acceleration;
        if(lander.getVY() <= -50) {
            acceleration = (Math.pow(lander.getVY(), 2) / (2 * lander.posY.getLast()));
        } else {
            acceleration = -g;
        }
        double velY = lander.getVY() + (acceleration * (lander.timeSteps.getLast() - lander.timeSteps.get(lander.timeSteps.size() - 2)));
        double positionY = lander.posY.getLast() + velY * lander.getStep();

        // wind account
        double velX = lander.getVX() + (external * lander.getStep());
        lander.addX(velX * lander.getStep() + lander.posX.getLast());
        lander.addY(positionY);
//        lander.addAngle(lander.posDeg.getLast());
        // add WIND to posX
        //double positionX = lander.posX.getLast() + wind.wind(positionY);
        //return positionY
        lander.setStep();
        printTest();
    }

    private void horizontal()
    {
        double thrust = g/Math.cos(lander.posDeg.getLast());
        double accX = Math.sin(lander.posDeg.getLast()) * thrust + external;

        double velX = accX * lander.getStep() + lander.getVX();
        lander.addX(velX * lander.getStep() + lander.posX.getLast());

        lander.addY(lander.posY.getLast() + (lander.getVY() * lander.getStep()));
        lander.setStep();
        printTest();
    }

    private double[] distanceToZero(){
        double angle = Math.abs(lander.posDeg.getLast());
        double time = Math.sqrt(angle / torque);
        double a = (Math.pow(lander.getVX() - wind.evaluateVelocity(lander.posY.getLast()/1000.00), 2))/300;
        double vt1 = a * time + lander.getVX();
        double average = Math.abs(lander.getVX() - vt1 / 2);
        double distance1 = average * time;
        double a2 = Math.pow((vt1 - wind.evaluateVelocity(lander.posY.getLast()/1000.00)) / 2, 2) / 300;
        double time2 = Math.abs(-vt1 / a2);
        double[] results = new double[2];
        results[0] = (lander.getVX() * time2 / 2 ) + distance1; // distance
        results[1] = time + time2; // time needed to cover distance
        return results;
    }

    /**
     * calculate the external forces that act on the system
     */
    private void externalForce ()
    {
        double windA = wind.evaluateVelocity(lander.posY.getLast()/1000.00);
        if (windA != 0 && DEBUG)
        {
            System.out.println("Wind= " + windA);
        }
        if(lander.getVX() > 0 ){
            external = -Math.pow((lander.getVX() - windA),2) * lander.DRAG_C;
        } else {
            external = Math.pow((lander.getVX() - windA),2) * lander.DRAG_C;
        }
    }

    private void backup()
    {
        if (lander.posX.getLast() <= lander.ACCURACY_X)
        {
            vertical();
        }
        if (lander.posY.getLast() <= lander.Y_END)
        {
            //lander.addY(0);
            lander.addAngle(desiredAngle);
            reachedEnd = true;
        }
    }

    // This is to check whilst running the print that everything works as wanted.
    private int printLoop(int count)
    {
        // in the end there is a possibility that it goes mad and changes the vals because it just wants to.
        if (lander.posY.getLast() < 0 || lander.posY.getLast().isNaN() || lander.posY.getLast().isInfinite())
        {
            int i = lander.posY.size() -1;
            while (lander.posY.get(i).isNaN() || lander.posY.get(i).isInfinite()) {
                lander.addY(lander.posY.get(i));
                lander.addY(lander.posY.get(i));
            }
        }

        if (lander.posDeg.getLast().isNaN() || lander.posDeg.getLast().isInfinite())
        {
            int i = lander.posDeg.size() -1;
            while (lander.posDeg.get(i).isNaN() || lander.posDeg.get(i).isInfinite()) {
                lander.addAngle(lander.posDeg.get(i - 1));
                lander.addAngle(lander.posDeg.get(i));
            }
        }

        if (lander.posX.getLast().isNaN() || lander.posX.getLast().isInfinite())
        {
            //lander.addX(0);
            //lander.addX(0);
            int i = lander.posX.size() -1;
            while (lander.posX.get(i).isNaN() || lander.posX.get(i).isInfinite()) {
                lander.addX(0);
                lander.addX(0);
            }
        }

        if (DEBUG) System.out.println("Loop " + (count++) + ": " + lander.toString());

        return count;
    }

    private void printTest()
    {
        if (DEBUG)
        {
            System.out.println("External: "+ external);
            System.out.println("step" + lander.getStep());
            System.out.println("VX:" +lander.getVX());
        }
    }
}


