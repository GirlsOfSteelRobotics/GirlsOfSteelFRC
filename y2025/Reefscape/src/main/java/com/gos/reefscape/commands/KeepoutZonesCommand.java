package com.gos.reefscape.commands;

import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.enums.PIESetpoint;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.reefscape.subsystems.ElevatorSubsystem;
import com.gos.reefscape.subsystems.PivotSubsystem;

import java.util.function.Consumer;


public class KeepoutZonesCommand extends Command {
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final PivotSubsystem m_pivotSubsystem;
    private final Consumer<KeepOutZoneEnum> m_keepOutConsumer;
    private final PIESetpoint m_setpoint;
    private final boolean m_runForever;

    private KeepOutZoneEnum m_keepOutZoneState;


    public KeepoutZonesCommand(ElevatorSubsystem elevator, PivotSubsystem pivot, Consumer<KeepOutZoneEnum> keepOutZoneConsumer, PIESetpoint setpoint, boolean runForever) {
        m_elevatorSubsystem = elevator;
        m_pivotSubsystem = pivot;
        m_keepOutConsumer = keepOutZoneConsumer;
        m_setpoint = setpoint;
        m_runForever = runForever;
        addRequirements(this.m_elevatorSubsystem, this.m_pivotSubsystem);
    }

    @Override
    public void initialize() {

        // TODO temp
        m_elevatorSubsystem.setIdleMode(IdleMode.kCoast);
        m_pivotSubsystem.setIdleMode(IdleMode.kCoast);

    }

    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.CognitiveComplexity", "PMD.NPathComplexity"})
    @Override
    public void execute() {
        String reason = "No reason";


        double pivotAngle = RobotBase.isSimulation() ? m_pivotSubsystem.getRelativeAngle() : m_pivotSubsystem.getAbsoluteAngle();
        double elevatorHeight = m_elevatorSubsystem.getHeight();

        double elevatorError = m_setpoint.m_height - elevatorHeight;
        boolean goingDown = elevatorError < 0;

        SmartDashboard.putBoolean("Elevator going down ", goingDown);

        if (elevatorHeight > 0.16 && elevatorHeight < .71) {

            // If we are going down, and the pivot wants to go home, don't let it.
            if (goingDown && pivotAngle > -45) {
                reason = "Were in the middle zone, but the pivot is trying to go too far in";
                m_keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
            } else {
                reason = "Were in the super safe zone";
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
            }
        } else if (elevatorHeight < 0.16) {
            if (pivotAngle > -160) {
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
                reason = "Were low but the pivot is cool";
            }
            else {
                m_keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
                reason = "Too low";
            }
        } else if (elevatorHeight > 0.71) {
            if (pivotAngle > -45) {
                m_keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_PIVOT;
                reason = "too high but the pivot is good";
            }
            else {
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
                reason = "super high but we can move both";
            }

        }









        switch (m_keepOutZoneState) {
        case ONLY_MOVE_ELEVATOR: {
            // m_elevatorSubsystem.goToHeight(m_setpoint.m_height);
            m_pivotSubsystem.stop();
            break;
        }
        case ONLY_MOVE_PIVOT: {
            m_elevatorSubsystem.stop();
            // m_pivotSubsystem.moveArmToAngle(m_setpoint.m_angle);
            break;
        }
        case CAN_MOVE_BOTH: {
            // m_elevatorSubsystem.goToHeight(m_setpoint.m_height);
            // m_pivotSubsystem.moveArmToAngle(m_setpoint.m_angle);
            break;
        }
        default:
            // Do nothing
            break;
        }

        if (m_elevatorSubsystem.isAtGoalHeight(m_setpoint.m_height)
            && m_pivotSubsystem.isAtGoalAngle(m_setpoint.m_angle)) {
            m_keepOutZoneState = KeepOutZoneEnum.IS_AT_POSITION;
        }

        m_keepOutConsumer.accept(m_keepOutZoneState);
        SmartDashboard.putString("Keepout Reason", reason);
        SmartDashboard.putString("Keepout State", m_keepOutZoneState.toString());
    }

    @Override
    public boolean isFinished() {
        if (!m_runForever) {
            return m_keepOutZoneState == KeepOutZoneEnum.IS_AT_POSITION;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_pivotSubsystem.stop();
        m_elevatorSubsystem.stop();
    }
}
