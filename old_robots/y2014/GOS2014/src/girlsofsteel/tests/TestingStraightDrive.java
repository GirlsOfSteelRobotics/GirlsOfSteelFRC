/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

/**
 *
 * @author sophia, sonia, and abby
 */
public class TestingStraightDrive extends CommandBase{

    public TestingStraightDrive(){
         requires(chassis);
    }

    protected void initialize() {
        System.out.println("Initializing TDS command.");
        chassis.initEncoders();
    }

    protected void execute() {
        //System.out.print("Left Encoder: " + chassis.getLeftEncoder());
        //System.out.println(" Right Encoder: " + chassis.getRightEncoder());
        SmartDashboard.putNumber("Left Encoder Distance: ", chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("right encoder distance: ", chassis.getRightEncoderDistance());
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
