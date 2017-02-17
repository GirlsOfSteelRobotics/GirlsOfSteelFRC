package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByVision extends Command {

	NetworkTable table;

	private static final double MAX_ANGULAR_VELOCITY = 1.0; //TODO: adjust (rad/s)
	private static final int IMAGE_WIDTH = 320;
	private static final double IMAGE_CENTER = IMAGE_WIDTH/2.0; 
	double[] defaultValue = new double[0];

	public CANTalon leftTalon = Robot.chassis.driveLeftA;
	public CANTalon rightTalon = Robot.chassis.driveRightA;
	
	//width of X or Y in pixels when the robot is at the lift
	//private static final double GOAL_WIDTH = 30; //TODO: test and change

	private static double encDist;
	private static double lastEncDist;

	private static final double MIN_DIST = 0.5;

	//distance b/w wheels
	private static final double WHEEL_BASE = 20; //TODO: measure (in)

	//radius of wheel
	private static final int WHEEL_RADIUS = 2; //(in)

	public DriveByVision() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//Change motor control to speed in the -1..+1 range
		Robot.chassis.driveLeftA.changeControlMode(TalonControlMode.PercentVbus);
		Robot.chassis.driveRightA.changeControlMode(TalonControlMode.PercentVbus);

		table = NetworkTable.getTable("GRIP/myContoursReport");
		encDist = Double.NaN; 
		
		System.out.println("DriveByVision Initialized");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double[] centerX = new double[2];
		centerX = table.getNumberArray("centerX", defaultValue);
		/*double[] centerY = new double[2];
		centerY = table.getNumberArray("centerY", defaultValue);
		double[] height = new double[2];
		height = table.getNumberArray("height", defaultValue);
		double[] width = new double[2];
		width = table.getNumberArray("width", defaultValue);*/

		lastEncDist = encDist;
		encDist = Robot.chassis.getEncoderDistance();

		// the center of the x and y rectangles (the target)
		double goalAngularVelocity, error;
		if (centerX.length != 2) {
			goalAngularVelocity = 0;
			SmartDashboard.putBoolean("Gear In Sight", false);
		} else {
			double targetX = (centerX[0] + centerX[1])/2.0;
			error = (targetX - IMAGE_CENTER)/IMAGE_CENTER;
			goalAngularVelocity = error * MAX_ANGULAR_VELOCITY;
			SmartDashboard.putBoolean("Gear In Sight", true);
			SmartDashboard.putNumber("CenterX0", centerX[0]);
			SmartDashboard.putNumber("CenterX1", centerX[1]);
		}
		SmartDashboard.putNumber("Gear curve value", goalAngularVelocity);

		//Robot.chassis.drive(.25, goalAngularVelocity); //TODO: change moveValue
		
		double goalLinearVelocity = 0; //TODO: change (in/s)

		//right and left desired wheel speeds in inches per second
		double vRight = goalLinearVelocity + (WHEEL_BASE * goalAngularVelocity) / 2; //(in/s)
		double vLeft = goalLinearVelocity + (WHEEL_BASE * goalAngularVelocity) / 2;
		
		//right and left desired wheel speeds in RPM
		double angVRight = 60 * vRight / (2 * Math.PI * WHEEL_RADIUS); //(RPM)
		double angVLeft = 60 * vLeft / (2 * Math.PI * WHEEL_RADIUS);
		
		rightTalon.changeControlMode(TalonControlMode.Speed);
		leftTalon.changeControlMode(TalonControlMode.Speed);
		
		//send desired wheel speeds to Talon set to velocity control mode
		rightTalon.set(angVRight);
		leftTalon.set(angVLeft);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (lastEncDist != Double.NaN) && (Math.abs(encDist - lastEncDist) <= MIN_DIST);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
