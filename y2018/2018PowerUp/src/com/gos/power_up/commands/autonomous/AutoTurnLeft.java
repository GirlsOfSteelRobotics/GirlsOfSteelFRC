package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnLeft extends CommandGroup {

    public AutoTurnLeft(Chassis chassis, double inches) {
        System.out.println("AutoTurnLeft starting");

        /*Position Control
        addSequential(new DriveByMotionProfile("/home/lvuser/shortTurn" + Robot.motionProfile + ".dat",
                "/home/lvuser/longTurn" + Robot.motionProfile + ".dat"));
                */

        //Motion Magic
        double heading = 90.0; //in degrees
        addSequential(new DriveByMotionMagic(chassis, inches, heading));
    }
}
