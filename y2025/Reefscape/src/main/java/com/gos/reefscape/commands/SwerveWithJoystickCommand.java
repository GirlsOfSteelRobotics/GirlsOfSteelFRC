package com.gos.reefscape.commands;

import com.gos.reefscape.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.reefscape.subsystems.SdsWithRevChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class SwerveWithJoystickCommand extends Command {
    private final CommandSwerveDrivetrain m_chassis;
    private final CommandXboxController m_joystick;

    public SwerveWithJoystickCommand(CommandSwerveDrivetrain chassisSubsystem, CommandXboxController joystick) {
        this.m_chassis = chassisSubsystem;
        m_joystick = joystick;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double xVelocity = -m_joystick.getLeftY() * m_chassis.MAX_TRANSLATION_SPEED;
        double yVelocity = m_joystick.getLeftX() * m_chassis.MAX_TRANSLATION_SPEED;
        double turn = m_joystick.getRightX() * m_chassis.MAX_ROTATION_SPEED;
        m_chassis.swerveDrive(xVelocity, yVelocity, turn);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.swerveDrive(0, 0, 0);
    }
}
