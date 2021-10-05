package com.gos.power_up.commands.autonomous;

import com.gos.power_up.GameData;
import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPrintData extends CommandGroup {

    public AutoPrintData() {

        if (Robot.m_gameData.getSwitchSide() == GameData.FieldSide.left) {
            System.out.println("Switch is on the left");
        } else if (Robot.m_gameData.getSwitchSide() == GameData.FieldSide.right) {
            System.out.println("Switch is on the right");
        } else if (Robot.m_gameData.getSwitchSide() == GameData.FieldSide.bad) {
            System.out.println("Switch is BAD");
        }

        if (Robot.m_gameData.getScaleSide() == GameData.FieldSide.left) {
            System.out.println("Scale is on the left");
        } else if (Robot.m_gameData.getScaleSide() == GameData.FieldSide.right) {
            System.out.println("Scale is on the right");
        } else if (Robot.m_gameData.getScaleSide() == GameData.FieldSide.bad) {
            System.out.println("Scale is BAD");
        }
    }
}
