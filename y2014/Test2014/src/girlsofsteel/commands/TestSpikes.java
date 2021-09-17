/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author Heather
 */
public class TestSpikes extends CommandBase {

    public TestSpikes() {
        requires(chassis);

        SmartDashboard.putBoolean("Spike 1 Foward", false);
        SmartDashboard.putBoolean("Spike 2 Foward", false);

        SmartDashboard.putBoolean("Spike 1 Reverse", false);
        SmartDashboard.putBoolean("Spike 2 Reverse", false);
    }

    protected void initialize() {

    }

    protected void execute() {
        if (SmartDashboard.getBoolean("Spike 1 Foward")) {
            chassis.forwardSpikeOne();
        }
        if (SmartDashboard.getBoolean("Spike 2 Foward")) {
            chassis.forwardSpikeTwo();
        }
        if (SmartDashboard.getBoolean("Spike 1 Reverse")) {
            chassis.reverseSpikeOne();
        }
        if (SmartDashboard.getBoolean("Spike 2 Reverse")) {
            chassis.reverseSpikeTwo();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {

    }

}
