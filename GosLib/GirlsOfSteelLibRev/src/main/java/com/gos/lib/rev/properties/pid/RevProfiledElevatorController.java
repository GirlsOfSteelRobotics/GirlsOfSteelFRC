package com.gos.lib.rev.properties.pid;

import com.gos.lib.properties.feedforward.ElevatorFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkBaseConfig;
import org.wpilib.math.controller.ProfiledPIDController;
import org.wpilib.math.trajectory.TrapezoidProfile;

public final class RevProfiledElevatorController {
    private final ClosedLoopSlot m_slot;
    private final double m_positionConversionFactor;
    private final SparkClosedLoopController m_sparkPidController;
    private final PidProperty m_sparkPidProperties;

    private final ProfiledPIDController m_profilePid;
    private final PidProperty m_profilePidProperties;

    private final ElevatorFeedForwardProperty m_wpiFeedForward;


    private RevProfiledElevatorController(
        ProfiledPIDController profiledPidController,
        PidProperty profilePidProperties,
        ElevatorFeedForwardProperty elevatorFf,
        ClosedLoopSlot slot,
        SparkBase motor,
        PidProperty sparkPidProperties,
        double positionConversionFactor) {
        m_slot = slot;
        m_positionConversionFactor = positionConversionFactor;
        m_profilePid = profiledPidController;
        m_wpiFeedForward = elevatorFf;
        m_sparkPidController = motor.getClosedLoopController();

        m_profilePidProperties = profilePidProperties;
        m_sparkPidProperties = sparkPidProperties;
    }

    public void updateIfChanged() {
        updateIfChanged(false);
    }

    public void updateIfChanged(boolean forceUpdate) {
        m_sparkPidProperties.updateIfChanged(forceUpdate);
        m_wpiFeedForward.updateIfChanged(forceUpdate);
        m_profilePidProperties.updateIfChanged(forceUpdate);
    }

    public double getPositionSetpoint() {
        return m_profilePid.getSetpoint().position;
    }

    public double getVelocitySetpoint() {
        return m_profilePid.getSetpoint().velocity;
    }

    public void goToHeight(double goalHeightMeters, double currentHeight, double currentVelocityMps) {
        m_profilePid.calculate(currentHeight, goalHeightMeters);

        TrapezoidProfile.State setpoint = m_profilePid.getSetpoint();
        double feedForwardVolts = m_wpiFeedForward.calculateWithVelocities(
            currentVelocityMps,
            setpoint.velocity);

        m_sparkPidController.setSetpoint(setpoint.position / m_positionConversionFactor, ControlType.kPosition, m_slot, feedForwardVolts);
    }

    public void resetPidController(double currentHeightMeters, double currentVelocityMps) {
        m_profilePid.reset(currentHeightMeters, currentVelocityMps);
    }


    public static class Builder {
        private final SparkBase m_motor;
        private final ClosedLoopSlot m_slot;
        private final RevPidPropertyBuilder m_revPidBuilder;
        private double m_positionConversionFactor = 1;

        private final ProfiledPIDController m_profilePid;
        private final WpiProfiledPidPropertyBuilder m_wpiBuilder;

        private final ElevatorFeedForwardProperty m_mechanismFeedForward;

        public Builder(String mechanismName, boolean isConstant, SparkBase motor, SparkBaseConfig parentConfig, ClosedLoopSlot slot) {
            m_motor = motor;
            m_slot = slot;

            m_profilePid = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(WpiProfiledPidPropertyBuilder.MIN_CONSTRAINT, WpiProfiledPidPropertyBuilder.MIN_CONSTRAINT));
            m_wpiBuilder = new WpiProfiledPidPropertyBuilder(mechanismName, isConstant, m_profilePid);

            m_mechanismFeedForward = new ElevatorFeedForwardProperty(mechanismName, isConstant);

            m_revPidBuilder = new RevPidPropertyBuilder(mechanismName, isConstant, motor, parentConfig, slot);
        }

        public Builder addMaxVelocity(double defaultValue) {
            m_wpiBuilder.addMaxVelocity(defaultValue);
            return this;
        }

        public Builder addMaxAcceleration(double defaultValue) {
            m_wpiBuilder.addMaxAcceleration(defaultValue);
            return this;
        }

        public Builder addKs(double defaultValue) {
            m_mechanismFeedForward.addKs(defaultValue);
            return this;
        }

        public Builder addKa(double defaultValue) {
            m_mechanismFeedForward.addKa(defaultValue);
            return this;
        }

        public Builder addKv(double defaultValue) {
            m_mechanismFeedForward.addKff(defaultValue);
            return this;
        }

        public Builder addKg(double defaultValue) {
            m_mechanismFeedForward.addKg(defaultValue);
            return this;
        }

        public Builder addKp(double defaultValue) {
            m_revPidBuilder.addP(defaultValue);
            return this;
        }

        public Builder addKi(double defaultValue) {
            m_revPidBuilder.addI(defaultValue);
            return this;
        }

        public Builder addKD(double defaultValue) {
            m_revPidBuilder.addD(defaultValue);
            return this;
        }

        /**
         * Sets the factor used to convert from SPARK native units (rotations) to mechanism units.
         *
         * <p>REVLib 2027 removed the SPARK side conversion factors, so the conversion is applied in software
         * before setpoints are sent to the motor controller.
         *
         * @param positionConversionFactor mechanism units per motor rotation
         * @return this
         */
        public Builder withPositionConversionFactor(double positionConversionFactor) {
            m_positionConversionFactor = positionConversionFactor;
            return this;
        }

        public RevProfiledElevatorController build() {
            return new RevProfiledElevatorController(
                m_profilePid,
                m_wpiBuilder.build(),
                m_mechanismFeedForward,
                m_slot,
                m_motor,
                m_revPidBuilder.build(),
                m_positionConversionFactor);
        }
    }
}
