package com.gos.lib.phoenix6.properties.pid;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.Slot2Configs;
import com.ctre.phoenix6.hardware.core.CoreTalonFX;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.gos.lib.properties.pid.IPidPropertyBuilder;
import com.gos.lib.properties.pid.PidProperty;

public final class Phoenix6TalonPidPropertyBuilder extends PidProperty.Builder implements IPidPropertyBuilder {
    private final CoreTalonFX m_motor;
    private final SlotConfigs m_slot;

    public Phoenix6TalonPidPropertyBuilder(String baseName, boolean isConstant, CoreTalonFX motor, int slot) {
        super(baseName, isConstant);
        m_motor = motor;
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

    @Override
    public IPidPropertyBuilder addP(double defaultValue) {
        addP(defaultValue, (double gain) -> {m_slot.kP = gain; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    @Override
    public IPidPropertyBuilder addI(double defaultValue) {
        addI(defaultValue, (double gain) -> {m_slot.kI = gain; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    @Override
    public IPidPropertyBuilder addD(double defaultValue) {
        addD(defaultValue, (double gain) -> {m_slot.kD = gain; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    public IPidPropertyBuilder addKV(double defaultValue) {
        addGenericProperty("kV", defaultValue, (double gain) -> {m_slot.kV = gain; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    public IPidPropertyBuilder addKS(double defaultValue) {
        addGenericProperty("kS", defaultValue, (double gain) -> {m_slot.kS = gain; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    public IPidPropertyBuilder addKG(double defaultValue, GravityTypeValue gravityType) {
        addGenericProperty("kG", defaultValue, (double gain) -> {m_slot.kG = gain; m_slot.GravityType = gravityType; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    public IPidPropertyBuilder addKA(double defaultValue) {
        addGenericProperty("kA", defaultValue, (double gain) -> {m_slot.kA = gain; m_motor.getConfigurator().apply(m_slot);});
        return this;
    }

    @Override
    public IPidPropertyBuilder addFF(double defaultValue) {
        throw new RuntimeException("Method Builder.addFF() doesn't work for the PhoenixV6 TalonFX. Try addKS(), addKV(), addKG(), and addKA() instead.");
    }

    @Override
    public IPidPropertyBuilder addMaxVelocity(double defaultValue) {
        throw new RuntimeException("Method Builder.addMaxVelocity() doesn't work for the PhoenixV6 TalonFX. Try setting up Feedforward values and using a MotionMagic control request instead.");
    }

    @Override
    public IPidPropertyBuilder addMaxAcceleration(double defaultValue) {
        throw new RuntimeException("Method Builder.addFF() doesn't work for the PhoenixV6 TalonFX. Try using addKA() or MotionMagic");
    }
}
