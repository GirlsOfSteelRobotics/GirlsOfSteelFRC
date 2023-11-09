/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

2nd chassis drives straight without any adjustments
*/

package com.gos.aerial_assist.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.commands.GosCommand;
import com.gos.aerial_assist.subsystems.Chassis;

/**
 * @author Blank...
 * This needs to be tested by the end of 2/13/14
 */
public class TestingDrivingStraight extends GosCommand {

    private final Chassis m_chassis;

    public TestingDrivingStraight(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        System.out.println("Initializing TDS command.");
        SmartDashboard.putNumber("LeftJagAdjust", 0); //Setting LeftJagAdjust  variable to zero in SmartDashboard
        SmartDashboard.putNumber("RightJagAdjust", 0); //Same as line above
        m_chassis.initEncoders();
    }

    @Override
    public void execute() {
        double rightJagAdjust = SmartDashboard.getNumber("RightJagAdjust", 0);
        double leftJagAdjust = SmartDashboard.getNumber("LeftJagAdjust", 0);
        m_chassis.setLeftJag(.5 * leftJagAdjust);
        m_chassis.setRightJag(.5 * rightJagAdjust);
        SmartDashboard.putNumber("ActualSpeedLeft", m_chassis.getRateLeftEncoder());
        SmartDashboard.putNumber("AcutalSpeedRight", m_chassis.getRateRightEncoder());
        SmartDashboard.putNumber("Left side Voltage", m_chassis.getLeftRaw());
        SmartDashboard.putNumber("Right side Voltage", m_chassis.getRightRaw());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setRightJag(0.0);
        m_chassis.setLeftJag(0.0);
    }



}
