package com.gos.lib.properties.feedforward;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;

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

    @SuppressWarnings("removal")
    @Deprecated(forRemoval = true, since = "2025")
    public double calculate(double velocity, double acceleration) {
        return m_feedForward.calculate(velocity, acceleration);
    }

    @SuppressWarnings("removal")
    @Deprecated(forRemoval = true, since = "2025")
    public double calculate(double velocity) {
        return m_feedForward.calculate(velocity);
    }

    /**
     * Calculates the feedforward from the gains and setpoints assuming discrete control when the
     * setpoint does not change.
     *
     * @param currentVelocity The velocity setpoint.
     * @return The computed feedforward.
     */
    public Voltage calculate(LinearVelocity currentVelocity) {
        return m_feedForward.calculate(currentVelocity);
    }

    /**
     * Calculates the feedforward from the gains and setpoints assuming discrete control.
     *
     * <p>Note this method is inaccurate when the velocity crosses 0.
     *
     * @param currentVelocity The current velocity setpoint.
     * @param nextVelocity The next velocity setpoint.
     * @return The computed feedforward.
     */
    public Voltage calculate(LinearVelocity currentVelocity, LinearVelocity nextVelocity) {
        return m_feedForward.calculate(currentVelocity, nextVelocity);
    }

    public double getKs() {
        return m_feedForward.getKs();
    }

    public double getKg() {
        return m_feedForward.getKg();
    }

    public double getKFf() {
        return m_feedForward.getKv();
    }

    public double getKa() {
        return m_feedForward.getKa();
    }
}
