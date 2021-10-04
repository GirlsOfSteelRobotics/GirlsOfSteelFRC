package com.gos.codelabs.pid.commands;

import com.gos.codelabs.pid.OI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.codelabs.pid.subsystems.ElevatorSubsystem;

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
        m_lift.setSpeed(m_oi.getElevatorJoystick());
    }
}
