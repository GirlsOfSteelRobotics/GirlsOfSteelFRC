package com.gos.reefscape.generated;

import com.gos.reefscape.ChoreoUtils;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class DebugPathsTab {
    private final ChassisSubsystem m_chassisSubsystem;

    public DebugPathsTab(ChassisSubsystem chassis) {
        m_chassisSubsystem = chassis;
    }

    public void addDebugPathsToShuffleBoard() {
        ShuffleboardTab debugPathsTab = Shuffleboard.getTab("Debug Paths");
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_01fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_05fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_10fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_13fps"));
        debugPathsTab.add(createDebugPathCommand("TestPath_Maxmpss_Maxfps"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_020DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_045DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_090DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_180DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_270DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_360DegPerSec"));
        debugPathsTab.add(createDebugPathCommand("TestRotation_MaxDegPerSec"));
    }

    private Command createDebugPathCommand(String name) {
        return Commands.sequence(
            Commands.runOnce(() -> m_chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose(name).getPose())),
            followChoreoPath(name)
        ).withName(name);
    }

}
