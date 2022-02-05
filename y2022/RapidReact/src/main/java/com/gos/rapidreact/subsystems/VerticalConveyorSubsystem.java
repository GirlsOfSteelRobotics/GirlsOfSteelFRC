package com.gos.rapidreact.subsystems;


import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VerticalConveyorSubsystem extends SubsystemBase {

    public static final int VERTICAL_CONVEYOR_MOTOR_SPEED = 1;

    private final SimableCANSparkMax m_motor;

    public VerticalConveyorSubsystem() {
        m_motor = new SimableCANSparkMax(Constants.VERTICAL_CONVEYOR_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void forwardVerticalConveyorMotor() {
        m_motor.set(VERTICAL_CONVEYOR_MOTOR_SPEED);
    }

    public void backwardVerticalConveyorMotor() {
        m_motor.set(-VERTICAL_CONVEYOR_MOTOR_SPEED);
    }


    public void stopVerticalConveyorMotor() {
        m_motor.set(0);
    }

}

