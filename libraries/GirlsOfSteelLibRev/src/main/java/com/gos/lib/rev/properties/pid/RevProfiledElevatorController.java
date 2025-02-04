package com.gos.lib.rev.properties.pid;

import com.gos.lib.properties.feedforward.ElevatorFeedForwardProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkBaseConfig;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class RevProfiledElevatorController {
    private final ClosedLoopSlot m_slot;
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
        PidProperty sparkPidProperties) {
        m_slot = slot;
        m_profilePid = profiledPidController;
        m_wpiFeedForward = elevatorFf;
        m_sparkPidController = motor.getClosedLoopController();

        m_profilePidProperties = profilePidProperties;
        m_sparkPidProperties = sparkPidProperties;
    }

    public final void updateIfChanged() {
        updateIfChanged(false);
    }

    public final void updateIfChanged(boolean forceUpdate) {
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

        m_sparkPidController.setReference(setpoint.position, ControlType.kPosition, m_slot, feedForwardVolts);
    }

    public void resetPidController(double currentHeightMeters, double currentVelocityMps) {
        m_profilePid.reset(currentHeightMeters, currentVelocityMps);
    }


    public static class Builder {
        private final SparkBase m_motor;
        private final ClosedLoopSlot m_slot;
        private final RevPidPropertyBuilder m_revPidBuilder;

        private final ProfiledPIDController m_profilePid;
        private final WpiProfiledPidPropertyBuilder m_wpiBuilder;

        private final ElevatorFeedForwardProperty m_mechanismFeedForward;

        public Builder(String mechanismName, boolean isConstant, SparkBase motor, SparkBaseConfig parentConfig, ClosedLoopSlot slot) {
            m_motor = motor;
            m_slot = slot;

            m_profilePid = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
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

        public RevProfiledElevatorController build() {
            return new RevProfiledElevatorController(
                m_profilePid,
                m_wpiBuilder.build(),
                m_mechanismFeedForward,
                m_slot,
                m_motor,
                m_revPidBuilder.build());
        }
    }
}
