package com.gos.crescendo2024.commands;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public abstract class BaseTeleopSwerve extends Command {

    protected static final double JOYSTICK_DEADBAND = 0.025;
    protected static final GosDoubleProperty TRANSLATION_DAMPING = new GosDoubleProperty(false, "SwerveJoystickTranslationDamping", 0.4);
    protected static final GosDoubleProperty ROTATION_DAMPING = new GosDoubleProperty(false, "SwerveJoystickRotationDamping", 0.4);
    protected final ChassisSubsystem m_subsystem;
    protected final CommandXboxController m_joystick;

    public BaseTeleopSwerve(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        m_subsystem = chassisSubsystem;
        m_joystick = joystick;

        addRequirements(chassisSubsystem);
    }

    protected abstract void handleJoystick(double xLeft, double yLeft, double xRight, double yRight);

    @Override
    public void execute() {
        double leftY = -MathUtil.applyDeadband(m_joystick.getLeftY(), JOYSTICK_DEADBAND);
        double leftX = -MathUtil.applyDeadband(m_joystick.getLeftX(), JOYSTICK_DEADBAND);
        double rightX = -MathUtil.applyDeadband(m_joystick.getRightX(), JOYSTICK_DEADBAND);
        double rightY = -MathUtil.applyDeadband(m_joystick.getRightY(), JOYSTICK_DEADBAND);

        handleJoystick(leftX, leftY, rightX, rightY);
    }
}
