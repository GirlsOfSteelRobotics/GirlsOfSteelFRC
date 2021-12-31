package com.gos.ultimate_ascent.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Chassis extends CommandGroup {

    public Chassis(com.gos.ultimate_ascent.subsystems.Chassis chassis) {
        addSequential(new ChassisJags(chassis));
        //adds chassis PID as soon as the user says testing jags is done
        addSequential(new ChassisPID(chassis));
    }
}
