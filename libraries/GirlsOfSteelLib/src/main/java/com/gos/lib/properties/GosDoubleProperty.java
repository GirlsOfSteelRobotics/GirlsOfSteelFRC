package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;

public class GosDoubleProperty implements PropertyManager.IProperty<Double> {
    private final PropertyManager.IProperty<Double> m_impl;

    public GosDoubleProperty(boolean isConstant, String key, double defaultValue) {
        if (isConstant) {
            m_impl = new PropertyManager.ConstantProperty<>(key, defaultValue);
        } else {
            m_impl = new PropertyManager.BaseProperty<>(key, defaultValue, Preferences::setDouble, Preferences::getDouble);
        }
    }

    @Override
    public Double getValue() {
        return m_impl.getValue();
    }

    @Override
    public String getName() {
        return m_impl.getName();
    }
}
