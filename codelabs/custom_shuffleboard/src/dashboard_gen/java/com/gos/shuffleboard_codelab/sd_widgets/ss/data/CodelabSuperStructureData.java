package com.gos.shuffleboard_codelab.sd_widgets.ss.data;

import com.gos.shuffleboard_codelab.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class CodelabSuperStructureData extends ComplexData<CodelabSuperStructureData> {

    private final ElevatorData m_elevator;
    private final PunchData m_punch;
    private final SpinningWheelData m_spinningWheel;

    public CodelabSuperStructureData() {

        m_elevator = new ElevatorData();
        m_punch = new PunchData();
        m_spinningWheel = new SpinningWheelData();
    }

    public CodelabSuperStructureData(Map<String, Object> map) {

        m_elevator = new ElevatorData(SmartDashboardNames.ELEVATOR_TABLE_NAME, map);
        m_punch = new PunchData(SmartDashboardNames.PUNCH_TABLE_NAME, map);
        m_spinningWheel = new SpinningWheelData(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME, map);
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        map.putAll(m_elevator.asMap(SmartDashboardNames.ELEVATOR_TABLE_NAME + "/"));
        map.putAll(m_punch.asMap(SmartDashboardNames.PUNCH_TABLE_NAME + "/"));
        map.putAll(m_spinningWheel.asMap(SmartDashboardNames.SPINNING_WHEEL_TABLE_NAME + "/"));
        return map;
    }

    public ElevatorData getElevator() {
        return m_elevator;
    }

    public PunchData getPunch() {
        return m_punch;
    }

    public SpinningWheelData getSpinningWheel() {
        return m_spinningWheel;
    }

}
