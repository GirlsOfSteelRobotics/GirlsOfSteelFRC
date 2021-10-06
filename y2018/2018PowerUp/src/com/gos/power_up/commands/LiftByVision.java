package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Blobs;
import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftByVision extends Command {
    private final Blobs m_blobs;
    private final Lift m_lift;

    public LiftByVision(Blobs blobs, Lift lift) {
        m_lift = lift;
        m_blobs = blobs;
    }


    @Override
    protected void initialize() {
        if (m_blobs.distanceBetweenBlobs() == -1) {
            System.out.print("LiftByVision initialize: line not in sight!!");
            end();
        }
    }


    @Override
    protected void execute() {
        if (m_blobs.distanceBetweenBlobs() != -1) {
            m_lift.incrementLift();
        }
    }


    @Override
    protected boolean isFinished() {
        return m_blobs.distanceBetweenBlobs() == -1;
    }


    @Override
    protected void end() {
    }


}
