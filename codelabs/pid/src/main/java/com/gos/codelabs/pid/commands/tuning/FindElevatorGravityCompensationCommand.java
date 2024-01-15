package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class FindElevatorGravityCompensationCommand extends Command {

    private final ElevatorSubsystem m_elevator;

    public FindElevatorGravityCompensationCommand(ElevatorSubsystem elevator) {
        m_elevator = elevator;
        addRequirements(m_elevator);
    }

    @Override
    public void execute() {
        m_elevator.setSpeed(ElevatorSubsystem.GRAVITY_COMPENSATION.getValue());
    }

}
