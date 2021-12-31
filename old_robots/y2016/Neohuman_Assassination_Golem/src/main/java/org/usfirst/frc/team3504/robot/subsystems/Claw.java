package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Claw extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final WPI_TalonSRX m_clawMotor;


    public Claw() {
        m_clawMotor = new WPI_TalonSRX(RobotMap.CLAW_MOTOR);
        addChild("Talon", m_clawMotor);
    }

    public void collectRelease(double speed) {
        m_clawMotor.set(ControlMode.PercentOutput, speed);

    }

    public void stopCollecting() {
        m_clawMotor.set(ControlMode.PercentOutput, 0.0);
        SmartDashboard.putBoolean("Claw Off", false);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
