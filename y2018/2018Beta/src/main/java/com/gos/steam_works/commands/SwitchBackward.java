package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchBackward extends CommandGroup {

    public SwitchBackward(Chassis chassis, Camera camera, OI oi) {
        addSequential(new SwitchToDriveBackward(chassis, oi));
        addSequential(new SwitchToCamClimb(camera));
    }
}
