package com.gos.lib.rev;

import com.revrobotics.REVLibError;
import com.gos.lib.alerts.AlertFactory;
import org.wpilib.util.Alert;
import org.wpilib.driverstation.DriverStationErrors;

import java.util.function.Supplier;

public class SparkMaxUtil {

    private static final StringBuilder ALERT_BUILDER = new StringBuilder(100); // NOPMD(AvoidStringBufferField)
    private static final Alert CONFIG_FAILED_ALERT = AlertFactory.create("Rev CAN config failure", Alert.Level.HIGH);
    private static boolean configFailed;

    public static void autoRetry(Supplier<REVLibError> command) {
        autoRetry(command, 10);
    }

    public static void autoRetry(Supplier<REVLibError> command, int maxRetries) {
        int ctr = 0;
        REVLibError error;

        do {
            error = command.get();
            ++ctr;
        }
        while (error != REVLibError.kOk && ctr < maxRetries);

        configFailed &= error != REVLibError.kOk;

        if (ctr != 1) {
            ALERT_BUILDER.append("Took ").append(ctr).append(" times to retry command");
            DriverStationErrors.reportError("Took " + ctr + " times to retry command", false);
        }

        CONFIG_FAILED_ALERT.set(configFailed);
        CONFIG_FAILED_ALERT.setText(ALERT_BUILDER.toString());
    }
}
