/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Sylvie
 */
public class Driving extends Subsystem{

    /*
    This subsystem is supposed to be completely empty (minus the initDefaultCommand()).
    The reason this subsystem is here is because of the requires capability.
    The reasoning is as follows:
    Using "requires" in a command means that only that command can use the
    subsystem that it requires, aka no other function can use that command.
    So the drive commands cannot be linked to the chassis since they need to
    be going for the full duration of the match and we might at other times
    need to use chassis methods. This is why a separate subsystem has been
    made for drive commands. DO NOT REMOVE/DELETE THIS SUBSYSTEM.
    */

    protected void initDefaultCommand() {
        }

}
