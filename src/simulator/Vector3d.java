package simulator;

/**
 * class implementing a 3d Vector data structure
 *
 * @author Leo
 */
public class Vector3d implements titan.Vector3dInterface {

    double x;
    double y;
    double z;

    public Vector3d(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public titan.Vector3dInterface add(titan.Vector3dInterface other) {
        Vector3d sum = new Vector3d();
        sum.setX(this.x + other.getX());
        sum.setY(this.y + other.getY());
        sum.setZ(this.z + other.getZ());
        return sum;
    }

    @Override
    public titan.Vector3dInterface sub(titan.Vector3dInterface other) {
        Vector3d sum = new Vector3d();
        sum.setX(this.x - other.getX());
        sum.setY(this.y - other.getY());
        sum.setZ(this.z - other.getZ());
        return sum;
    }

    @Override
    public titan.Vector3dInterface mul(double scalar) {
        Vector3d product = new Vector3d();
        product.setX(this.x * scalar);
        product.setY(this.y * scalar);
        product.setZ(this.z * scalar);
        return product;
    }

    @Override
    public titan.Vector3dInterface addMul(double scalar, titan.Vector3dInterface other) {
        titan.Vector3dInterface mul = other.mul(scalar);
        titan.Vector3dInterface res = this.add(mul);
        return res;
    }

    //euclidean norm of the vector
    @Override
    public double norm() {
        double norm = Math.sqrt((Math.pow((this.x - 0), 2) + Math.pow((this.y - 0), 2) + Math.pow((this.z - 0),2)));
        return norm;
    }

    //euclidean distance of 2 vectors
    @Override
    public double dist(titan.Vector3dInterface other) {
        double d = Math.sqrt((Math.pow((this.x - other.getX()), 2) + Math.pow((this.y - other.getY()), 2) + Math.pow(this.z - other.getZ(),2)));
        return d;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ") ";
    }

    /**
     * checks if two Vector3d are equal
     * @param other
     * @return true if equal
     */
    public boolean equals(Vector3d other){
        if(this.x == other.x && this.y == other.y && this.z == other.z){
            return true;
        }
        return false;
    }
}
