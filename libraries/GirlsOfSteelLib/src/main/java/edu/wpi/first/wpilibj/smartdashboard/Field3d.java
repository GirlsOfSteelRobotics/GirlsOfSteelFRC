
package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link Field2d}, but in 3D
 */
public class Field3d implements NTSendable, AutoCloseable {

    private final List<FieldObject3d> m_objects = new ArrayList<>();
    private NetworkTable m_table;

    @Override
    public void initSendable(NTSendableBuilder builder) {
        builder.setSmartDashboardType("Field3d");

        synchronized (this) {
            m_table = builder.getTable();
            for (FieldObject3d obj : m_objects) { // NOPMD(CloseResource)
                synchronized (obj) {
                    obj.m_entry = m_table.getStructArrayTopic(obj.m_name, Pose3d.struct).getEntry(new Pose3d[]{});
                    obj.updateEntry(true);
                }
            }
        }
    }

    @Override
    public void close() {
        for (FieldObject3d obj : m_objects) { // NOPMD(CloseResource)
            obj.close();
        }
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
        if (m_table != null) {
            synchronized (obj) {
                obj.m_entry = m_table.getStructArrayTopic(name, Pose3d.struct).getEntry(new Pose3d[] {});
            }
        }
        return obj;
    }

}
