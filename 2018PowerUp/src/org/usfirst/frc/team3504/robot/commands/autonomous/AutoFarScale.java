package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.CollectPosition;
import org.usfirst.frc.team3504.robot.commands.CollectorStop;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToScale;
import org.usfirst.frc.team3504.robot.commands.ReleaseFast;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoFarScale extends CommandGroup {
	private final double DISTANCE_FORWARD_1 = 160.0;
	private final double TURN_RADIUS_1 = 100.0;
	private final double DISTANCE_SIDE_1 = 135.0;
	private final double TURN_RADIUS_2 = 70.0;
	private final double DEGREES_2 = 120.0;
	private final double DISTANCE_FORWARD_2 = 10.0;
	private final double DISTANCE_SIDE_2 = 10.0;
	private final double BACK_UP = -30.0;

    public AutoFarScale(FieldSide robotPosition) {
    	System.out.println("AutoFarScale starting");
    	
    	//Get lift & wrist into position
    	addSequential(new WristToCollect());
    	//addSequential(new LiftToScale());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Move Robot into position
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD_1, 0));
    	
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnRight(TURN_RADIUS_1));
    	else addSequential(new AutoTurnLeft(TURN_RADIUS_1));
    	
    	addSequential(new DriveByMotionMagic(DISTANCE_SIDE_1, 0));
    	
    	if (robotPosition == FieldSide.left) addSequential(new DriveByMotionMagic(TURN_RADIUS_2, DEGREES_2));
    	else addSequential(new DriveByMotionMagic(TURN_RADIUS_2, -DEGREES_2));
    	/*
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD_2, 0));
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnLeft(25.0));
    	else addSequential(new AutoTurnRight(25.0));
    	addSequential(new DriveByMotionMagic(DISTANCE_SIDE_2, 0));
    	
    	//Release and back up
    	addParallel(new ReleaseFast());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByMotionMagic(BACK_UP, 0));

    	//Put lift down and stop collector
    	addSequential(new CollectPosition());
    	addSequential(new CollectorStop());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	*/
    }
}
