package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
//ok this is cool but I dont have any of the FRC function things so i have a ton of errors.
//~Rozie



import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem{

    private final CANTalon rt1 = RobotMap.rightTalon1;
    private final CANTalon rt2 = RobotMap.rightTalon2;
    private final CANTalon rt3 = RobotMap.rightTalon3;
    private final CANTalon lt1 = RobotMap.leftTalon1;
    private final CANTalon lt2 = RobotMap.leftTalon2;
    private final CANTalon lt3 = RobotMap.leftTalon3;
    private final RobotDrive driveSystem = RobotMap.driveSystem;

    public void driveByJoystick(Joystick stick) {
        driveSystem.arcadeDrive(stick);
    }

    @Override
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
