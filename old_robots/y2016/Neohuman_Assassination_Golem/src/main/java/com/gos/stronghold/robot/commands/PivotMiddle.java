package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Pivot;

/**
 *
 */
public class PivotMiddle extends CommandBase {

    private static final double ENCODER_VALUE_UP = -30; //based on initial position
    private static final double ENCODER_VALUE_MIDDLE = 0; //TODO: fix these depending on which way is positive for motor
    private static final double ENCODER_VALUE_DOWN = 30; //TODO: fix values
    private final Pivot m_pivot;
    private double m_encoderToUse;
    private double m_speed;

    public PivotMiddle(Pivot pivot) {
        m_pivot = pivot;
        addRequirements(m_pivot);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        if (m_pivot.getPosition() == 1) {
            m_encoderToUse = ENCODER_VALUE_UP;
            m_speed = -.1;
        } else if (m_pivot.getPosition() == 0) {
            m_encoderToUse = ENCODER_VALUE_MIDDLE;
        } else {
            m_encoderToUse = ENCODER_VALUE_DOWN;
            m_speed = .1;
        }

        m_pivot.resetDistance();

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_pivot.tiltUpandDown(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        if (m_speed == 1) {
            return m_pivot.getEncoderDistance() <= m_encoderToUse;
        } else if (m_speed == -1) {
            return m_pivot.getEncoderDistance() >= m_encoderToUse;
        } else {
            return true;
        }
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_pivot.tiltUpandDown(0);
    }


}
