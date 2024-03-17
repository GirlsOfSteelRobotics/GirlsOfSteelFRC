package com.gos.lib.rev;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.MotorFeedbackSensor;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DriverStation;
import org.littletonrobotics.frc2023.util.Alert;

import java.util.function.Supplier;

@SuppressWarnings("PMD.LinguisticNaming")
public class SparkMaxUtil {

    private static final StringBuilder ALERT_BUILDER = new StringBuilder(100); // NOPMD(AvoidStringBufferField)
    private static final Alert CONFIG_FAILED_ALERT = new Alert("Rev CAN config failure", Alert.AlertType.ERROR);
    private static boolean configFailed;

    public static REVLibError autoRetry(Supplier<REVLibError> command) {
        return autoRetry(command, 10);
    }

    public static REVLibError autoRetry(Supplier<REVLibError> command, int maxRetries) {
        int ctr = 0;
        REVLibError error;

        do {
            error = command.get();
            if (error != REVLibError.kOk) {
                System.out.println("Failed to configure something");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
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

        return error;
    }

    /////////////////////////////
    // Motor Controller
    /////////////////////////////
    private static REVLibError smartSetInverted(CANSparkBase controller, boolean inverted) {
        controller.setInverted(inverted);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean success = controller.getInverted() == inverted;
        return success ? REVLibError.kOk : REVLibError.kParamInvalid;
    }

    public static REVLibError setIdleMode(CANSparkBase controller, CANSparkMax.IdleMode idleMode) {
        return autoRetry(() -> controller.setIdleMode(idleMode));
    }

    public static REVLibError setInverted(AbsoluteEncoder encoder, boolean inverted) {
        return autoRetry(() -> encoder.setInverted(inverted));
    }

    public static REVLibError setInverted(CANSparkBase controller, boolean inverted) {
        return autoRetry(() -> smartSetInverted(controller, inverted));
    }

    public static REVLibError setSmartCurrentLimit(CANSparkBase controller, int limit) {
        return autoRetry(() -> controller.setSmartCurrentLimit(limit));
    }

    public static REVLibError enableVoltageCompensation(CANSparkBase controller, double nominalVoltage) {
        return autoRetry(() -> controller.enableVoltageCompensation(nominalVoltage));
    }

    public static REVLibError follow(CANSparkMax leader, CANSparkMax follower, boolean invert) {
        return autoRetry(() -> follower.follow(leader, true));
    }

    /////////////////////////////
    // Encoders
    /////////////////////////////
    public static REVLibError setPositionConversionFactor(RelativeEncoder encoder, double conversionFactor) {
        return autoRetry(() -> encoder.setPositionConversionFactor(conversionFactor));
    }

    public static REVLibError setPositionConversionFactor(AbsoluteEncoder encoder, double conversionFactor) {
        return autoRetry(() -> encoder.setPositionConversionFactor(conversionFactor));
    }

    public static REVLibError setVelocityConversionFactor(RelativeEncoder encoder, double conversionFactor) {
        return autoRetry(() -> encoder.setVelocityConversionFactor(conversionFactor));
    }

    public static REVLibError setVelocityConversionFactor(AbsoluteEncoder encoder, double conversionFactor) {
        return autoRetry(() -> encoder.setVelocityConversionFactor(conversionFactor));
    }

    public static REVLibError setZeroOffset(AbsoluteEncoder encoder, double zeroOffset) {
        return autoRetry(() -> encoder.setZeroOffset(zeroOffset));
    }

    /////////////////////////////
    // PID Controller
    /////////////////////////////
    public static REVLibError setPositionPIDWrappingEnabled(SparkPIDController pidController, boolean enabled) {
        return autoRetry(() -> pidController.setPositionPIDWrappingEnabled(enabled));
    }

    public static REVLibError setPositionPIDWrappingMinInput(SparkPIDController pidController, double min) {
        return autoRetry(() -> pidController.setPositionPIDWrappingMinInput(min));
    }

    public static REVLibError setPositionPIDWrappingMaxInput(SparkPIDController pidController, double max) {
        return autoRetry(() -> pidController.setPositionPIDWrappingMaxInput(max));
    }

    public static REVLibError setFeedbackDevice(SparkPIDController pidController, MotorFeedbackSensor sensor) {
        return autoRetry(() -> pidController.setFeedbackDevice(sensor));
    }

}
