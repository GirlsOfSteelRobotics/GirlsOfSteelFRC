package com.gos.chargedup.commands;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.littletonrobotics.frc2023.util.Alert;


public class PneumaticsMoveTest extends CommandBase {

    private final PneumaticHub m_pneumaticsHub;

    private final Solenoid m_solenoid;

    private final int m_channel;
    private double m_startPressure;

    private final Alert m_alert;

    public PneumaticsMoveTest(PneumaticHub pneumaticHub, Solenoid solenoid, int channel, String label) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
        m_pneumaticsHub = pneumaticHub;
        m_solenoid = solenoid;
        m_channel = channel;
        m_startPressure = 0;
        m_alert = new Alert(label, Alert.AlertType.ERROR);


    }

    @Override
    public void initialize() {
        m_startPressure = m_pneumaticsHub.getPressure(m_channel);
    }

    @Override
    public void execute() {

        m_solenoid.set(!m_solenoid.get());
    }

    @Override
    public boolean isFinished() {
        return (m_solenoid.get());
    }

    @Override
    public void end(boolean interrupted) {
        double endPressure = m_pneumaticsHub.getPressure(m_channel);
        boolean isPneuMoving = (m_startPressure < endPressure);
        m_alert.set(!isPneuMoving);
        m_solenoid.set(false);
    }
}
