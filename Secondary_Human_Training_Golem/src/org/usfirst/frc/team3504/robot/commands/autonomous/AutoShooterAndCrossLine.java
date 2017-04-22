package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnByDistance;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShooterAndCrossLine extends CommandGroup {

	public AutoShooterAndCrossLine() {
		addParallel(new CombinedShootKey());
		addSequential(new TimeDelay(10.0));
		addSequential(new TurnByDistance(-10.0, -60.0, Shifters.Speed.kLow));
		addSequential(new DriveByDistance(-48.0, Shifters.Speed.kLow));
	}
}
