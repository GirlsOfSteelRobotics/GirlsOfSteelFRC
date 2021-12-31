package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnRight extends CommandGroup {

    public AutoTurnRight(Chassis chassis, double inches) {
        System.out.println("AutoTurnRight starting");

        /*Position Control
        addSequential(new DriveByMotionProfile("/home/lvuser/longTurn" + Robot.motionProfile + ".dat",
                "/home/lvuser/shortTurn" + Robot.motionProfile + ".dat"));
                */

        //Motion Magic
        double heading = -90.0; //in degrees
        addSequential(new DriveByMotionMagic(chassis, inches, heading));
    }
}
