package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnRight extends CommandGroup {

    public AutoTurnRight() {
    	addSequential(new DriveByMotionProfile("/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat"));
    }
}
