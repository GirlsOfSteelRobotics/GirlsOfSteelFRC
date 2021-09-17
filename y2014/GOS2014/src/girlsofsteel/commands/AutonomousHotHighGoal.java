/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.Camera;

/**
 *
 * @author Parent
 */
public class AutonomousHotHighGoal extends CommandGroup{

    public AutonomousHotHighGoal() {
        addSequential(new MoveToPosition(1));
        addParallel(new setArmAnglePID(30));
    if (Camera.isGoalHot())
        addSequential(new ShootHigh());
}
}