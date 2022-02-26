package com.gos.rapidreact;

import java.util.Map;
import java.util.TreeMap;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;


public class ShooterLookupTable {

    private static final double MAX_SHOOTER_VELOCITY = 41.0;

    //Sorted array sorts greatest to least
    private final TreeMap<Double, Double> m_list = new TreeMap<>();

    //enters shooter data into the function that calculates the velocity the
    //ball should be shot at
    public ShooterLookupTable() {
        //old data (2012):
        m_list.put(2.87, 20.75);
        m_list.put(3.175, 21.3);
        m_list.put(3.785, 21.9);
        m_list.put(4.394, 23.7);
        m_list.put(5.004, 24.82);
        m_list.put(5.613, 26.7);
        m_list.put(6.223, 27.7);
        m_list.put(6.68, 28.8);
        m_list.put(24.1, 193.5);
    }

    /**
     * @param distance  needs to get here from the camera
     */
    public double getVelocityTable(double distance) {

        Map.Entry<Double, Double> floor = m_list.floorEntry(distance);
        // Below the min. Just zero it out
        if (floor == null) {
            return DEFAULT_SHOOTER_RPM;
        }

        Map.Entry<Double, Double> ceiling = m_list.ceilingEntry(distance);
        if (ceiling == null) {
            return MAX_SHOOTER_VELOCITY;
        }

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

