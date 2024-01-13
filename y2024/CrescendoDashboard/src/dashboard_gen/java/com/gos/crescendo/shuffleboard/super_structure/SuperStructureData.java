package com.gos.crescendo.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final double m_pivotMotorAngle;
    private final double m_goalAngle;
    private final double m_shooterMotorPercentage;
    private final double m_pivotMotorPercentage;
    private final boolean m_hasGamePiece;


    public SuperStructureData() {
        this(0.0, 0.0, 0.0, 0.0, false);
    }

    public SuperStructureData(double pivotMotorAngle, double goalAngle, double shooterMotorPercentage, double pivotMotorPercentage, boolean hasGamePiece) {
        m_pivotMotorAngle = pivotMotorAngle;
        m_goalAngle = goalAngle;
        m_shooterMotorPercentage = shooterMotorPercentage;
        m_pivotMotorPercentage = pivotMotorPercentage;
        m_hasGamePiece = hasGamePiece;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.PIVOT_MOTOR_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.GOAL_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.SHOOTER_MOTOR_PERCENTAGE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.PIVOT_MOTOR_PERCENTAGE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.HAS_GAME_PIECE, false));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.PIVOT_MOTOR_ANGLE, m_pivotMotorAngle);
        output.put(SmartDashboardNames.GOAL_ANGLE, m_goalAngle);
        output.put(SmartDashboardNames.SHOOTER_MOTOR_PERCENTAGE, m_shooterMotorPercentage);
        output.put(SmartDashboardNames.PIVOT_MOTOR_PERCENTAGE, m_pivotMotorPercentage);
        output.put(SmartDashboardNames.HAS_GAME_PIECE, m_hasGamePiece);
        return output;
    }

    public double getPivotMotorAngle() {
        return m_pivotMotorAngle;
    }

    public double getGoalAngle() {
        return m_goalAngle;
    }

    public double getShooterMotorPercentage() {
        return m_shooterMotorPercentage;
    }

    public double getPivotMotorPercentage() {
        return m_pivotMotorPercentage;
    }

    public boolean isHasGamePiece() {
        return m_hasGamePiece;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("pivotMotorAngle=" + m_pivotMotorAngle)
                    .add("goalAngle=" + m_goalAngle)
                    .add("shooterMotorPercentage=" + m_shooterMotorPercentage)
                    .add("pivotMotorPercentage=" + m_pivotMotorPercentage)
                    .add("hasGamePiece=" + m_hasGamePiece)
                    .toString();
    }
}
