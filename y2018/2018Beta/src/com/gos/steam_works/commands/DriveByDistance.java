package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.Robot;
import com.gos.steam_works.RobotMap;
import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByDistance extends Command {

    private final double m_rotations;

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    private double m_leftInitial;
    private double m_rightInitial;

    private final Shifters.Speed m_speed;

    public DriveByDistance(double inches, Shifters.Speed speed) {
        m_rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        this.m_speed = speed;

        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_shifters.shiftGear(m_speed);

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

    // Called repeatedly when this Command is scheduled to run
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

    // Make this return true when this Command no longer needs to run execute()
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

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_shifters.shiftGear(Shifters.Speed.kLow);
        System.out.println("DriveByDistance Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
