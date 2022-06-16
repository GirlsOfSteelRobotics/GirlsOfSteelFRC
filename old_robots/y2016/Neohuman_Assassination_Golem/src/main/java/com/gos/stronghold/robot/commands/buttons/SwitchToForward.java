package com.gos.stronghold.robot.commands.buttons;

import com.gos.stronghold.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import com.gos.stronghold.robot.commands.DriveForward;
import com.gos.stronghold.robot.commands.camera.SwitchToCamClaw;
import com.gos.stronghold.robot.subsystems.Camera;

/**
 *
 */
public class SwitchToForward extends ParallelCommandGroup {

    public SwitchToForward(Chassis chassis, Camera camera) {
        addCommands(new SwitchToCamClaw(camera));
        addCommands(new DriveForward(chassis));
    }
}
