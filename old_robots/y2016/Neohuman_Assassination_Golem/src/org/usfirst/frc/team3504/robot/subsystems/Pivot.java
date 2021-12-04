package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pivot extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final CANTalon pivotMotor;

    private double encOffsetValue = 0;

    public Pivot() {

        pivotMotor = new CANTalon(RobotMap.PIVOT_MOTOR);
        addChild("Talon", pivotMotor);

        if(RobotMap.USING_LIMIT_SWITCHES) {
            pivotMotor.configFwdLimitSwitchNormallyOpen(false);
            pivotMotor.configRevLimitSwitchNormallyOpen(false);
        }
        else {
            pivotMotor.enableLimitSwitch(false, false);
        }
        pivotMotor.setNeutralMode(NeutralMode.Brake);

    }

    public int getPosition() {
        if (getTopLimitSwitch() == true)
            return 1;
        else if(getBottomLimitSwitch() == true)
            return -1;
        else
            return 0;
    }

    public void tiltUpandDown(double speed) {
        pivotMotor.set(-speed);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());

    }

    public boolean getTopLimitSwitch(){
        return pivotMotor.isRevLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch(){
        return pivotMotor.isFwdLimitSwitchClosed() == 0;
    }

    public double getEncoderRight() {
        return pivotMotor.getEncPosition();
    }

    public double getEncoderDistance() {
        return (getEncoderRight() - encOffsetValue); //TODO: Know where encoder is
    }

    public void resetDistance() {
        encOffsetValue = getEncoderRight();
    }
}
