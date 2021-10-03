package frc.robot.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.ElevatorSubsystem;

public class FindElevatorGravityCompensationCommand extends CommandBase {

    public static final PropertyManager.DoubleProperty ELEVATOR_SPEED = new PropertyManager.DoubleProperty("Tuning.Elevator.GravityCompensationSpeed", 0);
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
