/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Moves the robot to the correct position and releases it into the low goal.
 *
 * @author Sophia, Sonia
 */
public class AutonomousLowGoal extends CommandGroup {

    /**
     * Moves the robot to the correct position and releases it into the low
     * goal.
     *
     * @author Sophia, Sonia
     *
     * WORKS DO NOT CHANGE
     */
    public AutonomousLowGoal() {
        addSequential(new setArmAnglePID(0));
        addParallel(new CollectorWheelForwardAutoVer());
        addSequential(new MoveToPositionLSPB(4.6)); //SET UP: At the tape of the red/white zone
        addParallel(new setArmAnglePID(-20));
        addSequential(new CollectorWheelReverse());
    }
}
