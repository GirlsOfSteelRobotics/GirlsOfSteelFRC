package com.gos.rebuilt.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rebuilt.subsystems.PivotSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class PivotJoyCommand extends Command {
    private final PivotSubsystem m_pivotSubsystem;
    private final CommandXboxController m_joystick;

    public PivotJoyCommand(PivotSubsystem pivotSubsystem, CommandXboxController joystick) {
        this.m_pivotSubsystem = pivotSubsystem;
        m_joystick = joystick;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_pivotSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (Math.abs(m_joystick.getRightY()) > 0.1) {
            m_pivotSubsystem.setSpeed(-m_joystick.getRightY());
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_pivotSubsystem.stop();
    }
}
