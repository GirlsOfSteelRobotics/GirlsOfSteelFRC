package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

public class ChassisPID extends CommandBase {
    
    double rate;
    
    double rightP;
    double rightI;
    double rightD;
    double backP;
    double backI;
    double backD;
    double leftP;
    double leftI;
    double leftD;
    
    public ChassisPID(){
        requires(chassis);
    }
    
    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
        SmartDashboard.putNumber("PID rate", 0.0);
        SmartDashboard.putBoolean("Right PID", false);
        SmartDashboard.putBoolean("Back PID", false);
        SmartDashboard.putBoolean("Left PID", false);
        SmartDashboard.putBoolean("Click When Done Testing Chassis PID", false);
        SmartDashboard.putNumber("Right P", 0.0);
        SmartDashboard.putNumber("Right I", 0.0);
        SmartDashboard.putNumber("Right D", 0.0);
        SmartDashboard.putNumber("Back P", 0.0);
        SmartDashboard.putNumber("Back I", 0.0);
        SmartDashboard.putNumber("Back D", 0.0);
        SmartDashboard.putNumber("Left P", 0.0);
        SmartDashboard.putNumber("Left I", 0.0);
        SmartDashboard.putNumber("Left D", 0.0);
        SmartDashboard.putNumber("Right Encoder", chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder", chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderRate());
    }

    protected void execute() {
        //get rate
        rate = SmartDashboard.getNumber("PID rate", 0.0);
        //get P, I, & D's
        //SmartDashboard.getBoolean("Click When Done Testing Chassis PID", false);
        rightP = SmartDashboard.getNumber("Right P", 0.0);
        rightI = SmartDashboard.getNumber("Right I", 0.0);
        rightD = SmartDashboard.getNumber("Right D", 0.0);
        backP = SmartDashboard.getNumber("Back P", 0.0);
        backI = SmartDashboard.getNumber("Back I", 0.0);
        backD = SmartDashboard.getNumber("Back D", 0.0);
        leftP = SmartDashboard.getNumber("Left P", 0.0);
        leftI = SmartDashboard.getNumber("Left I", 0.0);
        leftD = SmartDashboard.getNumber("Left D", 0.0);
        //set P, I, D values
        chassis.setRightPIDRateValues(rightP, rightI, rightD);
        System.out.println("Right P: " + rightP + " I:" + rightI + " D: " + rightD);
        chassis.setBackPIDRateValues(backP, backI, backD);
        chassis.setLeftPIDRateValues(leftP, leftI, leftD);
        //get rate
        rate = SmartDashboard.getNumber("PID rate", 0.0);
        //set the rate if enabled
        //right setting
        if(SmartDashboard.getBoolean("Right PID", false)){
            chassis.setRightPIDRate(rate);
        }else{
            chassis.setRightPIDRate(0.0);
        }
        //back setting
        if(SmartDashboard.getBoolean("Back PID", false)){
            chassis.setBackPIDRate(rate);
        }else{
            chassis.setBackPIDRate(0.0);
        }
        //left setting
        if(SmartDashboard.getBoolean("Left ", false)){
            chassis.setLeftPIDRate(rate);
        }else{
            chassis.setLeftPIDRate(0.0);
        }
        //print encoder rates
        SmartDashboard.putNumber("Right Encoder",
                chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder",
                chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder", 
                chassis.getLeftEncoderRate());
    }

    protected boolean isFinished() {
        return false; //from KiwiDrive
        //Eve:  return !SmartDashboard.getBoolean("Click When Done Testing Chassis PID", true);
    }

    protected void end() {
        chassis.stopRatePIDs();
        chassis.stopEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
    
}