package org.usfirst.frc.team3504.robot.commands;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

/**
 *
 */
public class DriveByDistance extends Command {

    private final double m_rotations;

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final CANTalon m_leftTalon;
    private final CANTalon m_rightTalon;

    private double m_leftInitial;
    private double m_rightInitial;

    private final Shifters.Speed m_speed;

    public DriveByDistance(Chassis chassis, Shifters shifters, double inches, Shifters.Speed speed) {
        m_chassis = chassis;
        m_shifters = shifters;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        m_rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        m_speed = speed;

        // Use requires() here to declare subsystem dependencies
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_chassis.setPositionMode();

        m_shifters.shiftGear(m_speed);

        // Robot.chassis.setupFPID(leftTalon);
        // Robot.chassis.setupFPID(rightTalon);

        if (m_speed == Shifters.Speed.kLow){
            m_leftTalon.setP(0.17);
            m_rightTalon.setP(0.17);

            m_leftTalon.setI(0.0);
            m_rightTalon.setI(0.0);

            m_leftTalon.setD(0.02);
            m_rightTalon.setD(0.02);

            m_leftTalon.setF(0.0);
            m_rightTalon.setF(0.0);
        }
        else if (m_speed == Shifters.Speed.kHigh){
            m_leftTalon.setP(0.02);
            m_rightTalon.setP(0.02);

            m_leftTalon.setI(0.0);
            m_rightTalon.setI(0.0);

            m_leftTalon.setD(0.04);
            m_rightTalon.setD(0.04);

            m_leftTalon.setF(0.0);
            m_rightTalon.setF(0.0);
        }


        // leftTalon.setPosition(0.0);
        // rightTalon.setPosition(0.0);

        System.out.println("Drive by Distance Started " + m_rotations);

        m_leftInitial = -m_leftTalon.getPosition();
        m_rightInitial = m_rightTalon.getPosition();

        m_leftTalon.set(-(m_rotations + m_leftInitial));
        m_rightTalon.set(m_rotations + m_rightInitial);

        System.out.println("LeftInitial: " + m_leftInitial + " RightInitial: " + m_rightInitial);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_leftTalon.set(-(m_rotations + m_leftInitial));
        m_rightTalon.set(m_rotations + m_rightInitial);

        SmartDashboard.putNumber("Drive Talon Left Goal", -m_rotations);
        SmartDashboard.putNumber("Drive Talon Left Position", m_leftTalon.getPosition());
        SmartDashboard.putNumber("Drive Talon Left Error", m_leftTalon.getError());

        //System.out.println("Left Goal " + (-(rotations + leftInitial)) + " Right Goal " + (rotations + rightInitial));
        //System.out.println("Left Position " + leftTalon.getPosition() + " Right Position " + rightTalon.getPosition());
        //System.out.println("Left Error " + ((-(rotations + leftInitial)) + leftTalon.getPosition()));
        //System.out.println("Right Error " + (((rotations + rightInitial)) - rightTalon.getPosition()));
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (m_rotations > 0) {
            return ((m_rightTalon.getPosition() > m_rotations + m_rightInitial)
                    && (-m_leftTalon.getPosition() > m_rotations + m_leftInitial));
        } else if (m_rotations < 0) {
            return ((m_rightTalon.getPosition() < m_rotations + m_rightInitial)
                    && (-m_leftTalon.getPosition() < m_rotations + m_leftInitial));
        } else {
            return true;
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_shifters.shiftGear(Shifters.Speed.kLow);
        System.out.println("DriveByDistance Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
