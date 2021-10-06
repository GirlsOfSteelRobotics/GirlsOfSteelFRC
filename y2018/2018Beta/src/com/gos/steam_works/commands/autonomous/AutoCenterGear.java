package com.gos.steam_works.commands.autonomous;

import com.gos.steam_works.GripPipelineListener;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.DriveByVision;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCenterGear extends CommandGroup {

    public AutoCenterGear(Chassis chassis, Shifters shifters, GripPipelineListener listener) {
        addSequential(new DriveByVision(chassis, listener));
        addSequential(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));
    }
}
