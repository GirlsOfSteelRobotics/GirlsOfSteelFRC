package com.gos.reefscape.subsystems.sysid;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

public class SwerveDriveSysId {
    public final ChassisSubsystem m_chassis;

    private final SwerveRequest.SysIdSwerveTranslation m_translationCharacterization = new SwerveRequest.SysIdSwerveTranslation();
    private final SwerveRequest.SysIdSwerveSteerGains m_steerCharacterization = new SwerveRequest.SysIdSwerveSteerGains();
    private final SwerveRequest.SysIdSwerveRotation m_rotationCharacterization = new SwerveRequest.SysIdSwerveRotation();

    private final SysIdRoutine m_sysIdRoutineTranslation;
    private final SysIdRoutine m_sysIdRoutineSteer;
    private final SysIdRoutine m_sysIdRoutineRotation;

    public SwerveDriveSysId(ChassisSubsystem chassis) {
        m_chassis = chassis;
        m_sysIdRoutineTranslation = new SysIdRoutine(
            new SysIdRoutine.Config(
                null,
                Volts.of(4),
                null,
                state -> SignalLogger.writeString("SysIdTranslation_State", state.toString())
            ),
            new SysIdRoutine.Mechanism(
                output -> m_chassis.setControl(m_translationCharacterization.withVolts(output)),
                null,
                m_chassis)
        );
        m_sysIdRoutineSteer = new SysIdRoutine(
            new SysIdRoutine.Config(
                null,
                Volts.of(7),
                null,
                state -> SignalLogger.writeString("SysIdSteer_State", state.toString())
            ),
            new SysIdRoutine.Mechanism(
                volts -> m_chassis.setControl(m_steerCharacterization.withVolts(volts)),
                null,
                m_chassis)
        );
        m_sysIdRoutineRotation = new SysIdRoutine(
            new SysIdRoutine.Config(
                Volts.of(Math.PI / 6).per(Second),
                Volts.of(Math.PI),
                null,
                state -> SignalLogger.writeString("SysIdRotation_State", state.toString())
            ),
            new SysIdRoutine.Mechanism(
                output -> {
                    m_chassis.setControl(m_rotationCharacterization.withRotationalRate(output.in(Volts)));
                    SignalLogger.writeDouble("Rotational_Rate", output.in(Volts));
                },
                null,
                m_chassis)
        );

    }

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction, SysIdRoutine routine) {
        return routine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction, SysIdRoutine routine) {
        return routine.dynamic(direction);
    }

    public Command createSysidRoutineCommand(SysIdRoutine routine) {
        return Commands.sequence(
            Commands.runOnce(SignalLogger::start),


            Commands.print("Waiting to start DynamicForward"),
            Commands.waitSeconds(2),
            sysIdDynamic(SysIdRoutine.Direction.kForward, routine).withTimeout(5),

            Commands.print("Waiting to start DynamicReverse"),
            Commands.waitSeconds(2),
            sysIdDynamic(SysIdRoutine.Direction.kReverse, routine).withTimeout(5),

            Commands.print("Waiting to start QuasistaticForward"),
            Commands.waitSeconds(2),
            sysIdQuasistatic(SysIdRoutine.Direction.kForward, routine).withTimeout(5),

            Commands.print("Waiting to start QuasistaticReverse"),
            Commands.waitSeconds(2),
            sysIdQuasistatic(SysIdRoutine.Direction.kReverse, routine).withTimeout(5),

            Commands.runOnce(SignalLogger::stop)

        ).withName("Elevator SysID Routine");
    }

    public Command createTranslationSysIdCommand() {
        return createSysidRoutineCommand(m_sysIdRoutineTranslation);
    }

    public Command createRotationSysIdCommand() {
        return createSysidRoutineCommand(m_sysIdRoutineRotation);
    }

    public Command createSteerSysIdCommand() {
        return createSysidRoutineCommand(m_sysIdRoutineSteer);
    }



}
