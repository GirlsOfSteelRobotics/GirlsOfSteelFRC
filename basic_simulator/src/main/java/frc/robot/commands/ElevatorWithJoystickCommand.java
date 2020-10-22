package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorWithJoystickCommand extends CommandBase {
    private final OI m_oi;
    private final ElevatorSubsystem m_lift;

    public ElevatorWithJoystickCommand(ElevatorSubsystem lift, OI oi) {
        m_lift = lift;
        m_oi = oi;

        addRequirements(lift);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
