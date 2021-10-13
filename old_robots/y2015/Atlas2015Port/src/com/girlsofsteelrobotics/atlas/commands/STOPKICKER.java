/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 *
 * @author Sylvie
 * 
 * Stops the kicker wherever it is (does not stop the PID)
 */
public class STOPKICKER extends CommandBase {

    public STOPKICKER() {
        requires(kicker);
    }
    
    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false; //this never ends.
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
