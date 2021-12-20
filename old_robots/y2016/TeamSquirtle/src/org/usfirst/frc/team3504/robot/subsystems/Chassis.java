package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem{

    private final WPI_TalonSRX m_rightTalon1;
    private final WPI_TalonSRX m_leftTalon1;
    private final WPI_TalonSRX m_rightTalon2;
    private final WPI_TalonSRX m_leftTalon2;
    private final WPI_TalonSRX m_rightTalon3;
    private final WPI_TalonSRX m_leftTalon3;
    private final RobotDrive m_driveSystem;


    public Chassis() {
        m_rightTalon1 = new WPI_TalonSRX(1);
        m_rightTalon2 = new WPI_TalonSRX(2);
        m_rightTalon3 = new WPI_TalonSRX(3);
        m_leftTalon1 = new WPI_TalonSRX(4);
        m_leftTalon2 = new WPI_TalonSRX(5);
        m_leftTalon3 = new WPI_TalonSRX(6);

        m_rightTalon2.follow(m_rightTalon1);
        m_rightTalon3.follow(m_rightTalon1);

        m_leftTalon2.follow(m_leftTalon1);
        m_leftTalon3.follow(m_leftTalon1);

        m_driveSystem = new RobotDrive(m_rightTalon1, m_leftTalon1);

    }

    public void driveByJoystick(Joystick stick) {
        m_driveSystem.arcadeDrive(stick);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void driveForward() {
        m_driveSystem.drive(.5, 0);
    }

    public double resetDistance() {
        return 0; //FIXME: don't know what it should return
    }

    public void stop() {
        //TODO: Figure out what goes here
    }

}
