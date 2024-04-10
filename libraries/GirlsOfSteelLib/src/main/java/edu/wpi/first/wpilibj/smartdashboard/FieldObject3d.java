package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.StructArrayPublisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Game field object on a Field3d. */
@SuppressWarnings({"PMD.CommentDefaultAccessModifier", "PMD.AvoidSynchronizedAtMethodLevel"})
public class FieldObject3d implements AutoCloseable {

  private final List<Pose3d> m_poses = new ArrayList<>();
  String m_name;
  StructArrayPublisher<Pose3d> m_entry;

  /**
   * Package-local constructor.
   *
   * @param name name
   */
  FieldObject3d(String name) {
    m_name = name;
  }

  @Override
  public void close() {
    if (m_entry != null) {
      m_entry.close();
    }
  }

  /**
   * Set the pose from a Pose object.
   *
   * @param pose 2D pose
   */
  public synchronized void setPose(Pose3d pose) {
    setPoses(pose);
  }

  /**
   * Set multiple poses from a list of Pose objects. The total number of poses is limited to 85.
   *
   * @param poses list of 2D poses
   */
  public synchronized void setPoses(List<Pose3d> poses) {
    m_poses.clear();
    m_poses.addAll(poses);
    updateEntry();
  }

  /**
   * Set multiple poses from a list of Pose objects. The total number of poses is limited to 85.
   *
   * @param poses list of 2D poses
   */
  public synchronized void setPoses(Pose3d... poses) {
    m_poses.clear();
    Collections.addAll(m_poses, poses);
    updateEntry();
  }

  void updateEntry() {
    updateEntry(false);
  }

  synchronized void updateEntry(boolean setDefault) {
    if (m_entry == null) {
      return;
    }

    Pose3d[] arr = m_poses.toArray(new Pose3d[0]);
    if (setDefault) {
      m_entry.setDefault(arr);
    } else {
      m_entry.set(arr);
    }
  }
}
