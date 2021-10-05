/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.power_up.subsystems;

import com.gos.power_up.Robot;
import com.gos.power_up.RobotMap;
import com.gos.power_up.commands.DriveByJoystick;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {
	
	//Drive talons
	private WPI_TalonSRX driveLeftA;
	private WPI_TalonSRX driveLeftB;
	private WPI_TalonSRX driveLeftC;
	
	private WPI_TalonSRX driveRightA;
	private WPI_TalonSRX driveRightB;
	private WPI_TalonSRX driveRightC;
	
	//Motion Magic constants
	public final static int REMOTE_ENCODER = 0;
	public final static int REMOTE_PIGEON = 1;
	public final static int PID_DISTANCE = 0;
	public final static int PID_TURNING = 1;
	public final static int SLOT_DISTANCE = 0;
	public final static int SLOT_TURNING = 1;
	
	public final static double TURN_UNITS_PER_ROTATION = 3600;
	public final static int PIGEON_UNITS_PER_ROTATION = 8192;

	PigeonIMU pigeonIMU;
	
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public DifferentialDrive drive;
	
	public Chassis() {
		
		pigeonIMU = new PigeonIMU(Robot.collector.getRightCollector());
		
		driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
		driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);
		
		setFollowerMode();
		
		driveRightA.setSensorPhase(true);
		driveLeftA.setSensorPhase(true);
		
		driveRightA.configForwardSoftLimitEnable(false, 0);
		driveLeftA.configForwardSoftLimitEnable(false, 0);
		
		driveRightA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		driveLeftA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		
		driveLeftA.setNeutralMode(NeutralMode.Brake);
		driveLeftB.setNeutralMode(NeutralMode.Brake);
		driveRightA.setNeutralMode(NeutralMode.Brake);
		driveRightB.setNeutralMode(NeutralMode.Brake);
		
		driveLeftA.setSafetyEnabled(false);
		driveLeftB.setSafetyEnabled(false);
		driveRightA.setSafetyEnabled(false);
		driveRightB.setSafetyEnabled(false);
    	
    	driveLeftA.setName("Chassis", "driveLeftA");
    	driveLeftB.setName("Chassis", "driveLeftB");
    	driveRightA.setName("Chassis", "driveRightA");
    	driveRightB.setName("Chassis", "driveRightB");
    	addChild(driveLeftA);
    	addChild(driveLeftB);
    	addChild(driveLeftC);
    	addChild(driveRightA);
    	addChild(driveRightB);
    	addChild(driveRightC);
    	
    	setupFPID(driveLeftA);
    	setupFPID(driveRightA);
    	
    	driveLeftA.configClosedloopRamp(0, 10);
    	driveRightA.configClosedloopRamp(0, 10);
    	// clyde values
    	//driveLeftA.configOpenloopRamp(0.25, 10);
    	//driveRightA.configOpenloopRamp(0.25, 10);
    	
    	//blinky values
    	driveLeftA.configOpenloopRamp(0.37, 10);
    	driveRightA.configOpenloopRamp(0.37, 10);

    	
    	driveLeftA.configPeakOutputForward(1, 10);
    	driveLeftA.configPeakOutputReverse(-1, 10);
    	
    	driveRightA.configPeakOutputForward(1, 10);
    	driveRightA.configPeakOutputReverse(-1, 10);
		
		drive = new DifferentialDrive(driveLeftA, driveRightA);
		drive.setSafetyEnabled(false);
		
		drive.setDeadband(0.02);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveByJoystick());
		// Set the command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void setupFPID(WPI_TalonSRX talon) { //PID values from DriveByDistance
		//Motion Magic
		
		/* distance servo */
		driveRightA.config_kP(SLOT_DISTANCE, 0.014, 10); //0.03
		driveRightA.config_kI(SLOT_DISTANCE, 0.0, 10); //0 
		driveRightA.config_kD(SLOT_DISTANCE, 6.0, 10); //.02
		driveRightA.config_kF(SLOT_DISTANCE, 0.08, 10); //.05
		driveRightA.config_IntegralZone(SLOT_DISTANCE, 100, 10);
		driveRightA.configClosedLoopPeakOutput(SLOT_DISTANCE, 0.70, 10);

		/* turn servo */
		driveRightA.config_kP(SLOT_TURNING, 4.0, 10);
		driveRightA.config_kI(SLOT_TURNING, 0.0, 10);
		driveRightA.config_kD(SLOT_TURNING, 6.0, 10);
		driveRightA.config_kF(SLOT_TURNING, 0.0, 10);
		driveRightA.config_IntegralZone(SLOT_TURNING, 200, 10);
		driveRightA.configClosedLoopPeakOutput(SLOT_TURNING, 1.00, 10);
		
		/* DriveByDistance for Clyde
		talon.config_kF(0, 0, 10);
		talon.config_kP(0, 0.025, 10); //increase until overshoot/oscillation
		talon.config_kI(0, 0, 10);
		talon.config_kD(0, 6.5, 10); //D is around 1/10 to 1/100 of P value

		
		talon.config_kF(1, 0.3008, 10);
		talon.config_kP(1, 0, 10); //increase until overshoot/oscillation
		talon.config_kI(1, 0, 10);
		talon.config_kD(1, 0, 10);
		*/
