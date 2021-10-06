package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagicAbsolute;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveToBaseline extends CommandGroup {

    public AutoDriveToBaseline(Chassis chassis) {

        System.out.println("AutoDriveToBaseline");

        addSequential(new DriveByMotionMagicAbsolute(chassis, 160.0, 0, false));
    }
}
