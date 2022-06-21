package com.gos.preseason2016.team_fbi.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Manipulator extends SubsystemBase {
    private final MotorController m_conveyorBeltMotorRight;
    private final MotorController m_conveyorBeltMotorLeft;

    public Manipulator() {

        m_conveyorBeltMotorRight = new WPI_TalonSRX(6);
        m_conveyorBeltMotorLeft = new WPI_TalonSRX(7);
    }



    public void manipulatorConveyorBeltMotorRight(boolean fwd) {
        if (fwd) {
            m_conveyorBeltMotorRight.set(1.0);
        } else {
            m_conveyorBeltMotorRight.set(-1.0);
        }

    }

    public void manipulatorConveyorBeltMotorLeft(boolean fwd) {
        if (fwd) {
            m_conveyorBeltMotorLeft.set(1.0);
        } else {
            m_conveyorBeltMotorLeft.set(-1.0);
        }

    }

    public void stopConveyorBeltMotor() {
        m_conveyorBeltMotorRight.set(0.0);
        m_conveyorBeltMotorLeft.set(0.0);
    }

}
