package com.gos.reefscape.auto.modes.left;


import com.gos.reefscape.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class Left_J_LCommandGroup extends SequentialCommandGroup {
    public Left_J_LCommandGroup(GOSSwerveDrive swerveDrive, CombinedCommands combinedCommands) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("LeftToJ"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.levelFour)); //might be dif
        addCommands(followChoreoPath("JToLeftHumanPlayer"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.humanPlayerStation));
        addCommands(followChoreoPath("LeftHumanPlayerToL"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.levelFour)); //might be dif
    }
}
