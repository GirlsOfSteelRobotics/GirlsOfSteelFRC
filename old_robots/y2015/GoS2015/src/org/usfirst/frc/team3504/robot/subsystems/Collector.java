package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.collector.CollectReleaseTote;

import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Collector extends Subsystem {

    // Talons
    private CANTalon rightCollector;
    private CANTalon leftCollector;

    // Pistons
    private DoubleSolenoid collectorLeftSolenoid;
    private DoubleSolenoid collectorRightSolenoid;

    // Triggers (Collect/Release Tote Buttons)
    private Joystick collectorTrigger;

    private final static double speed = .5;

    public Collector() // this is the constructor
    {
        rightCollector = new CANTalon(RobotMap.RIGHT_COLLECTOR_WHEEL);
        leftCollector = new CANTalon(RobotMap.LEFT_COLLECTOR_WHEEL);

        collectorLeftSolenoid = new DoubleSolenoid(RobotMap.LEFT_COLLECTOR_MODULE, RobotMap.LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
                RobotMap.LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL);
        collectorRightSolenoid = new DoubleSolenoid(RobotMap.RIGHT_COLLECTOR_MODULE,
                RobotMap.RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL, RobotMap.RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL);

        collectorTrigger = new Joystick(RobotMap.OPERATOR_JOYSTICK);

        SmartDashboard.putBoolean("Collector On", false);
    }

    public void collectReleaseTote() {
        if (collectorTrigger.getZ() < -0.5) // release
            collectorToteOut();
        else if (collectorTrigger.getZ() > 0.5) // collect
            collectorToteIn();
        else {
            rightCollector.set(0);
            leftCollector.set(0);
        }

    }

    // Method suckToteIn which suck a tote inside the robot
    public void collectorToteIn() {
        rightCollector.set(speed);
        leftCollector.set(-speed);
        SmartDashboard.putBoolean("Collecter On", true);
    }

    // Method suckToteOut which pushes a Tote out
    public void collectorToteOut() {
        rightCollector.set(-speed);
        leftCollector.set(speed);
        SmartDashboard.putBoolean("Collecter On", true);
    }

    // Method collectorToteRotate which rotates the tote inside the trifold
    public void collectorToteRotateRight() {
        rightCollector.set(speed);
        leftCollector.set(speed);
    }

    public void collectorToteRotateLeft() {
        rightCollector.set(-speed);
        leftCollector.set(-speed);
    }

    public void collectorIn() {
        collectorRightSolenoid.set(DoubleSolenoid.Value.kForward);
        collectorLeftSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void collectorOut() {
        collectorRightSolenoid.set(DoubleSolenoid.Value.kReverse);
        collectorLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void stopCollecting() {
        rightCollector.set(0.0);
        leftCollector.set(0.0);
        SmartDashboard.putBoolean("Collector Off", false);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        new CollectReleaseTote();
    }

}
