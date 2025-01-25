package com.gos.reefscape.auto.modes.right;


import com.gos.reefscape.ChoreoUtils;
import com.gos.reefscape.subsystems.IntakeSubsystem;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class Right_E_CCommandGroupCommandGroup extends SequentialCommandGroup {
    public Right_E_CCommandGroupCommandGroup(GOSSwerveDrive swerveDrive) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("RightToE"));
        addCommands(followChoreoPath("EToHumanPlayer"));
        addCommands(followChoreoPath("HumanPlayertoC"));
    }
}