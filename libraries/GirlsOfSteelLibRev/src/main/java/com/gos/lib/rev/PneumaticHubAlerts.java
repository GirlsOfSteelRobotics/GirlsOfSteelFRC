package com.gos.lib.rev;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.PneumaticHub;
import org.littletonrobotics.frc2023.util.Alert;

public class PneumaticHubAlerts {
    private final PneumaticHub m_pneumaticHub;
    private final Alert m_alert;

    public PneumaticHubAlerts(PneumaticHub pneumaticHub) {
        m_pneumaticHub = pneumaticHub;
        m_alert = new Alert("pneumatic hub", Alert.AlertType.ERROR);
    }

    public void checkAlerts() {
        if (m_pneumaticHub.getFaults().Channel0Fault) {
            m_alert.setText("pneumatic hub channel 0 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel1Fault) {
            m_alert.setText("pneumatic hub channel 1 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel2Fault) {
            m_alert.setText("pneumatic hub channel 2 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel3Fault) {
            m_alert.setText("pneumatic hub channel 3 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel4Fault) {
            m_alert.setText("pneumatic hub channel 4 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel5Fault) {
            m_alert.setText("pneumatic hub channel 5 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel6Fault) {
            m_alert.setText("pneumatic hub channel 6 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel7Fault) {
            m_alert.setText("pneumatic hub channel 7 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel8Fault) {
            m_alert.setText("pneumatic hub channel 8 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel9Fault) {
            m_alert.setText("pneumatic hub channel 9 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel10Fault) {
            m_alert.setText("pneumatic hub channel 10 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel11Fault) {
            m_alert.setText("pneumatic hub channel 11 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel12Fault) {
            m_alert.setText("pneumatic hub channel 12 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel13Fault) {
            m_alert.setText("pneumatic hub channel 13 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel14Fault) {
            m_alert.setText("pneumatic hub channel 14 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel15Fault) {
            m_alert.setText("pneumatic hub channel 15 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Channel6Fault) {
            m_alert.setText("pneumatic hub channel 16 error");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().Brownout) {
            m_alert.setText("pneumatic hub brownout");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().CompressorOverCurrent) {
            m_alert.setText("compressor over current");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().CanWarning) {
            m_alert.setText("pneumatic hub CAN warning");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().SolenoidOverCurrent) {
            m_alert.setText("pneumatic hub solenoid over current");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().CompressorOpen) {
            m_alert.setText("compressor open");
            m_alert.set(true);
        }
        if (m_pneumaticHub.getFaults().HardwareFault) {
            m_alert.setText("pneumatic hub hardware fault");
            m_alert.set(true);
        }
    }
}
