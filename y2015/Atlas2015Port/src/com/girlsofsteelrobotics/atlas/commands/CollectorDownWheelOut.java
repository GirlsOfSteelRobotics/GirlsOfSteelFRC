/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Sylvie and Jisue
 */
public class CollectorDownWheelOut extends CommandGroup {

    public CollectorDownWheelOut()
    {
        addParallel(new EngageCollector());
        addParallel(new CollectorWheelReverse());
    }
}
