package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.stronghold.robot.commands.NudgeFlapDown;
import com.gos.stronghold.robot.commands.NudgeFlapUp;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Claw;
import com.gos.stronghold.robot.subsystems.Flap;
import com.gos.stronghold.robot.subsystems.Pivot;

/**
 *
 */
public class AutoLowBarAndScore extends CommandGroup {

    public AutoLowBarAndScore(Chassis chassis, Flap flap, Pivot pivot, Claw claw) {
        addSequential(new NudgeFlapDown(flap));
        addSequential(new AutoDriveBackwards(chassis, 186, .6));
        addSequential(new NudgeFlapUp(flap));
        addSequential(new AutoTurn(chassis, 21, 0.3));
        addSequential(new AutoDriveForward(chassis, 140, .6));
        addSequential(new AutoPivotDown(pivot, 0.5));
        addSequential(new AutoReleaseBall(claw, 2.0));
    }
}
