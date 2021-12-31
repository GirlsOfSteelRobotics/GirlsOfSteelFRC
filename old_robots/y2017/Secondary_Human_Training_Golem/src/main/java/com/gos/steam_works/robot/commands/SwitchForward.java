package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.OI;
import com.gos.steam_works.robot.subsystems.Camera;
import com.gos.steam_works.robot.subsystems.Chassis;

/**
 *
 */
public class SwitchForward extends CommandGroup {

    public SwitchForward(OI oi, Chassis chassis, Camera camera) {
        addSequential(new SwitchToDriveForward(oi, chassis));
        addSequential(new SwitchToCamGear(camera));
    }
}
