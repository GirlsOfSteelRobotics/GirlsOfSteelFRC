/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Sylvie
 */
public class ArcadeDrive extends CommandBase {

    Joystick chassisJoy;
    
    protected void initialize() {
        chassisJoy = oi.getChassisJoy();
    }

    protected void execute() {
        System.out.println("in arcade drive");
        chassis.arcadeDrive(chassisJoy.getX(), chassisJoy.getY());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopTalons();
    }

    protected void interrupted() {
        end();
    }

}
