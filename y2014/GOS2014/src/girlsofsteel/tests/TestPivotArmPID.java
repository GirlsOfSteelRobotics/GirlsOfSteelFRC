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
    private double allowedAngleError;
    
    public TestPivotArmPID() {
        requires(manipulator);
        allowedAngleError = 2; //2 degrees of error on both sides allowed
    }

    protected void initialize() {
       // manipulator.initEncoder();
       // manipulator.resetPIDError();
       // manipulator.startPID();
       // SmartDashboard.putNumber("Pivot Angle", 0.0);
        SmartDashboard.putNumber("Pivot Arm Encoder Value", 0.0);
    }

    protected void execute() {
        //desiredAngle = SmartDashboard.getNumber("Pivot Angle");
        //manipulator.setSetPoint(desiredAngle);
        currentAngle = manipulator.getAbsoluteDistance();
        SmartDashboard.putNumber("Pivot Arm Encoder Value", currentAngle);
    }

    protected boolean isFinished() {
        return false;//Math.abs(desiredAngle-currentAngle) < allowedAngleError;
    }

    protected void end() {
        manipulator.stopManipulator();
        manipulator.stopEncoder();
        manipulator.disablePID();
    }

    protected void interrupted() {
        end();
    }

}
