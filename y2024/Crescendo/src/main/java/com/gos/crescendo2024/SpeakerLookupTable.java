package com.gos.crescendo2024;

import edu.wpi.first.math.util.Units;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class SpeakerLookupTable {
    private static final double MIN_DISTANCE_ANGLE = 10.0;
    private static final double MAX_DISTANCE_ANGLE = 45.0;

    private final NavigableMap<Double, Double> m_list = new TreeMap<>();

    public SpeakerLookupTable() {
        m_list.put(Units.feetToMeters(0), MIN_DISTANCE_ANGLE);
        m_list.put(Units.feetToMeters(9), 30.0);
        m_list.put(Units.feetToMeters(18), 40.0);
        m_list.put(Units.feetToMeters(27), MAX_DISTANCE_ANGLE);

    }

    public double getVelocityTable(double distance) {
        Map.Entry<Double, Double> floor = m_list.floorEntry(distance);

        if (floor == null) {
            return MIN_DISTANCE_ANGLE;
        }

        Map.Entry<Double, Double> ceiling = m_list.ceilingEntry(distance);
        if (ceiling == null) {
            return MAX_DISTANCE_ANGLE;
        }

        if (floor.equals(ceiling)) {
            return floor.getValue();
        }

        double distance1 = floor.getKey();
        double angle1 = floor.getValue();
        double distance2 = ceiling.getValue();
        double angle2 = ceiling.getValue();
        return interpolate(distance, distance1, angle1, distance2, angle2);
    }


    private double interpolate(double distance, double distance1, double angle1, double distance2, double angle2) {
        return angle1 + (angle2 - angle1) * ((distance - distance1) / (distance2 - distance1));

    }
}
