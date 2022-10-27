package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.commands.NudgeFlapDown;
import com.gos.stronghold.robot.commands.NudgeFlapUp;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class AutoLowBarAndTurn extends SequentialCommandGroup {

    public AutoLowBarAndTurn(Chassis chassis, Flap flap) {
        addCommands(new NudgeFlapDown(flap));
        addCommands(new AutoDriveBackwards(chassis, 156, .6));
        addCommands(new NudgeFlapUp(flap));
        addCommands(new AutoTurn(chassis, 21, 0.1));
    }
}
