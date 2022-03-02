/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.subsystems.Collector;

/**
 * @author Sylvie and Jisue
 */
public class CollectorDownWheelOut extends SequentialCommandGroup {

    public CollectorDownWheelOut(Collector collector) {
        addParallel(new EngageCollector(collector));
        addParallel(new CollectorWheelReverse(collector));
    }
}
