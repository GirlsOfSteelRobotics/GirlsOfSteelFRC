package com.gos.steam_works.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.steam_works.robot.commands.DriveByDistance;
import com.gos.steam_works.robot.commands.DriveByVision;
import com.gos.steam_works.robot.subsystems.Camera;
import com.gos.steam_works.robot.subsystems.Chassis;
import com.gos.steam_works.robot.subsystems.Shifters;

/**
 *
 */
public class AutoCenterGear extends CommandGroup {

    public AutoCenterGear(Chassis chassis, Shifters shifters, Camera camera) {
        addSequential(new DriveByVision(chassis, camera));
        addSequential(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));
    }
}
