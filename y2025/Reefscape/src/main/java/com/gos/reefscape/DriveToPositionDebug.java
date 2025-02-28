package com.gos.reefscape;

import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriveToPositionDebug {
    private final ChassisSubsystem m_chassisSubsystem;

    public DriveToPositionDebug(ChassisSubsystem chassis) {
        m_chassisSubsystem = chassis;
    }


    public void createMoveRobotToPositionCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Move Robot To Position");
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.E).withName("E"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.C).withName("C"));

    }

}
