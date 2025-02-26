package com.gos.reefscape.commands;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.reefscape.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class MovePivotWithJoystickCommand extends Command {
    private final PivotSubsystem m_pivotSubsystem;
    private final CommandXboxController m_controller;

    private static final GosDoubleProperty JOYSTICK_DAMPER = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "PivotDamp", 0.25);

    public MovePivotWithJoystickCommand(PivotSubsystem pivotSubsystem, CommandXboxController joystick) {
        m_pivotSubsystem = pivotSubsystem;
        m_controller = joystick;

        addRequirements(m_pivotSubsystem);
    }

    @Override
    public void execute() {
        if (Math.abs(m_controller.getLeftY()) <= 0.07) {
            m_pivotSubsystem.stop();
        } else {
            m_pivotSubsystem.setSpeed(-m_controller.getLeftY() * JOYSTICK_DAMPER.getValue());
        }
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_pivotSubsystem.stop();
    }
}
