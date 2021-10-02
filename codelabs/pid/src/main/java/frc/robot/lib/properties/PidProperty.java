package frc.robot.lib.properties;

import frc.robot.lib.properties.PropertyManager.ConstantProperty;
import frc.robot.lib.properties.PropertyManager.DoubleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;


@SuppressWarnings("PMD")
public final class PidProperty {
    private final List<HeavyDoubleProperty> m_properties;

    private PidProperty(List<HeavyDoubleProperty> properties) {
        m_properties = properties;
    }

    public void updateIfChanged() {
        updateIfChanged(false);
    }
    

    public void updateIfChanged(boolean forceUpdate) {
        for (HeavyDoubleProperty property : m_properties) {
            property.updateIfChanged(forceUpdate);
        }
    }

    public abstract static class Builder implements IPidPropertyBuilder {
        private final String m_baseName;
        private final boolean m_isConstant;

        private final List<HeavyDoubleProperty> m_properties;

        protected Builder(String baseName, boolean isConstant) {
            m_baseName = baseName;
            m_isConstant = isConstant;
            m_properties = new ArrayList<>();
        }

        private HeavyDoubleProperty createDoubleProperty(String propertyNameSuffix, double defaultValue, DoubleConsumer setter) {
            String propertyName = m_baseName + ".pid." + propertyNameSuffix;
            if (m_isConstant) {
                return new HeavyDoubleProperty(setter, new ConstantProperty<>(propertyName, defaultValue));
            }
            else {
                return new HeavyDoubleProperty(setter, new DoubleProperty(propertyName, defaultValue));
            }
        }

        protected void addP(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("kp", defaultValue, setter));
        }

        protected void addI(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("ki", defaultValue, setter));
        }

        protected void addD(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("kd", defaultValue, setter));
        }

        protected void addFF(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("kff", defaultValue, setter));
        }

        protected void addMaxVelocity(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("max_velocity", defaultValue, setter));
        }

        protected void addMaxAcceleration(double defaultValue, DoubleConsumer setter) {
            m_properties.add(createDoubleProperty("max_acceleration", defaultValue, setter));
        }

        @Override
        public PidProperty build() {
            PidProperty output = new PidProperty(m_properties);
            output.updateIfChanged(true);
            return output;
        }
    }
}
