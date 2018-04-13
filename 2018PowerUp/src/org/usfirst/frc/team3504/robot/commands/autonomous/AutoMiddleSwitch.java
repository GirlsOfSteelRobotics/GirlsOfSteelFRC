package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
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
public class AutoMiddleSwitch extends CommandGroup {
	
	public final static double RIGHT_ANGLE = 53.0;
	public final static double RIGHT_DISTANCE = 72.0;
	public final static double LEFT_ANGLE = 65.0;
	public final static double LEFT_DISTANCE = 75.0;
	public final static double BACK_UP = -30.0;

    public AutoMiddleSwitch(FieldSide switchSide) {
    	
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
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
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByMotionMagic(BACK_UP, 0));
    	
    	//Put lift down and stop collector
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    }
}
