package com.gos.lib.rev;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel;

import java.util.Map;

public class CanUtilizationUtil {

    private static final int ROBOT_PERIOD = 20;
    private static final int MAX_PERIOD = (1 << 15) - 1;

    // Defaults from https://docs.revrobotics.com/sparkmax/operating-modes/control-interfaces
    private static final Map<CANSparkLowLevel.PeriodicFrame, Integer> DEFAULT_RATES = Map.of(
        // Applied Output, [sticky] faults, follower info
        CANSparkLowLevel.PeriodicFrame.kStatus0, 10,
        // Velocity, Temperature, Voltage, Current
        CANSparkLowLevel.PeriodicFrame.kStatus1, 20,
        // Position
        CANSparkLowLevel.PeriodicFrame.kStatus2, 20,
        // Analog Sensor - Voltage, Velocity, Position
        CANSparkLowLevel.PeriodicFrame.kStatus3, 50,
        // Alternate Encoder - Velocity, Position
        CANSparkLowLevel.PeriodicFrame.kStatus4, 20,
        // Absolute Encoder - Position, Absolute Angle
        CANSparkLowLevel.PeriodicFrame.kStatus5, 200,
        // Absolute Encoder - Velocity, Encoder Frequency
        CANSparkLowLevel.PeriodicFrame.kStatus6, 200,
        // IAccum
        CANSparkLowLevel.PeriodicFrame.kStatus7, 250
    );

    public static void disablePositionFrame(CANSparkBase controller) {
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus2, MAX_PERIOD));
    }

    public static void disableAnalogSensor(CANSparkBase controller) {
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus3, MAX_PERIOD));
    }

    public static void disableAlternateEncoder(CANSparkBase controller) {
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus4, MAX_PERIOD));
    }

    public static void disableAbsoluteEncoder(CANSparkBase controller) {
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus5, MAX_PERIOD));
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus6, MAX_PERIOD));
    }

    public static void disableExternalSensors(CANSparkBase controller) {
        disableAnalogSensor(controller);
        disableAlternateEncoder(controller);
        disableAbsoluteEncoder(controller);
    }

    public static void enableAbsoluteEncoder(CANSparkBase controller, int periodMs) {
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus5, periodMs));
        SparkMaxUtil.autoRetry(() -> controller.setPeriodicFramePeriod(CANSparkLowLevel.PeriodicFrame.kStatus6, periodMs));
    }
}
