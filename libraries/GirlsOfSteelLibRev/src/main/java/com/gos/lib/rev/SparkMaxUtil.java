package com.gos.lib.rev;

import com.revrobotics.REVLibError;
import edu.wpi.first.wpilibj.DriverStation;

import java.util.function.Supplier;

public class SparkMaxUtil {

    public static void autoRetry(Supplier<REVLibError> command) {
        autoRetry(command, 3);
    }

    public static void autoRetry(Supplier<REVLibError> command, int maxRetries) {
        int ctr = 0;
        REVLibError error;

        do {
            error = command.get();
            ++ctr;
        }
        while (error != REVLibError.kOk && ctr < maxRetries);

        if (ctr != 1) {
            DriverStation.reportError("Took " + ctr + " times to retry command", false);
        }
    }
}
