package org.usfirst.frc.team3504.robot;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // Joystick ports -> should align with driver station
    public static final int OPERATOR_JOYSTICK = 1;
    public static final int CHASSIS_JOYSTICK = 0;

    public static CANTalon driveSystemDriveLeft0;
    public static CANTalon driveSystemDriveRight0;
    public static CANTalon driveSystemDriveLeft1;
    public static CANTalon driveSystemDriveRight1;
    public static CANTalon driveSystemDriveLeft2;
    public static CANTalon driveSystemDriveRight2;

    public static RobotDrive driveRobotDrive;

    public static DoubleSolenoid shiftersShifterLeft;
    public static DoubleSolenoid shiftersShifterRight;

    public static CANTalon conveyorBeltMotorRight;
    public static CANTalon conveyorBeltMotorLeft;


    //public static Image cameraFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    //public static int cameraSession = NIVision.IMAtQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);


    public static void init() {
        driveSystemDriveLeft0 = new CANTalon(0);
        driveSystemDriveRight0 = new CANTalon(1);
        driveSystemDriveLeft1 = new CANTalon(2);
        driveSystemDriveRight1 = new CANTalon(3);
        driveSystemDriveLeft2 = new CANTalon(4);
        driveSystemDriveRight2 = new CANTalon(5);
        conveyorBeltMotorRight = new CANTalon(6);
        conveyorBeltMotorLeft = new CANTalon(7);
        // Follower: The m_motor will run at the same throttle as the specified
        // other talon.
        driveSystemDriveLeft1.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveSystemDriveLeft2.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveSystemDriveRight1.changeControlMode(CANTalon.TalonControlMode.Follower);
        driveSystemDriveRight2.changeControlMode(CANTalon.TalonControlMode.Follower);
        //set arguments refer to CANTalon port numbers
        driveSystemDriveLeft1.set(0);
        driveSystemDriveRight1.set(1);
        driveSystemDriveLeft2.set(0);
        driveSystemDriveRight2.set(1);

        driveRobotDrive = new RobotDrive(driveSystemDriveLeft0, driveSystemDriveRight0);

        driveRobotDrive.setSafetyEnabled(true);
        driveRobotDrive.setExpiration(0.1);
        driveRobotDrive.setSensitivity(0.5);
        driveRobotDrive.setMaxOutput(1.0);
        //driveSystemRobotDrive2.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        shiftersShifterLeft = new DoubleSolenoid(0, 1);
        shiftersShifterRight = new DoubleSolenoid(2, 3);
    }
}
