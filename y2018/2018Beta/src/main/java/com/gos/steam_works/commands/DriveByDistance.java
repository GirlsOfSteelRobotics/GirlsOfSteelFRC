package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.RobotMap;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByDistance extends Command {

    private final double m_rotations;

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    private double m_leftInitial;
    private double m_rightInitial;

    private final Shifters.Speed m_speed;

    public DriveByDistance(Chassis chassis, Shifters shifters, double inches, Shifters.Speed speed) {
        m_chassis = chassis;
        m_shifters = shifters;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        m_rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        this.m_speed = speed;

        // Use requires() here to declare subsystem dependencies
        requires(m_chassis);
    }


    @Override
    protected void initialize() {
        m_shifters.shiftGear(m_speed);

        // Robot.chassis.setupFPID(leftTalon);
        // Robot.chassis.setupFPID(rightTalon);

        if (m_speed == Shifters.Speed.kLow) {
            m_leftTalon.config_kF(0, 0, 0);
            m_leftTalon.config_kP(0, 0.17, 0);
            m_leftTalon.config_kI(0, 0, 0);
            m_leftTalon.config_kD(0, 0.02, 0);

            m_rightTalon.config_kF(0, 0, 0);
            m_rightTalon.config_kP(0, 0.17, 0);
            m_rightTalon.config_kI(0, 0, 0);
            m_rightTalon.config_kD(0, 0.02, 0);
        } else if (m_speed == Shifters.Speed.kHigh) {
            m_leftTalon.config_kF(0, 0, 0);
            m_leftTalon.config_kP(0, 0.02, 0);
            m_leftTalon.config_kI(0, 0, 0);
            m_leftTalon.config_kD(0, 0.04, 0);

            m_rightTalon.config_kF(0, 0, 0);
            m_rightTalon.config_kP(0, 0.02, 0);
            m_rightTalon.config_kI(0, 0, 0);
            m_rightTalon.config_kD(0, 0.04, 0);
        }


        // leftTalon.setPosition(0.0);
        // rightTalon.setPosition(0.0);

        System.out.println("Drive by Distance Started " + m_rotations);

        m_leftInitial = -m_leftTalon.getSelectedSensorPosition(0);
        m_rightInitial = m_rightTalon.getSelectedSensorPosition(0);

        m_leftTalon.set(ControlMode.Position, -(m_rotations + m_leftInitial));
        m_rightTalon.set(ControlMode.Position, m_rotations + m_rightInitial);

        System.out.println("LeftInitial: " + m_leftInitial + " RightInitial: " + m_rightInitial);

    }


    @Override
    protected void execute() {
        m_leftTalon.set(ControlMode.Position, -(m_rotations + m_leftInitial));
        m_rightTalon.set(ControlMode.Position, m_rotations + m_rightInitial);

        SmartDashboard.putNumber("Drive Talon Left Goal", -m_rotations);
        SmartDashboard.putNumber("Drive Talon Left Position", m_leftTalon.getSelectedSensorPosition(0));
        //SmartDashboard.putNumber("Drive Talon Left Error", leftTalon.getError());

        //System.out.println("Left Goal " + (-(rotations + leftInitial)) + " Right Goal " + (rotations + rightInitial));
        //System.out.println("Left Position " + leftTalon.getPosition() + " Right Position " + rightTalon.getPosition());
        //System.out.println("Left Error " + ((-(rotations + leftInitial)) + leftTalon.getPosition()));
        //System.out.println("Right Error " + (((rotations + rightInitial)) - rightTalon.getPosition()));
    }


    @Override
    protected boolean isFinished() {
        if (m_rotations > 0) {
            return (m_rightTalon.getSelectedSensorPosition(0) > m_rotations + m_rightInitial)
                && (-m_leftTalon.getSelectedSensorPosition(0) > m_rotations + m_leftInitial);
        } else if (m_rotations < 0) {
            return (m_rightTalon.getSelectedSensorPosition(0) < m_rotations + m_rightInitial)
                && (-m_leftTalon.getSelectedSensorPosition(0) < m_rotations + m_leftInitial);
        } else {
            return true;
        }
    }


    @Override
    protected void end() {
        m_shifters.shiftGear(Shifters.Speed.kLow);
        System.out.println("DriveByDistance Finished");
    }


}
