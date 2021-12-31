/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;

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
     * <p>
     * WORKS DO NOT CHANGE
     */
    public AutonomousLowGoal(Chassis chassis, Driving driving, Camera camera, Manipulator manipulator, Collector collector) {
        addSequential(new SetArmAnglePID(manipulator, 0));
        addParallel(new CollectorWheelForwardAutoVer(collector, camera));
        addSequential(new MoveToPositionLSPB(chassis, driving, 4.6)); //SET UP: At the tape of the red/white zone
        addParallel(new SetArmAnglePID(manipulator, -20));
        addSequential(new CollectorWheelReverse(collector));
    }
}
