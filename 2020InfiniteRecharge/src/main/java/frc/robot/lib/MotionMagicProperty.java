package frc.robot.lib;

import frc.robot.lib.HeavyDoubleProperty;
import frc.robot.lib.PropertyManager.ConstantProperty;
import frc.robot.lib.PropertyManager.DoubleProperty;
import frc.robot.lib.PropertyManager.IProperty;
import frc.robot.lib.PropertyManager.IntProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;


public class MotionMagicProperty
{
    private final List<HeavyDoubleProperty> m_properties;

    private MotionMagicProperty(List<HeavyDoubleProperty> properties) {
        m_properties = properties;
    }

    public void updateIfChanged() {
        for(HeavyDoubleProperty property : m_properties)
        {
            property.updateIfChanged();
        }
    }

    public static class Builder
    {
        private final String m_baseName;

        private final List<HeavyDoubleProperty> m_properties;

        private Builder(String baseName)
        {
            m_baseName = baseName;
            m_properties = new ArrayList<>();
        }

        public static Builder createBuilder(String baseName) {
            return new Builder(baseName);
        }

        private HeavyDoubleProperty createDoubleProperty(String propertyNameSuffix, boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            String propertyName = m_baseName + ".mm." + propertyNameSuffix;
            if (isConstant)
            {
                return new HeavyDoubleProperty(setter, new ConstantProperty<>(propertyName, defaultValue));
            }
            else
            {
                return new HeavyDoubleProperty(setter, new DoubleProperty(propertyName, defaultValue));
            }
        }

        public Builder addP(boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            m_properties.add(createDoubleProperty("kp", isConstant, defaultValue, setter));
            return this;
        }
        public Builder addI(boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            m_properties.add(createDoubleProperty("ki", isConstant, defaultValue, setter));
            return this;
        }
        public Builder addD(boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            m_properties.add(createDoubleProperty("kd", isConstant, defaultValue, setter));
            return this;
        }

        public Builder addFF(boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            m_properties.add(createDoubleProperty("kff", isConstant, defaultValue, setter));
            return this;
        }

        public Builder addMaxVelocity(boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            m_properties.add(createDoubleProperty("max_velocity", isConstant, defaultValue, setter));
            return this;
        }

        public Builder addMaxAcceleration(boolean isConstant, double defaultValue, DoubleConsumer setter)
        {
            m_properties.add(createDoubleProperty("max_acceleration", isConstant, defaultValue, setter));
            return this;
        }

        public MotionMagicProperty build()
        {
            return new MotionMagicProperty(m_properties);
        }
    }
}
