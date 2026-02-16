package com.gos.rebuilt.choreo_gen;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;

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
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel_MaxSpeed"));
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel_01fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel_05fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel_10fps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel_01rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel_02rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel_04rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel_08rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel_12rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel_01rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel_05rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel_10rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel_15rps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel_01fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel_05fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel_10fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel_15fps"));
    }

    private Command createDebugPathCommand(String name) {
        return Commands.sequence(
            Commands.runOnce(() -> m_chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose(name).getPose())),
            followChoreoPath(name)
        ).withName(name);
    }

}
