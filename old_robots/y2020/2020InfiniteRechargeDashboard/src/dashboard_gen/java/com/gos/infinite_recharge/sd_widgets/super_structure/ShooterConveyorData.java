package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class ShooterConveyorData extends ComplexData<ShooterConveyorData> {

    private final double m_speed;
    private final boolean m_handoffBallSensor;
    private final boolean m_secondaryBallSensor;
    private final boolean m_topBallSensor;


    public ShooterConveyorData() {
        this(0.0, false, false, false);
    }

    public ShooterConveyorData(double speed, boolean handoffBallSensor, boolean secondaryBallSensor, boolean topBallSensor) {
        m_speed = speed;
        m_handoffBallSensor = handoffBallSensor;
        m_secondaryBallSensor = secondaryBallSensor;
        m_topBallSensor = topBallSensor;
    }

    public ShooterConveyorData(Map<String, Object> map) {
        this("", map);
    }

    public ShooterConveyorData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.SHOOTER_CONVEYOR_SPEED, 0.0),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.CONVEYOR_HANDOFF_BALL_SENSOR, false),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.CONVEYOR_SECONDARY_BALL_SENSOR, false),
            Maps.getOrDefault(map, prefix + SmartDashboardNames.CONVEYOR_TOP_BALL_SENSOR, false));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.SHOOTER_CONVEYOR_SPEED, m_speed);
        output.put(prefix + SmartDashboardNames.CONVEYOR_HANDOFF_BALL_SENSOR, m_handoffBallSensor);
        output.put(prefix + SmartDashboardNames.CONVEYOR_SECONDARY_BALL_SENSOR, m_secondaryBallSensor);
        output.put(prefix + SmartDashboardNames.CONVEYOR_TOP_BALL_SENSOR, m_topBallSensor);
        return output;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.SHOOTER_CONVEYOR_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.SHOOTER_CONVEYOR_SPEED);
        changed |= changes.containsKey(prefix + SmartDashboardNames.CONVEYOR_HANDOFF_BALL_SENSOR);
        changed |= changes.containsKey(prefix + SmartDashboardNames.CONVEYOR_SECONDARY_BALL_SENSOR);
        changed |= changes.containsKey(prefix + SmartDashboardNames.CONVEYOR_TOP_BALL_SENSOR);

        return changed;
    }

    public double getSpeed() {
        return m_speed;
    }

    public boolean isHandoffBallSensor() {
        return m_handoffBallSensor;
    }

    public boolean isSecondaryBallSensor() {
        return m_secondaryBallSensor;
    }

    public boolean isTopBallSensor() {
        return m_topBallSensor;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ShooterConveyorData [", "]")
                    .add("speed=" + m_speed)
                    .add("handoffBallSensor=" + m_handoffBallSensor)
                    .add("secondaryBallSensor=" + m_secondaryBallSensor)
                    .add("topBallSensor=" + m_topBallSensor)
                    .toString();
    }
}
