package com.gos.lib.properties;

import java.util.function.DoubleConsumer;

/**
 * A wrapper around PropertyManager.DoubleProperty, for things that shouldn't
 * be updated frequently for performance issues, like PIDF gains that go over the
 * CAN bus.
 *
 * You should call updateIfChanged every loop, but the actual setter will only get
 * called if the value changes
 */
public class HeavyDoubleProperty {
    private final DoubleConsumer m_setter;
    private final PropertyManager.IProperty<Double> m_property;
    private double m_lastValue;

    public HeavyDoubleProperty(DoubleConsumer setter, PropertyManager.IProperty<Double> property) {
        m_setter = setter;
        m_property = property;
        m_lastValue = property.getValue();

        updateIfChanged(true);
    }

    public final void updateIfChanged() {
        updateIfChanged(false);
    }

    public final void updateIfChanged(boolean forceUpdate) {
        double newValue = m_property.getValue();
        if (newValue != m_lastValue || forceUpdate) {
            System.out.println("Value for " + m_property.getName() + " changed from " + m_lastValue + " to " + newValue);
            m_setter.accept(newValue);
            m_lastValue = newValue;
        }
    }

}
