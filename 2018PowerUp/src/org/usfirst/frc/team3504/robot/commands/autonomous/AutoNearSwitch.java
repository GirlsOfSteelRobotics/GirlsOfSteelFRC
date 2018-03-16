package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot.FieldSide;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnRight;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnLeft;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearSwitch extends CommandGroup {
	
	private final double DISTANCE_FORWARD = 140.0;
	private final double DISTANCE_SIDE = 2.0;
	private final double BACK_UP = -30;

    public AutoNearSwitch(FieldSide robotPosition) {
    	System.out.println("AutoNearSwitch starting");
    	
    	//Get lift & wrist into position
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Move Robot into position
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnRight());
    	else addSequential(new AutoTurnLeft());
    	addSequential(new DriveByMotionMagic(DISTANCE_SIDE, 0));
    	
    	//Release and back up
    	addParallel(new ReleaseFast());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByMotionMagic(BACK_UP, 0));
    	
    	/*Position Control
    	//Get lift & wrist into position
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Move Robot into position
    	addSequential(new DriveByDistance(DISTANCE_FORWARD, Shifters.Speed.kLow));
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnRight());
    	else addSequential(new AutoTurnLeft());
    	addSequential(new DriveByDistance(DISTANCE_SIDE, Shifters.Speed.kLow));
    	
    	//Release and back up
    	addParallel(new Release());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByDistance(BACK_UP, Shifters.Speed.kLow));
    	*/
    }
}
