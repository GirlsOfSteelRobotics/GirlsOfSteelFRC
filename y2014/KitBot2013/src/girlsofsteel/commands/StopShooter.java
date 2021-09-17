/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author Sylvie
 */
public class StopShooter extends CommandBase{

    public StopShooter(){
        requires(shooter);
    }
    
    protected void initialize() {
    }

    protected void execute() {
        shooter.disableJag();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
    
}
