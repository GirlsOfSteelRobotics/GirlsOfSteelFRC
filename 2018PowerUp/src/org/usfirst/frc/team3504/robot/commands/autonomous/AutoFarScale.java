package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot.FieldSide;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToScale;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.Release;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoFarScale extends CommandGroup {
	private final double DISTANCE_FORWARD_1 = 250.0;
	private final double DISTANCE_SIDE_1 = 150.0;
	private final double DISTANCE_FORWARD_2 = 10.0;
	private final double DISTANCE_SIDE_2 = 10.0;

    public AutoFarScale(FieldSide robotPosition) {
    	System.out.println("AutoFarScale starting");
    	//Get lift & wrist into position
    	addSequential(new WristToCollect());
    	addSequential(new LiftToScale());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	
    	//Move Robot into position
    	addSequential(new DriveByDistance(DISTANCE_FORWARD_1, Shifters.Speed.kLow));
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnRight());
    	else addSequential(new AutoTurnLeft());
    	addSequential(new DriveByDistance(DISTANCE_SIDE_1, Shifters.Speed.kLow));
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnLeft());
    	else addSequential(new AutoTurnRight());
    	addSequential(new DriveByDistance(DISTANCE_FORWARD_2, Shifters.Speed.kLow));
    	if (robotPosition == FieldSide.left) addSequential(new AutoTurnLeft());
    	else addSequential(new AutoTurnRight());
    	addSequential(new DriveByDistance(DISTANCE_SIDE_2, Shifters.Speed.kLow));
    	
    	//Release and back up
    	addParallel(new Release());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByDistance(-30, Shifters.Speed.kLow));
    }
}
