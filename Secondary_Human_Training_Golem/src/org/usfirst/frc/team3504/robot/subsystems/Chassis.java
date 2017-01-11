package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Chassis extends Subsystem {
	private CANTalon driveLeftA;
	private CANTalon driveLeftB;
	private CANTalon driveLeftC;

	private CANTalon driveRightA;
	private CANTalon driveRightB;
	private CANTalon driveRightC;
	
	private RobotDrive robotDrive;
	
	public Chassis() {
		/*driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A);
		driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B);
		driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C);
		driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A);
		driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B);
		driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C);*/

		driveLeftA.enableBrakeMode(true);
		driveLeftB.enableBrakeMode(true);
		driveLeftC.enableBrakeMode(true);
		driveRightA.enableBrakeMode(true);
		driveRightB.enableBrakeMode(true);
		driveRightC.enableBrakeMode(true);

		robotDrive = new RobotDrive(driveLeftA, driveRightA);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

