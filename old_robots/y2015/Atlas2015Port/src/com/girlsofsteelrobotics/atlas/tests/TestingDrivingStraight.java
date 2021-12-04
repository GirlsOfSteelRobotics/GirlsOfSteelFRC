/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

2nd chassis drives straight without any adjustments
*/
package com.girlsofsteelrobotics.atlas.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.girlsofsteelrobotics.atlas.commands.CommandBase;

/**
 *
 * @author Blank...
 * This needs to be tested by the end of 2/13/14
 */
public class TestingDrivingStraight extends CommandBase{


    public TestingDrivingStraight(){
         requires(chassis);
    }

    @Override
    protected void initialize() {
        System.out.println("Initializing TDS command.");
        SmartDashboard.putNumber("LeftJagAdjust", 0); //Setting LeftJagAdjust  variable to zero in SmartDashboard
        SmartDashboard.putNumber("RightJagAdjust", 0); //Same as line above
        chassis.initEncoders();
    }

    @Override
    protected void execute() {
        double RightJagAdjust = SmartDashboard.getNumber("RightJagAdjust", 0);
        double LeftJagAdjust = SmartDashboard.getNumber("LeftJagAdjust", 0);
        chassis.setLeftJag(.5 * LeftJagAdjust);
        chassis.setRightJag(.5 * RightJagAdjust);
        SmartDashboard.putNumber("ActualSpeedLeft",chassis.getRateLeftEncoder());
        SmartDashboard.putNumber("AcutalSpeedRight",chassis.getRateRightEncoder());
        SmartDashboard.putNumber("Left side Voltage", chassis.getLeftRaw());
        SmartDashboard.putNumber("Right side Voltage", chassis.getRightRaw());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.setRightJag(0.0);
        chassis.setLeftJag(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }

}
