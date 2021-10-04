
package com.gos.deep_space.subsystems;

import com.gos.deep_space.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class Collector extends Subsystem {

    private WPI_TalonSRX leftCollect;
    private WPI_TalonSRX rightCollect;

    private static final double SLOW_COLLECTOR_SPEED = 0.15;
    private static final double COLLECTOR_INTAKE_SPEED = 0.4;
    private static final double COLLECTOR_RELEASE_SPEED = 0.35; // .5 pre-GPR

    public Collector() {
        leftCollect = new WPI_TalonSRX(RobotMap.COLLECT_LEFT_TALON);
        rightCollect = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT_TALON);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void stop() {
        leftCollect.stopMotor();
        rightCollect.stopMotor();
    }

    public void collect() {
        leftCollect.set(-COLLECTOR_INTAKE_SPEED);
        rightCollect.set(COLLECTOR_INTAKE_SPEED);
    }

    public void release() {
        leftCollect.set(COLLECTOR_RELEASE_SPEED);
        rightCollect.set(-COLLECTOR_RELEASE_SPEED);
    }

    public void slowCollect() {
        leftCollect.set(-SLOW_COLLECTOR_SPEED);
        rightCollect.set(SLOW_COLLECTOR_SPEED);
    }
}
