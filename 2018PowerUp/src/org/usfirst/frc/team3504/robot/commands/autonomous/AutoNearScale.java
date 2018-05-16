package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToScale;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.TurnInPlace;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToShoot;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearScale extends CommandGroup {

	private final double DISTANCE_FORWARD = 295.0;
	private final double DISTANCE_SIDE = -30.0;

    public AutoNearScale(FieldSide robotPosition) {
    	System.out.println("AutoNearScale starting");
    	
    	//Move Robot, lift, wrist into position
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));
    	Robot.shifters.shiftGear(Shifters.Speed.kLow);
    	if (robotPosition == FieldSide.left) 
		{
    		addSequential(new TurnByMotionMagic(-90.0));
    		addSequential(new DriveByMotionMagic(DISTANCE_SIDE, -90, false));
        	addSequential(new WristToShoot());
        	addSequential(new LiftToScale());
        	addParallel(new WristHold());
        	addParallel(new LiftHold());  		
		}
    	else 
		{
    		addSequential(new TurnByMotionMagic(90.0));
    		addSequential(new DriveByMotionMagic(DISTANCE_SIDE, 90, false));
        	addSequential(new WristToShoot());
        	addSequential(new LiftToScale());
        	addParallel(new WristHold());
        	addParallel(new LiftHold());
    		
		}
    	
    	//Wait for lift and wrist to get into position then shoot
    	addSequential(new TimeDelay(2.5));
    	addParallel(new ReleaseFast(0.75));
    	addSequential(new TimeDelay(1.5));
    	
    	//Put lift down and stop collector
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
        
    }
}
