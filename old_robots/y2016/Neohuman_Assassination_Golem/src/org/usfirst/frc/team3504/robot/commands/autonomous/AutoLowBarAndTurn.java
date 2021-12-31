package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.NudgeFlapDown;
import org.usfirst.frc.team3504.robot.commands.NudgeFlapUp;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Flap;

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
