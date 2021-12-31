package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.NudgeFlapDown;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Flap;

/**
 *
 */
public class FlapThenLowBar extends CommandGroup {

    public FlapThenLowBar(Chassis chassis, Flap flap, double inches, double speed) {
        addSequential(new NudgeFlapDown(flap));
        addSequential(new AutoDriveBackwards(chassis, inches, speed));
    }
}
