package com.gos.recycle_rush.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.recycle_rush.robot.RobotMap;


public class Collector extends SubsystemBase {

    private static final double SPEED = .5;

    // Talons
    private final WPI_TalonSRX m_rightCollector;
    private final WPI_TalonSRX m_leftCollector;

    // Pistons
    private final DoubleSolenoid m_collectorLeftSolenoid;
    private final DoubleSolenoid m_collectorRightSolenoid;

    // Triggers (Collect/Release Tote Buttons)
    private final Joystick m_collectorTrigger;

    public Collector() {
        m_rightCollector = new WPI_TalonSRX(RobotMap.RIGHT_COLLECTOR_WHEEL);
        m_leftCollector = new WPI_TalonSRX(RobotMap.LEFT_COLLECTOR_WHEEL);

        m_collectorLeftSolenoid = new DoubleSolenoid(RobotMap.LEFT_COLLECTOR_MODULE, PneumaticsModuleType.CTREPCM, RobotMap.LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
            RobotMap.LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL);
        m_collectorRightSolenoid = new DoubleSolenoid(RobotMap.RIGHT_COLLECTOR_MODULE, PneumaticsModuleType.CTREPCM,
            RobotMap.RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL, RobotMap.RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL);

        m_collectorTrigger = new Joystick(RobotMap.OPERATOR_JOYSTICK);

        SmartDashboard.putBoolean("Collector On", false);
    }

    public void collectReleaseTote() {
        if (m_collectorTrigger.getZ() < -0.5) { // release
            collectorToteOut();
        } else if (m_collectorTrigger.getZ() > 0.5) { // collect
            collectorToteIn();
        } else {
            m_rightCollector.set(ControlMode.PercentOutput, 0);
            m_leftCollector.set(ControlMode.PercentOutput, 0);
        }

    }

    // Method suckToteIn which suck a tote inside the robot
    public void collectorToteIn() {
        m_rightCollector.set(ControlMode.PercentOutput, SPEED);
        m_leftCollector.set(ControlMode.PercentOutput, -SPEED);
        SmartDashboard.putBoolean("Collecter On", true);
    }

    // Method suckToteOut which pushes a Tote out
    public void collectorToteOut() {
        m_rightCollector.set(ControlMode.PercentOutput, -SPEED);
        m_leftCollector.set(ControlMode.PercentOutput, SPEED);
        SmartDashboard.putBoolean("Collecter On", true);
    }

    // Method collectorToteRotate which rotates the tote inside the trifold
    public void collectorToteRotateRight() {
        m_rightCollector.set(ControlMode.PercentOutput, SPEED);
        m_leftCollector.set(ControlMode.PercentOutput, SPEED);
    }

    public void collectorToteRotateLeft() {
        m_rightCollector.set(ControlMode.PercentOutput, -SPEED);
        m_leftCollector.set(ControlMode.PercentOutput, -SPEED);
    }

    public void collectorIn() {
        m_collectorRightSolenoid.set(DoubleSolenoid.Value.kForward);
        m_collectorLeftSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void collectorOut() {
        m_collectorRightSolenoid.set(DoubleSolenoid.Value.kReverse);
        m_collectorLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void stopCollecting() {
        m_rightCollector.set(ControlMode.PercentOutput, 0.0);
        m_leftCollector.set(ControlMode.PercentOutput, 0.0);
        SmartDashboard.putBoolean("Collector Off", false);
    }

}
