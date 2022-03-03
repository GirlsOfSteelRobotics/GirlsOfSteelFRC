package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.steam_works.RobotMap;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Shifters;

/**
 *
 */
public class TurnByDistance extends CommandBase {

    private final double m_rotationsRight;
    private final double m_rotationsLeft;

    private final Chassis m_chassis;
    private final Shifters m_shifters;

    private double m_leftInitial;
    private double m_rightInitial;

    private final Shifters.Speed m_speed;

    public TurnByDistance(Chassis chassis, Shifters shifters, double rightInches, double leftInches, Shifters.Speed speed) {
        m_rotationsRight = rightInches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        m_rotationsLeft = leftInches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        m_speed = speed;

        m_shifters = shifters;
        m_chassis = chassis;

        addRequirements(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

        m_shifters.shiftGear(m_speed);

        // Robot.chassis.setupFPID(leftTalon);
        // Robot.chassis.setupFPID(rightTalon);

        if (m_speed == Shifters.Speed.kLow) {
            m_chassis.setPid(0.17, 0.0, 0.02, 0.0);
        } else if (m_speed == Shifters.Speed.kHigh) {
            m_chassis.setPid(0.02, 0.0, 0.04, 0.0);
        }


        // leftTalon.setPosition(0.0);
        // rightTalon.setPosition(0.0);

        System.out.println("TurnByDistance Started " + m_rotationsRight + m_rotationsLeft);

        m_leftInitial = -m_chassis.getLeftPosition();
        m_rightInitial = m_chassis.getRightPosition();


        m_chassis.setPositionGoal(-(m_rotationsLeft + m_leftInitial), m_rotationsRight + m_rightInitial);

        System.out.println("LeftInitial: " + m_leftInitial + " RightInitial: " + m_rightInitial);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.setPositionGoal(-(m_rotationsLeft + m_leftInitial), m_rotationsRight + m_rightInitial);

        SmartDashboard.putNumber("Drive Talon Left Goal", -m_rotationsLeft);
        SmartDashboard.putNumber("Drive Talon Left Position", m_chassis.getLeftPosition());
        SmartDashboard.putNumber("Drive Talon Left Error", m_chassis.getClosedLoopError());

        //System.out.println("Left Goal " + (-(rotations + leftInitial)) + " Right Goal " + (rotations + rightInitial));
        //System.out.println("Left Position " + leftTalon.getPosition() + " Right Position " + rightTalon.getPosition());
        //System.out.println("Left Error " + ((-(rotations + leftInitial)) + leftTalon.getPosition()));
        //System.out.println("Right Error " + (((rotations + rightInitial)) - rightTalon.getPosition()));
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_shifters.shiftGear(Shifters.Speed.kLow);
        System.out.println("TurnByDistance Finished");
    }


}
