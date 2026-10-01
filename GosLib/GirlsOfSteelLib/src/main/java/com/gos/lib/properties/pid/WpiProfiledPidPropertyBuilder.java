package com.gos.lib.properties.pid;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class WpiProfiledPidPropertyBuilder extends PidProperty.Builder implements IPidPropertyBuilder {
    private final ProfiledPIDController m_pidController;
    private TrapezoidProfile.Constraints m_constraints;

    public WpiProfiledPidPropertyBuilder(String baseName, boolean isConstant, ProfiledPIDController motor) {
        super(baseName, isConstant);
        m_pidController = motor;
        m_constraints = new TrapezoidProfile.Constraints(0, 0);
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
        addMaxVelocity(defaultValue, (double val) -> {
            m_constraints = new TrapezoidProfile.Constraints(val, m_constraints.maxAcceleration);
            m_pidController.setConstraints(m_constraints);
        });
        return this;
    }

    @Override
    public IPidPropertyBuilder addMaxAcceleration(double defaultValue) {
        addMaxAcceleration(defaultValue, (double val) -> {
            m_constraints = new TrapezoidProfile.Constraints(m_constraints.maxVelocity, val);
            m_pidController.setConstraints(m_constraints);
        });
        return this;
    }
}
