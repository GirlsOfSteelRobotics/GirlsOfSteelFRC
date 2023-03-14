package com.gos.chargedup.shuffleboard.scoring_position;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class ScoringPositionData extends ComplexData<ScoringPositionData> {

    private final double m_scoringPosition;


    public ScoringPositionData() {
        this(0.0);
    }

    public ScoringPositionData(double scoringPosition) {
        m_scoringPosition = scoringPosition;
    }

    public ScoringPositionData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.SCORING_POSITION, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.SCORING_POSITION, m_scoringPosition);
        return output;
    }

    public double getScoringPosition() {
        return m_scoringPosition;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "ScoringPositionData [", "]")
                    .add("scoringPosition=" + m_scoringPosition)
                    .toString();
    }
}
