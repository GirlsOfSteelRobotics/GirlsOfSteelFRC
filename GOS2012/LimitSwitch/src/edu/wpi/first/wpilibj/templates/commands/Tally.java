/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.subsystems.Collector;

/**
 *
 * @author Maureen
 */
public class Tally extends CommandBase{

    protected void initialize() {
    }
    //use the 1st switch to add to tally, second to reverse rollers, and 4th to subtract from tally.

    protected void execute() {
        collector.tallyBalls();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
