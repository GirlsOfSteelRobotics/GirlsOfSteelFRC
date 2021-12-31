package org.usfirst.frc.team3504.robot.commands.buttons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.commands.DriveForward;
import org.usfirst.frc.team3504.robot.commands.camera.SwitchToCamClaw;
import org.usfirst.frc.team3504.robot.subsystems.Camera;

/**
 *
 */
public class SwitchToForward extends CommandGroup {

    public SwitchToForward(OI oi, Camera camera) {
        addParallel(new SwitchToCamClaw(camera));
        addParallel(new DriveForward(oi));
    }
}
