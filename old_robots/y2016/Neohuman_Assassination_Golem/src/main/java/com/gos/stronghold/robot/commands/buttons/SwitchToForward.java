package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.commands.DriveForward;
import com.gos.stronghold.robot.commands.camera.SwitchToCamClaw;
import com.gos.stronghold.robot.subsystems.Camera;

/**
 *
 */
public class SwitchToForward extends SequentialCommandGroup {

    public SwitchToForward(OI oi, Camera camera) {
        addParallel(new SwitchToCamClaw(camera));
        addParallel(new DriveForward(oi));
    }
}
