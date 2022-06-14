package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoTurnLeft extends SequentialCommandGroup {

    public AutoTurnLeft(Chassis chassis, double inches) {
        System.out.println("AutoTurnLeft starting");

        /*Position Control
        addCommands(new DriveByMotionProfile("/home/lvuser/shortTurn" + Robot.motionProfile + ".dat",
                "/home/lvuser/longTurn" + Robot.motionProfile + ".dat"));
                */

        //Motion Magic
        double heading = 90.0; //in degrees
        addCommands(new DriveByMotionMagic(chassis, inches, heading));
    }
}
