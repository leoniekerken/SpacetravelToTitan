package geneticAlgorithm;

import simulator.ODESolver;
import simulator.Planet;
import simulator.Vector3d;

/**
 * create a new individual
 * an individual consists of
 * a random position of titan during its one-year orbit /
 * position of the probe at start
 * a random initial velocity of the probe in m/s
 * multiplied
 *
 * @author Leo
 */
public class Individual {

    Vector3d velVector;
    Vector3d posVector;
    Vector3d unitVector;
    Vector3d targetPos;
    double initVel;
    double fitness;

    static boolean DEBUG = false;

    public Individual(){

    }

    /**
     * picks a random position of titan during one year orbit
     * calculates unitVector probe to titan
     * generates a random initial velocity in m/s
     * multiplies unitVector with initVel
     * @return individual - randomly generated velocity vector
     */
    public Individual generateIndividual(){

        if(DEBUG){
            System.out.println("generate individual");
        }

        //generate random unitVector
        int i = ((int) Math.round(Math.random() * (ODESolver.titanPos.length-1)));
        this.targetPos = ODESolver.titanPos[i];
        this.unitVector = (Vector3d) targetPos.sub(Planet.planets[3].posVector).mul(1/targetPos.dist(Planet.planets[3].posVector));

        //calculate starting position
        this.posVector = (Vector3d) Planet.planets[3].posVector.addMul(Planet.planets[3].radius, unitVector);

        //generate random initVel between 50000 and 60000
        this.initVel = 50000 + (Math.random() * 10000);

        //calculate velocity vector
        this.velVector = (Vector3d) unitVector.mul(initVel);

        return this;
    }

    public Individual generateSexySon(){

        //generate unitVector to position of titan at target
        this.targetPos = ODESolver.titanPos[ODESolver.titanPos.length-1];
        this.unitVector = (Vector3d) targetPos.sub(Planet.planets[3].posVector.mul(1/targetPos.dist(Planet.planets[3].posVector)));

        //calculate starting position
        this.posVector = (Vector3d) Planet.planets[3].posVector.addMul(Planet.planets[3].radius, unitVector);

        //get best possible velocity of 60000
        this.initVel = 60000;

        //calculate velocity vector
        this.velVector = (Vector3d) unitVector.mul(initVel);

        return this;
    }

    public Individual getChild(Individual other){

        if(DEBUG){
            System.out.println("getting child");
        }
        Individual child = new Individual();
        //get average of target position
        child.setTargetPos((Vector3d) this.targetPos.add(other.targetPos));
        //get average of both initVel
        child.setInitVel((this.initVel + other.initVel) / 2);
        child.setUnitVector();
        child.setPosVector();
        child.setVelVector();

        return child;
    }

    public void setPosVector(){
        this.posVector = (Vector3d) Planet.planets[3].posVector.addMul(Planet.planets[3].radius, unitVector);
    }

    public void setVelVector(){
        this.velVector = (Vector3d) unitVector.mul(initVel);
    }

    public void setUnitVector(){
        this.unitVector = (Vector3d) targetPos.sub(Planet.planets[3].posVector).mul(1/targetPos.dist(Planet.planets[3].posVector));
    }

    public void setTargetPos(Vector3d targetPos){
        this.targetPos = targetPos;
    }

    public void setInitVel(double initVel){
        this.initVel = initVel;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    public double getFitness(){
        return fitness;
    }
}
