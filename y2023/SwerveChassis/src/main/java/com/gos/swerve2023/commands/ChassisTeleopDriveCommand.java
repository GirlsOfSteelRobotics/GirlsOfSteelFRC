package com.gos.swerve2023.commands;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.swerve2023.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class ChassisTeleopDriveCommand extends Command {
    private static final double JOYSTICK_DEADBAND = 0.025;
    private static final GosDoubleProperty TRANSLATION_DAMPING = new GosDoubleProperty(false, "SwerveJoystickTranslationDamping", 0.4);
    private static final GosDoubleProperty ROTATION_DAMPING = new GosDoubleProperty(false, "SwerveJoystickRotationDamping", 0.4);

    private final ChassisSubsystem m_chassisSubsystem;
    private final CommandXboxController m_joystick;

    public ChassisTeleopDriveCommand(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        m_chassisSubsystem = chassisSubsystem;
        m_joystick = joystick;
        addRequirements(m_chassisSubsystem);
    }

    @Override
    public void execute() {
        double xPercent = -MathUtil.applyDeadband(m_joystick.getLeftY(), JOYSTICK_DEADBAND);
        double yPercent = -MathUtil.applyDeadband(m_joystick.getLeftX(), JOYSTICK_DEADBAND);
        double rotPercent = -MathUtil.applyDeadband(m_joystick.getRightX(), JOYSTICK_DEADBAND);

        m_chassisSubsystem.teleopDrive(
            xPercent * TRANSLATION_DAMPING.getValue(),
            yPercent * TRANSLATION_DAMPING.getValue(),
            rotPercent * ROTATION_DAMPING.getValue(),
            true);
    }
}
