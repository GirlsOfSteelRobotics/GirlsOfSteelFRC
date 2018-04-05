package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.Collect;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.OldTurnInPlace;
import org.usfirst.frc.team3504.robot.commands.ReleaseSlow;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMiddleSwitchTwoCubeBetter extends CommandGroup {
	public final static double STRAIGHT_1 = 30.0;
	public final static double TURN_DEGREES_1 = 30.0;
	public final static double STRAIGHT_2 = 60.0;
	public final static double STRAIGHT_3 = 40.0;
	
	public final static double BACK_UP = -30.0;

    public AutoMiddleSwitchTwoCubeBetter(FieldSide switchSide) {
    	
    	//Lift to switch position, ready to release
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	
    	//Drive forward and turn in place and drive forward to switch
    	addSequential(new DriveByMotionMagic(STRAIGHT_1, 0));
    	
    	if(switchSide == FieldSide.right)
    	{
    		addSequential(new OldTurnInPlace(-TURN_DEGREES_1));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addSequential(new OldTurnInPlace(TURN_DEGREES_1));
    	}
    	
    	addSequential(new DriveByMotionMagic(STRAIGHT_2, 0));
    	
    	
    	//Spit cube out
    	addParallel(new ReleaseSlow());
    	addSequential(new TimeDelay(1.0));
    	
    	//Drive back
    	addSequential(new DriveByMotionMagic(-STRAIGHT_2, 0));
    	
    	//Put lift down and start collector
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	addParallel(new Collect());
    	
    	//Turn back straight
    	if(switchSide == FieldSide.right)
    	{
    		addSequential(new OldTurnInPlace(TURN_DEGREES_1));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addSequential(new OldTurnInPlace(-TURN_DEGREES_1));
    	}
    	
    	//Drive into cube then back up into place
    	addParallel(new DriveByMotionMagic(STRAIGHT_3, 0));
    	addSequential(new TimeDelay(2.0));
    	addParallel(new DriveByMotionMagic(-STRAIGHT_3, 0));
    	
    	//Turn in place and drive forward
    	if(switchSide == FieldSide.right)
    	{
    		addSequential(new OldTurnInPlace(-TURN_DEGREES_1));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addSequential(new OldTurnInPlace(TURN_DEGREES_1));
    	}
    	
    	addSequential(new DriveByMotionMagic(STRAIGHT_2, 0));
    	
    	
    	//Spit cube out
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
