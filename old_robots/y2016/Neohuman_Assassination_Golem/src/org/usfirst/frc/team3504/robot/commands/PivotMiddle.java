package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Pivot;

/**
 *
 */
public class PivotMiddle extends Command {

    private static final double EncoderValueUp = -30; //based on initial position
    private static final double EncoderValueMiddle = 0; //TODO: fix these depending on which way is positive for motor
    private static final double EncoderValueDown = 30; //TODO: fix values
    private final Pivot m_pivot;
    private double m_encoderToUse;
    private double m_speed;

    public PivotMiddle(Pivot pivot) {
        m_pivot = pivot;
        requires(m_pivot);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        if (m_pivot.getPosition() == 1) {
            m_encoderToUse = EncoderValueUp;
            m_speed = -.1;}
        else if (m_pivot.getPosition() == 0) {
            m_encoderToUse = EncoderValueMiddle;
        }
        else {
            m_encoderToUse = EncoderValueDown;
            m_speed = .1; }

        m_pivot.resetDistance();

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_pivot.tiltUpandDown(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (m_speed == 1) {
            return m_pivot.getEncoderDistance() <= m_encoderToUse;
        }
        else if (m_speed == -1) {
            return m_pivot.getEncoderDistance() >= m_encoderToUse;
        }
        else {
            return true;
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_pivot.tiltUpandDown(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
