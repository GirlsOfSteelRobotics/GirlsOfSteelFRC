package com.gos.outreach2016.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.outreach2016.robot.subsystems.Manipulator;

/**
 *
 */
public class ShootBall extends Command {

    private final Manipulator m_manipulator;

    public ShootBall(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_manipulator.shootBall();
        System.out.println("Shoot ball");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
