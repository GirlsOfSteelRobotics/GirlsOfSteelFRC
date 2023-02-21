package com.gos.lib.rev;

import com.revrobotics.CANSparkMax;
import org.littletonrobotics.frc2023.util.Alert;

public class SparkMaxAlerts {
    public final CANSparkMax m_sparkMax;
    public final String m_motorString;
    public final Alert m_alert;
    //public final Alert m_alertSticky;

    public SparkMaxAlerts(CANSparkMax sparkMax, String motor) {
        m_sparkMax = sparkMax;
        m_motorString = motor;
        m_alert = new Alert(m_motorString, Alert.AlertType.ERROR);
        //m_alertSticky = new Alert(m_motorString, Alert.AlertType.ERROR);
    }

    public void checkAlerts() {
        short bitmask = m_sparkMax.getFaults();


        StringBuilder errorBuilder = new StringBuilder(m_motorString);
        for (CANSparkMax.FaultID faultId : CANSparkMax.FaultID.values()) {
            if ((bitmask & (1 << faultId.value)) != 0) {
                errorBuilder.append(' ').append(faultId);
            }
        }

        String errorString = errorBuilder.toString();
        m_alert.setText(errorString);

        m_alert.set(!(errorString.equals(m_motorString)));
    }
}
