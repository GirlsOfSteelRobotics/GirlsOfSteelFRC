package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class SwitchBackward extends CommandGroup {

    public SwitchBackward(OI oi, Chassis chassis, Camera camera) {
        addSequential(new SwitchToDriveBackward(oi, chassis));
        addSequential(new SwitchToCamClimb(camera));
    }
}
