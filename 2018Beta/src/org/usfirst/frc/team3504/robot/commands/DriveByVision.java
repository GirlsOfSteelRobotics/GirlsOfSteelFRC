package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.GripPipelineListener;
import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner.Listener;

/**
 *
 */
public class DriveByVision extends Command {


	private static final double MAX_ANGULAR_VELOCITY = 1.0; // TODO: adjust
	// (rad/s) current
	// value works
	private static final int IMAGE_WIDTH = 320;
	private static final double IMAGE_CENTER = IMAGE_WIDTH / 2.0;
	double[] defaultValue = new double[0];
	public WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	public WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	private final int TIMEOUT = 8;
	private final int SLIPPING_VELOCITY = 850;
	private Timer tim;
	private double slowLinearVelocity = 22; // TODO: change (in/s)
	private double fastLinearVelocity = 28; // TODO: change (in/s)

	// width of X or Y in pixels when the robot is at the lift
	// private static final double GOAL_WIDTH = 30; //TODO: test and change

	// distance b/w wheels
	private static final double WHEEL_BASE = 24.25; // (in)

	// radius of wheel
	private static final int WHEEL_RADIUS = 2; // (in)

	public DriveByVision() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.chassis);
		tim = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		// not calling setupFPID because other PID values override
		leftTalon.setSelectedSensorPosition(0, 0, 0);
		rightTalon.setSelectedSensorPosition(0, 0, 0);

		// tuned by janet and ziya on 2/20, overrides PID set in chassis method
		leftTalon.config_kF(0, 0.22, 0); //carpet on practice field
		leftTalon.config_kP(0, 0.235, 0);
		rightTalon.config_kF(0, 0.22, 0); //carpet on practice field
		rightTalon.config_kP(0, 0.235, 0);

		System.out.println("DriveByVision Initialized");

		tim.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		/*table = NetworkTable.getTable("GRIP/myContoursReport");

		double[] centerX = new double[2];
		centerX = table.getNumberArray("centerX", defaultValue);*/
		/*
		 * double[] centerY = new double[2]; centerY =
		 * table.getNumberArray("centerY", defaultValue);

		double[] height = new double[2];
		height = table.getNumberArray("height", defaultValue);*/
		/*
		 * double[] width = new double[2]; width = table.getNumberArray("width",
		 * defaultValue);
		 */
		double targetX;
		double height;
		synchronized(Robot.listener.cameraLock){
			targetX = Robot.listener.targetX;
			height = Robot.listener.height;
		}
		// the center of the x and y rectangles (the target)
		double goalAngularVelocity;
		if (targetX < 0) {
			goalAngularVelocity = 0;
			SmartDashboard.putBoolean("Gear In Sight", false);
		} else {
			//double targetX = (centerX[0] + centerX[1]) / 2.0;
			double error = (targetX - IMAGE_CENTER) / IMAGE_CENTER;
			goalAngularVelocity = error * MAX_ANGULAR_VELOCITY;
			SmartDashboard.putBoolean("Gear In Sight", true);
			SmartDashboard.putNumber("TargetX", targetX);
			SmartDashboard.putNumber("Height", height);
		}


		double goalLinearVelocity;
		if (height < 0 && tim.get() < 1)
			goalLinearVelocity = fastLinearVelocity;
		else if (height < 0) {
			goalLinearVelocity = slowLinearVelocity;
		} else if (height >= 52.0)
			goalLinearVelocity = slowLinearVelocity;
		else
			goalLinearVelocity = fastLinearVelocity;


		// right and left desired wheel speeds in inches per second
		double vRight = goalLinearVelocity - (WHEEL_BASE * goalAngularVelocity) / 2; // (in/s)
		double vLeft = goalLinearVelocity + (WHEEL_BASE * goalAngularVelocity) / 2;
		// right and left desired wheel speeds in RPM
		double angVRight = 75 * vRight / (2 * Math.PI * WHEEL_RADIUS); // (RPM)
		double angVLeft = 75 * vLeft / (2 * Math.PI * WHEEL_RADIUS);
		// send desired wheel speeds to Talon set to velocity control mode
		rightTalon.set(ControlMode.Velocity, angVRight);
		leftTalon.set(ControlMode.Velocity, -angVLeft);

		if (targetX >= 0){
			System.out.println("Number of Contours: " + 2/*centerX.length*/ + " Goal Linear Velocity: " + goalLinearVelocity
					+ " Goal Angular Velocity: " + goalAngularVelocity + " Timer: " + tim.get());
		}
		else {
			System.out.println("Number of Contours: " + "not 2" /*centerX.length*/ + " Goal Linear Velocity: " + goalLinearVelocity
					+ " Goal Angular Velocity: " + goalAngularVelocity + " Timer: " + tim.get());
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {

		return ((tim.get() > 1 && Math.abs(leftTalon.getSelectedSensorVelocity(0)) < SLIPPING_VELOCITY
				&& Math.abs(rightTalon.getSelectedSensorVelocity(0)) < SLIPPING_VELOCITY) || (tim.get() > TIMEOUT));
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("DriveByVision Finished");
		tim.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}