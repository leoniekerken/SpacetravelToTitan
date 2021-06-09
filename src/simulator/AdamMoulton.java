package simulator;
import java.util.LinkedList;
public class AdamMoulton {

    public State corrector (ODEFunction f, LinkedList<State> states, double timeStep, double h, State stateRK)
    {
        //do something --- correct RK function (4th)
        // W(i+1) = w(i) + (1/24) h ((9 * f(itterator + h, stateRK) + (19 f(itterator -h, states.getLast())) - (5* f(itterator - 2*h, states.get(states.size))) + (states.get(states.size - 3))
        Rate fPos1 = (Rate) f.call (timeStep + h, stateRK);
        Rate fPos2 = (Rate) f.call (timeStep, states.getLast());
        Rate fPos3 = (Rate) f.call (timeStep - h, states.get(states.size() - 2));
        Rate fPos4 = (Rate) f.call (timeStep - (2*h), states.get(states.size() - 3));
        Rate newRate = fPos1.mul(9).add(fPos2.mul(19)).add(fPos3.mul(-5)).add(fPos4);
        State newState = (State)states.getLast().addMul(h/24.0, newRate);
        return newState;
    }
}
