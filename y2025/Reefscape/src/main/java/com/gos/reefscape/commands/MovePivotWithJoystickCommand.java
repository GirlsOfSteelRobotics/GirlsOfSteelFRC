package com.gos.reefscape.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class MovePivotWithJoystickCommand extends Command {
    private final PivotSubsystem m_pivotSubsystem;
    private final CommandXboxController m_controller;

    public MovePivotWithJoystickCommand(PivotSubsystem pivotSubsystem, CommandXboxController joystick) {
        m_pivotSubsystem = pivotSubsystem;
        m_controller = joystick;

        addRequirements(m_pivotSubsystem);
    }

    @Override
    public void execute() {
        if (Math.abs(m_controller.getLeftY()) <= 0.02) {
            m_pivotSubsystem.stop();
        } else {
            m_pivotSubsystem.setSpeed(-m_controller.getRightY());
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
