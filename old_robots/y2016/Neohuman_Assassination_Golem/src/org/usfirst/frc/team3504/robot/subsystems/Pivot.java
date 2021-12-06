package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Pivot extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final CANTalon m_pivotMotor;

    private double m_encOffsetValue;

    public Pivot() {

        m_pivotMotor = new CANTalon(RobotMap.PIVOT_MOTOR);
        addChild("Talon", m_pivotMotor);

        if(RobotMap.USING_LIMIT_SWITCHES) {
            m_pivotMotor.configFwdLimitSwitchNormallyOpen(false);
            m_pivotMotor.configRevLimitSwitchNormallyOpen(false);
        }
        else {
            m_pivotMotor.enableLimitSwitch(false, false);
        }
        m_pivotMotor.setNeutralMode(NeutralMode.Brake);

    }

    public int getPosition() {
        if (getTopLimitSwitch()) {
            return 1;
        }
        else if(getBottomLimitSwitch()) {
            return -1;
        }
        else {
            return 0;
        }
    }

    public void tiltUpandDown(double speed) {
        m_pivotMotor.set(-speed);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());

    }

    public boolean getTopLimitSwitch(){
        return m_pivotMotor.isRevLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch(){
        return m_pivotMotor.isFwdLimitSwitchClosed() == 0;
    }

    public double getEncoderRight() {
        return m_pivotMotor.getEncPosition();
    }

    public double getEncoderDistance() {
        return (getEncoderRight() - m_encOffsetValue); //TODO: Know where encoder is
    }

    public void resetDistance() {
        m_encOffsetValue = getEncoderRight();
    }
}
