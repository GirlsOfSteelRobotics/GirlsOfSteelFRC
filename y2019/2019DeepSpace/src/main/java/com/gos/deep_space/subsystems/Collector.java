package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class Collector extends SubsystemBase {

    private static final double SLOW_COLLECTOR_SPEED = 0.15;
    private static final double COLLECTOR_INTAKE_SPEED = 0.4;
    private static final double COLLECTOR_RELEASE_SPEED = 0.35; // .5 pre-GPR

    private final WPI_TalonSRX m_leftCollect;
    private final WPI_TalonSRX m_rightCollect;

    public Collector() {
        m_leftCollect = new WPI_TalonSRX(RobotMap.COLLECT_LEFT_TALON);
        m_rightCollect = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT_TALON);
    }



    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stop() {
        m_leftCollect.stopMotor();
        m_rightCollect.stopMotor();
    }

    public void collect() {
        m_leftCollect.set(-COLLECTOR_INTAKE_SPEED);
        m_rightCollect.set(COLLECTOR_INTAKE_SPEED);
    }

    public void release() {
        m_leftCollect.set(COLLECTOR_RELEASE_SPEED);
        m_rightCollect.set(-COLLECTOR_RELEASE_SPEED);
    }

    public void slowCollect() {
        m_leftCollect.set(-SLOW_COLLECTOR_SPEED);
        m_rightCollect.set(SLOW_COLLECTOR_SPEED);
    }
}
