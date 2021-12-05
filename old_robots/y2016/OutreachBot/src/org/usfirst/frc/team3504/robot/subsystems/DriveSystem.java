package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSystem extends Subsystem {
    private final CANTalon driveLeftA;
    private final CANTalon driveLeftB;
    private final CANTalon driveLeftC;

    private final CANTalon driveRightA;
    private final CANTalon driveRightB;
    private final CANTalon driveRightC;

    private final RobotDrive robotDrive;

    private double encOffsetValue = 0;

    public DriveSystem() {
        driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A_CAN_ID);
        //addChild("Drive Left A", driveLeftA);
        driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B_CAN_ID);
        //addChild("Drive Left B", driveLeftB);
        driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C_CAN_ID);
        //addChild("Drive Left C", driveLeftC);

        driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A_CAN_ID);
        //addChild("Drive Right A", driveRightA);
        driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B_CAN_ID);
        //addChild("Drive Right B", driveRightB);
        driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C_CAN_ID);
        //addChild("Drive Right C", driveRightC);

        // On each side, all three drive motors MUST run at the same speed.
        // Use the CAN Talon Follower mode to set the speed of B and C,
        // making always run at the same speed as A.
        driveLeftB.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveLeftC.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveRightB.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveRightC.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveLeftB.set(driveLeftA.getDeviceID());
        driveLeftC.set(driveLeftA.getDeviceID());
        driveRightB.set(driveRightA.getDeviceID());
        driveRightC.set(driveRightA.getDeviceID());

        // Define a robot drive object in terms of only the A motors.
        // The B and C motors will play along at the same speed (see above.)
        robotDrive = new RobotDrive(driveLeftA, driveRightA);

        // Set some safety controls for the drive system
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setSensitivity(0.5);
        robotDrive.setMaxOutput(1.0);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveByJoystick());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void takeJoystickInputs(Joystick joystk) {
        robotDrive.arcadeDrive(joystk);
    }

    public void forward() {
        robotDrive.drive(1.0, 0);
    }

    public void stop() {
        robotDrive.drive(/* speed */0, /* curve */0);
    }

    public double getEncoderRight() {
        return driveRightA.getEncPosition();
    }

    public double getEncoderLeft() {
        return driveLeftA.getEncPosition();
    }

    public double getEncoderDistance() {
        return (getEncoderLeft() - encOffsetValue) * RobotMap.DISTANCE_PER_PULSE;
    }

    public void resetDistance() {
        encOffsetValue = getEncoderLeft();
    }
}
