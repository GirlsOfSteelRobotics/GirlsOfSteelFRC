package com.gos.preseason2017.team2.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.preseason2017.team2.robot.RobotMap;

/**
 *
 */
public class Manipulator extends SubsystemBase {
    private final WPI_TalonSRX m_collectRight;
    private final WPI_TalonSRX m_collectLeft;

    private final DoubleSolenoid m_pusher;

    private final WPI_TalonSRX m_pivotA;
    private final WPI_TalonSRX m_pivotB;

    public Manipulator() {
        m_collectRight = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT);
        m_collectLeft = new WPI_TalonSRX(RobotMap.COLLECT_LEFT);

        m_pusher = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

        m_pivotA = new WPI_TalonSRX(RobotMap.PIVOT_RIGHT);
        m_pivotB = new WPI_TalonSRX(RobotMap.PIVOT_LEFT);
    }



    public void collectIn(double speed) {
        m_collectRight.set(ControlMode.PercentOutput, speed);
        m_collectLeft.set(ControlMode.PercentOutput, speed);
    }

    public void pusherOut() {
        m_pusher.set(DoubleSolenoid.Value.kForward);
    }

    public void pusherIn() {
        m_pusher.set(DoubleSolenoid.Value.kReverse);
    }

    public void pivotUp(double speed) {
        System.out.println("Pivot Up Speed" + speed);
        m_pivotA.set(ControlMode.PercentOutput, -speed);
        m_pivotB.set(ControlMode.PercentOutput, -speed);
    }

    public void pivotDown(double speed) {
        System.out.println("Pivot Down Speed" + speed);
        m_pivotA.set(ControlMode.PercentOutput, speed);
        m_pivotB.set(ControlMode.PercentOutput, speed);
    }

    public void stopCollector() {
        m_collectRight.set(ControlMode.PercentOutput, 0);
        m_collectLeft.set(ControlMode.PercentOutput, 0);
    }

    public void stopPivot() {
        m_pivotA.set(ControlMode.PercentOutput, 0);
        m_pivotB.set(ControlMode.PercentOutput, 0);
    }
}
