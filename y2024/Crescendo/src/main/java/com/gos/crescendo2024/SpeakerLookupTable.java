package com.gos.crescendo2024;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public class SpeakerLookupTable {
    private static final double MIN_DISTANCE_ANGLE = 10.0;
    private static final double MAX_DISTANCE_ANGLE = 37.0;
    private static final double SUBWOOFER_SIZE = Units.inchesToMeters(3);

    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();

    public SpeakerLookupTable() {
        m_table.put(SUBWOOFER_SIZE, MIN_DISTANCE_ANGLE);
        m_table.put(SUBWOOFER_SIZE + Units.feetToMeters(2), 25.0); //2ft away
        m_table.put(SUBWOOFER_SIZE + Units.feetToMeters(4), 35.0); //4ft away
        m_table.put(SUBWOOFER_SIZE + Units.feetToMeters(6), MAX_DISTANCE_ANGLE);

    }

    public double getAngleTable(double distance) {
        return m_table.get(distance);
    }
}
