package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.commands.MovePunchCommand;
import com.gos.lib.logging.LoggingUtil;
import org.wpilib.hardware.pneumatic.PneumaticsModuleType;
import org.wpilib.hardware.bus.CANPort;
import org.wpilib.hardware.pneumatic.Solenoid;
import com.gos.lib.tunables.CommandTab;
import org.wpilib.command2.SubsystemBase;

public class PunchSubsystem extends SubsystemBase implements AutoCloseable {

    private final Solenoid m_punchSolenoid;
    private final LoggingUtil m_loggingUtil;

    public PunchSubsystem() {
        m_punchSolenoid = new Solenoid(CANPort.CAN_S0, PneumaticsModuleType.CTRE_PCM, Constants.SOLENOID_PUNCH);

        m_loggingUtil = new LoggingUtil("Punch");
        m_loggingUtil.addBoolean("Extended", this::isExtended);
    }


    @Override
    public void close() {
        m_punchSolenoid.close();
    }

    @Override
    public void periodic() {
        m_loggingUtil.updateLogs();
    }

    public boolean isExtended() {
        // TODO implement
        return false;
    }

    public void extend() {
        // TODO implement
    }

    public void retract() {
        // TODO implement
    }

    public void addPunchDebugCommands() {
        CommandTab tab = new CommandTab("Punch");
        tab.add(new MovePunchCommand(this, true).withName("Extend Punch"));
        tab.add(new MovePunchCommand(this, false).withName("Retract Punch"));
    }
}
