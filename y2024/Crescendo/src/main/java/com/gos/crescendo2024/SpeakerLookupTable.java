package com.gos.crescendo2024;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public class SpeakerLookupTable {
    private static final double SUBWOOFER_SIZE = Units.feetToMeters(3);
    private static final GosDoubleProperty HACK_VALUE = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Hack Lookup Table", 0);

    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();

    public SpeakerLookupTable() {
        // tuned 2/27
        m_table.put(SUBWOOFER_SIZE + HACK_VALUE.getValue(), 9.0);
        m_table.put(1.8 + HACK_VALUE.getValue(), 25.0);
        m_table.put(2.45 + HACK_VALUE.getValue(), 35.0);
        m_table.put(2.96 + HACK_VALUE.getValue(), 37.0);

    }

    public double getAngleTable(double distance) {
        return m_table.get(distance);
    }
}
