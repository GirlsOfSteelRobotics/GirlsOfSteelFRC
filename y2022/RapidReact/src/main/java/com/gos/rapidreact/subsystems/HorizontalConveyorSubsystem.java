package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HorizontalConveyorSubsystem extends SubsystemBase {

    public static final double HORIZONTAL_CONVEYOR_MOTOR_SPEED = 0.5;

    private final SimableCANSparkMax m_leader;
    

    public HorizontalConveyorSubsystem() {
        m_leader = new SimableCANSparkMax(Constants.HORIZONTAL_CONVEYOR_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leader.restoreFactoryDefaults();
        m_leader.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_leader.burnFlash();
    }

    public double getHorizontalConveyorSpeed() {
        return m_leader.getAppliedOutput();
    }

    public void forwardHorizontalConveyorMotor() {
        m_leader.set(HORIZONTAL_CONVEYOR_MOTOR_SPEED);
    }

    public void backwardHorizontalConveyorMotor() {
        m_leader.set(-HORIZONTAL_CONVEYOR_MOTOR_SPEED);
    }

    public void stopHorizontalConveyorMotor() {
        m_leader.set(0);
    }

}

