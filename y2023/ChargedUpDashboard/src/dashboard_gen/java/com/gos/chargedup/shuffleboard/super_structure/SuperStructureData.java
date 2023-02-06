package com.gos.chargedup.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final double m_armAngle;
    private final double m_armGoalAngle;
    private final double m_armSpeed;
    private final boolean m_intakeDown;
    private final boolean m_armExtension1;
    private final boolean m_armExtension2;
    private final double m_intakeSpeed;
    private final double m_turretSpeed;
    private final double m_turretAngle;
    private final double m_turretGoalAngle;
    private final double m_robotAngle;


    public SuperStructureData() {
        this(0.0, 0.0, 0.0, false, false, false, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public SuperStructureData(double armAngle, double armGoalAngle, double armSpeed, boolean intakeDown, boolean armExtension1, boolean armExtension2, double intakeSpeed, double turretSpeed, double turretAngle, double turretGoalAngle, double robotAngle) {
        m_armAngle = armAngle;
        m_armGoalAngle = armGoalAngle;
        m_armSpeed = armSpeed;
        m_intakeDown = intakeDown;
        m_armExtension1 = armExtension1;
        m_armExtension2 = armExtension2;
        m_intakeSpeed = intakeSpeed;
        m_turretSpeed = turretSpeed;
        m_turretAngle = turretAngle;
        m_turretGoalAngle = turretGoalAngle;
        m_robotAngle = robotAngle;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.ARM_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_GOAL_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_DOWN, false),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_EXTENSION1, false),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_EXTENSION2, false),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.TURRET_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.TURRET_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.TURRET_GOAL_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ROBOT_ANGLE, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.ARM_ANGLE, m_armAngle);
        output.put(SmartDashboardNames.ARM_GOAL_ANGLE, m_armGoalAngle);
        output.put(SmartDashboardNames.ARM_SPEED, m_armSpeed);
        output.put(SmartDashboardNames.INTAKE_DOWN, m_intakeDown);
        output.put(SmartDashboardNames.ARM_EXTENSION1, m_armExtension1);
        output.put(SmartDashboardNames.ARM_EXTENSION2, m_armExtension2);
        output.put(SmartDashboardNames.INTAKE_SPEED, m_intakeSpeed);
        output.put(SmartDashboardNames.TURRET_SPEED, m_turretSpeed);
        output.put(SmartDashboardNames.TURRET_ANGLE, m_turretAngle);
        output.put(SmartDashboardNames.TURRET_GOAL_ANGLE, m_turretGoalAngle);
        output.put(SmartDashboardNames.ROBOT_ANGLE, m_robotAngle);
        return output;
    }

    public double getArmAngle() {
        return m_armAngle;
    }

    public double getArmGoalAngle() {
        return m_armGoalAngle;
    }

    public double getArmSpeed() {
        return m_armSpeed;
    }

    public boolean isIntakeDown() {
        return m_intakeDown;
    }

    public boolean isArmExtension1() {
        return m_armExtension1;
    }

    public boolean isArmExtension2() {
        return m_armExtension2;
    }

    public double getIntakeSpeed() {
        return m_intakeSpeed;
    }

    public double getTurretSpeed() {
        return m_turretSpeed;
    }

    public double getTurretAngle() {
        return m_turretAngle;
    }

    public double getTurretGoalAngle() {
        return m_turretGoalAngle;
    }

    public double getRobotAngle() {
        return m_robotAngle;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("armAngle=" + m_armAngle)
                    .add("armGoalAngle=" + m_armGoalAngle)
                    .add("armSpeed=" + m_armSpeed)
                    .add("intakeDown=" + m_intakeDown)
                    .add("armExtension1=" + m_armExtension1)
                    .add("armExtension2=" + m_armExtension2)
                    .add("intakeSpeed=" + m_intakeSpeed)
                    .add("turretSpeed=" + m_turretSpeed)
                    .add("turretAngle=" + m_turretAngle)
                    .add("turretGoalAngle=" + m_turretGoalAngle)
                    .add("robotAngle=" + m_robotAngle)
                    .toString();
    }
}
