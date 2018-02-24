package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPrintData extends CommandGroup {

    public AutoPrintData() {
    	
    	if (Robot.getSwitchSide() == Robot.FieldSide.left) System.out.println("Switch is on the left");
    	else if (Robot.getSwitchSide() == Robot.FieldSide.right) System.out.println("Switch is on the right");
    	else if (Robot.getSwitchSide() == Robot.FieldSide.bad) System.out.println("Switch is BAD");
    	
    	if (Robot.getScaleSide() == Robot.FieldSide.left) System.out.println("Scale is on the left");
    	else if (Robot.getScaleSide() == Robot.FieldSide.right) System.out.println("Scale is on the right");
    	else if (Robot.getScaleSide() == Robot.FieldSide.bad) System.out.println("Scale is BAD");
    }
}
