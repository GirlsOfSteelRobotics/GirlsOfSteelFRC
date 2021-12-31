package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Agitator;
import edu.wpi.first.wpilibj.command.Command;

public class Agitate extends Command {

    private final Agitator m_agitator;
    private int m_loopCounter;

    public Agitate(Agitator agitator) {
        m_agitator = agitator;
        requires(m_agitator);
    }


    @Override
    protected void initialize() {
        System.out.println("Agitate Initialzed");
        m_loopCounter = 0;
    }


    @Override
    protected void execute() {
        if (m_loopCounter % 10 == 0) {
            m_agitator.agitateBackwards();
        } else if (m_loopCounter % 5 == 0) {
            m_agitator.agitateForwards();
        }

        m_loopCounter++;
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        System.out.println("Agitate Finished");
    }


}
