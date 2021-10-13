/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.objects.GoSPIDController;

/**
 *
 * @author Maureen
 */
public class PositionControl extends CommandBase{
    
    double rightP = 0;
    double rightI = 0;
    double rightD = 0;
    private GoSPIDController rightPID = new GoSPIDController(rightP, rightI, rightD,chassis.rightEncoder, 
            //this is an anonymous class, it lets us send values to both jags
            //new output parameter
            new PIDOutput() {
                public void pidWrite(double output) {
                    chassis.setRight(output);
                }
            }, GoSPIDController.POSITION);
    double leftP = 0;
    double leftI = 0;
    double leftD = 0;
    private GoSPIDController leftPID = new GoSPIDController(leftP, leftI, leftD,chassis.rightEncoder, 
            //this is an anonymous class, it lets us send values to both jags
            //new output parameter
            new PIDOutput() {
                public void pidWrite(double output) {
                    chassis.setLeft(output);
                }
            }, GoSPIDController.POSITION);

    protected void initialize() {
        rightPID.setPID(SmartDashboard.getDouble("P1", 1), SmartDashboard.getDouble("I1", 0), SmartDashboard.getDouble("D1", 0));
        rightPID.enable();
        rightPID.setSetPoint(SmartDashboard.getDouble("Setpoint", 0));
        chassis.initRightEncoder(1.0/250.0);
        leftPID.setPID(SmartDashboard.getDouble("P1", 1), SmartDashboard.getDouble("I1", 0), SmartDashboard.getDouble("D1", 0));
        leftPID.enable();
        leftPID.setSetPoint(SmartDashboard.getDouble("Setpoint", 0));
        chassis.initRightEncoder(1.0/250.0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        rightPID.disable();
        leftPID.disable();
    }

    protected void interrupted() {
        end();
    }
    
}
