package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class SwitchForward extends SequentialCommandGroup {

    public SwitchForward(Chassis chassis, Camera camera, OI oi) {
        addCommands(new SwitchToDriveForward(chassis, oi));
        addCommands(new SwitchToCamGear(camera));
    }
}
