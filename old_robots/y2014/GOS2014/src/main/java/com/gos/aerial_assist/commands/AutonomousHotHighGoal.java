/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Parent
 */
public class AutonomousHotHighGoal extends SequentialCommandGroup {

    public AutonomousHotHighGoal(Chassis chassis, Driving driving, Manipulator manipulator) {
        addCommands(new ParallelCommandGroup(
            new MoveToPosition(chassis, driving, 1),
            new SetArmAnglePID(manipulator, 30)
        ));
        if (Camera.isGoalHot()) {
            addCommands(new ShootHigh());
        }
    }
}
