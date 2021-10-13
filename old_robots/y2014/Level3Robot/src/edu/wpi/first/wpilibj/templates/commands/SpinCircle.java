/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author sunhalee
 */
public class SpinCircle extends CommandBase{

    public SpinCircle()
    {
        requires(chassis);
       
    }
    protected void initialize() {
    }

    protected void execute() {
        chassis.setJags(.5, -.5);
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
