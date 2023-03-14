package com.gos.chargedup.shuffleboard.scoring_position;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class ScoringPositionData extends ComplexData<ScoringPositionData> {

    private final double m_selectedPosition;


    public ScoringPositionData() {
        this(0.0);
    }

    public ScoringPositionData(double selectedPosition) {
        m_selectedPosition = selectedPosition;
    }

    public ScoringPositionData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.SELECTED_POSITION, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.SELECTED_POSITION, m_selectedPosition);
        return output;
    }

    public double getSelectedPosition() {
        return m_selectedPosition;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ScoringPositionData [", "]")
                    .add("selectedPosition=" + m_selectedPosition)
                    .toString();
    }
}
