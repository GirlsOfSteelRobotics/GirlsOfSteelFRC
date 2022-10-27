/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Parent
 */
public class AutonomousHighGoal extends SequentialCommandGroup {
    //Magic numbers need to be added!
    public AutonomousHighGoal(Chassis chassis, Driving driving, Manipulator manipulator) {
        addCommands(new MoveToPosition(chassis, driving));
        addCommands(new ParallelCommandGroup(
            new SetArmAnglePID(manipulator, 85)), //Magic angle, needs to be corrected
            new ShootHigh()
        );
    }
}
