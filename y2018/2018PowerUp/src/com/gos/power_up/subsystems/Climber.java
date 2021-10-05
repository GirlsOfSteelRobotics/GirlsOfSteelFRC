package com.gos.power_up.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

    public WPI_TalonSRX climbMotor;

    public Climber() {
        climbMotor = new WPI_TalonSRX(RobotMap.CLIMB_MOTOR);

        climbMotor.setNeutralMode(NeutralMode.Brake);
        climbMotor.configContinuousCurrentLimit(200, 10);


        // Put methods for controlling this subsystem
        // here. Call these from Commands.

    }

    public void climb(double speed) {
        climbMotor.set(speed);
    }

    public void stopClimb() {
        climbMotor.set(0.0);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
