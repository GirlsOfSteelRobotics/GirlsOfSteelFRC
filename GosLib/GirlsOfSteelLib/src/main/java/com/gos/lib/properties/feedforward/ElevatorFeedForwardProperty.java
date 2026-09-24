package com.gos.lib.properties.feedforward;

import edu.wpi.first.math.controller.ElevatorFeedforward;

/**
 * Wrapper around {@link ElevatorFeedforward} to leverage tunable properties from the network tables
 */
public class ElevatorFeedForwardProperty extends BaseFeedForwardProperty {

    private ElevatorFeedforward m_feedForward;

    public ElevatorFeedForwardProperty(String baseName, boolean isConstant) {
        this(baseName, isConstant, new ElevatorFeedforward(0, 0, 0, 0));
    }

    public ElevatorFeedForwardProperty(String baseName, boolean isConstant, ElevatorFeedforward feedforward) {
        super(baseName + ".eff.", isConstant);
        m_feedForward = feedforward;
    }

    public ElevatorFeedForwardProperty addKff(double defaultValue) {
        m_properties.add(createDoubleProperty("kff", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(m_feedForward.getKs(), m_feedForward.getKg(), v, m_feedForward.getKa())));
        return this;
    }

    public ElevatorFeedForwardProperty addKs(double defaultValue) {
        m_properties.add(createDoubleProperty("ks", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(v, m_feedForward.getKg(), m_feedForward.getKv(), m_feedForward.getKa())));
        return this;
    }


    public ElevatorFeedForwardProperty addKa(double defaultValue) {
        m_properties.add(createDoubleProperty("ka", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(m_feedForward.getKs(), m_feedForward.getKg(), m_feedForward.getKv(), v)));
        return this;
    }

    public ElevatorFeedForwardProperty addKg(double defaultValue) {
        m_properties.add(createDoubleProperty("kg", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(m_feedForward.getKs(), v, m_feedForward.getKv(), m_feedForward.getKa())));
        return this;
    }

    /**
     * Calculates the feedforward from the gains and setpoints assuming continuous control.
     *
     * @param velocity The velocity setpoint.
     * @param acceleration The acceleration setpoint.
     * @return The computed feedforward.
     */
    @SuppressWarnings("removal")
    @Deprecated(forRemoval = true, since = "2025")
    public double calculate(double velocity, double acceleration) {
        return m_feedForward.calculate(velocity, acceleration);
    }

    /**
     * Calculates the feedforward from the gains and velocity setpoint assuming continuous control
     * (acceleration is assumed to be zero).
     *
     * @param velocity The velocity setpoint.
     * @return The computed feedforward.
     */
    @Deprecated(forRemoval = true, since = "2025")
    public double calculate(double velocity) {
        return m_feedForward.calculate(velocity);
    }

    /**
     * Calculates the feedforward from the gains and setpoints assuming discrete control.
     *
     * <p>Note this method is inaccurate when the velocity crosses 0.
     *
     * @param currentVelocity The current velocity setpoint in meters per second.
     * @param nextVelocity The next velocity setpoint in meters per second.
     * @return The computed feedforward in volts.
     */
    public double calculateWithVelocities(double currentVelocity, double nextVelocity) {
        return m_feedForward.calculateWithVelocities(currentVelocity, nextVelocity);
    }

    /**
     * Returns the static gain in volts.
     *
     * @return The static gain in volts.
     */
    public double getKs() {
        return m_feedForward.getKs();
    }

    /**
     * Returns the gravity gain in volts.
     *
     * @return The gravity gain in volts.
     */
    public double getKg() {
        return m_feedForward.getKg();
    }

    /**
     * Returns the velocity gain in V/(m/s).
     *
     * @return The velocity gain.
     */
    public double getKFf() {
        return m_feedForward.getKv();
    }

    /**
     * Returns the acceleration gain in V/(m/s²).
     *
     * @return The acceleration gain.
     */
    public double getKa() {
        return m_feedForward.getKa();
    }
}
