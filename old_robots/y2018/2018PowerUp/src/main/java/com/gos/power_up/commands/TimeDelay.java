package com.gos.power_up.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

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
    public void initialize() {
        m_tim.start();
        System.out.println("TimeDelay Initialzed with " + m_seconds + " seconds as parameter");
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return m_tim.get() > m_seconds;
    }


    @Override
    public void end(boolean interrupted) {
        m_tim.stop();
        System.out.println("TimeDelay Finished");
    }


}
