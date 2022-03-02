package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class CollectPosition extends SequentialCommandGroup {

    public CollectPosition(Lift lift, Wrist wrist) {
        System.out.println("CollectPosition");
        addCommands(new LiftToGround(lift));
        addCommands(new WristToCollect(wrist));

    }
}
