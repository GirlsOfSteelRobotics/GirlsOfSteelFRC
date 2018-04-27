package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToScale;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.commands.WristToShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoFarScale extends CommandGroup {
	private final double DISTANCE_FORWARD_1 = 175.0;
	private final double TURN_RADIUS_1 = 100.0;
	private final double DISTANCE_SIDE_1 = 80.0;
	private final double TURN_RADIUS_2 = 150.0;
	private final double DEGREES_2 = 90.0;

    public AutoFarScale(FieldSide scaleSide) {
    	System.out.println("AutoFarScale starting: scaleSide=" + scaleSide);
    	
    	//Initial forward distance past switch
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD_1, 0));
    	
    	//First turn behind the switch
    	if (scaleSide == FieldSide.right) addSequential(new AutoTurnRight(TURN_RADIUS_1));
    	else addSequential(new AutoTurnLeft(TURN_RADIUS_1));
    	
    	//Get lift and wrist into position
    	addSequential(new LiftToScale());
    	addSequential(new WristToShoot());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Driving across the field behind the switch
    	if (scaleSide == FieldSide.right) addSequential(new DriveByMotionMagic(DISTANCE_SIDE_1, -90, false));
    	else addSequential(new DriveByMotionMagic(DISTANCE_SIDE_1, 90, false));
    	
    	//Turning towards the scale
    	if (scaleSide == FieldSide.right) 
		{
    		addSequential(new DriveByMotionMagic(TURN_RADIUS_2, DEGREES_2));
    		addSequential(new TurnByMotionMagic(90));
		}
    	else 
		{
    		addSequential(new DriveByMotionMagic(TURN_RADIUS_2, -DEGREES_2));
    		addSequential(new TurnByMotionMagic(-90));
		}

    	//Release cube
    	addParallel(new ReleaseFast(0.5));
    	addSequential(new TimeDelay(1.0));

    	//Put lift down and stop collector motors
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    }
}
