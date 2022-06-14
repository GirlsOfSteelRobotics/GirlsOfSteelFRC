package com.gos.power_up.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class AutoBaseLine extends CommandBase {

    private int m_time;

    private final Chassis m_chassis;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    public AutoBaseLine(Chassis chassis) {
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        addRequirements(m_chassis);
    }


    @Override
    public void initialize() {
        m_time = 0;
        m_leftTalon.set(ControlMode.PercentOutput, 0.5);
        m_rightTalon.set(ControlMode.PercentOutput, 0.5);
    }


    @Override
    public void execute() {
        m_time++;
        m_leftTalon.set(ControlMode.PercentOutput, 0.5);
        m_rightTalon.set(ControlMode.PercentOutput, 0.5);
    }


    @Override
    public boolean isFinished() {
        return m_time >= 500;
    }


    @Override
    public void end(boolean interrupted) {
    }



}
