package landing;

public class ClosedController {
    Lander lander;
    WindModel wind;
    double g = 1.352;
    double dragCoefficient = 1.00/300;
    double  torque = 50;
    double desiredAngle;
    double force = 1e-20;
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

            experimental();
            if(DEBUG)
            {
                System.out.println("Loop " + (count++) + ": " + lander.toString());
                //count = printLoop(count);
//                if (lander.posX.getLast().isNaN() && lander.posY.getLast().isNaN() && lander.posDeg.getLast().isNaN());
//                {
//                    reachedEnd = true;
//                }
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
              //  rotation();
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
                lander.setStep(lander.step);
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
                //rotation();

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

    private double rotation (){
       //if (DEBUG) System.out.println("ROTATION");

        return  angularAccelerate(desiredAngle);
        //lander.setStep(timeStep);
       // horizontal();
        //printTest();
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
        }
        else if (Math.signum(lander.getVY()) > 0)
        {
            acceleration = Math.pow(g,2) * (-1);
        }
        else {
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
        lander.setStep(lander.step);
        printTest();
    }

    private void horizontal()
    {
        double thrust = g/Math.cos(lander.posDeg.getLast());
        double accX = Math.sin(lander.posDeg.getLast()) * thrust + external;

        double velX = accX * lander.getStep() + lander.getVX();
        lander.addX(velX * lander.getStep() + lander.posX.getLast());

        lander.addY(lander.posY.getLast() + (lander.getVY() * lander.getStep()));
        lander.setStep(lander.step);
        printTest();
    }

    private double[] distanceToZero(){
        double angle = Math.abs(lander.posDeg.getLast());
        double time = Math.sqrt(angle / torque);
        double windApplied = wind.evalVel(lander.posY.getLast()) / force;
        if (DEBUG) System.out.println("WIND=" + windApplied);
        double a = (Math.pow(lander.getVX() - windApplied, 2)) * lander.DRAG_C;
        double vt1 = a * time + lander.getVX();
        double average = Math.abs(lander.getVX() - vt1 / 2);
        double distance1 = average * time;
        double a2 = (Math.pow((vt1 - windApplied) / 2, 2)) * lander.DRAG_C;
        // double a2 = Math.pow((vt1 - wind.evaluateVelocity(lander.posY.getLast())) / 2, 2) * lander.DRAG_C;
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
        double windA = wind.evalVel(lander.posY.getLast()) / force;
//        if (windA != 0 && DEBUG)
//        {
//            System.out.println("Wind= " + windA);
//        }
        if(lander.getVX() > 0 ){
            external = -Math.pow((lander.getVX() - windA),2) * lander.DRAG_C;
        } else {
            external = Math.pow((lander.getVX() - windA),2) * lander.DRAG_C;
        }
    }

    private void backup()
    {
        /*if (lander.posX.getLast() <= lander.ACCURACY_X)
        {
            vertical();
        }*/

        // if Xposition is positive and V is positive
        //get v and make it negative
        // same the other way round

        if (lander.posX.getLast() > 0 && lander.getVX() > 0)
        {
            lander.addX(lander.posX.getLast() - (lander.getVX() * lander.getStep()));
        }

        if (lander.posY.getLast() > 0 && lander.getVY() > 0)
        {
            lander.addY(lander.posY.getLast() - (lander.getVY() * lander.getStep()));
        }


        if (lander.posY.getLast() < 150)
        {
            double grav = g *10;
            force = -(grav * (1 - Math.pow(Math.E, (lander.posY.getLast()/grav))));
        }
        if (lander.posY.getLast() <= lander.ACCURACY_Y1)
        {
            lander.addY(0.1);
            lander.addY(0);
            lander.addAngle(desiredAngle);
            reachedEnd = true;
        }

        //stepSize reduction
        if (lander.posY.getLast() > 100 && lander.step != lander.stepDefault)
        {
            lander.resetStep();
            if (DEBUG) System.out.println("Step" + lander.getStep());
        }
        else if (lander.posY.getLast() <= 50)
        {
            lander.step = 0.125;
            lander.setStep(lander.step);
            if (DEBUG) System.out.println("Step" + lander.getStep());
        }
        else if (lander.posY.getLast() <= 100)
        {
            lander.step = 0.25;
            lander.setStep(lander.step);
            if (DEBUG) System.out.println("Step" + lander.getStep());
        }
        else if (lander.posY.getLast() <= 200)
        {
            lander.step = 0.5;
            lander.setStep(lander.step);
            if (DEBUG) System.out.println("Step" + lander.getStep());
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

    public void experimental(){
        backup();
        double thrust = 1;
        double sign = Math.signum(lander.posX.getLast()) * - 1;

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

        if(Math.abs(lander.posX.getLast()) > 4000 ){
            desiredAngle = 80 * sign;
        } else if (Math.abs(lander.posX.getLast()) > 5 && Math.abs(lander.posX.getLast()) <= 4000){
            desiredAngle = sign * (Math.abs(lander.posX.getLast()) / 100) + 5;
        } else {
            desiredAngle = Math.abs(lander.posX.getLast()) * sign;
        }

        if(lander.posY.getLast() > 7000 && lander.getVY() > -50){
            thrust = g/Math.cos(lander.posDeg.getLast()) / 2;
        } else if(lander.posY.getLast() < 7000 || lander.getVY() <= -50){
            thrust = g/Math.cos(lander.posDeg.getLast());
        }

        if(Math.abs(lander.posX.getLast()) <= lander.ACCURACY_X){
            desiredAngle = 0;
            if(lander.getVY() <= -50) {
                thrust = (Math.pow(lander.getVY(), 2) / (2 * lander.posY.getLast())) + g;
            } else {
                thrust = 0;
            }
        }
        externalForce();

        timestep(thrust);


    }

    public void timestep(double thrust){
       double timestep = lander.getStep();
       double rot = rotation();
        if(rot > timestep){
            timestep = rot;
        }

        System.out.println("thrust : " + thrust);
        //update x
        double accX = Math.sin(lander.posDeg.getLast()) * thrust + external;
        double velX = accX * timestep + lander.getVX();
        lander.addX(velX * timestep + lander.posX.getLast());
        if(DEBUG)
        {
            System.out.println("acc : " + accX);
            System.out.println("vel : " + velX);
        }

        //update y
        double accY = Math.cos(lander.posDeg.getLast()) * thrust - g;
        double velY = lander.getVY() + (accY * timestep);
        lander.addY(lander.posY.getLast() + velY * lander.getStep());
    }
}


