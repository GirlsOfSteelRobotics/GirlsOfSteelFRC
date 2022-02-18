package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VerticalConveyorSubsystem extends SubsystemBase {

    public static final double VERTICAL_CONVEYOR_MOTOR_SPEED = 0.5;

    private final SimableCANSparkMax m_motor;

    public VerticalConveyorSubsystem() {
        m_motor = new SimableCANSparkMax(Constants.VERTICAL_CONVEYOR_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_motor.restoreFactoryDefaults();
        m_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

    }

    public void forwardVerticalConveyorMotor() {
        m_motor.set(VERTICAL_CONVEYOR_MOTOR_SPEED);
    }

    public void backwardVerticalConveyorMotor() {
        m_motor.set(-VERTICAL_CONVEYOR_MOTOR_SPEED);
    }

    public double getVerticalConveyorSpeed() {
        return m_motor.get();
    }

    public void stopVerticalConveyorMotor() {
        m_motor.set(0);
    }

}

