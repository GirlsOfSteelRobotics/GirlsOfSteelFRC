package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.TurnByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnToGear.Direction;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBoilerGearAndShoot extends CommandGroup {

    public AutoBoilerGearAndShoot(double distance, Direction direction) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
    	addSequential(new DriveByDistance(distance, Shifters.Speed.kLow));
		if (direction == Direction.kLeft){
			addSequential(new DriveByMotionProfile("/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
		} else if (direction == Direction.kRight){
			addSequential(new DriveByMotionProfile("/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
		}
		addSequential(new DriveByVision());
		addSequential(new DriveByMotionProfile("/home/lvuser/BackupFourInches.dat", "/home/lvuser/BackupFourInches.dat", 1.0));
		
		if (direction == Direction.kLeft){
			addParallel(new TurnByDistance(-8.0, 3.0, Shifters.Speed.kLow));
		} else if (direction == Direction.kRight){
			addParallel(new TurnByDistance(3.0, -2.0, Shifters.Speed.kLow));
		}
		
		addSequential(new TimeDelay(1.5)); 	
    	addSequential(new CombinedShootGear());

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
