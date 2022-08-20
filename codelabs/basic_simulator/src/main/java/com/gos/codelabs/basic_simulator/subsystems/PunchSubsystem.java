package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PunchSubsystem extends SubsystemBase implements AutoCloseable {

    private final Solenoid m_punchSolenoid;

    public PunchSubsystem() {
        m_punchSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.SOLENOID_PUNCH);
    }


    @Override
    public void close() {
        m_punchSolenoid.close();
    }

    @Override
    public void periodic() {
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
}
