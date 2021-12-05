package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class SwitchForward extends CommandGroup {

    public SwitchForward(OI oi, Chassis chassis, Camera camera) {
        addSequential(new SwitchToDriveForward(oi, chassis));
        addSequential(new SwitchToCamGear(camera));
    }
}
