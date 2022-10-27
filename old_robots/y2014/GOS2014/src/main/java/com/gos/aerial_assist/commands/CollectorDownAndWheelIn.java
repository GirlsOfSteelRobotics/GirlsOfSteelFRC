/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * @author Sylvie and Heather
 */
public class CollectorDownAndWheelIn extends ParallelCommandGroup {
    public CollectorDownAndWheelIn(Collector collector) {
        addCommands(new CollectorWheelForward(collector));
        addCommands(new EngageCollector(collector));
    }
}
