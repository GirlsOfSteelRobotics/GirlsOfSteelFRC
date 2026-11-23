package com.gos.lib.field;

import org.wpilib.math.geometry.Pose3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Game field object on a Field3d. */
@SuppressWarnings({"PMD.CommentDefaultAccessModifier", "PMD.AvoidSynchronizedAtMethodLevel"})
public class FieldObject3d {

    private final List<Pose3d> m_poses = new ArrayList<>();
    final String m_name;

    /**
     * Package-local constructor.
     *
     * @param name name
     */
    FieldObject3d(String name) {
        m_name = name;
    }

    /**
     * Set the pose from a Pose object.
     *
     * @param pose 3D pose
     */
    public synchronized void setPose(Pose3d pose) {
        setPoses(pose);
    }

    /**
     * Set multiple poses from a list of Pose objects.
     *
     * @param poses list of 3D poses
     */
    public synchronized void setPoses(List<Pose3d> poses) {
        m_poses.clear();
        m_poses.addAll(poses);
    }

    /**
     * Set multiple poses from a list of Pose objects.
     *
     * @param poses list of 3D poses
     */
    public synchronized void setPoses(Pose3d... poses) {
        m_poses.clear();
        Collections.addAll(m_poses, poses);
    }

    synchronized Pose3d[] getPosesArray() {
        return m_poses.toArray(new Pose3d[0]);
    }
}
