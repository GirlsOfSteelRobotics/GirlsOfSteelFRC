package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CollectPosition extends CommandGroup {

    public CollectPosition(Lift lift, Wrist wrist) {
        System.out.println("CollectPosition");
        addSequential(new LiftToGround(lift));
        addSequential(new WristToCollect(wrist));

    }
}
