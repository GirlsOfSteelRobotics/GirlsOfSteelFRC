package com.gos.crescendo2024;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class SpeakerLookupTable {
    // private static final double SUBWOOFER_SIZE = Units.feetToMeters(3);

    private static final GosDoubleProperty HACK_VALUE = new GosDoubleProperty(false, "Arm: Lookup Table Adjustment", 0);

    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();

    public SpeakerLookupTable() {
        // tuned GPR - Use camera for distance - no hack value
        //m_table.put(1.8, 25.0);
        //m_table.put(2.45, 35.0);
        //m_table.put(2.99, 37.0);
        //m_table.put(3.42, 40.0);
        //m_table.put(3.87, 41.0);
        //m_table.put(5.62, 35.5);    // 3/28 statichooter testing

//        m_table.put(1.2, 16.0);
//        m_table.put(1.93, 20.0);
//        m_table.put(2.37, 26.0);
//        m_table.put(2.75, 29.0);
//        m_table.put(3.25, 33.0);
//        m_table.put(3.75, 35.0);
//        m_table.put(4.22, 39.0);
//        m_table.put(4.61, 40.00);
//        m_table.put(5.53, 41.0);

        m_table.put(1.26, 16.0);
        m_table.put(1.91, 24.0);
        m_table.put(2.5, 30.0);
        m_table.put(2.67, 33.0);
        m_table.put(3.34, 36.0);
        m_table.put(3.87, 39.0);
        m_table.put(4.6, 42.0);
        m_table.put(5.11, 41.7);



    }

    public double getAngleTable(double distance) {
        return m_table.get(distance) + HACK_VALUE.getValue();
    }
}
