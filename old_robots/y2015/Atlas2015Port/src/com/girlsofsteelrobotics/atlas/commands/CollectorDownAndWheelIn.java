/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Sylvie and Heather
 */
public class CollectorDownAndWheelIn extends CommandGroup{
    public CollectorDownAndWheelIn () {
        addParallel(new CollectorWheelForward());
        addParallel(new EngageCollector());
    }
}
