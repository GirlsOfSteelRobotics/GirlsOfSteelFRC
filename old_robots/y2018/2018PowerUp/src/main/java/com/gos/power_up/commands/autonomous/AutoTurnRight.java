package com.gos.power_up.commands.autonomous;

import com.gos.power_up.commands.DriveByMotionMagic;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class AutoTurnRight extends SequentialCommandGroup {

    public AutoTurnRight(Chassis chassis, double inches) {
        System.out.println("AutoTurnRight starting");

        /*Position Control
        addCommands(new DriveByMotionProfile("/home/lvuser/longTurn" + Robot.motionProfile + ".dat",
                "/home/lvuser/shortTurn" + Robot.motionProfile + ".dat"));
                */

        //Motion Magic
        double heading = -90.0; //in degrees
        addCommands(new DriveByMotionMagic(chassis, inches, heading));
    }
}
