package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.lib.properties.PropertyManager;

public class FindElevatorGravityCompensationCommand extends CommandBase {

    public static final PropertyManager.IProperty<Double> ELEVATOR_SPEED = PropertyManager.createDoubleProperty(false,"Tuning.Elevator.GravityCompensationSpeed", 0);
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
