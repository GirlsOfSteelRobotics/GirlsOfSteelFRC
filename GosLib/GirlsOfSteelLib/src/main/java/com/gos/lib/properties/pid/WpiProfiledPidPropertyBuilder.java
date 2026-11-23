package com.gos.lib.properties.pid;

import org.wpilib.math.controller.ProfiledPIDController;
import org.wpilib.math.trajectory.TrapezoidProfile;

public final class WpiProfiledPidPropertyBuilder extends PidProperty.Builder implements IPidPropertyBuilder {
    /**
     * WPILib 2027 requires trapezoid profile constraints to be strictly positive. This is used as the
     * placeholder for an "unset" constraint, and as the lower bound for tuned values.
     */
    public static final double MIN_CONSTRAINT = Double.MIN_VALUE;

    private final ProfiledPIDController m_pidController;
    private TrapezoidProfile.Constraints m_constraints;

    public WpiProfiledPidPropertyBuilder(String baseName, boolean isConstant, ProfiledPIDController motor) {
        super(baseName, isConstant);
        m_pidController = motor;
        m_constraints = new TrapezoidProfile.Constraints(MIN_CONSTRAINT, MIN_CONSTRAINT);
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
            m_constraints = new TrapezoidProfile.Constraints(Math.max(val, MIN_CONSTRAINT), m_constraints.maxAcceleration);
            m_pidController.setConstraints(m_constraints);
        });
        return this;
    }

    @Override
    public IPidPropertyBuilder addMaxAcceleration(double defaultValue) {
        addMaxAcceleration(defaultValue, (double val) -> {
            m_constraints = new TrapezoidProfile.Constraints(m_constraints.maxVelocity, Math.max(val, MIN_CONSTRAINT));
            m_pidController.setConstraints(m_constraints);
        });
        return this;
    }
}
