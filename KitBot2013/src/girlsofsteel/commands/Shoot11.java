/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author Sylvie
 */
public class Shoot11 extends CommandBase{

    public Shoot11(){
        requires(shooter);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        shooter.shootXVolts(11.0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.disableJag();
    }

    protected void interrupted() {
        end();
    }
    
}
