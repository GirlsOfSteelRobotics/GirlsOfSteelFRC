package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.stronghold.robot.subsystems.Pivot;


public class PivotUp extends Command {

    /*private static final double EncoderValueUp = 0; //based on initial position
    private static final double EncoderValueMiddle = 30; //TODO: fix these depending on which way is positive for motor
    private static final double EncoderValueDown = 60; //TODO: fix values
    private double encoderToUse = 0;
    */

    private final Pivot m_pivot;

    public PivotUp(Pivot pivot) {
        m_pivot = pivot;
        addRequirements(m_pivot);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        /*if (Robot.pivot.getPosition() == 1)
            encoderToUse = EncoderValueUp;
        else if (Robot.pivot.getPosition() == 0)
            encoderToUse = EncoderValueMiddle;
        else
            encoderToUse = EncoderValueDown;

        Robot.pivot.resetDistance();
        */
        SmartDashboard.putString("pivot up", "initializing");

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_pivot.tiltUpandDown(-0.3);
        SmartDashboard.putBoolean("Top Pivot LS:", m_pivot.getTopLimitSwitch());
        SmartDashboard.putBoolean("Bottom Pivot LS", m_pivot.getBottomLimitSwitch());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putString("pivot up", "ending");
        m_pivot.tiltUpandDown(0);
    }


}
