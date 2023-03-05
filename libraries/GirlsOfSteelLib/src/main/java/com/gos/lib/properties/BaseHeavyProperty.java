package com.gos.lib.properties;

import java.util.function.Consumer;

public class BaseHeavyProperty<T> {
    protected final Consumer<T> m_setter;
    protected final PropertyManager.IProperty<T> m_property;
    protected T m_lastValue;

    public BaseHeavyProperty(Consumer<T> setter, PropertyManager.IProperty<T> property) {
        m_setter = setter;
        m_property = property;
        m_lastValue = property.getValue();

        updateIfChanged(true);
    }

    public final void updateIfChanged() {
        updateIfChanged(false);
    }

    public final void updateIfChanged(boolean forceUpdate) {
        T newValue = m_property.getValue();
        if (newValue != null && !newValue.equals(m_lastValue) || forceUpdate) {
            System.out.println("Value for " + m_property.getName() + " changed from " + m_lastValue + " to " + newValue);
            m_setter.accept(newValue);
            m_lastValue = newValue;
        }
    }
}
