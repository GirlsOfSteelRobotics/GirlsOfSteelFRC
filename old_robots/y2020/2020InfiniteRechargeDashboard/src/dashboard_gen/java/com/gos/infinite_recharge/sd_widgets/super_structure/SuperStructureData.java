package com.gos.infinite_recharge.sd_widgets.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final ControlPanelData m_controlPanel;
    private final LiftData m_lift;
    private final ShooterConveyorData m_shooterConveyor;
    private final ShooterIntakeData m_shooterIntake;
    private final ShooterWheelsData m_shooterWheels;
    private final WinchData m_winch;

    public SuperStructureData() {

        m_controlPanel = new ControlPanelData();
        m_lift = new LiftData();
        m_shooterConveyor = new ShooterConveyorData();
        m_shooterIntake = new ShooterIntakeData();
        m_shooterWheels = new ShooterWheelsData();
        m_winch = new WinchData();
    }

    public SuperStructureData(Map<String, Object> map) {

        m_controlPanel = new ControlPanelData(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME + "/", map);
        m_lift = new LiftData(SmartDashboardNames.LIFT_TABLE_NAME + "/", map);
        m_shooterConveyor = new ShooterConveyorData(SmartDashboardNames.SHOOTER_CONVEYOR_TABLE_NAME + "/", map);
        m_shooterIntake = new ShooterIntakeData(SmartDashboardNames.SHOOTER_INTAKE_TABLE_NAME + "/", map);
        m_shooterWheels = new ShooterWheelsData(SmartDashboardNames.SHOOTER_WHEELS_TABLE_NAME + "/", map);
        m_winch = new WinchData(SmartDashboardNames.WINCH_TABLE_NAME + "/", map);
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        map.putAll(m_controlPanel.asMap(SmartDashboardNames.CONTROL_PANEL_TABLE_NAME + "/"));
        map.putAll(m_lift.asMap(SmartDashboardNames.LIFT_TABLE_NAME + "/"));
        map.putAll(m_shooterConveyor.asMap(SmartDashboardNames.SHOOTER_CONVEYOR_TABLE_NAME + "/"));
        map.putAll(m_shooterIntake.asMap(SmartDashboardNames.SHOOTER_INTAKE_TABLE_NAME + "/"));
        map.putAll(m_shooterWheels.asMap(SmartDashboardNames.SHOOTER_WHEELS_TABLE_NAME + "/"));
        map.putAll(m_winch.asMap(SmartDashboardNames.WINCH_TABLE_NAME + "/"));
        return map;
    }

    public ControlPanelData getControlPanel() {
        return m_controlPanel;
    }

    public LiftData getLift() {
        return m_lift;
    }

    public ShooterConveyorData getShooterConveyor() {
        return m_shooterConveyor;
    }

    public ShooterIntakeData getShooterIntake() {
        return m_shooterIntake;
    }

    public ShooterWheelsData getShooterWheels() {
        return m_shooterWheels;
    }

    public WinchData getWinch() {
        return m_winch;
    }

}