//		if (speed == Shifters.Speed.kLow){
//			talon.config_kF(0, 0, 10);
//			talon.config_kP(0, 0.3, 10);
//			talon.config_kI(0, 0, 10);
//			talon.config_kD(0, 0.001, 10);
//		}
//		else if (speed == Shifters.Speed.kHigh){
//			talon.config_kF(0, 0, 10);
//			talon.config_kP(0, 0.3, 10);
//			talon.config_kI(0, 0, 10);
//			talon.config_kD(0, 0.001, 10);
//		} else {
//			System.out.println("No bueno PID setting");
//		}
	}
	
	public WPI_TalonSRX getLeftTalon() {
		return driveLeftA;
	}
	
	public WPI_TalonSRX getRightTalon() {
		return driveRightA;
	}
	
	public void stop() {
		drive.stopMotor();
	}
	
	public void setFollowerMode() {
		driveLeftB.follow(driveLeftA, FollowerType.PercentOutput);
		
		driveRightB.follow(driveRightA, FollowerType.PercentOutput);
	}
	
	public void setPositionPIDSlot()
	{
		driveLeftA.selectProfileSlot(0, 0);
		driveRightA.selectProfileSlot(0, 0);
	}
	
	public void setVelocityPIDSlot()
	{
		driveLeftA.selectProfileSlot(1, 0);
		driveRightA.selectProfileSlot(1, 0);
	}
	
	public void setInverted(boolean inverted)
	{
		driveLeftA.setInverted(false);
		driveLeftB.setInverted(false);
		
		driveRightA.setInverted(inverted);
		driveRightB.setInverted(inverted);
	}
	
	public void configForTurnByMotionMagic()
	{

		/* Remote 1 will be a pigeon */
		driveRightA.configRemoteFeedbackFilter(Robot.collector.getRightCollectorID(), RemoteSensorSource.GadgeteerPigeon_Yaw, REMOTE_PIGEON, 10);
		
		driveRightA.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 0, 10);
		driveRightA.configSelectedFeedbackCoefficient(TURN_UNITS_PER_ROTATION / PIGEON_UNITS_PER_ROTATION, 0, 10);
	
		//telemetry------------------
		driveRightA.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 10);
		driveRightA.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 10);
		driveRightA.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 10);
		driveRightA.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, 10);
	
		driveLeftA.configNeutralDeadband(0.001, 10);
		driveRightA.configNeutralDeadband(0.001, 10);

		driveRightA.configMotionAcceleration(3600 / 20, 10);
		driveRightA.configMotionCruiseVelocity(162, 10); // = 0.9 * (3600 / 20)

		/* max out the peak output (for all modes).  However you can
		 * limit the output of a given PID object with configClosedLoopPeakOutput().
		 */
		driveLeftA.configPeakOutputForward(+1.0, 10);
		driveLeftA.configPeakOutputReverse(-1.0, 10);
		driveRightA.configPeakOutputForward(+1.0, 10);
		driveRightA.configPeakOutputReverse(-1.0, 10);
		
		driveLeftA.setNeutralMode(NeutralMode.Brake);
		driveRightA.setNeutralMode(NeutralMode.Brake);
		
		//pid loop speed = 1ms
		driveRightA.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, 0, 10);
	
		driveRightA.selectProfileSlot(SLOT_TURNING, 0);

	}
	
	public void configForMotionMagic()
	{
		driveLeftA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_DISTANCE, 10);
		/* Remote 0 will be the other side's Talon */
		driveRightA.configRemoteFeedbackFilter(driveLeftA.getDeviceID(), RemoteSensorSource.TalonSRX_SelectedSensor, REMOTE_ENCODER, 10);
		/* Remote 1 will be a pigeon */
		driveRightA.configRemoteFeedbackFilter(Robot.collector.getRightCollectorID(), RemoteSensorSource.GadgeteerPigeon_Yaw, REMOTE_PIGEON, 10);
		/* setup sum and difference signals */
		driveRightA.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, 10);
		driveRightA.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, 10);
		driveRightA.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0, 10);
		driveRightA.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, 10);
		/* select sum for distance(0), different for turn(1) */
		driveRightA.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, PID_DISTANCE, 10);
		driveRightA.configSelectedFeedbackCoefficient(1.0, PID_DISTANCE, 10);
		driveRightA.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, PID_TURNING, 10);
		driveRightA.configSelectedFeedbackCoefficient(TURN_UNITS_PER_ROTATION / PIGEON_UNITS_PER_ROTATION, PID_TURNING, 10);
	
		//telemetry------------------
		driveRightA.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 10);
		driveRightA.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 10);
		driveRightA.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 10);
		driveRightA.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, 10);
		/* speed up the left since we are polling it's sensor */
		driveLeftA.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 10);

		driveLeftA.configNeutralDeadband(0.001, 10);
		driveRightA.configNeutralDeadband(0.001, 10);

		driveRightA.configMotionAcceleration(5000, 10); //28000
		driveRightA.configMotionCruiseVelocity(10000, 10);

		/* max out the peak output (for all modes).  However you can
		 * limit the output of a given PID object with configClosedLoopPeakOutput().
		 */
		driveLeftA.configPeakOutputForward(+1.0, 10);
		driveLeftA.configPeakOutputReverse(-1.0, 10);
		driveRightA.configPeakOutputForward(+1.0, 10);
		driveRightA.configPeakOutputReverse(-1.0, 10);
		
		driveLeftA.setNeutralMode(NeutralMode.Brake);
		driveRightA.setNeutralMode(NeutralMode.Brake);
		
		//pid loop speed = 1ms
		driveRightA.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, PID_DISTANCE, 10);
		driveRightA.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, PID_TURNING, 10);
		
		/**
		 * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
		 * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
		 */
		driveRightA.configAuxPIDPolarity(false, 10);
		
		driveRightA.selectProfileSlot(SLOT_DISTANCE, PID_DISTANCE);
		driveRightA.selectProfileSlot(SLOT_TURNING, PID_TURNING);

	}
	public void zeroSensors()
	{
		driveLeftA.getSensorCollection().setQuadraturePosition(0, 10);
		driveRightA.getSensorCollection().setQuadraturePosition(0, 10);
		pigeonIMU.setYaw(0, 10);
		pigeonIMU.setAccumZAngle(0, 10);
		System.out.println("Chassis: All sensors are zeroed.");
	}
	
	public void zeroEncoder()
	{
		driveLeftA.getSensorCollection().setQuadraturePosition(0, 10);
		driveRightA.getSensorCollection().setQuadraturePosition(0, 10);
		System.out.println("Chassis: Encoders are zeroed.");
	}
	
	
	public double getYaw()
	{
		double[] imuData = new double[3];
		pigeonIMU.getYawPitchRoll(imuData);
		return imuData[0];
	}

		
}
