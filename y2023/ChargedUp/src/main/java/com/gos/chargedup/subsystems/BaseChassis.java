package com.gos.chargedup.subsystems;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.gos.chargedup.GosField;
import com.gos.lib.properties.HeavyDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.feedforward.SimpleMotorFeedForwardProperty;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.List;

public abstract class BaseChassis extends SubsystemBase implements ChassisSubsystemInterface {
    private static final double PITCH_LOWER_LIMIT = -3.0;
    private static final double PITCH_UPPER_LIMIT = 3.0;
    private final HeavyDoubleProperty m_turnPidAllowableError;

    private final WPI_Pigeon2 m_gyro;

    private final GosField m_field;

    private final List<Vision> m_cameras;
    private final ProfiledPIDController m_turnAnglePID;
    private final PidProperty m_turnAnglePIDProperties;
    private final SimpleMotorFeedForwardProperty m_turnAnglePIDFFProperty;
}
