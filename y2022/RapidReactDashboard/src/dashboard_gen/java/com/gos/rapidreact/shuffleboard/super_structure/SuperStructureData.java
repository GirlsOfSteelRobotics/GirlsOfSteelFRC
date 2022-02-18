package com.gos.rapidreact.shuffleboard.super_structure;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class SuperStructureData extends ComplexData<SuperStructureData> {

    private final double m_intakeAngle;
    private final double m_intakeSpeed;
    private final double m_rollerSpeed;
    private final double m_hangerSpeed;
    private final double m_hangerHeight;
    private final double m_horizontalConveyorSpeed;
    private final double m_verticalConveyorSpeed;
    private final double m_shooterSpeed;


    public SuperStructureData() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public SuperStructureData(double intakeAngle, double intakeSpeed, double rollerSpeed, double hangerSpeed, double hangerHeight, double horizontalConveyorSpeed, double verticalConveyorSpeed, double shooterSpeed) {
        m_intakeAngle = intakeAngle;
        m_intakeSpeed = intakeSpeed;
        m_rollerSpeed = rollerSpeed;
        m_hangerSpeed = hangerSpeed;
        m_hangerHeight = hangerHeight;
        m_horizontalConveyorSpeed = horizontalConveyorSpeed;
        m_verticalConveyorSpeed = verticalConveyorSpeed;
        m_shooterSpeed = shooterSpeed;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ROLLER_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.HANGER_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.HANGER_HEIGHT, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.HORIZONTAL_CONVEYOR_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.VERTICAL_CONVEYOR_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.SHOOTER_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.INTAKE_ANGLE, m_intakeAngle);
        output.put(SmartDashboardNames.INTAKE_SPEED, m_intakeSpeed);
        output.put(SmartDashboardNames.ROLLER_SPEED, m_rollerSpeed);
        output.put(SmartDashboardNames.HANGER_SPEED, m_hangerSpeed);
        output.put(SmartDashboardNames.HANGER_HEIGHT, m_hangerHeight);
        output.put(SmartDashboardNames.HORIZONTAL_CONVEYOR_SPEED, m_horizontalConveyorSpeed);
        output.put(SmartDashboardNames.VERTICAL_CONVEYOR_SPEED, m_verticalConveyorSpeed);
        output.put(SmartDashboardNames.SHOOTER_SPEED, m_shooterSpeed);
        return output;
    }

    public double getIntakeAngle() {
        return m_intakeAngle;
    }

    public double getIntakeSpeed() {
        return m_intakeSpeed;
    }

    public double getRollerSpeed() {
        return m_rollerSpeed;
    }

    public double getHangerSpeed() {
        return m_hangerSpeed;
    }

    public double getHangerHeight() {
        return m_hangerHeight;
    }

    public double getHorizontalConveyorSpeed() {
        return m_horizontalConveyorSpeed;
    }

    public double getVerticalConveyorSpeed() {
        return m_verticalConveyorSpeed;
    }

    public double getShooterSpeed() {
        return m_shooterSpeed;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("intakeAngle=" + m_intakeAngle)
                    .add("intakeSpeed=" + m_intakeSpeed)
                    .add("rollerSpeed=" + m_rollerSpeed)
                    .add("hangerSpeed=" + m_hangerSpeed)
                    .add("hangerHeight=" + m_hangerHeight)
                    .add("horizontalConveyorSpeed=" + m_horizontalConveyorSpeed)
                    .add("verticalConveyorSpeed=" + m_verticalConveyorSpeed)
                    .add("shooterSpeed=" + m_shooterSpeed)
                    .toString();
    }
}
