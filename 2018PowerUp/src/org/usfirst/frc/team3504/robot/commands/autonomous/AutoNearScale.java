package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot.FieldSide;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToScale;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnInPlace;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.commands.WristToShoot;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoNearScale extends CommandGroup {

	private final double DISTANCE_FORWARD = 295.0;
	private final double DISTANCE_SIDE = -20.0;
	private final double DISTANCE_SIDE_2 = 20.0;
	private final double BACK_UP = -30;

    public AutoNearScale(FieldSide robotPosition) {
    	System.out.println("AutoNearScale starting");
    	
    	//Hold lift & wrist in place while driving
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Move Robot, lift, wrist into position
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));
    	if (robotPosition == FieldSide.left) 
		{
    		addSequential(new TurnInPlace(-90.0));
        	addSequential(new WristToShoot());
        	addSequential(new LiftToScale());
        	addParallel(new WristHold());
        	addParallel(new LiftHold());
    		addSequential(new DriveByMotionMagic(DISTANCE_SIDE, -90, false));
		}
    	else 
		{
    		addSequential(new TurnInPlace(90.0));
        	addSequential(new WristToShoot());
        	addSequential(new LiftToScale());
        	addParallel(new WristHold());
        	addParallel(new LiftHold());
    		addSequential(new DriveByMotionMagic(DISTANCE_SIDE, 90.0, false));
		}
    	
    	//Wait for lift and wrist to get into position then shoot
    	addSequential(new TimeDelay(2.0));
    	addParallel(new ReleaseFast(0.65));
    	
    	/*Position Control
    	//Get lift & wrist into position
    	addSequential(new WristToCollect());
    	addSequential(new LiftToScale());
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
