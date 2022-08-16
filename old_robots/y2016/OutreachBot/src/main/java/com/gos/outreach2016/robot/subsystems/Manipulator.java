package com.gos.outreach2016.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.outreach2016.robot.RobotMap;

/**
 *
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Manipulator extends SubsystemBase {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final WPI_TalonSRX m_pivotMotor;
    private final WPI_TalonSRX m_collectorMotor;
    private final DoubleSolenoid m_shooter; //shoots the ball
    private final DoubleSolenoid m_arm; //opens and closes the arm to get the ball in

    public Manipulator() {
        m_pivotMotor = new WPI_TalonSRX(RobotMap.ARM_PIVOT_CAN_ID);
        m_collectorMotor = new WPI_TalonSRX(RobotMap.COLLECTOR_CAN_ID);
        m_shooter = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.SHOOTER_PISTON_A, RobotMap.SHOOTER_PISTON_B);
        m_arm = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARM_PISTON_A, RobotMap.ARM_PISTON_B);
    }

    public void openArm() {
        m_arm.set(DoubleSolenoid.Value.kForward);
    }

    public void closeArm() {
        m_arm.set(DoubleSolenoid.Value.kReverse);
    }

    public void shootBall() {
        m_shooter.set(DoubleSolenoid.Value.kReverse);
    }

    public void shooterIn() {
        m_shooter.set(DoubleSolenoid.Value.kForward);
    }

    public void collectBall() {
        m_collectorMotor.set(ControlMode.PercentOutput, -1);
    }

    public void releaseBall() {
        m_collectorMotor.set(ControlMode.PercentOutput, 1);
    }

    public void stopCollecting() {
        m_collectorMotor.set(ControlMode.PercentOutput, 0);
    }

    public void pivotUp() {
        m_pivotMotor.set(ControlMode.PercentOutput, 1);
    }

    public void pivotDown() {
        m_pivotMotor.set(ControlMode.PercentOutput, -1);
    }

    public void stopPivot() {
        m_pivotMotor.set(ControlMode.PercentOutput, 0);
    }



    public boolean getTopLimitSwitch() {
        return m_pivotMotor.isFwdLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch() {
        return m_collectorMotor.isRevLimitSwitchClosed() == 0;
    }
}
