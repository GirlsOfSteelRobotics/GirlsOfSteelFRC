package com.gos.steam_works.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Shifters;

/**
 *
 */
public class AutoCenterGear extends SequentialCommandGroup {

    public AutoCenterGear(Chassis chassis, Shifters shifters, Camera camera) {
        addCommands(new DriveByVision(chassis, camera));
        addCommands(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.LOW));
    }
}
