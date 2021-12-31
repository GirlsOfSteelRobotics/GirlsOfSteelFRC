package org.usfirst.frc.team3504.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Manipulator extends Subsystem {
    private final SpeedController m_conveyorBeltMotorRight;
    private final SpeedController m_conveyorBeltMotorLeft;

    public Manipulator() {

        m_conveyorBeltMotorRight = new WPI_TalonSRX(6);
        m_conveyorBeltMotorLeft = new WPI_TalonSRX(7);
    }

    @Override
    public void initDefaultCommand() {
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
