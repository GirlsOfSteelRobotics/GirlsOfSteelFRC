package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.StayClimbed;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Climber extends Subsystem {
    public CANTalon climbMotorA;
    public CANTalon climbMotorB;

    public Climber() {
        climbMotorA = new CANTalon(RobotMap.CLIMB_MOTOR_A);
        climbMotorB = new CANTalon(RobotMap.CLIMB_MOTOR_B);

        climbMotorA.setNeutralMode(NeutralMode.Brake);
        climbMotorB.setNeutralMode(NeutralMode.Brake);

        climbMotorA.setFeedbackDevice(FeedbackDevice.CTRE_MagEncoder_Relative);
        climbMotorA.reverseSensor(true);

        climbMotorA.setF(0);
        climbMotorA.setP(0.5);
        climbMotorA.setI(0);
        climbMotorA.setD(0);

        addChild("climbMotorA", climbMotorA);
        addChild("climbMotorB", climbMotorB);
    }

    public void climb(double speed) {
        climbMotorA.set(speed);
        climbMotorB.set(speed);
    }

    public void stopClimb() {
        climbMotorA.set(0.0);
        climbMotorB.set(0.0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new StayClimbed());
    }
}
