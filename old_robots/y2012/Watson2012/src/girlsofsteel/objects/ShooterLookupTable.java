package girlsofsteel.objects;

import java.util.Map;
import java.util.TreeMap;

public class ShooterLookupTable {

    private static final double MAX_SHOOTER_VELOCITY = 41.0;

    //Sorted array sorts greatest to least
    private final TreeMap<Double, Double> m_list = new TreeMap<>();

    //enters shooter data into the function that calculates the velocity the
    //ball should be shot at
    public ShooterLookupTable() {
        //new data (4/20/12):

        m_list.put(2.87, 20.75);
        m_list.put(3.175, 21.3);
        m_list.put(3.785, 21.9);
        m_list.put(4.394, 23.7);
        m_list.put(5.004, 24.82);
        m_list.put(5.613, 26.7);
        m_list.put(6.223, 27.7);
        m_list.put(6.68, 28.8);
        m_list.put(24.1, 193.5);

        //old data:
//        list.addElement(new MapDouble(0.0, 0.0));
//        list.addElement(new MapDouble(2.2733, 20.15));
//        list.addElement(new MapDouble(2.4257,20.5));
//        list.addElement(new MapDouble(2.5781,21.25));
//        list.addElement(new MapDouble(2.7305,21.75));
//        list.addElement(new MapDouble(2.8829,22.0));
//        list.addElement(new MapDouble(3.0353,22.5));
//        list.addElement(new MapDouble(3.1877,23.15));
//        list.addElement(new MapDouble(3.3401,23.25));
//        list.addElement(new MapDouble(3.4925,23.4));
//        list.addElement(new MapDouble(3.6449,24.1));
//        list.addElement(new MapDouble(3.7973,24.525));
//        list.addElement(new MapDouble(3.9497,24.9));
//        list.addElement(new MapDouble(4.1021,25.14));
//        list.addElement(new MapDouble(4.2161,25.3));
//        list.addElement(new MapDouble(4.4101,25.6));
//        list.addElement(new MapDouble(4.5593,26.25));
//        list.addElement(new MapDouble(4.7117,26.8));
//        list.addElement(new MapDouble(4.8641,27.2));
//        list.addElement(new MapDouble(5.0165,27.6));
//        list.addElement(new MapDouble(5.1689,27.8));
//        list.addElement(new MapDouble(5.3213,28.1));
//        list.addElement(new MapDouble(5.4637,28.4));
    }

    /**
     * @param distance, needs to get here from the camera*-
     * @return
     */
    public double getVelocityFrTable(double distance) {

        if (m_list.size() == 0) {
            return 0;//ends the getVelocityFrTable method -> sends a velocity of 0
        }

        Map.Entry<Double, Double> floor = m_list.floorEntry(distance);
        // Below the min. Just zero it out
        if (floor == null) {
            return 0;
        }

        Map.Entry<Double, Double> ceiling = m_list.ceilingEntry(distance);

        // Above the max. Return the cap
        if (ceiling == null) {
            return MAX_SHOOTER_VELOCITY;
        }

        // The basically impossible chance that you hit a lookup key exactly
        if (floor.equals(ceiling)) {
            return floor.getValue();
        }

        double distance1 = floor.getKey();
        double velocity1 = floor.getValue();
        double distance2 = ceiling.getKey();
        double velocity2 = ceiling.getValue();
        //finds the velocity needed based on the distance
        return interpolate(distance, distance1, velocity1, distance2, velocity2);
    }

    //uses the distance that the camera is at (distance)
    //and the 2 sets of data that the distance lies between (distance & velocity)
    //to find the velocity the shooter wheel should be set at
    private double interpolate(double distance, double distance1, double velocity1, double distance2, double velocity2) {
        double velocity;
        velocity = velocity1 + (velocity2 - velocity1) * ((distance - distance1) / (distance2 - distance1));
        return velocity;
    }
}
