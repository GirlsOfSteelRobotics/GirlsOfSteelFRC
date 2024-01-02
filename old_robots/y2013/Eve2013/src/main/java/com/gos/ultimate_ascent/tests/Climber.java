/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.tests;


import com.gos.ultimate_ascent.subsystems.Gripper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.GosCommand;

/**
 * @author sam
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Climber extends GosCommand {

    private final com.gos.ultimate_ascent.subsystems.Climber m_climber;
    private final Gripper m_gripper;
    public int m_state;

    public Climber(com.gos.ultimate_ascent.subsystems.Climber climber, Gripper gripper) {
        m_climber = climber;
        m_gripper = gripper;
        addRequirements(climber);

        SmartDashboard.putString("Comand called", "comand called");


        SmartDashboard.putBoolean("Spikes", false);
        SmartDashboard.putBoolean("Spikes Foward Backwards", false);

        SmartDashboard.putBoolean("Bottom grip Open Close", false);
        SmartDashboard.putBoolean("Middle grip Open Close", false);
        SmartDashboard.putBoolean("Top grip Open Close", false);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {


        if (SmartDashboard.getBoolean("Spikes", false) && SmartDashboard.getBoolean("Spikes Foward Backwards", false)) {
            m_climber.forwardRightClimberSpike();
            m_climber.forwardLeftClimberSpike();
        } else if (SmartDashboard.getBoolean("Spikes", false) && !SmartDashboard.getBoolean("Spikes Foward Backwards", false)) {
            m_climber.reverseLeftClimberSpike();
            m_climber.reverseRightClimberSpike();
        } else if (!SmartDashboard.getBoolean("Spikes", false)) {
            m_climber.stopLeftClimberSpike();
            m_climber.stopRightClimberSpike();
        }

        if (SmartDashboard.getBoolean("Bottom grip Open Close", false)) {
            m_gripper.openGrip();
        } else {
            m_gripper.closeGrip();
        }

        //        if (SmartDashboard.getBoolean("Middle grip Open Close", false)) {
        //            middleGripper.openGrip();
        //        } else {
        //            middleGripper.closeGrip();
        //        }
        //
        //        if (SmartDashboard.getBoolean("Top grip Open Close", true)) {
        //            topGripper.openGrip();
        //        } else {
        //            topGripper.closeGrip();
        //        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }


}
