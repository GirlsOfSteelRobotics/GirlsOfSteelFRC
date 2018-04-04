package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnRight;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnLeft;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearSwitch extends CommandGroup {
	
	private final double DISTANCE_FORWARD = 130.0;
	private final double DISTANCE_SIDE = 0.0;
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
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnRight(25.0));
    	else addSequential(new AutoTurnLeft(25.0));
    	addSequential(new DriveByMotionMagic(DISTANCE_SIDE, 0));
    	
    	//Release and back up
    	addParallel(new ReleaseFast());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByMotionMagic(BACK_UP, 0));
    	
    	//Put lift down and stop collector
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    }
}
