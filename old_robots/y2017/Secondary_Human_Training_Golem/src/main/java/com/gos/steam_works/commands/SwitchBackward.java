package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;

/**
 *
 */
public class SwitchBackward extends SequentialCommandGroup {

    public SwitchBackward(OI oi, Chassis chassis, Camera camera) {
        addCommands(new SwitchToDriveBackward(oi, chassis));
        addCommands(new SwitchToCamClimb(camera));
    }
}
