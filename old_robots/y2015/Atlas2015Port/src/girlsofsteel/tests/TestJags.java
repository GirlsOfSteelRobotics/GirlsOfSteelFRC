/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author user
 */
public class TestJags extends CommandBase {

    public TestJags() {
        requires(chassis);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        }

    @Override
    protected void execute() {
        chassis.setJags(1.0);
        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", chassis.getRightEncoder());
    }

    @Override
    protected boolean isFinished() {
        return false;
                }

    @Override
    protected void end() {
        chassis.setJags(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
