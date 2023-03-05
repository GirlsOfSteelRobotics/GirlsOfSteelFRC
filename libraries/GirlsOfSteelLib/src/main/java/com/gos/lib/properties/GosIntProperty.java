package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;

public class GosIntProperty implements PropertyManager.IProperty<Integer> {
    private final PropertyManager.IProperty<Integer> m_impl;

    public GosIntProperty(boolean isConstant, String key, int defaultValue) {
        if (isConstant) {
            m_impl = new PropertyManager.ConstantProperty<>(key, defaultValue);
        } else {
            m_impl = new PropertyManager.BaseProperty<>(key, defaultValue, Preferences::setInt, Preferences::getInt);
        }
    }

    @Override
    public Integer getValue() {
        return m_impl.getValue();
    }

    @Override
    public String getName() {
        return m_impl.getName();
    }
}
