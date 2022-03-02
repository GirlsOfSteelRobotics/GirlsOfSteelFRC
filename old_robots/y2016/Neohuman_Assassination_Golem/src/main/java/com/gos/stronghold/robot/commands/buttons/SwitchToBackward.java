package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.commands.DriveBackward;
import com.gos.stronghold.robot.commands.camera.SwitchToCamFlap;
import com.gos.stronghold.robot.subsystems.Camera;

/**
 *
 */
public class SwitchToBackward extends SequentialCommandGroup {

    public SwitchToBackward(OI oi, Camera camera) {
        addParallel(new SwitchToCamFlap(camera));
        addParallel(new DriveBackward(oi));
    }
}
