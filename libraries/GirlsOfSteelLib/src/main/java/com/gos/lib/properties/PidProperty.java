package com.gos.lib.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;


public class PidProperty {
    private final List<HeavyDoubleProperty> m_properties;


    /* default */ PidProperty(List<HeavyDoubleProperty> properties) {
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
            PropertyManager.IProperty<Double> prop = PropertyManager.createDoubleProperty(m_isConstant, propertyName, defaultValue);
            if (m_isConstant) {
                return new HeavyDoubleProperty(setter, prop);
            }
            else {
                return new HeavyDoubleProperty(setter, prop);
            }
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

        public PidProperty build() {
            return new PidProperty(m_properties);
        }
    }
}
