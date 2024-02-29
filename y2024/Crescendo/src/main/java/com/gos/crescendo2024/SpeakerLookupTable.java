package com.gos.crescendo2024;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public class SpeakerLookupTable {
    private static final double SUBWOOFER_SIZE = Units.feetToMeters(3);

    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();

    public SpeakerLookupTable() {
        // tuned 2/27
        m_table.put(SUBWOOFER_SIZE, 7.0);
        m_table.put(SUBWOOFER_SIZE + Units.feetToMeters(2), 25.0);
        m_table.put(SUBWOOFER_SIZE + Units.feetToMeters(4), 35.0);
        m_table.put(SUBWOOFER_SIZE + Units.feetToMeters(6), 37.0);

    }

    public double getAngleTable(double distance) {
        return m_table.get(distance);
    }
}
