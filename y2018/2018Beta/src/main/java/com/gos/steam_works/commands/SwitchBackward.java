package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class SwitchBackward extends SequentialCommandGroup {

    public SwitchBackward(Chassis chassis, Camera camera, OI oi) {
        addCommands(new SwitchToDriveBackward(chassis, oi));
        addCommands(new SwitchToCamClimb(camera));
    }
}
