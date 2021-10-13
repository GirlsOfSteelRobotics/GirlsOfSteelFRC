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
public class TestPneumatics extends CommandBase {

    public TestPneumatics() {
        requires(manipulator);

        SmartDashboard.putBoolean("Extend piston 1", false);
        SmartDashboard.putBoolean("Extend piston 2", false);

        SmartDashboard.putBoolean("Retract piston 1", false);
        SmartDashboard.putBoolean("Retract piston 2", false);
    }

    protected void initialize() {

    }

    protected void execute() {

        if (SmartDashboard.getBoolean("Extend piston 1")) {
            manipulator.extendPistonOne();
        }
        if (SmartDashboard.getBoolean("Extend piston 2")) {
            manipulator.extendPistonTwo();
        }
        if (SmartDashboard.getBoolean("Retract piston 1")) {
            manipulator.retractPistonOne();
        }
        if (SmartDashboard.getBoolean("Retract piston 2")) {
            manipulator.retractPistonTwo();
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
