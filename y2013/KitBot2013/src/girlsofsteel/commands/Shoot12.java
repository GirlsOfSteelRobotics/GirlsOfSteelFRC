/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author Sylvie
 */
public class Shoot12 extends CommandBase {

    public Shoot12() {
        requires(shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.shootXVolts(12.0);
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
