package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveToBaseline extends CommandGroup {
	
	private final double DISTANCE_FORWARD = 121;

    public AutoDriveToBaseline() {
        
    	System.out.println("AutoDriveToBaseline");
    	
    	addSequential(new DriveByMotionMagic(DISTANCE_FORWARD, 0));
    	
    	/*Position Control
    	addSequential(new DriveByDistance(DISTANCE_FORWARD, Shifters.Speed.kLow));
    	*/
    }
}
