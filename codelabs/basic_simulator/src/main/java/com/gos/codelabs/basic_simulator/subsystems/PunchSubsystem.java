package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.commands.MovePunchCommand;
import com.gos.lib.logging.LoggingUtil;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PunchSubsystem extends SubsystemBase implements AutoCloseable {

    private final Solenoid m_punchSolenoid;
    private final LoggingUtil m_loggingUtil;

    public PunchSubsystem() {
        m_punchSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.SOLENOID_PUNCH);

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
        return m_punchSolenoid.get();
    }

    public void extend() {
        m_punchSolenoid.set(true);
    }

    public void retract() {
        // TODO implement
        m_punchSolenoid.set(false);
    }

    public void addPunchDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Punch");
        tab.add(new MovePunchCommand(this, true).withName("Extend Punch"));
        tab.add(new MovePunchCommand(this, false).withName("Retract Punch"));
    }
}
