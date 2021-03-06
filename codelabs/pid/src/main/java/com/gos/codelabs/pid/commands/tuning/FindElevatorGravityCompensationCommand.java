package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.gos.codelabs.pid.subsystems.ElevatorSubsystem.ELEVATOR_SPEED;

public class FindElevatorGravityCompensationCommand extends CommandBase {

    private final ElevatorSubsystem m_elevator;

    public FindElevatorGravityCompensationCommand(ElevatorSubsystem elevator) {
        m_elevator = elevator;
        addRequirements(m_elevator);
    }

    @Override
    public void execute() {
        m_elevator.setSpeed(ELEVATOR_SPEED.getValue());
    }

}
