package geneticAlgorithm;

import simulator.Planet;
import simulator.TakeOffPoint;
import simulator.Vector3d;

public class Individual {

    /**
     * create a new individual
     * an individual consists of
     * @param targetPos a random target position
     * @param posVector position at start
     * @param unitVector
     * @param initVel initial velocity of the probe in m/s
     * @param velVector directed 3d velocity vector
     *
     * @param fitness euclidean distance probe to titan at its best position
     * @param position best position during flight
     * @param distance distance vector probe to titan at best position
     *
     * @author Leo
     */

    static boolean DEBUG = false;
    public boolean MUTATION = true;

    static double mutationRate = 1 - Evolution.mutationRate;

    public Vector3d[] genePool = Evolution.genePool;
    static Vector3d posEarth = (Vector3d) Planet.planets[3].posVector;
    static double radiusEarth = Planet.planets[3].radius;

    static Vector3d takeOffPoint = new Vector3d(-1.471868229554755E11, -2.8606557057938354E10, 8287486.0632270835);

    Vector3d targetPos;
    Vector3d posVector;
    Vector3d velVector;
    Vector3d unitVector;
    double initVel;
    double fitness;
    int position;
    Vector3d distanceVector;

    public Individual(){

    }

    /**
     * generates random individual by
     * generating random unitVector
     * and random initial velocity between 60000 and 80000 m/s
     * calculating velocity vector
     * @return new individual
     */
    public Individual generateIndividualRandom(){

        //generate random unitVector
        this.targetPos = RandomPosition.generateRandomPosition();

        //calculate unitVector
        this.unitVector = (Vector3d) targetPos.sub(posEarth).mul(1/targetPos.dist(posEarth));

        //keep starting position constant (not)
        this.posVector = takeOffPoint;

        //generate random initVel between 50000 and 60000
        //this.initVel = 50000 + (Math.random() * 10000);

        //have a constant initial velocity
        //this.initVel = 60000;

        //generate random initVel between 60000 and 80000
        this.initVel = 60000 + (Math.random() * 20000);

        //calculate velocity vector
        this.velVector = (Vector3d) unitVector.mul(initVel);

        return this;
    }

    /**
     * generates individual based on random position of titan
     * during one year orbit (genePool)
     * @return individual
     */
    public Individual generateIndividualFromGenePool(){

        //generate random unitVector
        int i = ((int) Math.round(Math.random() * (genePool.length-1)));
        this.targetPos = genePool[i];
        this.unitVector = (Vector3d) targetPos.sub(posEarth).mul(1/targetPos.dist(posEarth));

        //calculate starting position
        this.posVector = (Vector3d) posEarth.addMul(radiusEarth, unitVector);

        //generate random initVel between 50000 and 60000
        this.initVel = 50000 + (Math.random() * 10000);

        //calculate velocity vector
        this.velVector = (Vector3d) unitVector.mul(initVel);

        return this;
    }

    /**
     * generates an individual we know has "good genes" - the sexySon!
     * @return sexySon
     */
    public Individual generateSexySon(){

        this.initVel = 60000;
        Vector3d titanAtStart = (Vector3d) Planet.planets[8].posVector;

        TakeOffPoint takeOffPoint = new TakeOffPoint();
        takeOffPoint.calculateTakeOffPoint(initVel, titanAtStart);

        this.targetPos = titanAtStart;

        //unitVector
        this.unitVector = takeOffPoint.unitVector;

        //starting position
        this.posVector = takeOffPoint.startPos;

        //starting velocity vector
        this.velVector = takeOffPoint.startVel;

        return this;
    }

    /**
     * makes child of two parent individuals by
     * taking the average of the target position of both parents
     * @param other individual
     * @return child
     */
    public Individual getChild(Individual other){

        Individual child = new Individual();

        //get average of target position
        Vector3d newPos = (Vector3d) (this.targetPos.add(other.targetPos));
        child.setTargetPos((Vector3d) newPos.mul(0.5));

        //add mutation
        if(MUTATION && Math.random() > mutationRate){
            Vector3d mutation = Mutation.mutate();
            if(DEBUG){
                System.out.println();
                System.out.println();
                System.out.println("MUTATION");
                System.out.println();
                System.out.println();
                System.out.println("mutation added: " + mutation);
                System.out.println();
                System.out.println();
                System.out.println("before mutation: " + child.targetPos);
                System.out.println();
                System.out.println();
            }
            child.setTargetPos((Vector3d) child.targetPos.add(mutation));
            if(DEBUG){
                System.out.println("after mutation: " + child.targetPos);
            }

        }

        //get average of both initVel
        child.setInitVel((this.initVel + other.initVel) / 2);

        child.setUnitVector();
        child.setPosVector();
        child.setVelVector();

        return child;
    }

    public void setPosVector(){
        //this.posVector = (Vector3d) Planet.planets[3].posVector.addMul(Planet.planets[3].radius, this.unitVector);
        //keep it static
        this.posVector = takeOffPoint;
    }

    public void setVelVector(){
        this.velVector = (Vector3d) this.unitVector.mul(this.initVel);
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

    public void setPosition(int position){
        this.position = position;
    }

    public void setDistanceVector(Vector3d distanceVector){
        this.distanceVector = distanceVector;
    }

    public int getPosition(){
        return position;
    }

    public Vector3d getDistanceVector(){
        return distanceVector;
    }

    public double getFitness(){
        return fitness;
    }

    public void reset(){
        this.targetPos = new Vector3d();
    }
}
