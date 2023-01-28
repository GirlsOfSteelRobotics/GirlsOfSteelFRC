package com.gos.chargedup.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings({"PMD.DataClass", "PMD.ExcessiveParameterList"})
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final double m_armAngle;
    private final double m_armSpeed;
    private final boolean m_intakeDown;
    private final boolean m_armExtension1;
    private final boolean m_armExtension2;
    private final double m_intakeSpeed;


    public SuperStructureData() {
        this(0.0, 0.0, false, false, false, 0.0);
    }

    public SuperStructureData(double armAngle, double armSpeed, boolean intakeDown, boolean armExtension1, boolean armExtension2, double intakeSpeed) {
        m_armAngle = armAngle;
        m_armSpeed = armSpeed;
        m_intakeDown = intakeDown;
        m_armExtension1 = armExtension1;
        m_armExtension2 = armExtension2;
        m_intakeSpeed = intakeSpeed;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.ARM_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_DOWN, false),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_EXTENSION1, false),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_EXTENSION2, false),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.ARM_ANGLE, m_armAngle);
        output.put(SmartDashboardNames.ARM_SPEED, m_armSpeed);
        output.put(SmartDashboardNames.INTAKE_DOWN, m_intakeDown);
        output.put(SmartDashboardNames.ARM_EXTENSION1, m_armExtension1);
        output.put(SmartDashboardNames.ARM_EXTENSION2, m_armExtension2);
        output.put(SmartDashboardNames.INTAKE_SPEED, m_intakeSpeed);
        return output;
    }

    public double getArmAngle() {
        return m_armAngle;
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


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("armAngle=" + m_armAngle)
                    .add("armSpeed=" + m_armSpeed)
                    .add("intakeDown=" + m_intakeDown)
                    .add("armExtension1=" + m_armExtension1)
                    .add("armExtension2=" + m_armExtension2)
                    .add("intakeSpeed=" + m_intakeSpeed)
                    .toString();
    }
}
