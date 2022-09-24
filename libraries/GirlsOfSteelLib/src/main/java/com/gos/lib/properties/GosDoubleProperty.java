package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;

public class GosDoubleProperty {
    private final PropertyManager.IProperty<Double> m_impl;

    public GosDoubleProperty(boolean isConstant, String key, double defaultValue) {
        if (isConstant) {
            m_impl = new PropertyManager.ConstantProperty<>(key, defaultValue);
        } else {
            m_impl = new PropertyManager.BaseProperty<>(key, defaultValue, Preferences::setDouble, Preferences::getDouble);
        }
    }

    public double getValue() {
        return m_impl.getValue();
    }

    public String getName() {
        return m_impl.getName();
    }
}
