package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.commands.NudgeFlapDown;
import com.gos.stronghold.robot.commands.NudgeFlapUp;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Claw;
import com.gos.stronghold.robot.subsystems.Flap;
import com.gos.stronghold.robot.subsystems.Pivot;

/**
 *
 */
public class AutoLowBarAndScore extends SequentialCommandGroup {

    public AutoLowBarAndScore(Chassis chassis, Flap flap, Pivot pivot, Claw claw) {
        addCommands(new NudgeFlapDown(flap));
        addCommands(new AutoDriveBackwards(chassis, 186, .6));
        addCommands(new NudgeFlapUp(flap));
        addCommands(new AutoTurn(chassis, 21, 0.3));
        addCommands(new AutoDriveForward(chassis, 140, .6));
        addCommands(new AutoPivotDown(pivot, 0.5));
        addCommands(new AutoReleaseBall(claw, 2.0));
    }
}
