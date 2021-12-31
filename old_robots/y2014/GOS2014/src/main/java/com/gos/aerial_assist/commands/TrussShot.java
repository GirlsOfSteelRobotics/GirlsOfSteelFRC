/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Kicker;
import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Sylvie
 */
public class TrussShot extends CommandGroup {

    public TrussShot(Manipulator manipulator, Collector collector, Kicker kicker) {
        addSequential(new SetArmAnglePID(manipulator, 50)); //angle of 50 was experimentally determined
        //Robot must be between red-white zone tape and about a meter towards the truss
        addParallel(new DisengageCollector(collector));
        addSequential(new KickerUsingLimitSwitch(kicker, 0, false));
        addSequential(new KickerUsingLimitSwitch(kicker, 1, false));
        addSequential(new StopCollector(collector)); //never 'stops', must be interuppted
    }
}
