package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.stronghold.robot.commands.NudgeFlapDown;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class FlapThenLowBar extends CommandGroup {

    public FlapThenLowBar(Chassis chassis, Flap flap, double inches, double speed) {
        addSequential(new NudgeFlapDown(flap));
        addSequential(new AutoDriveBackwards(chassis, inches, speed));
    }
}
