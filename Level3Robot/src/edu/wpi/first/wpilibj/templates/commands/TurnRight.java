/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author sunhalee
 */
public class TurnRight extends CommandBase{

   public TurnRight()
    {
        requires(chassis);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        chassis.setJags(0,.5);
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
