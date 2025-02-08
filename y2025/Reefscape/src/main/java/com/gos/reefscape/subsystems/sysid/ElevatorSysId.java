package com.gos.reefscape.subsystems.sysid;

import com.gos.reefscape.subsystems.ElevatorSubsystem;
import edu.wpi.first.units.measure.MutDistance;
import edu.wpi.first.units.measure.MutLinearVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

public class ElevatorSysId {
    private final ElevatorSubsystem m_elevator;
    private final SysIdRoutine m_routine;

    private final MutVoltage m_appliedVoltage = Volts.mutable(0);
    private final MutDistance m_height = Meters.mutable(0);
    private final MutLinearVelocity m_velocity = MetersPerSecond.mutable(0);

    public ElevatorSysId(ElevatorSubsystem elevator) {
        m_elevator = elevator;
        m_routine = new SysIdRoutine(
            new SysIdRoutine.Config(
                Volts.of(0.25).per(Second),
                Volts.of(2.5),
                Seconds.of(10)),
            new SysIdRoutine.Mechanism(this::setVoltage, this::logMotors, elevator)
        );
    }

    private void setVoltage(Voltage volts) {
        m_elevator.setVoltage(volts.in(Volts));
    }

    private void logMotors(SysIdRoutineLog log) {
        log.motor("elevator")
            .voltage(
                m_appliedVoltage.mut_replace(
                    m_elevator.getVoltage(), Volts))
            .linearPosition(m_height.mut_replace(m_elevator.getHeight(), Meters))
            .linearVelocity(
                m_velocity.mut_replace(m_elevator.getEncoderVel(), MetersPerSecond));
    }

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return m_routine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return m_routine.dynamic(direction);
    }

    public Command createSysidRoutineCommand() {
        return Commands.sequence(
            Commands.print("Waiting to start DynamicForward"),
            Commands.waitSeconds(2),
            sysIdDynamic(SysIdRoutine.Direction.kForward).until(() -> m_elevator.getHeight() > 60),

            Commands.print("Waiting to start DynamicReverse"),
            Commands.waitSeconds(2),
            sysIdDynamic(SysIdRoutine.Direction.kReverse).until(() -> m_elevator.getHeight() < 5),

            Commands.print("Waiting to start QuasistaticForward"),
            Commands.waitSeconds(2),
            sysIdQuasistatic(SysIdRoutine.Direction.kForward).until(() -> m_elevator.getHeight() > 60),

            Commands.print("Waiting to start QuasistaticReverse"),
            Commands.waitSeconds(2),
            sysIdQuasistatic(SysIdRoutine.Direction.kReverse).until(() -> m_elevator.getHeight() < 5)

        ).withName("Elevator SysID Routine");
    }

}
