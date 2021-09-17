/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author Sylvie
 */
public class Shoot10 extends CommandBase{

    public Shoot10(){
        requires(shooter);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        shooter.shootXVolts(10.0);
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
