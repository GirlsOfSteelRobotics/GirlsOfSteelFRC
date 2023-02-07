package com.gos.lib.rev;

import edu.wpi.first.wpilibj.PneumaticHub;
import org.littletonrobotics.frc2023.util.Alert;

public class PneumaticHubAlerts {
    private final PneumaticHub m_pneumaticHub;
    private final Alert m_alert;
    private StringBuilder m_alertMessage = new StringBuilder("pneumatic hub");

    public PneumaticHubAlerts(PneumaticHub pneumaticHub) {
        m_pneumaticHub = pneumaticHub;
        m_alert = new Alert("pneumatic hub", Alert.AlertType.ERROR);
    }

    public void checkAlerts() {
        // lookie so many if statements :)))))))

        if (m_pneumaticHub.getFaults().Channel0Fault) {
            m_alertMessage.append(" channel 0 error");
        }
        if (m_pneumaticHub.getFaults().Channel1Fault) {
            m_alertMessage.append(" channel 1 error");
        }
        if (m_pneumaticHub.getFaults().Channel2Fault) {
            m_alertMessage.append(" channel 2 error");
        }
        if (m_pneumaticHub.getFaults().Channel3Fault) {
            m_alertMessage.append(" channel 3 error");
        }
        if (m_pneumaticHub.getFaults().Channel4Fault) {
            m_alertMessage.append(" channel 4 error");
        }
        if (m_pneumaticHub.getFaults().Channel5Fault) {
            m_alertMessage.append(" channel 5 error");
        }
        if (m_pneumaticHub.getFaults().Channel6Fault) {
            m_alertMessage.append(" channel 6 error");
        }
        if (m_pneumaticHub.getFaults().Channel7Fault) {
            m_alertMessage.append(" channel 7 error");
        }
        if (m_pneumaticHub.getFaults().Channel8Fault) {
            m_alertMessage.append(" channel 8 error");
        }
        if (m_pneumaticHub.getFaults().Channel9Fault) {
            m_alertMessage.append(" channel 9 error");
        }
        if (m_pneumaticHub.getFaults().Channel10Fault) {
            m_alertMessage.append(" channel 10 error");
        }
        if (m_pneumaticHub.getFaults().Channel11Fault) {
            m_alertMessage.append(" channel 11 error");
        }
        if (m_pneumaticHub.getFaults().Channel12Fault) {
            m_alertMessage.append(" channel 12 error");
        }
        if (m_pneumaticHub.getFaults().Channel13Fault) {
            m_alertMessage.append(" channel 13 error");
        }
        if (m_pneumaticHub.getFaults().Channel14Fault) {
            m_alertMessage.append(" channel 14 error");
        }
        if (m_pneumaticHub.getFaults().Channel15Fault) {
            m_alertMessage.append(" channel 15 error");
        }
        if (m_pneumaticHub.getFaults().Channel6Fault) {
            m_alertMessage.append(" channel 16 error");
        }
        if (m_pneumaticHub.getFaults().Brownout) {
            m_alertMessage.append(" brownout");
        }
        if (m_pneumaticHub.getFaults().CompressorOverCurrent) {
            m_alertMessage.append(" compressor over current");
        }
        if (m_pneumaticHub.getFaults().CanWarning) {
            m_alertMessage.append(" can warning");
        }
        if (m_pneumaticHub.getFaults().SolenoidOverCurrent) {
            m_alertMessage.append(" solenoid over current");
        }
        if (m_pneumaticHub.getFaults().CompressorOpen) {
            m_alertMessage.append(" compressor open");
        }
        if (m_pneumaticHub.getFaults().HardwareFault) {
            m_alertMessage.append(" hardware fault");
        }
        m_alert.setText(m_alert.toString());

        if (m_alertMessage.equals("pneumatic hub")) {
            m_alert.set(false);
        }
        else if (!(m_alertMessage.equals("pneumatic hub"))) {
            m_alert.set(true);
        }
    }
}
