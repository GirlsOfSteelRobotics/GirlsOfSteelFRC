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
public class AutoMiddleSwitchTwoCubeS extends CommandGroup {
	//Parameters for first cube
	public final static double RIGHT_ANGLE = 53.0;
	public final static double RIGHT_DISTANCE = 72.0;
	public final static double LEFT_ANGLE = 65.0;
	public final static double LEFT_DISTANCE = 75.0;
	
	//Parameters for collecting second cube
	public final static double LONG_BACK_UP = -80.0;
	public final static double TURN_RADIUS_2 = 100.0;
	public final static double TURN_DEGREES_2 = 70.0;
	
	//After second cube
	public final static double SHORT_BACK_UP = -30.0;

    public AutoMiddleSwitchTwoCubeS(FieldSide switchSide) {
    	
    	//Raise lift, lower wrist to get ready to spit cube out
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Drive to the switch plate
    	if(switchSide == FieldSide.right)
    	{
    		addSequential(new DriveByMotionMagic(RIGHT_DISTANCE, -RIGHT_ANGLE));
    		addSequential(new DriveByMotionMagic(RIGHT_DISTANCE, RIGHT_ANGLE));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addSequential(new DriveByMotionMagic(LEFT_DISTANCE, LEFT_ANGLE));
    		addSequential(new DriveByMotionMagic(LEFT_DISTANCE, -LEFT_ANGLE));
    	}
    	else System.out.println("AutoMiddleSwitch: invalid switch side");
    	
    	//Release and back up
    	addParallel(new ReleaseSlow());
    	addSequential(new TimeDelay(0.5));
    	addSequential(new DriveByMotionMagic(LONG_BACK_UP, 0));
    	
    	//Put lift down and start collecting
    	addSequential(new CollectPosition());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	addParallel(new Collect());
    	
    	//Grab second cube and come back
    	if(switchSide == FieldSide.right)
    	{
    		addSequential(new DriveByMotionMagic(TURN_RADIUS_2, TURN_DEGREES_2));
    		addParallel(new CollectorHold());
    		addSequential(new DriveByMotionMagic(-TURN_RADIUS_2/2, -20, false));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addSequential(new DriveByMotionMagic(TURN_RADIUS_2, -TURN_DEGREES_2));
    		addParallel(new CollectorHold());
    		addSequential(new DriveByMotionMagic(-TURN_RADIUS_2/2, 20, false));
    	}
    	else System.out.println("AutoMiddleSwitch: invalid switch side");
    	
    	//lift up to shoot out
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Approach switch plate
    	addSequential(new DriveByMotionMagic(-LONG_BACK_UP, 0));
    	
    	//Release and back up
    	addParallel(new ReleaseSlow());
    	addSequential(new TimeDelay(0.5));
    	addSequential(new DriveByMotionMagic(SHORT_BACK_UP, 0));
    }
}
