package com.gos.lib.shuffleboard.swerve;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class SwerveDriveData extends ComplexData<SwerveDriveData> {

    private final SwerveModuleData m_leftFront;
    private final SwerveModuleData m_leftRear;
    private final SwerveModuleData m_rightFront;
    private final SwerveModuleData m_rightRear;

    public SwerveDriveData() {

        m_leftFront = new SwerveModuleData();
        m_leftRear = new SwerveModuleData();
        m_rightFront = new SwerveModuleData();
        m_rightRear = new SwerveModuleData();
    }

    public SwerveDriveData(Map<String, Object> map) {

        m_leftFront = new SwerveModuleData(SmartDashboardNames.LEFT_FRONT_SWERVE_DRIVE + "/", map);
        m_leftRear = new SwerveModuleData(SmartDashboardNames.LEFT_REAR_SWERVE_DRIVE + "/", map);
        m_rightFront = new SwerveModuleData(SmartDashboardNames.RIGHT_FRONT_SWERVE_DRIVE + "/", map);
        m_rightRear = new SwerveModuleData(SmartDashboardNames.RIGHT_REAR_SWERVE_DRIVE + "/", map);
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        map.putAll(m_leftFront.asMap(SmartDashboardNames.LEFT_FRONT_SWERVE_DRIVE + "/"));
        map.putAll(m_leftRear.asMap(SmartDashboardNames.LEFT_REAR_SWERVE_DRIVE + "/"));
        map.putAll(m_rightFront.asMap(SmartDashboardNames.RIGHT_FRONT_SWERVE_DRIVE + "/"));
        map.putAll(m_rightRear.asMap(SmartDashboardNames.RIGHT_REAR_SWERVE_DRIVE + "/"));
        return map;
    }

    public SwerveModuleData getLeftFront() {
        return m_leftFront;
    }

    public SwerveModuleData getLeftRear() {
        return m_leftRear;
    }

    public SwerveModuleData getRightFront() {
        return m_rightFront;
    }

    public SwerveModuleData getRightRear() {
        return m_rightRear;
    }

}
