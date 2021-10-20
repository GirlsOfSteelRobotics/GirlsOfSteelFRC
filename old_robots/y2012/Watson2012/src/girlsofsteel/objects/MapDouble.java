package girlsofsteel.objects;

public class MapDouble {

    private double velocity = 0.0;
    private double distance = 0.0;

    public MapDouble(double distance, double velocity) {
        this.distance = distance;
        this.velocity = velocity;
    }

    public double getVelocity(){
        return velocity;
    }

    public double getDistance(){
        return distance;
    }

    public int compare(MapDouble o2) {
        if (this.distance < o2.distance) {
            return -1;
        } else if (this.distance > o2.distance) {
            return 1;
        } else {
            System.out.println("Must be of type MapDouble");
            return 0;
        }
    }

}
