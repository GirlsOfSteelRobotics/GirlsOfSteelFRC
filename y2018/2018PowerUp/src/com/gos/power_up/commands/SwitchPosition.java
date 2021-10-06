package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchPosition extends CommandGroup {

    public SwitchPosition(Lift lift, Wrist wrist) {
        System.out.println("SwitchPosition");
        addSequential(new LiftToSwitch(lift));
        addSequential(new WristToCollect(wrist));
    }
}
