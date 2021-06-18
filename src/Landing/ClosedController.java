package Landing;

public class ClosedController {
    Lander lander;
    double g = 1.352;
    double dragCoefficient = 1/300;
    public void ifLoop()
    {
        if(lander.posY.getLast() == 0){
            return;
        }

        // WIND MODEL FOR Y > 7 KM

        if(Math.abs(lander.posX.getLast()) <= 1 && lander.posY.getLast() > 100) {
            // combat slight movement just by the wind to not harm us every time.

            //find difference in angle to angle we want
            //adjust angle
            //calculate thrust
            //calculate velocity it will reach with given thrust
            //calculate distance needed to rotate to 0 and stop x velocity
            /* t0 -> t1 calculate time needed to perform rotation
             *   average velocity - wind velocity
             *   velocity divided by 300 = acceleration
             *   actual velocity after rotation will be accelartion * time * velocity at t0
             *   distance traveled is vt0 - vt1 * t / 2
             *   t1 -> t2
             *   average velocity - wind velocity
             *   velocity divided by 300 = acceleration
             *  time needed = -v1 / a
             *  distance needed = v1 * t / 2
             * */
        }
        else if (Math.abs(lander.posX.getLast()) <= lander.ACCURACY_X){
            //find difference in angle to angle we want
            //adjust angle
            //calculate thrust
            //calculate velocity it will reach with given thrust
            //calculate distance needed to rotate to 0 and stop x velocity
            /* t0 -> t1 calculate time needed to perform rotation
            *   average velocity - wind velocity
            *   velocity divided by 300 = acceleration
            *   actual velocity after rotation will be accelartion * time * velocity at t0
            *   distance traveled is vt0 - vt1 * t / 2
            *   t1 -> t2
            *   average velocity - wind velocity
            *   velocity divided by 300 = acceleration
            *  time needed = -v1 / a
            *  distance needed = v1 * t / 2
            * */


        } else {
            // landerX is equal to 0
            if(lander.posDeg.getLast() == 0){
                // calculate thrust needed to slow down to zero until reaching the ground
                // subtract the gravitational force and the drag


            } else {
                //find difference in angle to angle we want
                //adjust angle
            }

        }



    }

    private double rotation ()
    {
        angularAcceleration();
        angularAccelerate();
        return 0;
    }

    private void angularAccelerate() {
        double desAngle = 0;
        double angle = Math.abs(lander.angle - desAngle);
    }

    private void angularAcceleration() {
    }
}



// OLD DRAG CALC
// ∆Drag = (wind - velocity[x])/300
// the 1/300 comes from rho/2 * (area * DragCoefficient) / LanderMass
// rho = 4 kg/m^3 (density)
// area = 10 m^2
// DragCoeefficient = 1
// Lander Mass = 6000 kg

/*
        other drag calc
        F[drag] = 1/2 * rho * v[rel]^2 * C[d] * A
        v[rel] = v/2 - wind
        C[d] is drag coefficient = 1
        F[drag] / mass = acceleration due to wind
        */

// double vX = lander.getVX(lander.posX.size() - 1); // m/s
// double vY = lander.getVY(lander.posX.size() - 1); // might be needed - very unlikely
// double wind = Wind.getWind(lander.posY.getLast()); // adding the Wind to the System.
// double deltaV = vX/2; //- wind
// double acc = Math.pow(deltaV, 2) / 300;

/*
        //this is a detailed version of the acc variable since there are mostly constants in here.
        double coeff = 1;
        double rho = 4;
        double area = 10; // m^2
        double mass = 6000; // kg
        double fDragStatic = 1/2 * rho * coeff * area;
        double deltaFDrag = fDragStatic * Math.pow(deltaV,2);
        double acceleration = deltaFDrag / mass;
        */