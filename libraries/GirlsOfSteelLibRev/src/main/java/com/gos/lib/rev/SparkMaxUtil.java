package com.gos.lib.rev;

import com.revrobotics.REVLibError;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.DriverStation;

import java.util.function.Supplier;

public class SparkMaxUtil {

    private static final StringBuilder ALERT_BUILDER = new StringBuilder(100); // NOPMD(AvoidStringBufferField)
    private static final Alert CONFIG_FAILED_ALERT = new Alert("Rev CAN config failure", AlertType.kError);
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
            DriverStation.reportError("Took " + ctr + " times to retry command", false);
        }

        CONFIG_FAILED_ALERT.set(configFailed);
        CONFIG_FAILED_ALERT.setText(ALERT_BUILDER.toString());
    }
}
