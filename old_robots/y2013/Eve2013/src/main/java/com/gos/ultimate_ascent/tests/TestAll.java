/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.tests;


import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Gripper;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author kateashwood
 */
public class TestAll extends CommandGroup {

    // Called just before this Command runs the first time
    public TestAll(Feeder feeder, com.gos.ultimate_ascent.subsystems.Chassis chassis, com.gos.ultimate_ascent.subsystems.Shooter shooter, com.gos.ultimate_ascent.subsystems.Climber climber, Gripper gripper) {
        //add all test commands to smartdashboard
        SmartDashboard.putData(new Chassis(chassis));
        SmartDashboard.putData(new Shooter(feeder, shooter));
        SmartDashboard.putData(new Climber(climber, gripper));
        //NOT WORKING FIX****************************************************************

    }

}
