/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author Mackenzie
 */
public class ArcadeDrive extends CommandBase{
    
    Joystick joystick1; //randomly picked right joystick from robot map
    
    double Xcoord;
    double Ycoord;
    double XcoordSq;
    double YcoordSq;
    
    public ArcadeDrive() {
        requires(driving);
    }

    protected void initialize() {
        System.out.println("ARCADE DRIVE______________________________________________________");
        joystick1= oi.getChassisJoystick();
    }

    protected void execute() {
        Xcoord = joystick1.getX();
        //XcoordSq = chassis.square(Xcoord, 1.0);
        Ycoord = joystick1.getY();
        //YcoordSq = chassis.square(Ycoord, 1.0);
        chassis.arcadeDrive(-Xcoord, -Ycoord);
        //System.out.println("");
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
     
}
