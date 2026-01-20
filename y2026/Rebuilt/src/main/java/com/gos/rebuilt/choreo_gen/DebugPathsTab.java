package com.gos.rebuilt.choreo_gen;

import com.gos.rebuilt.ChoreoUtils;
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
        debugPathsTab.add(createDebugPathCommand("NormalPath_MaxAccelMaxSpeed"));
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel1fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel10fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPath_MaxAccel5fps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel1rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel2rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel4rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel8rps"));
        debugPathsTab.add(createDebugPathCommand("RotatedPath_MaxAccel12rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel1rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel5rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel10rps"));
        debugPathsTab.add(createDebugPathCommand("RotStrPath_MaxAccel15rps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel5fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel1fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel10fps"));
        debugPathsTab.add(createDebugPathCommand("StraightPathNAV_MaxAccel15fps"));
    }

    private Command createDebugPathCommand(String name) {
        return Commands.sequence(
            Commands.runOnce(() -> m_chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose(name).getPose())),
            followChoreoPath(name)
        ).withName(name);
    }

}
