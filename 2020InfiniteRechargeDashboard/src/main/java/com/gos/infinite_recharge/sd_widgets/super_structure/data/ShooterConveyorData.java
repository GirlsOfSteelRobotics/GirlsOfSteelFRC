package com.gos.infinite_recharge.sd_widgets.super_structure.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class ShooterConveyorData extends ComplexData<ShooterConveyorData> {

    private final double m_speed;
    private final boolean m_handoffBallSensor;
    private final boolean m_secondaryBallSensor;
    private final boolean m_topBallSensor;


    public ShooterConveyorData() {
        this(0.0, false, false, false);
    }

    public ShooterConveyorData(Map<String, Object> map) {
        this("", map);
    }

    public ShooterConveyorData(String prefix, Map<String, Object> map) {
        this((Double) map.getOrDefault(prefix + "/" + SmartDashboardNames.SHOOTER_CONVEYOR_SPEED, 0.0), (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.CONVEYOR_HANDOFF_BALL_SENSOR, false), (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.CONVEYOR_SECONDARY_BALL_SENSOR, false), (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.CONVEYOR_TOP_BALL_SENSOR, false));
    }

    public ShooterConveyorData(double speed, boolean handoffBallSensor, boolean secondaryBallSensor, boolean topBallSensor) {
        m_speed = speed;
        m_handoffBallSensor = handoffBallSensor;
        m_secondaryBallSensor = secondaryBallSensor;
        m_topBallSensor = topBallSensor;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.SHOOTER_CONVEYOR_SPEED, m_speed);
        map.put(prefix + SmartDashboardNames.CONVEYOR_HANDOFF_BALL_SENSOR, m_handoffBallSensor);
        map.put(prefix + SmartDashboardNames.CONVEYOR_SECONDARY_BALL_SENSOR, m_secondaryBallSensor);
        map.put(prefix + SmartDashboardNames.CONVEYOR_TOP_BALL_SENSOR, m_topBallSensor);
        return map;
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

    public boolean getHandoffBallSensor() {
        return m_handoffBallSensor;
    }

    public boolean getSecondaryBallSensor() {
        return m_secondaryBallSensor;
    }

    public boolean getTopBallSensor() {
        return m_topBallSensor;
    }
}
