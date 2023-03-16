package com.gos.lib.rev;

import com.revrobotics.REVLibError;
import edu.wpi.first.wpilibj.DriverStation;
import org.littletonrobotics.frc2023.util.Alert;

import java.util.function.Supplier;

public class SparkMaxUtil {

    private static final StringBuilder ALERT_BUILDER = new StringBuilder();
    private static boolean CONFIG_FAILED = false;
    private static final Alert CONFIG_FAILED_ALERT = new Alert("Rev CAN config failure", Alert.AlertType.ERROR);

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

        CONFIG_FAILED &= error != REVLibError.kOk;

        if (ctr != 1) {
            ALERT_BUILDER.append("Took " + ctr + " times to retry command");
            DriverStation.reportError("Took " + ctr + " times to retry command", false);
        }

        CONFIG_FAILED_ALERT.set(CONFIG_FAILED);
        CONFIG_FAILED_ALERT.setText(ALERT_BUILDER.toString());
    }
}
