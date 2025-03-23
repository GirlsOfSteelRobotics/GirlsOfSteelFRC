package com.gos.reefscape.commands;

import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.enums.PIESetpoint;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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

    private final NetworkTableEntry m_intermediateElevatorSetpointEntry;
    private final NetworkTableEntry m_intermediatePivotSetpointEntry;
    private final NetworkTableEntry m_reasonEntry;
    private final NetworkTableEntry m_stateEntry;

    private KeepOutZoneEnum m_keepOutZoneState;


    public KeepoutZonesCommand(ElevatorSubsystem elevator, PivotSubsystem pivot, Consumer<KeepOutZoneEnum> keepOutZoneConsumer, PIESetpoint setpoint, boolean runForever) {
        m_elevatorSubsystem = elevator;
        m_pivotSubsystem = pivot;
        m_keepOutConsumer = keepOutZoneConsumer;
        m_setpoint = setpoint;
        m_runForever = runForever;
        addRequirements(this.m_elevatorSubsystem, this.m_pivotSubsystem);

        NetworkTable table = NetworkTableInstance.getDefault().getTable("KeepOutZoneCommand");
        m_intermediateElevatorSetpointEntry = table.getEntry("Intermediate Elevator Setpoint");
        m_intermediatePivotSetpointEntry = table.getEntry("Intermediate Pivot Setpoint");
        m_reasonEntry = table.getEntry("Reason");
        m_stateEntry = table.getEntry("State");
    }

    @Override
    public void initialize() {

    }

    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.CognitiveComplexity", "PMD.NPathComplexity", "PMD.NcssCount", "PMD.ExcessiveMethodLength"})
    @Override
    public void execute() {
        String reason = "No reason";


        double pivotAngle = RobotBase.isSimulation() ? m_pivotSubsystem.getRelativeAngle() : m_pivotSubsystem.getAbsoluteAngle();
        double elevatorHeight = m_elevatorSubsystem.getHeight();

        double elevatorError = m_setpoint.m_height - elevatorHeight;
        boolean goingDown = elevatorError < 0;

        double pivotError = m_setpoint.m_angle - pivotAngle;
        boolean retracting = pivotError > 0;

        SmartDashboard.putBoolean("KOZ: Retracting", retracting);

        double pivotGoal = m_setpoint.m_angle;
        double elevatorGoal = m_setpoint.m_height;

        // Middle section. Mostly good, but need to be careful of the pivot going home
        if (elevatorHeight > 0.16 && elevatorHeight < .71) {

            // If we are going down, and the pivot wants to go home, don't let it.
            if (goingDown && pivotAngle > -45) {
                reason = "Were in the middle zone, but the pivot is trying to go too far in";
                m_keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
            } else {
                reason = "Were in the super safe zone";
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
            }
        }


        // Elevator is low. Need to be careful about the pivot swinging too far out (like to fetch algae)
        else if (elevatorHeight < 0.16) {
            if (pivotAngle > -160) {
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
                reason = "Were low but the pivot is cool";
            } else if (retracting) {
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH_WITH_MODIFIED_ELEVATOR_GOAL;
                elevatorGoal = 0.16;
                reason = "We're low but are retracting the pivot and need to hack the elevator";
            }
            else {
                m_keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
                reason = "Too low";
            }
        }


        // Elevator is very high up. Need to be careful about retracting the pivot too much
        else if (elevatorHeight > 0.71) {
            if (pivotGoal > -45) {
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
                reason = "In the high zone and we are modifying the goal angle to prevent crashing into the elevator";
                pivotGoal = -45;
            }
            else if (pivotAngle > -45) {
                m_keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_PIVOT;
                reason = "In the high zone and the elevator is too retracted";
            } else {
                m_keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
                reason = "super high but we can move both";
            }

        }


        switch (m_keepOutZoneState) {
        case ONLY_MOVE_ELEVATOR: {
            m_elevatorSubsystem.goToHeight(elevatorGoal);
            m_pivotSubsystem.stop();
            break;
        }
        case ONLY_MOVE_PIVOT: {
            m_elevatorSubsystem.stop();
            m_pivotSubsystem.moveArmToAngle(pivotGoal);
            break;
        }
        case CAN_MOVE_BOTH:
        case CAN_MOVE_BOTH_WITH_MODIFIED_ELEVATOR_GOAL:
        case CAN_MOVE_BOTH_WITH_MODIFIED_PIVOT_GOAL: {
            m_elevatorSubsystem.goToHeight(elevatorGoal);
            m_pivotSubsystem.moveArmToAngle(pivotGoal);
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

        m_intermediateElevatorSetpointEntry.setDouble(elevatorGoal);
        m_intermediatePivotSetpointEntry.setDouble(pivotGoal);
        m_reasonEntry.setString(reason);
        m_stateEntry.setString(m_keepOutZoneState.toString());
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

        m_keepOutConsumer.accept(KeepOutZoneEnum.NOT_RUNNING);
    }
}
