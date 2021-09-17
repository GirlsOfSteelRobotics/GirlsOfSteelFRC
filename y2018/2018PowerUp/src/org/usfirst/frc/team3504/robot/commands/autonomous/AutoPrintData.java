package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPrintData extends CommandGroup {

    public AutoPrintData() {
    	
    	if (Robot.gameData.getSwitchSide() == FieldSide.left) System.out.println("Switch is on the left");
    	else if (Robot.gameData.getSwitchSide() == FieldSide.right) System.out.println("Switch is on the right");
    	else if (Robot.gameData.getSwitchSide() == FieldSide.bad) System.out.println("Switch is BAD");
    	
    	if (Robot.gameData.getScaleSide() == FieldSide.left) System.out.println("Scale is on the left");
    	else if (Robot.gameData.getScaleSide() == FieldSide.right) System.out.println("Scale is on the right");
    	else if (Robot.gameData.getScaleSide() == FieldSide.bad) System.out.println("Scale is BAD");
    }
}
