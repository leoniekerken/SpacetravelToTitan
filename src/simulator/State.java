package simulator;

import titan.RateInterface;
import titan.StateInterface;
import titan.Vector3dInterface;

/**
 * data structure to store the state of the solar system
 * here information (position, velocity) about all objects in solar system at a time point is stored
 * [i][0] = position of object i
 * [i][1] = velocity of object i
 *
 * @author Leo, Chiara
 */
public class State implements StateInterface{

    public static boolean DEBUG = false;

    public Vector3dInterface[][] state;
    public int size;

    public State() {
        //initialize empty array
        state = new Vector3d[Planet.planets.length][2];
        this.size = state.length;
    }

    //initialize the first state
    public void initializeState() {

        for (int i = 0; i < state.length; i++) {
            state[i][0] = Planet.planets[i].posVector;    //Initialize pos
            state[i][1] = Planet.planets[i].velVector;    //Initialize vel

            if(DEBUG){
                System.out.println("simulator.State - initState position " + Planet.planets[i].name + " " + state[i][0]);
                System.out.println("simulator.State - initState velocity " + Planet.planets[i].name + " " + state[i][1]);
            }
        }
    }

    public StateInterface addMul(double step, RateInterface rate) {

        //create new state
        State newState = new State();

        //for all objects
        for (int i = 0; i < state.length; i++) {
            newState.addPos(i, state[i][0].addMul(step, ((Rate) rate).getVelocity(i))); //update position
            newState.addVel(i, state[i][1].addMul(step, ((Rate) rate).getAcceleration(i))); //update velocity
        }

        if(DEBUG){
            int i = 11; //which object you look at
            System.out.println("simulator.State - position after update " + Planet.planets[i].name + " " + newState.getPos(i));
            System.out.println(("simulator.State - velocity after update " + Planet.planets[i].name + " " + newState.getVel(i)));
        }
        return newState;
    }

    public void addPos(int i, Vector3dInterface pos){
        state[i][0] = pos;
    }

    public void addVel(int i, Vector3dInterface vel){
        state[i][1] = vel;
    }

    public Vector3dInterface getPos(int i){
        return state[i][0];
    }

    public Vector3dInterface getVel(int i){
        return state[i][1];
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < state.length; i++) {
            str += i  + " pos: " + state[i][0].toString() + " vel: " + state[i][1].toString() + "\n";
        }
        return str;
    }

    public int size(){
        return size;
    }

}
