package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;

public class GosStringProperty {
    private final PropertyManager.IProperty<String> m_impl;

    public GosStringProperty(boolean isConstant, String key, String defaultValue) {
        if (isConstant) {
            m_impl = new PropertyManager.ConstantProperty<>(key, defaultValue);
        } else {
            m_impl = new PropertyManager.BaseProperty<>(key, defaultValue, Preferences::setString, Preferences::getString);
        }
    }

    public String getValue() {
        return m_impl.getValue();
    }

    public String getName() {
        return m_impl.getName();
    }
}
