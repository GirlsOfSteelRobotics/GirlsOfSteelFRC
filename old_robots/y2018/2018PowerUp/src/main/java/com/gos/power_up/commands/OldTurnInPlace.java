package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class OldTurnInPlace extends Command {

    private static final double ERROR = 3.0;

    private final double m_headingTarget;
    private final double m_speed;

    private final Chassis m_chassis;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    public OldTurnInPlace(Chassis chassis, double degrees) {
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();

        addRequirements(m_chassis);
        m_headingTarget = degrees;
        m_speed = 0.2;
    }


    @Override
    public void initialize() {
        m_chassis.setInverted(true);
        m_chassis.zeroSensors();
        System.out.println("OldTurnInPlace: intitialized");
    }


    @Override
    public void execute() {
        if (m_headingTarget > 0) {
            m_leftTalon.set(ControlMode.PercentOutput, -m_speed);
            m_rightTalon.set(ControlMode.PercentOutput, m_speed);
        } else {
            m_leftTalon.set(ControlMode.PercentOutput, m_speed);
            m_rightTalon.set(ControlMode.PercentOutput, -m_speed);
        }
    }


    @Override
    public boolean isFinished() {
        return Math.abs(m_chassis.getYaw() - m_headingTarget) < ERROR;
    }


    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
        m_chassis.setInverted(false);
        System.out.println("OldTurnInPlace: finished");
    }



}
