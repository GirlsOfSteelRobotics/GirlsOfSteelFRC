package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Agitator;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Agitate extends CommandBase {

    private final Agitator m_agitator;
    private int m_loopCounter;

    public Agitate(Agitator agitator) {
        m_agitator = agitator;
        addRequirements(m_agitator);
    }


    @Override
    public void initialize() {
        System.out.println("Agitate Initialzed");
        m_loopCounter = 0;
    }


    @Override
    public void execute() {
        if (m_loopCounter % 10 == 0) {
            m_agitator.agitateBackwards();
        } else if (m_loopCounter % 5 == 0) {
            m_agitator.agitateForwards();
        }

        m_loopCounter++;
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("Agitate Finished");
    }


}
