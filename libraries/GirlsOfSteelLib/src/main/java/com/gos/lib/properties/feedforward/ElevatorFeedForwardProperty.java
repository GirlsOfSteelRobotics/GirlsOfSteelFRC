package com.gos.lib.properties.feedforward;

import edu.wpi.first.math.controller.ElevatorFeedforward;

public class ElevatorFeedForwardProperty extends BaseFeedForwardProperty {

    private ElevatorFeedforward m_feedForward;

    public ElevatorFeedForwardProperty(String baseName, boolean isConstant) {
        this(baseName, isConstant, new ElevatorFeedforward(0, 0, 0, 0));
    }

    public ElevatorFeedForwardProperty(String baseName, boolean isConstant, ElevatorFeedforward feedforward) {
        super(baseName + ".aff.", isConstant);
        m_feedForward = feedforward;
    }

    public ElevatorFeedForwardProperty addKff(double defaultValue) {
        m_properties.add(createDoubleProperty("kff", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(m_feedForward.ks, m_feedForward.kg, v, m_feedForward.ka)));
        return this;
    }

    public ElevatorFeedForwardProperty addKs(double defaultValue) {
        m_properties.add(createDoubleProperty("ks", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(v, m_feedForward.kg, m_feedForward.kv, m_feedForward.ka)));
        return this;
    }


    public ElevatorFeedForwardProperty addKa(double defaultValue) {
        m_properties.add(createDoubleProperty("ka", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(m_feedForward.ks, m_feedForward.kg, m_feedForward.kv, v)));
        return this;
    }

    public ElevatorFeedForwardProperty addKg(double defaultValue) {
        m_properties.add(createDoubleProperty("kg", defaultValue,
            (v) -> m_feedForward = new ElevatorFeedforward(m_feedForward.ks, v, m_feedForward.kv, m_feedForward.ka)));
        return this;
    }

    public double calculate(double velocity, double acceleration) {
        return m_feedForward.calculate(velocity, acceleration);
    }

    public double calculate(double velocity) {
        return m_feedForward.calculate(velocity);
    }

    public double getKs() {
        return m_feedForward.ks;
    }

    public double getKg() {
        return m_feedForward.kg;
    }

    public double getKFf() {
        return m_feedForward.kv;
    }

    public double getKa() {
        return m_feedForward.ka;
    }
}
