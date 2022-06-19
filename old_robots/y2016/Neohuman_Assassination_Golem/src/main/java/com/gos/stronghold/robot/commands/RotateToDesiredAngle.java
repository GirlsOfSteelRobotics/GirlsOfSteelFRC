package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class RotateToDesiredAngle extends CommandBase {
    private final Chassis m_chassis;
    private final double m_move;
    private final double m_desiredAngle; // NOPMD

    public RotateToDesiredAngle(Chassis chassis, double moveValue, double angle) {
        m_chassis = chassis;
        addRequirements(m_chassis);
        m_move = moveValue;
        m_desiredAngle = angle;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        try {
            // Use the joystick X axis for lateral movement,
            // Y axis for forward movement, and the current
            // calculated rotation rate (or joystick Z axis),
            // depending upon whether "rotate to angle" is active.
            m_chassis.drive(m_move, m_chassis.getRotationAngleRate());
        } catch (RuntimeException ex) { // NOPMD
            DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
        //Math.abs(Robot.chassis.getGyroAngle()) >= Math.abs(desiredAngle);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
    }


}
