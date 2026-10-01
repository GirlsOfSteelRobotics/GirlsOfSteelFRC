package com.gos.lib.rev;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

public enum RevMotorControllerModel {
    SPARK_MAX,
    SPARK_FLEX;

    public SparkBaseConfig createConfig() {
        return switch (this) {
        case SPARK_MAX -> new SparkMaxConfig();
        case SPARK_FLEX -> new SparkFlexConfig();
        default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public SparkBase createMotor(int canId, MotorType motorTYpe) {
        return switch (this) {
        case SPARK_MAX -> new SparkMax(canId, motorTYpe);
        case SPARK_FLEX -> new SparkFlex(canId, motorTYpe);
        default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
