package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class GoToLocation extends GosCommand {

    private final Chassis m_chassis;
    private final double m_x;
    private final double m_y;
    private final double m_degreesToFace; //degree change relative to beginning position of the
    //robot to end facing

    public GoToLocation(Chassis chassis, double x, double y, double degreesToFace) {
        m_chassis = chassis;
        this.m_x = x;
        this.m_y = y;
        this.m_degreesToFace = degreesToFace;
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    public void execute() {
        m_chassis.goToLocation(m_x, m_y, m_degreesToFace);
    }

    @Override
    public boolean isFinished() {
        return m_chassis.isGoToLocationFinished(m_x, m_y);
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }


}
