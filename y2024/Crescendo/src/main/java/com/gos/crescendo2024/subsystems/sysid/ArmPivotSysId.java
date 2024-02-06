package com.gos.crescendo2024.subsystems.sysid;

import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.MutableMeasure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import java.util.function.Consumer;

import static edu.wpi.first.units.MutableMeasure.mutable;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

public class ArmPivotSysId {
    private final SysIdRoutine m_routine;
    private final ArmPivotSubsystem m_armPivot;

    private final MutableMeasure<Voltage> m_appliedVoltage = mutable(Volts.of(0));
    private final MutableMeasure<Angle> m_rotations = mutable(Rotations.of(0));
    private final MutableMeasure<Velocity<Angle>> m_velocity = mutable(RotationsPerSecond.of(0));

    public ArmPivotSysId(ArmPivotSubsystem armPivot) {
        m_armPivot = armPivot;
        m_routine = new SysIdRoutine(
            new SysIdRoutine.Config(),
            new SysIdRoutine.Mechanism(this.voltageMotors(), this.logMotors(), armPivot)
        );
    }

    private Consumer<Measure<Voltage>> voltageMotors() {
        return (Measure<Voltage> volts) -> {
            m_armPivot.setVoltageLeadAndFollow(volts.in(Volts));
        };
    }

    private Consumer<SysIdRoutineLog> logMotors() {
        return log -> {
            log.motor("armPivot")
                .voltage(
                    m_appliedVoltage.mut_replace(
                        m_armPivot.replaceVoltage(), Volts))
                .angularPosition(m_rotations.mut_replace(m_armPivot.getEncoderPos(), Rotations))
                .angularVelocity(
                    m_velocity.mut_replace(m_armPivot.getEncoderVel(), RotationsPerSecond));
        };
    }


}
