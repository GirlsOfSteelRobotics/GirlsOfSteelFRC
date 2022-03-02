package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Blobs;
import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class LiftByVision extends CommandBase {
    private final Blobs m_blobs;
    private final Lift m_lift;

    public LiftByVision(Blobs blobs, Lift lift) {
        m_lift = lift;
        m_blobs = blobs;
    }


    @Override
    public void initialize() {
        if (m_blobs.distanceBetweenBlobs() == -1) {
            System.out.print("LiftByVision initialize: line not in sight!!");
            end(true);
        }
    }


    @Override
    public void execute() {
        if (m_blobs.distanceBetweenBlobs() != -1) {
            m_lift.incrementLift();
        }
    }


    @Override
    public boolean isFinished() {
        return m_blobs.distanceBetweenBlobs() == -1;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
