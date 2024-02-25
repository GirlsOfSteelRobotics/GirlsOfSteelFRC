package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;

public class AutoMove extends GosCommandBaseBase {

    private final Chassis m_chassis;
    private final double m_x;
    private final double m_y;
    private final double m_time;

    private double m_startTime;

    /**
     * Moves in the direction given for a certain amount of time automatically.
     *
     * @param x    the percentage to move in the x direction (-1 to 1)
     * @param y    the percentage to move in the y direction (-1 to 1)
     * @param time the length of time to move
     */
    public AutoMove(Chassis chassis, double x, double y, double time) {
        m_chassis = chassis;
        this.m_x = x;
        this.m_y = y;
        this.m_time = time;
    }

    @Override
    public void initialize() {
        m_startTime = timeSinceInitialized();
    }

    @Override
    public void execute() {
        m_chassis.driveVoltage(m_x, m_y, 0, 1.0, true);
    }

    @Override
    public boolean isFinished() {
        return timeSinceInitialized() - m_startTime > m_time;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
    }



}
