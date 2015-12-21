package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTChassisPID extends CommandBase {
    
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
    
    public TESTChassisPID(){
        requires(chassis);
        SmartDashboard.putBoolean("Right Velocity PID", false);
        SmartDashboard.putBoolean("Back Velocity PID", false);
        SmartDashboard.putBoolean("Left Velocity PID", false);
        SmartDashboard.putNumber("PID rate", 0.0);
        SmartDashboard.putNumber("Right P", 0.0);
        SmartDashboard.putNumber("Right I", 0.0);
        SmartDashboard.putNumber("Right D", 0.0);
        SmartDashboard.putNumber("Back P", 0.0);
        SmartDashboard.putNumber("Back I", 0.0);
        SmartDashboard.putNumber("Back D", 0.0);
        SmartDashboard.putNumber("Left P", 0.0);
        SmartDashboard.putNumber("Left I", 0.0);
        SmartDashboard.putNumber("Left D", 0.0);
        SmartDashboard.putNumber("Right Encoder",
                chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder",
                chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder",
                chassis.getLeftEncoderRate()); 
    }
    
    protected void initialize() {
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    protected void execute() {
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
        chassis.setRightPIDValues(rightP, rightI, rightD);
        System.out.println("Right P: " + rightP + " I: " + rightI + " D: " + rightD);
        chassis.setBackPIDValues(backP, backI, backD);
        chassis.setLeftPIDValues(leftP, leftI, leftD);
        //get rate
        rate = SmartDashboard.getNumber("PID rate", 0.0);
        //set the rate if enabled
        if(SmartDashboard.getBoolean("Right Velocity PID", false)){
            chassis.setRightPIDRate(rate);
            System.out.println("Set Right PID to " + rate);
        }
        if(SmartDashboard.getBoolean("Back Velocity PID", false)){
            chassis.setBackPIDRate(rate);
        }
        if(SmartDashboard.getBoolean("Left Velocity PID", false)){
            chassis.setLeftPIDRate(rate);
        }
        //print encoder rates
        SmartDashboard.putNumber("Right Encoder",
                chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder",
                chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder", 
                chassis.getLeftEncoderRate());
        
        /*
         * right: P=0.05 I=0.0005
         * back: P=? I=0.00001?
         * left: P=? I=0.0001 (0.00005?)
         */
        
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopPIDs();
        chassis.stopEncoders();
    }

    protected void interrupted() {
        end();
    }
    
}
