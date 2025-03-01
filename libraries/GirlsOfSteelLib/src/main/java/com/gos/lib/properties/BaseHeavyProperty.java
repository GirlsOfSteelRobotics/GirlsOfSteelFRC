package com.gos.lib.properties;

import java.util.function.Consumer;

/**
 * Base class for a "Heavy" property. Heavy properties are ones that require extra work to set or get a value that might be expensive to do if the value has not changed. For example, sending a CAN message to configure PID gains.
 * @param <T> The type of the underlying property
 */
public class BaseHeavyProperty<T> {
    protected final Consumer<T> m_setter;
    protected final PropertyManager.IProperty<T> m_property;
    protected T m_lastValue;

    /**
     * Constructor.
     * @param setter The consumer called when values are changed
     * @param property The underlying property
     */
    public BaseHeavyProperty(Consumer<T> setter, PropertyManager.IProperty<T> property) {
        m_setter = setter;
        m_property = property;
        m_lastValue = property.getValue();

        updateIfChanged(true);
    }

    /**
     * Updates the value, only if it was changed.
     */
    public final void updateIfChanged() {
        updateIfChanged(false);
    }

    /**
     * Updates the value. If forceUpdate is set the consumer will be called regardless of if the value has changed.
     * @param forceUpdate If the update should be forced
     */
    public final void updateIfChanged(boolean forceUpdate) {
        T newValue = m_property.getValue();
        if (newValue != null && !newValue.equals(m_lastValue) || forceUpdate) {
            System.out.println("Value for " + m_property.getName() + " changed from " + m_lastValue + " to " + newValue);
            m_setter.accept(newValue);
            m_lastValue = newValue;
        }
    }

    /**
     * Resets the underlying property to the default value
     */
    public final void resetValue() {
        m_property.resetValue();
    }
}
