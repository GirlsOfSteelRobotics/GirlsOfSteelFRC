package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class TeleopSwerveDrive extends Command {
    private static final double JOYSTICK_DEADBAND = 0.025;
    private static final GosDoubleProperty TRANSLATION_DAMPING = new GosDoubleProperty(false, "SwerveJoystickTranslationDamping", 0.4);
    private static final GosDoubleProperty ROTATION_DAMPING = new GosDoubleProperty(false, "SwerveJoystickRotationDamping", 0.4);
    private final ChassisSubsystem m_subsystem;
    private final CommandXboxController m_joystick;

    public TeleopSwerveDrive(ChassisSubsystem subsystem, CommandXboxController joystick) {
        m_subsystem = subsystem;
        m_joystick = joystick;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_subsystem);
    }

    @Override
    public void execute() {
        double xPercent = -MathUtil.applyDeadband(m_joystick.getLeftY(), JOYSTICK_DEADBAND);
        double yPercent = -MathUtil.applyDeadband(m_joystick.getLeftX(), JOYSTICK_DEADBAND);
        double rotPercent = -MathUtil.applyDeadband(m_joystick.getRightX(), JOYSTICK_DEADBAND);

        m_subsystem.teleopDrive(
            xPercent * TRANSLATION_DAMPING.getValue(),
            yPercent * TRANSLATION_DAMPING.getValue(),
            rotPercent * ROTATION_DAMPING.getValue(),
            true);

        System.out.println("Implement me!" + m_subsystem);
    }
}
