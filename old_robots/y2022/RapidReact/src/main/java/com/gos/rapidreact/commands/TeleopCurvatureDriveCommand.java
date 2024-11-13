package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;


public class TeleopCurvatureDriveCommand extends Command {
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;
    private final SlewRateLimiter m_slewRateLimiter = new SlewRateLimiter(2);

    public TeleopCurvatureDriveCommand(ChassisSubsystem chassisSubsystem, XboxController joystick) {
        m_chassis = chassisSubsystem;
        m_joystick = joystick;
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.setCurvatureDrive(m_slewRateLimiter.calculate(-m_joystick.getLeftY()) * 0.6, m_joystick.getRightX() * 0.6);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
