package org.usfirst.frc.team3504.robot.commands.buttons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.commands.DriveBackward;
import org.usfirst.frc.team3504.robot.commands.camera.SwitchToCamFlap;
import org.usfirst.frc.team3504.robot.subsystems.Camera;

/**
 *
 */
public class SwitchToBackward extends CommandGroup {

    public SwitchToBackward(OI oi, Camera camera) {
        addParallel(new SwitchToCamFlap(camera));
        addParallel(new DriveBackward(oi));
    }
}
