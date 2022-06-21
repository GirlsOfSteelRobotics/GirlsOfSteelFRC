/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.commands.CommandBase;
import com.gos.aerial_assist.subsystems.Chassis;

/**
 * @author sophia, sonia, and abby
 */
public class TestingStraightDrive extends CommandBase {

    private final Chassis m_chassis;

    public TestingStraightDrive(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        System.out.println("Initializing TDS command.");
        m_chassis.initEncoders();
    }

    @Override
    public void execute() {
        //System.out.print("Left Encoder: " + chassis.getLeftEncoder());
        //System.out.println(" Right Encoder: " + chassis.getRightEncoder());
        SmartDashboard.putNumber("Left Encoder Distance: ", m_chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("right encoder distance: ", m_chassis.getRightEncoderDistance());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
