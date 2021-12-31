package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.stronghold.robot.commands.NudgeFlapDown;
import com.gos.stronghold.robot.commands.NudgeFlapUp;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class AutoLowBarAndTurn extends CommandGroup {

    public AutoLowBarAndTurn(Chassis chassis, Flap flap) {
        addSequential(new NudgeFlapDown(flap));
        addSequential(new AutoDriveBackwards(chassis, 156, .6));
        addSequential(new NudgeFlapUp(flap));
        addSequential(new AutoTurn(chassis, 21, 0.1));
    }
}
