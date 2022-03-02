package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.commands.DriveBackward;
import com.gos.stronghold.robot.commands.camera.SwitchToCamFlap;
import com.gos.stronghold.robot.subsystems.Camera;

/**
 *
 */
public class SwitchToBackward extends ParallelCommandGroup {

    public SwitchToBackward(OI oi, Camera camera) {
        addCommands(new SwitchToCamFlap(camera));
        addCommands(new DriveBackward(oi));
    }
}
