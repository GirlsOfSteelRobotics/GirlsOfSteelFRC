/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * This command shoots into the low goal and assumes that the drivers have lined up already.
 *
 * @author Sonia, Sophia
 */
public class ShootLowGoal extends CommandGroup {

    /**
     * This variable (0) stores the angle for the collector arm in order to shoot into the low goal.
     *
     * @author Sonia, Sophia
     */
    private static final double m_kickAngle = 0;

    /**
     * This method sets the arm angle to 0 and reverses the collector wheel in order to shoot the ball into the low
     * goal.
     *
     * @author Sonia, Sophia
     */
    public ShootLowGoal(Manipulator manipulator, Collector collector) {
        addSequential(new SetArmAngle(manipulator, m_kickAngle));
        addSequential(new CollectorWheelReverse(collector));
    }
}
