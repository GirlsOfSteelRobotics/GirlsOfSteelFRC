/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Sylvie
 */
public class Chassis extends Subsystem{

    Talon leftTalon;
    //Talon leftTalon2;
    Jaguar leftJag2;
    Talon rightTalon2;
    Talon rightTalon;
    
    public Chassis() {
        leftTalon = new Talon(4); //assuming the left and right sides are
        leftJag2 = new Jaguar(6);
        //leftTalon2 = new Talon(2); //y-cabled 
        //rightTalon = new Talon(3); //chassis 1
        rightTalon = new Talon(3); //for chassis 2
        //rightTalon2 = new Talon(4); //chassis 1
        
        stopTalons();
    }
    
    public double simpleDeadZone(double value, double deadzone) {
        if(Math.abs(value) < deadzone)
            return 0.0;
        return value;
    }
    
    public void arcadeDrive(double x, double y) {
        double dead = 0.2;
        double deadX = simpleDeadZone(x, dead);
        double deadY = simpleDeadZone(y, dead);
        
        System.out.println("Setting the left talon to: " + (deadY-deadX) + "\tSetting the right talon to: " + (deadY + deadX));
        
        
    //leftTalon.set(deadY-deadX);
        setLeftSide(deadY-deadX);
        rightTalon.set(-(deadY+deadX)); //for chassis 2
        
        //With four individual talons
//        setRightSide(deadY+deadX); //for chassis #1
//        
//        setLeftSide(deadY-deadX);
        
        //rightTalon.set(0.1);
        //stopTalons();
        
        System.out.println("Left Talon Value: " + leftTalon.get() + "\tRight Talon Value: " + rightTalon.get());
    }
    
    public void stopTalons() {
        leftTalon.set(0.0);
        //leftTalon2.set(0.0);
        //rightTalon2.set(0.0);
        rightTalon.set(0.0);
    }
    
    public void setRightSide(double speed) {
        rightTalon.set(speed);
        rightTalon2.set(speed);
    }
    
    public void setLeftSide(double speed) {
        leftTalon.set(speed);
        //leftTalon2.set(speed);
        leftJag2.set(speed);
    }
    
    protected void initDefaultCommand() {
    }
}
