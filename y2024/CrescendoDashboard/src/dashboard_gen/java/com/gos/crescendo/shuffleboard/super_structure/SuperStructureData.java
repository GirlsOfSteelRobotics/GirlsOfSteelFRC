package com.gos.crescendo.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final double m_fieldName;


    public SuperStructureData() {
        this(0.0);
    }

    public SuperStructureData(double fieldName) {
        m_fieldName = fieldName;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.FIELD_NAME, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.FIELD_NAME, m_fieldName);
        return output;
    }

    public double getFieldName() {
        return m_fieldName;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("fieldName=" + m_fieldName)
                    .toString();
    }
}
