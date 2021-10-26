/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command shoots into the low goal and assumes that the drivers have lined up already.
 * @author Sonia, Sophia
 */
public class ShootLowGoal extends CommandGroup {

    /**
     * This variable (0) stores the angle for the collector arm in order to shoot into the low goal.
     * @author Sonia, Sophia
     */
    private double kickAngle = 0;

    /**
     * This method sets the arm angle to 0 and reverses the collector wheel in order to shoot the ball into the low
     * goal.
     * @author Sonia, Sophia
     */
    public ShootLowGoal()
    {
        addSequential(new SetArmAngle(kickAngle));
        addSequential(new CollectorWheelReverse());
    }
}
