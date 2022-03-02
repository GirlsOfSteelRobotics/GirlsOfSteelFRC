package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagicAbsolute;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoDriveToBaseline extends SequentialCommandGroup {

    public AutoDriveToBaseline(Chassis chassis) {

        System.out.println("AutoDriveToBaseline");

        addCommands(new DriveByMotionMagicAbsolute(chassis, 160.0, 0, false));
    }
}
