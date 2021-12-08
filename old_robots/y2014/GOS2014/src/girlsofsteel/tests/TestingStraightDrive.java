/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.subsystems.Chassis;

/**
 *
 * @author sophia, sonia, and abby
 */
public class TestingStraightDrive extends CommandBase{

    private final Chassis m_chassis;

    public TestingStraightDrive(Chassis chassis){
        m_chassis = chassis;
         requires(m_chassis);
    }

    @Override
    protected void initialize() {
        System.out.println("Initializing TDS command.");
        m_chassis.initEncoders();
    }

    @Override
    protected void execute() {
        //System.out.print("Left Encoder: " + chassis.getLeftEncoder());
        //System.out.println(" Right Encoder: " + chassis.getRightEncoder());
        SmartDashboard.putNumber("Left Encoder Distance: ", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("right encoder distance: ", m_chassis.getRightEncoderDistance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
