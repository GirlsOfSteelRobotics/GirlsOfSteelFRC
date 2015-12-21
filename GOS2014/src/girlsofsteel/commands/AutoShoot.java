/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author
 */
public class AutoShoot extends CommandGroup {

    private double angle = CommandBase.camera.getVerticalAngleOffset();
    private boolean shoot = false;
    private double error = 1; //magic

    public AutoShoot() {
        addSequential(new setArmAnglePID(angle));
        if(CommandBase.manipulator.getAbsoluteDistance() < angle + error){
        }
    }
}