package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchForward extends CommandGroup {

    public SwitchForward(Chassis chassis, Camera camera, OI oi) {
        addSequential(new SwitchToDriveForward(chassis, oi));
        addSequential(new SwitchToCamGear(camera));
    }
}
