package com.gos.reefscape.generated;

import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DriveToPositionDebugTab {
    private final ChassisSubsystem m_chassisSubsystem;

    public DriveToPositionDebugTab(ChassisSubsystem chassis) {
        m_chassisSubsystem = chassis;
    }


    public void createMoveRobotToPositionCommand() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Move Robot To Position");
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.A).withName("A"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.AB).withName("AB"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.B).withName("B"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.BLUE_NET).withName("BLUE_NET"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.C).withName("C"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.CD).withName("CD"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.D).withName("D"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.E).withName("E"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.EF).withName("EF"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.F).withName("F"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.G).withName("G"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.GH).withName("GH"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.H).withName("H"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.HUMAN_PLAYER_LEFT).withName("HUMAN_PLAYER_LEFT"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.HUMAN_PLAYER_RIGHT).withName("HUMAN_PLAYER_RIGHT"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.I).withName("I"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.IJ).withName("IJ"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.J).withName("J"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.K).withName("K"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.KL).withName("KL"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.L).withName("L"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.LEFT_ICE_CREAM).withName("LEFT_ICE_CREAM"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.PROCESSOR).withName("PROCESSOR"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.RIGHT_ICE_CREAM).withName("RIGHT_ICE_CREAM"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.STARTING_POS_CENTER).withName("STARTING_POS_CENTER"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.STARTING_POS_LEFT).withName("STARTING_POS_LEFT"));
        debugTab.add(m_chassisSubsystem.createDriveToMaybeFlippedPose(ChoreoPoses.STARTING_POS_RIGHT).withName("STARTING_POS_RIGHT"));
    }
}
