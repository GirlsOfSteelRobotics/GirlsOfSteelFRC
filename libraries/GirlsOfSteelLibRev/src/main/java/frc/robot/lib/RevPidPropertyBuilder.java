package frc.robot.lib;

import com.revrobotics.CANPIDController;

public final class RevPidPropertyBuilder extends PidProperty.Builder implements IPidPropertyBuilder {
    private final CANPIDController m_pidController;
    private final int m_slot;

    public RevPidPropertyBuilder(String baseName, boolean isConstant, CANPIDController pidController, int slot) {
        super(baseName, isConstant);
        m_pidController = pidController;
        m_slot = slot;
    }

    @Override
    public IPidPropertyBuilder addP(double defaultValue) {
        addP(defaultValue, (double gain) -> m_pidController.setP(gain, m_slot));
        return this;
    }

    @Override
    public IPidPropertyBuilder addI(double defaultValue) {
        addI(defaultValue, (double gain) -> m_pidController.setI(gain, m_slot));
        return this;
    }

    @Override
    public IPidPropertyBuilder addD(double defaultValue) {
        addD(defaultValue, (double gain) -> m_pidController.setD(gain, m_slot));
        return this;
    }

    @Override
    public IPidPropertyBuilder addFF(double defaultValue) {
        addFF(defaultValue, (double gain) -> m_pidController.setFF(gain, m_slot));
        return this;
    }

    @Override
    public IPidPropertyBuilder addMaxVelocity(double defaultValue) {
        addMaxVelocity(defaultValue, (double gain) -> m_pidController.setSmartMotionMaxVelocity(gain, m_slot));
        return this;
    }

    @Override
    public IPidPropertyBuilder addMaxAcceleration(double defaultValue) {
        addMaxAcceleration(defaultValue, (double gain) -> m_pidController.setSmartMotionMaxAccel(gain, m_slot));
        return this;
    }
}
