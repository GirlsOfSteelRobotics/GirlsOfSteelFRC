/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * This command shoots into the low goal and assumes that the drivers have lined up already.
 *
 * @author Sonia, Sophia
 */
public class ShootLowGoal extends SequentialCommandGroup {

    /**
     * This variable (0) stores the angle for the collector arm in order to shoot into the low goal.
     *
     * @author Sonia, Sophia
     */
    private static final double KICK_ANGLE = 0;

    /**
     * This method sets the arm angle to 0 and reverses the collector wheel in order to shoot the ball into the low
     * goal.
     *
     * @author Sonia, Sophia
     */
    public ShootLowGoal(Manipulator manipulator, Collector collector) {
        addCommands(new SetArmAngle(manipulator, KICK_ANGLE));
        addCommands(new CollectorWheelReverse(collector));
    }
}
