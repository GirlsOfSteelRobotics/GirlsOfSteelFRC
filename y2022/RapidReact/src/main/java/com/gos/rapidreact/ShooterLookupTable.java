package com.gos.rapidreact;

import edu.wpi.first.math.util.Units;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;
import static com.gos.rapidreact.subsystems.ShooterSubsystem.MAX_SHOOTER_RPM;
import static com.gos.rapidreact.subsystems.ShooterSubsystem.TARMAC_EDGE_RPM_HIGH;


public class ShooterLookupTable {

    //Sorted array sorts greatest to least
    private final NavigableMap<Double, Double> m_list = new TreeMap<>();

    //enters shooter data into the function that calculates the velocity the
    //ball should be shot at
    public ShooterLookupTable() {
        //2022 data but units???? meters, rpm??
        m_list.put(1.46, 1500.0); //45 inches irl
        m_list.put(1.99, 1650.0); //66 inches irl
        m_list.put(2.50, 1750.0); //85 inches irl
        m_list.put(3.36, 2050.0); //111 inches irl

    }

    /**
     * @param distance  needs to get here from the camera
     */
    public double getVelocityTable(double distance) {

        Map.Entry<Double, Double> floor = m_list.floorEntry(distance);
        // Below the min. Just zero it out
        if (floor == null) {
            return TARMAC_EDGE_RPM_HIGH;
        }

        Map.Entry<Double, Double> ceiling = m_list.ceilingEntry(distance);
        if (ceiling == null) {
            return MAX_SHOOTER_RPM;
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

