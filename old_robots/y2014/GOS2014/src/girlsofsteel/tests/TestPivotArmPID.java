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
public class TestPivotArmPID extends CommandBase{
     private double desiredAngle;
    private double currentAngle;
    private final double allowedAngleError;

    public TestPivotArmPID() {
        requires(manipulator);
        allowedAngleError = 2; //2 degrees of error on both sides allowed
    }

    @Override
    protected void initialize() {
       // manipulator.initEncoder();
       // manipulator.resetPIDError();
       // manipulator.startPID();
       // SmartDashboard.putNumber("Pivot Angle", 0.0);
        SmartDashboard.putNumber("Pivot Arm Encoder Value", 0.0);
    }

    @Override
    protected void execute() {
        //desiredAngle = SmartDashboard.getNumber("Pivot Angle", 0);
        //manipulator.setSetPoint(desiredAngle);
        currentAngle = manipulator.getAbsoluteDistance();
        SmartDashboard.putNumber("Pivot Arm Encoder Value", currentAngle);
    }

    @Override
    protected boolean isFinished() {
        return false;//Math.abs(desiredAngle-currentAngle) < allowedAngleError;
    }

    @Override
    protected void end() {
        manipulator.stopManipulator();
        manipulator.disablePID();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
