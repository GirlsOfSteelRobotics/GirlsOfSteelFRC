package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.GripPipelineListener;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoCenterGear extends SequentialCommandGroup {

    public AutoCenterGear(Chassis chassis, Shifters shifters, GripPipelineListener listener) {
        addCommands(new DriveByVision(chassis, listener));
        addCommands(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));
    }
}
