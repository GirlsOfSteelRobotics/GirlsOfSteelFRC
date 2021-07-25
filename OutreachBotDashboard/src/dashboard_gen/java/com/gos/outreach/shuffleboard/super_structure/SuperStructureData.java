package com.gos.outreach.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final double m_hoodAngle;
    private final double m_hoodMotorSpeed;
    private final boolean m_collectorIn;
    private final double m_collectorSpeed;
    private final double m_shooterMotorSpeed;


    public SuperStructureData() {
        this(0.0, 0.0, false, 0.0, 0.0);
    }

    public SuperStructureData(double hoodAngle, double hoodMotorSpeed, boolean collectorIn, double collectorSpeed, double shooterMotorSpeed) {
        m_hoodAngle = hoodAngle;
        m_hoodMotorSpeed = hoodMotorSpeed;
        m_collectorIn = collectorIn;
        m_collectorSpeed = collectorSpeed;
        m_shooterMotorSpeed = shooterMotorSpeed;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.HOOD_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.HOOD_MOTOR_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.COLLECTOR_IN, false),
            Maps.getOrDefault(map, SmartDashboardNames.COLLECTOR_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.SHOOTER_MOTOR_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.HOOD_ANGLE, m_hoodAngle);
        output.put(SmartDashboardNames.HOOD_MOTOR_SPEED, m_hoodMotorSpeed);
        output.put(SmartDashboardNames.COLLECTOR_IN, m_collectorIn);
        output.put(SmartDashboardNames.COLLECTOR_SPEED, m_collectorSpeed);
        output.put(SmartDashboardNames.SHOOTER_MOTOR_SPEED, m_shooterMotorSpeed);
        return output;
    }

    public double getHoodAngle() {
        return m_hoodAngle;
    }

    public double getHoodMotorSpeed() {
        return m_hoodMotorSpeed;
    }

    public boolean isCollectorIn() {
        return m_collectorIn;
    }

    public double getCollectorSpeed() {
        return m_collectorSpeed;
    }

    public double getShooterMotorSpeed() {
        return m_shooterMotorSpeed;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("hoodAngle=" + m_hoodAngle)
                    .add("hoodMotorSpeed=" + m_hoodMotorSpeed)
                    .add("collectorIn=" + m_collectorIn)
                    .add("collectorSpeed=" + m_collectorSpeed)
                    .add("shooterMotorSpeed=" + m_shooterMotorSpeed)
                    .toString();
    }
}
