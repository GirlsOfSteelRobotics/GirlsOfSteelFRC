package com.gos.power_up.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimeDelay extends Command {

    private final double m_seconds;
    private final Timer m_tim;

    public TimeDelay(double seconds) {
        // Use requires() here to declare subsystem dependencies
        m_tim = new Timer();
        this.m_seconds = seconds;

    }


    @Override
    protected void initialize() {
        m_tim.start();
        System.out.println("TimeDelay Initialzed with " + m_seconds + " seconds as parameter");
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return m_tim.get() > m_seconds;
    }


    @Override
    protected void end() {
        m_tim.stop();
        System.out.println("TimeDelay Finished");
    }


}
