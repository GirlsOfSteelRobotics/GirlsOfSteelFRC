package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot.FieldSide;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMiddleSwitch extends CommandGroup {
	
	public final static double TURN1 = 50.0;
	public final static double TURN2 = 50.0;
	public final static double BACK_UP = -30.0;

    public AutoMiddleSwitch(FieldSide switchSide) {
    	
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	if(switchSide == FieldSide.right)
    	{
    		addSequential(new DriveByMotionMagic(TURN1, -45.0));
    		addSequential(new DriveByMotionMagic(TURN2, 0, false));
    	}
    	else if (switchSide == FieldSide.left)
    	{
    		addSequential(new DriveByMotionMagic(TURN1, 45.0));
    		addSequential(new DriveByMotionMagic(TURN2, 0, false));
    	}
    	else System.out.println("AutoMiddleSwitch: invalid switch side");
    	
    	//Release and back up
    	addParallel(new ReleaseFast());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByMotionMagic(BACK_UP, 0));
    }
}
