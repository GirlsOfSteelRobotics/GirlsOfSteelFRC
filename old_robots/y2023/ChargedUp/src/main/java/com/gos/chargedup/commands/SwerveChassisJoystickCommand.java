package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.SwerveDriveChassisSubsystem;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class SwerveChassisJoystickCommand extends Command {
    private final SwerveDriveChassisSubsystem m_swerveDriveChassisSubsystem;

    private final CommandXboxController m_joystick;

    public SwerveChassisJoystickCommand(SwerveDriveChassisSubsystem swerveDriveChassisSubsystem, CommandXboxController joystick) {
        this.m_swerveDriveChassisSubsystem = swerveDriveChassisSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        m_joystick = joystick;
        addRequirements(this.m_swerveDriveChassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double yVelocity = -m_joystick.getLeftX() * SwerveDriveChassisSubsystem.MAX_TRANSLATION_SPEED;
        double xVelocity = -m_joystick.getLeftY() * SwerveDriveChassisSubsystem.MAX_TRANSLATION_SPEED;
        double omega = -m_joystick.getRightX() * SwerveDriveChassisSubsystem.MAX_ROTATION_SPEED;
        ChassisSpeeds speeds = new ChassisSpeeds(xVelocity, yVelocity, omega);
        speeds.toRobotRelativeSpeeds(m_swerveDriveChassisSubsystem.getPose().getRotation());

        // Now use this in our kinematics
        m_swerveDriveChassisSubsystem.setChassisSpeed(speeds);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
