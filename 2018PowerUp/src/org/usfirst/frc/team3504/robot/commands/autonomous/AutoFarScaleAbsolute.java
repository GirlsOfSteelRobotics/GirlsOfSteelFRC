package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagicAbsolute;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToScale;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.TurnByMotionMagicAbsolute;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.commands.WristToShoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoFarScaleAbsolute extends CommandGroup {
	private final double DISTANCE_FORWARD_1 = 160.0;
	private final double TURN_RADIUS_1 = 110.0;
	private final double TURN_HEADING_1 = 90.0;//absolute value
	private final double DISTANCE_SIDE_1 = 110.0;
	private final double TURN_RADIUS_2 = 80.0;
	private final double TURN_HEADING_2 = 10;
	private final double BACK_UP = 30.0;

    public AutoFarScaleAbsolute(FieldSide scaleSide) {
    	System.out.println("AutoFarScaleAbsolute starting: scaleSide=" + scaleSide);
    	
    	addSequential(new WristToShoot());
    	addParallel(new WristHold());
    	
    	//Initial forward distance past switch
    	if (scaleSide == FieldSide.right) addSequential(new DriveByMotionMagicAbsolute(DISTANCE_FORWARD_1 + 10.0, 0, false));
    	else addSequential(new DriveByMotionMagicAbsolute(DISTANCE_FORWARD_1, 0, false));
    	
    	//First turn behind the switch
    	if (scaleSide == FieldSide.right) addSequential(new DriveByMotionMagicAbsolute((TURN_RADIUS_1), -TURN_HEADING_1, true));
    	else addSequential(new DriveByMotionMagicAbsolute(TURN_RADIUS_1, TURN_HEADING_1, true));
    	
    	//Get lift and wrist into position
    	addSequential(new LiftToScale());
    	addParallel(new LiftHold());
    	
    	//Driving across the field behind the switch
    	if (scaleSide == FieldSide.right) addSequential(new DriveByMotionMagicAbsolute((DISTANCE_SIDE_1 - 7.0), -TURN_HEADING_1, false));
    	else addSequential(new DriveByMotionMagicAbsolute(DISTANCE_SIDE_1, TURN_HEADING_1, false));
    	
    	//Turning towards the scale
    	if (scaleSide == FieldSide.right) 
		{
    		addSequential(new DriveByMotionMagicAbsolute(TURN_RADIUS_2, TURN_HEADING_2, true));
		}
    	else 
		{
    		addSequential(new DriveByMotionMagicAbsolute(TURN_RADIUS_2, -TURN_HEADING_2, true));
		}

    	//Release cube
    	addParallel(new ReleaseFast(0.3));
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByMotionMagic(-BACK_UP, 0));

    	//Put lift down and stop collector motors
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    }
}
