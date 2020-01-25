package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Winch extends SubsystemBase {

    private final TalonSRX motorA;
    private final TalonSRX motorB;

	public Winch () {
        motorA = new TalonSRX(Constants.WINCH_A_TALON);
        motorA.configFactoryDefault();
        motorA.setInverted(false);
        motorB = new TalonSRX(Constants.WINCH_B_TALON);
        motorB.configFactoryDefault();
        motorB.setInverted(false);
    } 

    public void wind(){
        motorA.set(ControlMode.PercentOutput, 1);
        motorB.set(ControlMode.PercentOutput, 1);
    }

    public void unwind(){
        motorA.set(ControlMode.PercentOutput, -1);
        motorB.set(ControlMode.PercentOutput, -1);
    }

    public void stop(){
        motorA.set(ControlMode.PercentOutput, 0);
        motorB.set(ControlMode.PercentOutput, 0);
    }

}