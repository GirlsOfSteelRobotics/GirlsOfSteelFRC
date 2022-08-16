package com.gos.ultimate_ascent.tests;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Chassis extends SequentialCommandGroup {

    public Chassis(com.gos.ultimate_ascent.subsystems.Chassis chassis) {
        addCommands(new ChassisJags(chassis));
        //adds chassis PID as soon as the user says testing jags is done
        addCommands(new ChassisPID(chassis));
    }
}
