package com.gos.lib.properties.feedforward;

import edu.wpi.first.math.controller.ArmFeedforward;

public class ArmFeedForwardProperty extends BaseFeedForwardProperty {

    private ArmFeedforward m_feedForward;

    public ArmFeedForwardProperty(String baseName, boolean isConstant) {
        this(baseName, isConstant, new ArmFeedforward(0, 0, 0, 0));
    }

    public ArmFeedForwardProperty(String baseName, boolean isConstant, ArmFeedforward feedforward) {
        super(baseName + ".smff.", isConstant);
        m_feedForward = feedforward;
    }

    public ArmFeedForwardProperty addKff(double defaultValue) {
        m_properties.add(createDoubleProperty("kff", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(m_feedForward.ks, m_feedForward.kg, v, m_feedForward.ka)));
        return this;
    }

    public ArmFeedForwardProperty addKs(double defaultValue) {
        m_properties.add(createDoubleProperty("ks", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(v, m_feedForward.kg, m_feedForward.kv, m_feedForward.ka)));
        return this;
    }


    public ArmFeedForwardProperty addKa(double defaultValue) {
        m_properties.add(createDoubleProperty("ka", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(m_feedForward.ks, m_feedForward.kg, m_feedForward.kv, v)));
        return this;
    }

    public ArmFeedForwardProperty addKg(double defaultValue) {
        m_properties.add(createDoubleProperty("kg", defaultValue,
            (v) -> m_feedForward = new ArmFeedforward(m_feedForward.ks, v, m_feedForward.kv, m_feedForward.ka)));
        return this;
    }

    public double calculate(double position, double velocity) {
        return m_feedForward.calculate(position, velocity);

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
