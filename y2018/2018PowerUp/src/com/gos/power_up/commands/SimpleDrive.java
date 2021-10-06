package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SimpleDrive extends Command {
    private final Chassis m_chassis;

    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    public SimpleDrive(Chassis chassis) {
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();

        requires(m_chassis);
    }


    @Override
    protected void initialize() {
        m_leftTalon.set(ControlMode.PercentOutput, 0.5);
        m_rightTalon.set(ControlMode.PercentOutput, 0.5);
        System.out.println("SimpleDrive: leftA " + m_leftTalon.getInverted());
        System.out.println("SimpleDrive: rightA " + m_rightTalon.getInverted());
    }


    @Override
    protected void execute() {
        m_leftTalon.set(ControlMode.PercentOutput, 0.5);
        m_rightTalon.set(ControlMode.PercentOutput, 0.5);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
    }


    @Override
    protected void interrupted() {
    }
}
