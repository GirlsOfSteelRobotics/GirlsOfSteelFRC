package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.collector.CollectReleaseTote;


public class Collector extends Subsystem {

    private static final double speed = .5;

    // Talons
    private final CANTalon m_rightCollector;
    private final CANTalon m_leftCollector;

    // Pistons
    private final DoubleSolenoid m_collectorLeftSolenoid;
    private final DoubleSolenoid m_collectorRightSolenoid;

    // Triggers (Collect/Release Tote Buttons)
    private final Joystick m_collectorTrigger;

    public Collector() // this is the constructor
    {
        m_rightCollector = new CANTalon(RobotMap.RIGHT_COLLECTOR_WHEEL);
        m_leftCollector = new CANTalon(RobotMap.LEFT_COLLECTOR_WHEEL);

        m_collectorLeftSolenoid = new DoubleSolenoid(RobotMap.LEFT_COLLECTOR_MODULE, RobotMap.LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL,
                RobotMap.LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL);
        m_collectorRightSolenoid = new DoubleSolenoid(RobotMap.RIGHT_COLLECTOR_MODULE,
                RobotMap.RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL, RobotMap.RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL);

        m_collectorTrigger = new Joystick(RobotMap.OPERATOR_JOYSTICK);

        SmartDashboard.putBoolean("Collector On", false);
    }

    public void collectReleaseTote() {
        if (m_collectorTrigger.getZ() < -0.5) { // release
            collectorToteOut();
        }
        else if (m_collectorTrigger.getZ() > 0.5) { // collect
            collectorToteIn();
        }
        else {
            m_rightCollector.set(0);
            m_leftCollector.set(0);
        }

    }

    // Method suckToteIn which suck a tote inside the robot
    public void collectorToteIn() {
        m_rightCollector.set(speed);
        m_leftCollector.set(-speed);
        SmartDashboard.putBoolean("Collecter On", true);
    }

    // Method suckToteOut which pushes a Tote out
    public void collectorToteOut() {
        m_rightCollector.set(-speed);
        m_leftCollector.set(speed);
        SmartDashboard.putBoolean("Collecter On", true);
    }

    // Method collectorToteRotate which rotates the tote inside the trifold
    public void collectorToteRotateRight() {
        m_rightCollector.set(speed);
        m_leftCollector.set(speed);
    }

    public void collectorToteRotateLeft() {
        m_rightCollector.set(-speed);
        m_leftCollector.set(-speed);
    }

    public void collectorIn() {
        m_collectorRightSolenoid.set(DoubleSolenoid.Value.kForward);
        m_collectorLeftSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void collectorOut() {
        m_collectorRightSolenoid.set(DoubleSolenoid.Value.kReverse);
        m_collectorLeftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void stopCollecting() {
        m_rightCollector.set(0.0);
        m_leftCollector.set(0.0);
        SmartDashboard.putBoolean("Collector Off", false);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        new CollectReleaseTote(this);
    }

}
