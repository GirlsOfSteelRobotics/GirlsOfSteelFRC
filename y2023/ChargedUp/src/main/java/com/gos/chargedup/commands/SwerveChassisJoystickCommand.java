package com.gos.chargedup.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.SwerveDriveChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class SwerveChassisJoystickCommand extends CommandBase {
    private final SwerveDriveChassisSubsystem swerveDriveChassisSubsystem;

    private final CommandXboxController m_joystick;
    public SwerveChassisJoystickCommand(SwerveDriveChassisSubsystem swerveDriveChassisSubsystem, CommandXboxController joystick) {
        this.swerveDriveChassisSubsystem = swerveDriveChassisSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        m_joystick = joystick;
        addRequirements(this.swerveDriveChassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double xVelocity = m_joystick.getLeftX()*SwerveDriveChassisSubsystem.MAX_TRANSLATION_SPEED;
        double yVelocity = m_joystick.getLeftY()*SwerveDriveChassisSubsystem.MAX_TRANSLATION_SPEED;
        double omega = m_joystick.getRightX()*SwerveDriveChassisSubsystem.MAX_ROTATION_SPEED;
        swerveDriveChassisSubsystem.setSpeeds(new ChassisSpeeds(xVelocity, yVelocity, omega));


    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
