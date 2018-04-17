package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.Collect;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorHold;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.ReleaseSlow;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMiddleSwitchTwoCube extends CommandGroup {
	//Parameters for first cube
	public final static double RIGHT_ANGLE = 50.0;
	public final static double RIGHT_DISTANCE = 63.0;
	public final static double LEFT_ANGLE = 65.0;
	public final static double LEFT_DISTANCE = 70.0;
	
	//Parameters for collecting second cube
	public final static double LONG_BACK_UP = -80.0;
	public final static double TURN_RADIUS_2 = 100.0;
	public final static double TURN_DEGREES_2 = 70.0;
	
	//After second cube
	public final static double SHORT_BACK_UP = -30.0;

    public AutoMiddleSwitchTwoCube(FieldSide switchSide) {
    	
    	//Raise lift, lower wrist to get ready to spit cube out
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Drive to the switch plate
    	if(switchSide == FieldSide.right)
    	{
    		addParallel(new DriveByMotionMagic(RIGHT_DISTANCE, -RIGHT_ANGLE));
    		addSequential(new TimeDelay(2.5));
    		addParallel(new DriveByMotionMagic(RIGHT_DISTANCE, RIGHT_ANGLE));
    		addSequential(new TimeDelay(2.0));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addParallel(new DriveByMotionMagic(LEFT_DISTANCE, LEFT_ANGLE));
    		addSequential(new TimeDelay(2.5));
    		addParallel(new DriveByMotionMagic(LEFT_DISTANCE, -LEFT_ANGLE));
    		addSequential(new TimeDelay(2.0));
    	}
    	else System.out.println("AutoMiddleSwitch: invalid switch side");
    	
    	//Release and back up
    	addParallel(new ReleaseSlow());
    	addSequential(new TimeDelay(0.5));
    	addParallel(new DriveByMotionMagic(LONG_BACK_UP, 0));
    	addSequential(new TimeDelay(2.5));
    	
    	//Put lift down and start collecting
    	addSequential(new CollectPosition());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	addParallel(new Collect());
    	
    	//Grab second cube and come back
    	if(switchSide == FieldSide.right)
    	{
    		addParallel(new DriveByMotionMagic(TURN_RADIUS_2, TURN_DEGREES_2));
    		addSequential(new TimeDelay(2.5));
    		addParallel(new CollectorHold());
    		addParallel(new DriveByMotionMagic(-TURN_RADIUS_2/2, -20, false));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addParallel(new DriveByMotionMagic(TURN_RADIUS_2, -TURN_DEGREES_2));
    		addSequential(new TimeDelay(2.5));
    		addParallel(new CollectorHold());
    		addParallel(new DriveByMotionMagic(-TURN_RADIUS_2/2, 20, false));
    	}
    	else System.out.println("AutoMiddleSwitch: invalid switch side");
    	
    	//lift up to shoot out
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Approach switch plate
    	addSequential(new TimeDelay(2.0));
    	addParallel(new DriveByMotionMagic(-LONG_BACK_UP, 0));
    	addSequential(new TimeDelay(2.0));
    	
    	//Release and back up
    	addParallel(new ReleaseSlow());
    	addSequential(new TimeDelay(0.5));
    	addSequential(new DriveByMotionMagic(SHORT_BACK_UP, 0));
    }
}
