package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class MoveToSetPoint extends GosCommandBaseBase {

    private final Chassis m_chassis;
    private double m_timeFinished = -1;
    private final double m_distanceToMove;

    public MoveToSetPoint(Chassis chassis, double distance) {
        m_chassis = chassis;
        m_distanceToMove = distance;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
        m_timeFinished = -1;
    }

    @Override
    public void execute() {
        m_chassis.setPIDsPosition();
        m_chassis.move(m_distanceToMove);
        if (m_timeFinished == -1 && m_chassis.isMoveFinished(m_distanceToMove)) {
            m_timeFinished = timeSinceInitialized();
        }
        //        if(timeSinceInitialized() > 3){
        //            System.out.println("Move to Set Point Timed Out");
        //            end();
        //        }
    }

    @Override
    public boolean isFinished() {
        if (m_timeFinished != -1 && timeSinceInitialized() - m_timeFinished > 0.5) {
            System.out.println("Move to Set Point Done");
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Done with moving");
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
    }


}
