package com.gos.lib.ctre;
import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.Pigeon2_Faults;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import org.littletonrobotics.frc2023.util.Alert;

public class PigeonAlerts {
    private static final String ALERT_NAME = "pigeon";
    private static final String STICKY_ALERT_NAME = "pigeon (sticky)";

    private final Pigeon2 m_pigeon;
    private final Alert m_alert;
    private final Alert m_alertSticky;


    public PigeonAlerts(Pigeon2 pigeon2) {
        int deviceNumber = pigeon2.getDeviceID();

        m_pigeon = pigeon2;
        m_alert = new Alert("pigeon", Alert.AlertType.ERROR);
        m_alertSticky = new Alert("pigeon (sticky)", Alert.AlertType.ERROR);
    }

    public void checkAlerts() {
        checkFaults();
        checkStickyFaults();
    }

    public void checkFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(ALERT_NAME);

        Pigeon2_Faults faults = m_pigeon.getFaults();

        if (faults.HardwareFault) {
            alertMessageBuilder.append(" Hardwar Fault");
        }
        if (faults.APIError) {
            alertMessageBuilder.append(" API Error");
        }
        if (faults.UnderVoltage) {
            alertMessageBuilder.append(" Under Voltage fault");
        }
        if (faults.ResetDuringEn) {
            alertMessageBuilder.append(" Reset During En fault");
        }
        if (faults.SaturatedRotVelocity) {
            alertMessageBuilder.append(" Saturated Rot Velocity fault");
        }
        if (faults.SaturatedAccel) {
            alertMessageBuilder.append(" Saturated Accel Fault");
        }
        if (faults.SaturatedMag) {
            alertMessageBuilder.append(" Saturated Mag fault");
        }
        if (faults.BootIntoMotion) {
            alertMessageBuilder.append(" Boot into motion fault");
        }
        if (faults.MagnetometerFault) {
            alertMessageBuilder.append(" Magnetometer fault");
        }
        if (faults.GyroFault) {
            alertMessageBuilder.append(" Gyro fault");
        }
        if (faults.AccelFault) {
            alertMessageBuilder.append(" Alert message builder fault");
        }

        String alertMessage = alertMessageBuilder.toString();
        m_alert.setText(alertMessage);

        m_alert.set(!ALERT_NAME.equals(alertMessage));
    }

    public void checkStickyFaults () {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(STICKY_ALERT_NAME);
    }
}
