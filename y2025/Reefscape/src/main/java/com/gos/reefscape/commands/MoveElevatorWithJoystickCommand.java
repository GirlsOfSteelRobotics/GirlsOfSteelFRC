package com.gos.reefscape.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class MoveElevatorWithJoystickCommand extends Command {
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final CommandXboxController m_controller;

    public MoveElevatorWithJoystickCommand(ElevatorSubsystem elevatorSubsystem, CommandXboxController controller) {
        this.m_elevatorSubsystem = elevatorSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_elevatorSubsystem);
        m_controller = controller;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {


        if (Math.abs(m_controller.getRightY()) <= 0.02) {
            m_elevatorSubsystem.stop();
        } else {
            m_elevatorSubsystem.setSpeed(-m_controller.getRightY());
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_elevatorSubsystem.stop();
    }
}
