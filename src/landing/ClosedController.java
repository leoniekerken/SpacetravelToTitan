package landing;

public class ClosedController {
    Lander lander;
    WindModel wind;
    double g = 1.352;
    double dragCoefficient = 1.00/300;
    double  torque = 50;
    double desiredAngle;
    double external;
    public boolean reachedEnd = false;

    public ClosedController()
    {
        lander = new Lander();
        wind = new WindModel();
    }

    public void reachTitan ()
    {
        while (!reachedEnd)
        {
            ifLoop();
        }
        System.out.println("hey");
    }


    public void ifLoop() {
        desiredAngle = 0;
        angleCalc();
        externalForce();
        if(lander.posY.getLast() == 0 && lander.accuracy())
        {
            reachedEnd = true;
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

            rotation();
            if(Math.abs(lander.posX.getLast()) <= distanceToZero()[1]){
                // decelerate and rotate to 0
                double timeNeeded = distanceToZero()[2];
                lander.addAngle(0);
                lander.addX(0);
                lander.addY(lander.posY.getLast() + (lander.getVY() * lander.getStep()));

                return;

            }
            horizontal();

        } else {
            // lander X is equal to 0
            if(lander.posDeg.getLast() == 0){
                // calculate thrust needed to slow down to zero until reaching the ground
                // subtract the gravitational force and the drag
                // have wind checked for next itteration

                // only do y directional movement

                double deceleration = (-Math.pow(lander.getVY(), 2)/ ( 2 * lander.posY.getLast())) - g;
                double mainThrust = deceleration + g;

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
        if(lander.posY.getLast() >= 80000){
            desiredAngle = 80 * sign;
        } else {
           desiredAngle = lander.posY.getLast() / 1000 * sign;
        }
        lander.addAngle(desiredAngle);
    }

    private void rotation (){
        double timeStep = angularAccelerate(desiredAngle);
        lander.setStep(timeStep);
        horizontal();

    }

    private double angularAccelerate(double desAngle){
        double angle = Math.abs(lander.posDeg.getLast() - desAngle);
        double time =  2 * Math.sqrt(angle / torque);
        //change time step to time
        return time;
        // perform two time steps where 1 step = time
        // after 1 time step the craft is rotated halfway

    }
    private void vertical()
    {
        double deceleration = (-Math.pow(lander.getVY(), 2)/ ( 2 * lander.posY.getLast())) - g ;
        double velY = lander.getVY() + (deceleration * (lander.timeSteps.getLast() - lander.timeSteps.get(lander.timeSteps.size() - 2)));
        double positionY = lander.posY.getLast() + velY * lander.getStep();

        // wind account
        double velX = lander.getVX() + (external * lander.getStep());
        lander.addX(velX * lander.getStep() + lander.posX.getLast());
        // add WIND to posX
        //double positionX = lander.posX.getLast() + wind.wind(positionY);
        //return positionY
    }


    private void horizontal()
    {

        double thrust = g/Math.cos(lander.posDeg.getLast());
        double accX = Math.sin(lander.posDeg.getLast()) * thrust + external;

        double velX = accX * lander.getStep() + lander.getVX();
        lander.addX(velX * lander.getStep() + lander.posX.getLast());

        lander.addY(lander.posY.getLast() + (lander.getVY() * lander.getStep()));
    }

    private double[] distanceToZero(){
        double angle = Math.abs(lander.posDeg.getLast());
        double time = Math.sqrt(angle / torque);
        double a = (Math.pow(lander.getVX() - wind.evaluateVelocity(lander.posY.getLast()), 2))/300;
        double vt1 = a * time + lander.getVX();
        double average = (lander.getVX() - vt1) / 2;
        double distance1 = average * time;
        double a2 = Math.pow((vt1 - wind.evaluateVelocity(lander.posY.getLast())) / 2, 2) / 300;
        double time2 = -vt1 / a2;
        double[] results = new double[2];
        results[1] = (lander.getVX() * time2 / 2 ) + distance1; // distance
        results[2] = time + time2; // time needed to cover distance
        return results;

    }

    /**
     * calculate the external forces that act on the system
     */
    private void externalForce ()
    {
        double windA = wind.evaluateVelocity(lander.posY.getLast());
        external = Math.pow((lander.getVX() - windA),2) * lander.DRAG_C;
    }



}


