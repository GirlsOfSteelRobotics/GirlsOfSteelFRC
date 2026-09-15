package com.gos.lib.properties.feedforward;

import com.gos.lib.properties.BaseHeavyProperty;
import com.gos.lib.properties.HeavyDoubleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

/**
 * Helper base class for storing a list of {@link HeavyDoubleProperty}'s
 */
public class BaseFeedForwardProperty {
    private final String m_baseName;
    private final boolean m_isConstant;
    protected final List<HeavyDoubleProperty> m_properties;

    /**
     * Constructor
     * @param baseName Prefix for the underlying {@link com.gos.lib.properties.GosDoubleProperty}
     * @param isConstant True if the value should be constant, and not editable over network tables
     */
    public BaseFeedForwardProperty(String baseName, boolean isConstant) {
        m_baseName = baseName;
        m_properties = new ArrayList<>();
        m_isConstant = isConstant;
    }

    /**
     * Helper to create a {@link HeavyDoubleProperty}
     * @param propertyNameSuffix The suffix to be added after the base name
     * @param defaultValue The default value
     * @param setter A consumer for accepting updates.
     * @return The new property
     */
    protected HeavyDoubleProperty createDoubleProperty(String propertyNameSuffix, double defaultValue, DoubleConsumer setter) {
        return HeavyDoubleProperty.create(m_baseName  + propertyNameSuffix, m_isConstant, defaultValue, setter);
    }


    /**
     * Will call the update consumer, if it has changed over the network tables
     */
    public final void updateIfChanged() {
        updateIfChanged(false);
    }

    /**
     * Will call the update consumers. If forceUpdate is true, the consumer will be called whether the underlying value has changed
     * @param forceUpdate If the update should be forced
     */
    public final void updateIfChanged(boolean forceUpdate) {
        for (HeavyDoubleProperty property : m_properties) {
            property.updateIfChanged(forceUpdate);
        }
    }

    /**
     * Resets the value to its default value
     */
    public final void resetValues() {
        m_properties.forEach(BaseHeavyProperty::resetValue);
    }
}
