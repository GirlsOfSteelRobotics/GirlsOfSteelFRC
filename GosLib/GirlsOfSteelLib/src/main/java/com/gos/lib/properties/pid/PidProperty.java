package com.gos.lib.properties.pid;

import com.gos.lib.properties.BaseHeavyProperty;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.HeavyDoubleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;


public class PidProperty {
    private final List<HeavyDoubleProperty> m_properties;

    /* default */  public PidProperty(List<HeavyDoubleProperty> properties) {
        m_properties = properties;
    }

    public final void updateIfChanged() {
        updateIfChanged(false);
    }


    public final void updateIfChanged(boolean forceUpdate) {
        for (HeavyDoubleProperty property : m_properties) {
            property.updateIfChanged(forceUpdate);
        }
    }

    public final void resetValues() {
        m_properties.forEach(BaseHeavyProperty::resetValue);
    }

    public static class Builder {
        private final String m_baseName;

        private final List<HeavyDoubleProperty> m_properties;
        private final boolean m_isConstant;

        protected Builder(String baseName, boolean isConstant) {
            m_baseName = baseName;
            m_properties = new ArrayList<>();
            m_isConstant = isConstant;
        }

        private HeavyDoubleProperty createDoubleProperty(String propertyNameSuffix, double defaultValue, DoubleConsumer setter) {
            String propertyName = m_baseName + ".mm." + propertyNameSuffix;
            GosDoubleProperty prop = new GosDoubleProperty(m_isConstant, propertyName, defaultValue);

            return new HeavyDoubleProperty(setter, prop);
        }

        protected Builder addP(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("kp", defaultValue, setter));
            return this;
        }

        protected Builder addI(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("ki", defaultValue, setter));
            return this;
        }

        protected Builder addD(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("kd", defaultValue, setter));
            return this;
        }

        protected Builder addFF(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("kff", defaultValue, setter));
            return this;
        }

        protected Builder addMaxVelocity(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("max_velocity", defaultValue, setter));
            return this;
        }

        protected Builder addMaxAcceleration(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("max_acceleration", defaultValue, setter));
            return this;
        }

        protected  Builder addGenericProperty(String suffix, double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty(suffix, defaultValue, setter));
            return this;
        }

        public PidProperty build() {
            return new PidProperty(m_properties);
        }
    }
}
