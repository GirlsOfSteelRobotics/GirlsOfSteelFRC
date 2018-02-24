package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnLeft extends CommandGroup {

    public AutoTurnLeft() {
        addSequential(new DriveByMotionProfile("/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat"));
    }
}
