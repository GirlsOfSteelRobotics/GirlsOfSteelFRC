/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *
 * @author Parent
 */
public class AutonomousHighGoal extends CommandGroup {
//Magic numbers need to be added!
    public AutonomousHighGoal() {
    addSequential(new MoveToPosition());
    addParallel(new setArmAnglePID(85)); //Magic angle, needs to be corrected
    addSequential(new ShootHigh()); //ShootHigh command is not finished
}
}