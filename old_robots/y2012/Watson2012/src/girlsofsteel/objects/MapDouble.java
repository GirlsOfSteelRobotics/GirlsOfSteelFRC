package girlsofsteel.objects;

public class MapDouble {

    private final double m_velocity;
    private final double m_distance;

    public MapDouble(double distance, double velocity) {
        this.m_distance = distance;
        this.m_velocity = velocity;
    }

    public double getVelocity(){
        return m_velocity;
    }

    public double getDistance(){
        return m_distance;
    }

    public int compare(MapDouble o2) {
        if (this.m_distance < o2.m_distance) {
            return -1;
        } else if (this.m_distance > o2.m_distance) {
            return 1;
        } else {
            System.out.println("Must be of type MapDouble");
            return 0;
        }
    }

}
