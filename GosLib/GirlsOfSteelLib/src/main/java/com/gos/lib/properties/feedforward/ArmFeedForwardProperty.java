package com.gos.lib.properties.feedforward;

import org.wpilib.math.controller.ArmFeedforward;

public class ArmFeedForwardProperty extends BaseFeedForwardProperty {

    private ArmFeedforward m_feedForward;

    public ArmFeedForwardProperty(String baseName, boolean isConstant) {
        this(baseName, isConstant, new ArmFeedforward(0, 0, 0, 0));
    }

    public ArmFeedForwardProperty(String baseName, boolean isConstant, ArmFeedforward feedforward) {
        super(baseName + ".aff.", isConstant);
        m_feedForward = feedforward;
    }

    public ArmFeedForwardProperty addKff(double defaultValue) {
        m_properties.add(createDoubleProperty("kff", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(m_feedForward.getKs(), m_feedForward.getKg(), v, m_feedForward.getKa())));
        return this;
    }

    public ArmFeedForwardProperty addKs(double defaultValue) {
        m_properties.add(createDoubleProperty("ks", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(v, m_feedForward.getKg(), m_feedForward.getKv(), m_feedForward.getKa())));
        return this;
    }


    public ArmFeedForwardProperty addKa(double defaultValue) {
        m_properties.add(createDoubleProperty("ka", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(m_feedForward.getKs(), m_feedForward.getKg(), m_feedForward.getKv(), v)));
        return this;
    }

    public ArmFeedForwardProperty addKg(double defaultValue) {
        m_properties.add(createDoubleProperty("kg", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(m_feedForward.getKs(), v, m_feedForward.getKv(), m_feedForward.getKa())));
        return this;
    }

    /**
     * @see ArmFeedforward#calculate
     */
    @SuppressWarnings("removal")
    @Deprecated(forRemoval = true, since = "2025")
    public double calculate(double positionRadians, double velocity) {
        return m_feedForward.calculate(positionRadians, velocity);
    }

    /**
     * @see ArmFeedforward#calculate
     */
    @SuppressWarnings("removal")
    @Deprecated(forRemoval = true, since = "2025")
    public double calculate(double positionRadians, double velocityRadPerSec, double accelRadPerSecSquared) {
        // 2027: WPILib's two/three argument calculate() now takes (current, next) velocities, so the old
        // continuous (velocity, acceleration) formula is computed directly from the gains.
        return m_feedForward.getKs() * Math.signum(velocityRadPerSec) + m_feedForward.getKg() * Math.cos(positionRadians)
            + m_feedForward.getKv() * velocityRadPerSec + m_feedForward.getKa() * accelRadPerSecSquared;
    }

    /**
     * @see ArmFeedforward#calculateWithVelocities
     */
    public double calculateWithVelocities(
        double currentAngle, double currentVelocity, double nextVelocity) {
        return m_feedForward.calculate(currentAngle, currentVelocity, nextVelocity);
    }

    /**
     * @see ArmFeedforward#getKs
     */
    public double getKs() {
        return m_feedForward.getKs();
    }

    /**
     * @see ArmFeedforward#getKg
     */
    public double getKg() {
        return m_feedForward.getKg();
    }

    /**
     * @see ArmFeedforward#getKv
     */
    public double getKFf() {
        return m_feedForward.getKv();
    }

    /**
     * @see ArmFeedforward#getKa
     */
    public double getKa() {
        return m_feedForward.getKa();
    }
}
