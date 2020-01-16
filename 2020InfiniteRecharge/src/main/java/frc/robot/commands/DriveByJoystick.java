package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Chassis;

public class DriveByJoystick extends CommandBase {

  Chassis chassis;
  OI oi;

	public DriveByJoystick(Chassis chassis, OI oi) {
		this.chassis = chassis;
		this.oi = oi;
		// Use requires() here to declare subsystem dependencies
		super.addRequirements(chassis);
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() { 
		// 4 is the axis number right x on the gamepad
		chassis.driveByJoystick(oi.getJoystickSpeed(),oi.getJoystickSpin());
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	public void end(boolean interrupted) {
		chassis.stop();
  }
}