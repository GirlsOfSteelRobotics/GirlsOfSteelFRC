/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.girlsofsteelrobotics.atlas.RobotMap;
import com.girlsofsteelrobotics.atlas.commands.CommandBase;

/**
 *
 * @author sophia
 */
public class TestManipulator extends CommandBase{

    private double speed;
    public TestManipulator(){
        requires(manipulator);
    }

    @Override
    protected void initialize() {
        SmartDashboard.putBoolean(RobotMap.manipulatorSD, true);
    }

    @Override
    protected void execute() {
        speed = SmartDashboard.getNumber(RobotMap.manipulatorSD, 0.0);
        if(SmartDashboard.getBoolean(RobotMap.manipulatorSD, false)){
            manipulator.setJag(speed);
        }else{
            manipulator.setJag(0.0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false; //TODO: im not sure what this should be
    }

    @Override
    protected void end() {
        manipulator.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }


}
