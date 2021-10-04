package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.OI;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveChassisWithJoystickCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final OI m_oi;

    public DriveChassisWithJoystickCommand(ChassisSubsystem chassis, OI oi) {
        m_chassis = chassis;
        m_oi = oi;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        // TODO implement
    }
}
