package com.gos.rebuilt.choreo_gen;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.subsystems.ChassisSubsystem;

import com.gos.lib.tunables.CommandTab;
import org.wpilib.command2.Command;
import org.wpilib.command2.Commands;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class DebugPathsTab {
    private final ChassisSubsystem m_chassisSubsystem;

    public DebugPathsTab(ChassisSubsystem chassis) {
        m_chassisSubsystem = chassis;
    }

    public void addDebugPathsToShuffleBoard() {
        CommandTab debugPathsTab = new CommandTab("Debug Paths");
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPath_MaxAccel_MaxSpeed"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPath_MaxAccel_01fps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPath_MaxAccel_05fps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPath_MaxAccel_10fps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotatedPath_MaxAccel_01rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotatedPath_MaxAccel_02rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotatedPath_MaxAccel_04rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotatedPath_MaxAccel_08rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotatedPath_MaxAccel_12rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotStrPath_MaxAccel_01rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotStrPath_MaxAccel_05rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotStrPath_MaxAccel_10rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_RotStrPath_MaxAccel_15rps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPathNAV_MaxAccel_01fps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPathNAV_MaxAccel_05fps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPathNAV_MaxAccel_10fps"));
        debugPathsTab.add(createDebugPathCommand("TUNING_StraightPathNAV_MaxAccel_15fps"));
    }

    private Command createDebugPathCommand(String name) {
        return Commands.sequence(
            Commands.runOnce(() -> m_chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose(name).getPose())),
            followChoreoPath(name)
        ).withName(name);
    }

}
