package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;

/**
 *
 */
public class SwitchForward extends SequentialCommandGroup {

    public SwitchForward(Chassis chassis, Camera camera) {
        addCommands(new SwitchToDriveForward(chassis));
        addCommands(new SwitchToCamGear(camera));
    }
}
