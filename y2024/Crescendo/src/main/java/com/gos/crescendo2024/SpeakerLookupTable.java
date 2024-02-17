package com.gos.crescendo2024;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public class SpeakerLookupTable {
    private static final double MIN_DISTANCE_ANGLE = 29.0;
    private static final double MAX_DISTANCE_ANGLE = 47.0;
    private static final double SUBWOOFER_SIZE = Units.inchesToMeters(3);

    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();

    public SpeakerLookupTable() {
        m_table.put(SUBWOOFER_SIZE, MIN_DISTANCE_ANGLE);
        m_table.put(1.93, 37.0);
        m_table.put(2.45, 46.0);
        m_table.put(3.01, MAX_DISTANCE_ANGLE);

    }

    public double getAngleTable(double distance) {
        return m_table.get(distance);
    }
}
