package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 *
 */
public class SwitchPosition extends SequentialCommandGroup {

    public SwitchPosition(Lift lift, Wrist wrist) {
        System.out.println("SwitchPosition");
        addCommands(new LiftToSwitch(lift));
        addCommands(new WristToCollect(wrist));
    }
}
