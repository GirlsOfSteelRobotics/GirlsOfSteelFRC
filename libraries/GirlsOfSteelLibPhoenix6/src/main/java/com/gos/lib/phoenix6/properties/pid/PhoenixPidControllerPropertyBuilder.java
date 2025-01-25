package com.gos.lib.phoenix6.properties.pid;

import com.ctre.phoenix6.swerve.utility.PhoenixPIDController;
import com.gos.lib.properties.pid.IPidPropertyBuilder;
import com.gos.lib.properties.pid.PidProperty;

public class PhoenixPidControllerPropertyBuilder extends PidProperty.Builder implements IPidPropertyBuilder {
    private final PhoenixPIDController m_pidController;

    public PhoenixPidControllerPropertyBuilder(String baseName, boolean isConstant, PhoenixPIDController motor) {
        super(baseName, isConstant);
        m_pidController = motor;
    }

    @Override
    public IPidPropertyBuilder addP(double defaultValue) {
        addP(defaultValue, m_pidController::setP);
        return this;
    }

    @Override
    public IPidPropertyBuilder addI(double defaultValue) {
        addI(defaultValue, m_pidController::setI);
        return this;
    }

    @Override
    public IPidPropertyBuilder addD(double defaultValue) {
        addD(defaultValue, m_pidController::setD);
        return this;
    }

    @Override
    public IPidPropertyBuilder addFF(double defaultValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPidPropertyBuilder addMaxVelocity(double defaultValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IPidPropertyBuilder addMaxAcceleration(double defaultValue) {
        throw new UnsupportedOperationException();
    }
}
