package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import com.gos.codelabs.basic_simulator.SmartDashboardNames;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        SmartDashboard.putBoolean(SmartDashboardNames.PUNCH_TABLE_NAME + "/" + SmartDashboardNames.PUNCH_IS_EXTENDED, isExtended());
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
