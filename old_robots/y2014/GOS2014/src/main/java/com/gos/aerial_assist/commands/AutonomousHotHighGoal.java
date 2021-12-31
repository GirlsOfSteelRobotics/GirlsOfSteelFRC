/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Parent
 */
public class AutonomousHotHighGoal extends CommandGroup {

    public AutonomousHotHighGoal(Chassis chassis, Driving driving, Manipulator manipulator) {
        addSequential(new MoveToPosition(chassis, driving, 1));
        addParallel(new SetArmAnglePID(manipulator, 30));
        if (Camera.isGoalHot()) {
            addSequential(new ShootHigh());
        }
    }
}
