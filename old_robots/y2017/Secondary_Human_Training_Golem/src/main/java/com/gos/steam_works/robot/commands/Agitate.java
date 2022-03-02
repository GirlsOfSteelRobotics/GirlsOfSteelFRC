package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.robot.subsystems.Agitator;

public class Agitate extends CommandBase {

    private final Agitator m_agitator;
    private int m_loopCounter;

    public Agitate(Agitator agitator) {
        m_agitator = agitator;
        addRequirements(m_agitator);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        System.out.println("Agitate Initialzed");
        m_loopCounter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        if (m_loopCounter % 10 == 0) {
            m_agitator.agitateBackwards();
        } else if (m_loopCounter % 5 == 0) {
            m_agitator.agitateForwards();
        }

        m_loopCounter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        System.out.println("Agitate Finished");
    }


}
