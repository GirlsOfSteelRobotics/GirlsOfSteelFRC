package com.gos.reefscape.commands;

import com.gos.lib.GetAllianceUtil;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class DavidDriveCommand extends Command {
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(false, "ChassisTranslationDamper", 0.75);

    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_joystick;
    private double m_lastAngle;

    public DavidDriveCommand(ChassisSubsystem chassis, CommandXboxController joystick) {
        m_chassis = chassis;
        m_joystick = joystick;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        if (GetAllianceUtil.isBlueAlliance()) {
            m_lastAngle = m_chassis.getState().Pose.getRotation().getRadians();
        } else {
            m_lastAngle = -m_chassis.getState().Pose.getRotation().getRadians();
        }
    }

    @Override
    public void execute() {
        double magnitude = Math.sqrt(m_joystick.getRightX() * m_joystick.getRightX() + m_joystick.getRightY() * m_joystick.getRightY());

        if (magnitude >= .7) {
            double angle = Math.atan2(-m_joystick.getRightX(), -m_joystick.getRightY());

            m_chassis.davidDrive(
                MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
                MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
                angle);
            m_lastAngle = angle;
        } else {
            m_chassis.davidDrive(
                MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
                MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
                m_lastAngle);
        }





    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.driveWithJoystick(0, 0, 0);
    }
}
