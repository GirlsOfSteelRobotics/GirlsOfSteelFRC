package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;

public class GosBooleanProperty {
    private final PropertyManager.IProperty<Boolean> m_impl;

    public GosBooleanProperty(boolean isConstant, String key, boolean defaultValue) {
        if (isConstant) {
            m_impl = new PropertyManager.ConstantProperty<>(key, defaultValue);
        } else {
            m_impl = new PropertyManager.BaseProperty<>(key, defaultValue, Preferences::setBoolean, Preferences::getBoolean);
        }
    }

    public boolean getValue() {
        return m_impl.getValue();
    }

    public String getName() {
        return m_impl.getName();
    }
}
