package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class CollectIn extends CommandBase {

    private final Manipulator m_manipulator;

    public CollectIn(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_manipulator.collectIn(-.75); //need to add whileheld button
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopCollector();
    }


}
