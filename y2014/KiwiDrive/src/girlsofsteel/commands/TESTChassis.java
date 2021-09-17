package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTChassis extends CommandBase {

    public TESTChassis() {
        requires(chassis);
    }

    protected void initialize() {
        SmartDashboard.putBoolean("Right Jag", false);
        SmartDashboard.putBoolean("Back Jag", false);
        SmartDashboard.putBoolean("Left Jag", false);
        SmartDashboard.putNumber("Jag Speed", 0.0);
        SmartDashboard.putBoolean("Right PID", false);
        SmartDashboard.putBoolean("Back PID", false);
        SmartDashboard.putBoolean("Left PID", false);
        SmartDashboard.putNumber("P", 0.0);
        SmartDashboard.putNumber("I", 0.0);
        SmartDashboard.putNumber("D", 0.0);
        SmartDashboard.putNumber("PID Rate", 0.0);
        chassis.initEncoders();
        chassis.initRatePIDs();
    }

    protected void execute() {
        double jagSpeed = SmartDashboard.getNumber("Jag Speed", 0.0);
        double p = SmartDashboard.getNumber("P", 0.0);
        double i = SmartDashboard.getNumber("I", 0.0);
        double d = SmartDashboard.getNumber("D", 0.0);
        double pidRate = SmartDashboard.getNumber("PID Rate", 0.0);
        if (SmartDashboard.getBoolean("Right Jag", false) == true) {
            chassis.setRightJag(jagSpeed);
        } else if (SmartDashboard.getBoolean("Right PID", false) == true) {
            chassis.setRightPIDValues(p, i, d);
            chassis.setRightPIDRate(pidRate);
        }else{
            chassis.setRightJag(0.0);
            chassis.setRightPIDRate(0.0);
        }//end right
        if (SmartDashboard.getBoolean("Back Jag", false) == true) {
            chassis.setBackJag(jagSpeed);
        } else if (SmartDashboard.getBoolean("Back PID", false) == true) {
            chassis.setBackPIDValues(p, i, d);
            chassis.setBackPIDRate(pidRate);
        }else{
            chassis.setBackJag(0.0);
            chassis.setBackPIDRate(0.0);
        }//end back
        if (SmartDashboard.getBoolean("Left Jag", false) == true) {
            chassis.setLeftJag(jagSpeed);
        } else if (SmartDashboard.getBoolean("Left PID", false) == true) {
            chassis.setLeftPIDValues(p, i, d);
            chassis.setLeftPIDRate(pidRate);
        }else{
            chassis.setLeftJag(0.0);
            chassis.setLeftPIDRate(0.0);
        }//end left
        
        SmartDashboard.putNumber("Right Encoder Rate",
                chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Encoder Rate",
                chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Encoder Rate",
                chassis.getLeftEncoderRate());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopPIDs();
        chassis.stopEncoders();
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }
}