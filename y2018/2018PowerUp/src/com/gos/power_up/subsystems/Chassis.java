/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.power_up.subsystems;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public final class Chassis extends Subsystem {

    //Motion Magic constants
    private static final int REMOTE_ENCODER = 0;
    private static final int REMOTE_PIGEON = 1;
    private static final int PID_DISTANCE = 0;
    private static final int PID_TURNING = 1;
    private static final int SLOT_DISTANCE = 0;
    private static final int SLOT_TURNING = 1;

    private static final double TURN_UNITS_PER_ROTATION = 3600;
    private static final int PIGEON_UNITS_PER_ROTATION = 8192;

    //Drive talons
    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;

    private final PigeonIMU m_pigeonIMU;

    private final DifferentialDrive m_drive;

    public Chassis() {

        m_driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
        m_driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);

        m_pigeonIMU = new PigeonIMU(m_driveLeftA);

        setFollowerMode();

        m_driveRightA.setSensorPhase(true);
        m_driveLeftA.setSensorPhase(true);

        m_driveRightA.configForwardSoftLimitEnable(false, 0);
        m_driveLeftA.configForwardSoftLimitEnable(false, 0);

        m_driveRightA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        m_driveLeftA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);

        m_driveLeftA.setSafetyEnabled(false);
        m_driveLeftB.setSafetyEnabled(false);
        m_driveRightA.setSafetyEnabled(false);
        m_driveRightB.setSafetyEnabled(false);

        addChild("driveLeftA", m_driveLeftA);
        addChild("driveLeftB", m_driveLeftB);
        addChild("driveRightA", m_driveRightA);
        addChild("driveRightB", m_driveRightB);

        setupFPID(m_driveLeftA);
        setupFPID(m_driveRightA);

        m_driveLeftA.configClosedloopRamp(0, 10);
        m_driveRightA.configClosedloopRamp(0, 10);
        // clyde values
        //driveLeftA.configOpenloopRamp(0.25, 10);
        //driveRightA.configOpenloopRamp(0.25, 10);

        //blinky values
        m_driveLeftA.configOpenloopRamp(0.37, 10);
        m_driveRightA.configOpenloopRamp(0.37, 10);


        m_driveLeftA.configPeakOutputForward(1, 10);
        m_driveLeftA.configPeakOutputReverse(-1, 10);

        m_driveRightA.configPeakOutputForward(1, 10);
        m_driveRightA.configPeakOutputReverse(-1, 10);

        m_drive = new DifferentialDrive(m_driveLeftA, m_driveRightA);
        m_drive.setSafetyEnabled(false);

        m_drive.setDeadband(0.02);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void setupFPID(WPI_TalonSRX talon) { //PID values from DriveByDistance
        //Motion Magic

        /* distance servo */
        m_driveRightA.config_kP(SLOT_DISTANCE, 0.014, 10); //0.03
        m_driveRightA.config_kI(SLOT_DISTANCE, 0.0, 10); //0
        m_driveRightA.config_kD(SLOT_DISTANCE, 6.0, 10); //.02
        m_driveRightA.config_kF(SLOT_DISTANCE, 0.08, 10); //.05
        m_driveRightA.config_IntegralZone(SLOT_DISTANCE, 100, 10);
        m_driveRightA.configClosedLoopPeakOutput(SLOT_DISTANCE, 0.70, 10);

        /* turn servo */
        m_driveRightA.config_kP(SLOT_TURNING, 4.0, 10);
        m_driveRightA.config_kI(SLOT_TURNING, 0.0, 10);
        m_driveRightA.config_kD(SLOT_TURNING, 6.0, 10);
        m_driveRightA.config_kF(SLOT_TURNING, 0.0, 10);
        m_driveRightA.config_IntegralZone(SLOT_TURNING, 200, 10);
        m_driveRightA.configClosedLoopPeakOutput(SLOT_TURNING, 1.00, 10);

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
        //        if (speed == Shifters.Speed.kLow){
        //            talon.config_kF(0, 0, 10);
        //            talon.config_kP(0, 0.3, 10);
        //            talon.config_kI(0, 0, 10);
        //            talon.config_kD(0, 0.001, 10);
        //        }
        //        else if (speed == Shifters.Speed.kHigh){
        //            talon.config_kF(0, 0, 10);
        //            talon.config_kP(0, 0.3, 10);
        //            talon.config_kI(0, 0, 10);
        //            talon.config_kD(0, 0.001, 10);
        //        } else {
        //            System.out.println("No bueno PID setting");
        //        }
    }

    public WPI_TalonSRX getLeftTalon() {
        return m_driveLeftA;
    }

    public WPI_TalonSRX getRightTalon() {
        return m_driveRightA;
    }

    public void stop() {
        m_drive.stopMotor();
    }

    public void setFollowerMode() {
        m_driveLeftB.follow(m_driveLeftA, FollowerType.PercentOutput);

        m_driveRightB.follow(m_driveRightA, FollowerType.PercentOutput);
    }

    public void setPositionPIDSlot() {
        m_driveLeftA.selectProfileSlot(0, 0);
        m_driveRightA.selectProfileSlot(0, 0);
    }

    public void setVelocityPIDSlot() {
        m_driveLeftA.selectProfileSlot(1, 0);
        m_driveRightA.selectProfileSlot(1, 0);
    }

    public void setInverted(boolean inverted) {
        m_driveLeftA.setInverted(false);
        m_driveLeftB.setInverted(false);

        m_driveRightA.setInverted(inverted);
        m_driveRightB.setInverted(inverted);
    }

    public void configForTurnByMotionMagic() {

        /* Remote 1 will be a pigeon */
        m_driveRightA.configRemoteFeedbackFilter(m_driveLeftA.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, REMOTE_PIGEON, 10);

        m_driveRightA.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 0, 10);
        m_driveRightA.configSelectedFeedbackCoefficient(TURN_UNITS_PER_ROTATION / PIGEON_UNITS_PER_ROTATION, 0, 10);

        //telemetry------------------
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 10);
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 10);
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 10);
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, 10);

        m_driveLeftA.configNeutralDeadband(0.001, 10);
        m_driveRightA.configNeutralDeadband(0.001, 10);

        m_driveRightA.configMotionAcceleration(3600.0 / 20, 10);
        m_driveRightA.configMotionCruiseVelocity(162, 10); // = 0.9 * (3600 / 20)

        /* max out the peak output (for all modes).  However you can
         * limit the output of a given PID object with configClosedLoopPeakOutput().
         */
        m_driveLeftA.configPeakOutputForward(+1.0, 10);
        m_driveLeftA.configPeakOutputReverse(-1.0, 10);
        m_driveRightA.configPeakOutputForward(+1.0, 10);
        m_driveRightA.configPeakOutputReverse(-1.0, 10);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);

        //pid loop speed = 1ms
        m_driveRightA.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, 0, 10);

        m_driveRightA.selectProfileSlot(SLOT_TURNING, 0);

    }

    public void configForMotionMagic() {
        m_driveLeftA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_DISTANCE, 10);
        /* Remote 0 will be the other side's Talon */
        m_driveRightA.configRemoteFeedbackFilter(m_driveLeftA.getDeviceID(), RemoteSensorSource.TalonSRX_SelectedSensor, REMOTE_ENCODER, 10);
        /* Remote 1 will be a pigeon */
        m_driveRightA.configRemoteFeedbackFilter(m_driveLeftA.getDeviceID(), RemoteSensorSource.GadgeteerPigeon_Yaw, REMOTE_PIGEON, 10);
        /* setup sum and difference signals */
        m_driveRightA.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, 10);
        m_driveRightA.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, 10);
        m_driveRightA.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0, 10);
        m_driveRightA.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, 10);
        /* select sum for distance(0), different for turn(1) */
        m_driveRightA.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, PID_DISTANCE, 10);
        m_driveRightA.configSelectedFeedbackCoefficient(1.0, PID_DISTANCE, 10);
        m_driveRightA.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, PID_TURNING, 10);
        m_driveRightA.configSelectedFeedbackCoefficient(TURN_UNITS_PER_ROTATION / PIGEON_UNITS_PER_ROTATION, PID_TURNING, 10);

        //telemetry------------------
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 10);
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 10);
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 10);
        m_driveRightA.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, 10);
        /* speed up the left since we are polling it's sensor */
        m_driveLeftA.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 10);

        m_driveLeftA.configNeutralDeadband(0.001, 10);
        m_driveRightA.configNeutralDeadband(0.001, 10);

        m_driveRightA.configMotionAcceleration(5000, 10); //28000
        m_driveRightA.configMotionCruiseVelocity(10000, 10);

        /* max out the peak output (for all modes).  However you can
         * limit the output of a given PID object with configClosedLoopPeakOutput().
         */
        m_driveLeftA.configPeakOutputForward(+1.0, 10);
        m_driveLeftA.configPeakOutputReverse(-1.0, 10);
        m_driveRightA.configPeakOutputForward(+1.0, 10);
        m_driveRightA.configPeakOutputReverse(-1.0, 10);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);

        //pid loop speed = 1ms
        m_driveRightA.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, PID_DISTANCE, 10);
        m_driveRightA.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, PID_TURNING, 10);

        /**
         * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
         * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
         */
        m_driveRightA.configAuxPIDPolarity(false, 10);

        m_driveRightA.selectProfileSlot(SLOT_DISTANCE, PID_DISTANCE);
        m_driveRightA.selectProfileSlot(SLOT_TURNING, PID_TURNING);

    }

    public void zeroSensors() {
        m_driveLeftA.getSensorCollection().setQuadraturePosition(0, 10);
        m_driveRightA.getSensorCollection().setQuadraturePosition(0, 10);
        m_pigeonIMU.setYaw(0, 10);
        m_pigeonIMU.setAccumZAngle(0, 10);
        System.out.println("Chassis: All sensors are zeroed.");
    }

    public void zeroEncoder() {
        m_driveLeftA.getSensorCollection().setQuadraturePosition(0, 10);
        m_driveRightA.getSensorCollection().setQuadraturePosition(0, 10);
        System.out.println("Chassis: Encoders are zeroed.");
    }


    public double getYaw() {
        double[] imuData = new double[3];
        m_pigeonIMU.getYawPitchRoll(imuData);
        return imuData[0];
    }

    public void curvatureDrive(double throttle, double rotation, boolean isQuickTurn) {
        m_drive.curvatureDrive(throttle, rotation, isQuickTurn);
    }
}
