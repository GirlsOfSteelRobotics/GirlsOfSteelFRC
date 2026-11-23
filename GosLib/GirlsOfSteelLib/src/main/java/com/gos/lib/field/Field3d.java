package com.gos.lib.field;

import org.wpilib.math.geometry.Pose3d;
import org.wpilib.telemetry.TelemetryLoggable;
import org.wpilib.telemetry.TelemetryTable;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link org.wpilib.smartdashboard.Field2d}, but in 3D.
 *
 * <p>Log this with {@code Telemetry.log(name, field3d)} every loop to publish it.
 */
public class Field3d implements TelemetryLoggable {

    private final List<FieldObject3d> m_objects = new ArrayList<>();

    @Override
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public synchronized void logTo(TelemetryTable table) {
        for (FieldObject3d obj : m_objects) {
            table.log(obj.m_name, obj.getPosesArray(), Pose3d.struct);
        }
    }

    @Override
    public String getTelemetryType() {
        return "Field3d";
    }

    /**
     * Get or create a field object.
     *
     * @param name The field object's name.
     * @return Field object
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public synchronized FieldObject3d getObject(String name) {
        for (FieldObject3d obj : m_objects) {
            if (obj.m_name.equals(name)) {
                return obj;
            }
        }
        FieldObject3d obj = new FieldObject3d(name);
        m_objects.add(obj);
        return obj;
    }

}
