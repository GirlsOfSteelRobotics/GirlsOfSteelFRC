package com.gos.lib.phoenix6.properties.pid;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.Slot2Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.hardware.core.CoreTalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.HeavyDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

/**
 * PID Property builder for a {@link com.ctre.phoenix6.hardware.TalonFX}
 */
public final class Phoenix6TalonPidPropertyBuilder {
    private final CoreTalonFX m_motor;
    private final SlotConfigs m_slot;
    private final boolean m_isConstant;
    private final String m_baseName;
    private final List<HeavyDoubleProperty> m_props;

    /**
     * Constructor.
     * @param baseName The name for this combined property. Used as a prefix for all of the property names
     * @param isConstant True if the values should be constant, aka not tunable
     * @param motor The motor to configure
     * @param slot The slot to configure
     */
    @SuppressWarnings("PMD.ImplicitSwitchFallThrough")
    public Phoenix6TalonPidPropertyBuilder(String baseName, boolean isConstant, CoreTalonFX motor, int slot) {
        m_isConstant = isConstant;
        m_baseName = baseName;
        m_motor = motor;

        m_props = new ArrayList<>();

        switch (slot) {
        case 1:
            m_slot = SlotConfigs.from(new Slot1Configs());
            break;
        case 2:
            m_slot = SlotConfigs.from(new Slot2Configs());
            break;
        case 0:
        default:
            m_slot = SlotConfigs.from(new Slot0Configs());
        }
    }

    private HeavyDoubleProperty createDoubleProperty(String propertyNameSuffix, double defaultValue, DoubleConsumer setter) {
        String propertyName = m_baseName + propertyNameSuffix;
        GosDoubleProperty prop = new GosDoubleProperty(m_isConstant, propertyName, defaultValue);

        return new HeavyDoubleProperty(setter, prop);
    }

    /**
     * Adds a kP value
     * @param defaultValue The default value
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addP(double defaultValue) {
        m_props.add(createDoubleProperty("kp", defaultValue, (double gain) -> {
            m_slot.kP = gain;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Adds a kI value
     * @param defaultValue The default value
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addI(double defaultValue) {
        m_props.add(createDoubleProperty("ki", defaultValue, (double gain) -> {
            m_slot.kI = gain;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Adds a kD value
     * @param defaultValue The default value
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addD(double defaultValue) {
        m_props.add(createDoubleProperty("kD", defaultValue, (double gain) -> {
            m_slot.kD = gain;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Adds a kFF / kV value
     * @param defaultValue The default value
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addKV(double defaultValue) {
        m_props.add(createDoubleProperty("kV", defaultValue, (double gain) -> {
            m_slot.kV = gain;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Adds a kS value
     * @param defaultValue The default value
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addKS(double defaultValue) {
        m_props.add(createDoubleProperty("kS", defaultValue, (double gain) -> {
            m_slot.kS = gain;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Adds a kG value
     * @param defaultValue The default value
     * @param gravityType The type of gravity to use
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addKG(double defaultValue, GravityTypeValue gravityType) {
        m_props.add(createDoubleProperty("kG", defaultValue, (double gain) -> {
            m_slot.kG = gain;
            m_slot.GravityType = gravityType;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Adds a kA value
     * @param defaultValue The default value
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder addKA(double defaultValue) {
        m_props.add(createDoubleProperty("kA", defaultValue, (double gain) -> {
            m_slot.kA = gain;
            m_motor.getConfigurator().apply(m_slot);
        }));
        return this;
    }

    /**
     * Builds the actual PID property. This should be called only after configuring all of your gains
     * @return The build PID property
     */
    public PidProperty build() {
        return new PidProperty(m_props);
    }

    /**
     * Configures a property builder from default values. Useful if the slot configs were already defined, but you want to make them tunable
     * @param config The predefined configs
     * @return The self builder
     */
    public Phoenix6TalonPidPropertyBuilder fromDefaults(SlotConfigs config) {
        if (config.kP != 0) {
            addP(config.kP);
        }
        if (config.kI != 0) {
            addI(config.kI);
        }
        if (config.kD != 0) {
            addD(config.kD);
        }
        if (config.kS != 0) {
            addKS(config.kS);
        }
        if (config.kV != 0) {
            addKV(config.kV);
        }
        if (config.kA != 0) {
            addKA(config.kA);
        }
        if (config.kG != 0) {
            addKG(config.kG, config.GravityType);
        }

        return this;
    }
}
