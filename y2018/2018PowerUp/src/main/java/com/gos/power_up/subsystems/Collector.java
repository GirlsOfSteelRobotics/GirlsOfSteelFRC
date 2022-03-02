package com.gos.power_up.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import com.gos.power_up.commands.CollectorHold;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Collector extends SubsystemBase {

    private final WPI_TalonSRX m_collectLeft;
    private final WPI_TalonSRX m_collectRight;

    private double m_collectorSpeed;

    public Collector() {
        m_collectLeft = new WPI_TalonSRX(RobotMap.COLLECT_LEFT);
        m_collectRight = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT);
        m_collectLeft.setSensorPhase(true);
        m_collectRight.setSensorPhase(true);
        m_collectorSpeed = 0; //TODO do we want it to turn during auto
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CollectorHold(this));
    }


    public void stop() {
        m_collectLeft.stopMotor();
        m_collectRight.stopMotor();
        m_collectorSpeed = 0;
        System.out.println("Collector: motors stopped");
    }

    public void collect() {
        m_collectLeft.set(-1.00); //TODO: tune this speed, and these values may be reversed
        m_collectRight.set(0.5);
    }

    public void release(double speed) {
        m_collectLeft.set(speed); //TODO: tune this speed, and these values may be reversed
        m_collectRight.set(-speed);
    }

    public int getRightCollectorID() {
        return m_collectRight.getDeviceID();
    }

    public WPI_TalonSRX getRightCollector() {
        return m_collectRight;
    }

    public void runCollector() {
        m_collectLeft.set(-m_collectorSpeed);
        m_collectRight.set(m_collectorSpeed);
    }

    public void runSlowCollect() {
        System.out.println("Collector: holding cube in");
        m_collectorSpeed = 0.15;
    }

}
