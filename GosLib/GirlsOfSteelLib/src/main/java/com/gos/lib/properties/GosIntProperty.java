package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Integer implementation of a GOS tunable property
 */
public class GosIntProperty implements PropertyManager.IProperty<Integer> {
    private final PropertyManager.IProperty<Integer> m_impl;

    /**
     * Constructor
     * @param isConstant If the value should be constant, aka not tunable
     * @param key The key for the property, used as the name in the Preferences widget
     * @param defaultValue The default value to use if there is no entry in the network tables
     */
    public GosIntProperty(boolean isConstant, String key, int defaultValue) {
        if (isConstant) {
            m_impl = new PropertyManager.ConstantProperty<>(key, defaultValue);
        } else {
            m_impl = new PropertyManager.BaseProperty<>(key, defaultValue, Preferences::setInt, Preferences::getInt);
        }
    }

    /**
     * Gets the value
     * @return The value
     */
    @Override
    public Integer getValue() {
        return m_impl.getValue();
    }

    /**
     * Gets the name of the property
     * @return The name
     */
    @Override
    public String getName() {
        return m_impl.getName();
    }

    /**
     * Resets the property to its default value
     */
    @Override
    public void resetValue() {
        m_impl.resetValue();
    }
}
