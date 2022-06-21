package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.commands.NudgeFlapDown;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class FlapThenLowBar extends SequentialCommandGroup {

    public FlapThenLowBar(Chassis chassis, Flap flap, double inches, double speed) {
        addCommands(new NudgeFlapDown(flap));
        addCommands(new AutoDriveBackwards(chassis, inches, speed));
    }
}
