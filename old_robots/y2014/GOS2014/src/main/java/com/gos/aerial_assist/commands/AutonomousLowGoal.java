/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
public class AutonomousLowGoal extends SequentialCommandGroup {

    /**
     * Moves the robot to the correct position and releases it into the low
     * goal.
     *
     * @author Sophia, Sonia
     * <p>
     * WORKS DO NOT CHANGE
     */
    public AutonomousLowGoal(Chassis chassis, Driving driving, Camera camera, Manipulator manipulator, Collector collector) {
        addCommands(new ParallelCommandGroup(
                new SetArmAnglePID(manipulator, 0),
                new CollectorWheelForwardAutoVer(collector, camera)
            ));
        addCommands(new ParallelCommandGroup(
            new MoveToPositionLSPB(chassis, driving, 4.6)), //SET UP: At the tape of the red/white zone
            new SetArmAnglePID(manipulator, -20));
        addCommands(new CollectorWheelReverse(collector));
    }
}
