package com.scra.mepi.rapid_react;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ShooterLookupTable {
    private final NavigableMap<Double, Double> m_list = new TreeMap<>();

    public ShooterLookupTable() {
        // fender at 1400\
    }

    public double getRpmTable(double distance) {

        Map.Entry<Double, Double> floor = m_list.floorEntry(distance);

        Map.Entry<Double, Double> ceiling = m_list.ceilingEntry(distance);

        if (floor == null) {
            return 0;
        }

        if (ceiling == null) {
            return 0;
        }

        if (floor.equals(ceiling)) {
            return floor.getValue();
        }

        double d1 = floor.getKey();
        double v1 = floor.getValue();
        double d2 = ceiling.getKey();
        double v2 = ceiling.getValue();

        return interpolate(distance, d1, v1, d2, v2);
    }

    private double interpolate(double distance, double d1, double v1, double d2, double v2) {
        return v1 + (v2 - v1) * ((distance - d1) / (d2 - d1));
    }
}
