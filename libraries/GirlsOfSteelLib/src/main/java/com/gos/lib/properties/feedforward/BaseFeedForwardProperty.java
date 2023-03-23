package com.gos.lib.properties.feedforward;

import com.gos.lib.properties.HeavyDoubleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

public class BaseFeedForwardProperty {
    private final String m_baseName;
    private final boolean m_isConstant;
    protected final List<HeavyDoubleProperty> m_properties;

    public BaseFeedForwardProperty(String baseName, boolean isConstant) {
        m_baseName = baseName;
        m_properties = new ArrayList<>();
        m_isConstant = isConstant;
    }

    protected HeavyDoubleProperty createDoubleProperty(String propertyNameSuffix, double defaultValue, DoubleConsumer setter) {
        return HeavyDoubleProperty.create(m_baseName  + propertyNameSuffix, m_isConstant, defaultValue, setter);
    }


    public final void updateIfChanged() {
        updateIfChanged(false);
    }

    public final void updateIfChanged(boolean forceUpdate) {
        for (HeavyDoubleProperty property : m_properties) {
            property.updateIfChanged(forceUpdate);
        }
    }
}
