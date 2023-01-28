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
    private final double m_intakeAngle;
    private final boolean m_armExtension1;
    private final boolean m_armExtension2;
    //private final double m_turretSpeed;
    //private final double m_turretAngle;
    private final double m_intakeSpeed;
    //private final double m_intakeRollerSpeed;


    public SuperStructureData() {
        this(0.0, 0.0, 0.0, false, false, 0.0);
    }

    public SuperStructureData(double armAngle, double armSpeed, double intakeAngle, boolean armExtension1, boolean armExtension2, double intakeSpeed) {
        m_armAngle = armAngle;
        m_armSpeed = armSpeed;
        m_intakeAngle = intakeAngle;
        m_armExtension1 = armExtension1;
        m_armExtension2 = armExtension2;
        ///m_turretSpeed = turretSpeed;
        //m_turretAngle = turretAngle;
        m_intakeSpeed = intakeSpeed;
        //m_intakeRollerSpeed = intakeRollerSpeed;
    }

    public SuperStructureData(Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, SmartDashboardNames.ARM_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_SPEED, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_EXTENSION1, false),
            Maps.getOrDefault(map, SmartDashboardNames.ARM_EXTENSION2, false),
            //Maps.getOrDefault(map, SmartDashboardNames.TURRET_SPEED, 0.0),
            //Maps.getOrDefault(map, SmartDashboardNames.TURRET_ANGLE, 0.0),
            Maps.getOrDefault(map, SmartDashboardNames.INTAKE_SPEED, 0.0)
        );
            //Maps.getOrDefault(map, SmartDashboardNames.INTAKE_ROLLER_SPEED, 0.0));
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put(SmartDashboardNames.ARM_ANGLE, m_armAngle);
        output.put(SmartDashboardNames.ARM_SPEED, m_armSpeed);
        output.put(SmartDashboardNames.INTAKE_ANGLE, m_intakeAngle);
        output.put(SmartDashboardNames.ARM_EXTENSION1, m_armExtension1);
        output.put(SmartDashboardNames.ARM_EXTENSION2, m_armExtension2);
        //output.put(SmartDashboardNames.TURRET_SPEED, m_turretSpeed);
        //output.put(SmartDashboardNames.TURRET_ANGLE, m_turretAngle);
        output.put(SmartDashboardNames.INTAKE_SPEED, m_intakeSpeed);
        //output.put(SmartDashboardNames.INTAKE_ROLLER_SPEED, m_intakeRollerSpeed);
        return output;
    }

    public double getArmAngle() {
        return m_armAngle;
    }

    public double getArmSpeed() {
        return m_armSpeed;
    }

    public double getIntakeAngle() {
        return m_intakeAngle;
    }

    public boolean isArmExtension1() {
        return m_armExtension1;
    }

    public boolean isArmExtension2() {
        return m_armExtension2;
    }

    //public double getTurretSpeed() {
       // return m_turretSpeed;
   // }

   // public double getTurretAngle() {
        //return m_turretAngle;
    //}

    public double getIntakeSpeed() {
        return m_intakeSpeed;
    }

    //public double getIntakeRollerSpeed() {
      //  return m_intakeRollerSpeed;
    //}


    @Override
    public String toString() {
        return new StringJoiner(", ", "SuperStructureData [", "]")
                    .add("armAngle=" + m_armAngle)
                    .add("armSpeed=" + m_armSpeed)
                    .add("intakeAngle=" + m_intakeAngle)
                    .add("armExtension1=" + m_armExtension1)
                    .add("armExtension2=" + m_armExtension2)
                    //.add("turretSpeed=" + m_turretSpeed)
                    //.add("turretAngle=" + m_turretAngle)
                    .add("intakeSpeed=" + m_intakeSpeed)
                    //.add("intakeRollerSpeed=" + m_intakeRollerSpeed)
                    .toString();
    }
}
