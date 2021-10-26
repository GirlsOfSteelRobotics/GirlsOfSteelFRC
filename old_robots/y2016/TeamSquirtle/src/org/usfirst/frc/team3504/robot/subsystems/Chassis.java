package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
//ok this is cool but I dont have any of the FRC function things so i have a ton of errors.
//~Rozie



import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem{

    CANTalon rt1 = RobotMap.rightTalon1;
    CANTalon rt2 = RobotMap.rightTalon2;
    CANTalon rt3 = RobotMap.rightTalon3;
    CANTalon lt1 = RobotMap.leftTalon1;
    CANTalon lt2 = RobotMap.leftTalon2;
    CANTalon lt3 = RobotMap.leftTalon3;
    RobotDrive driveSystem = RobotMap.driveSystem;

    public Chassis() {

    }

    public void driveByJoystick(Joystick stick) {
        driveSystem.arcadeDrive(stick);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveByJoystick());
    }

    public void driveForward() {
        driveSystem.drive(.5, 0);
    }

    public double resetDistance() {
        return 0; //FIXME: don't know what it should return
    }

    public void stop() {
        //TODO: Figure out what goes here
    }

}
