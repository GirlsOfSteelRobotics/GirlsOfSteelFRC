package girlsofsteel.tests;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.CommandBase;

public class ChassisJags extends CommandBase {

    double speed;

    public ChassisJags(){
        requires(chassis);
    }

    protected void initialize() {
        SmartDashboard.putBoolean("Chassis Jags", true);
        SmartDashboard.putBoolean("Right Jag", false);
        SmartDashboard.putBoolean("Back Jag", false);
        SmartDashboard.putBoolean("Left Jag", false);
        SmartDashboard.putNumber("Jag Speed", 0.0);
    }

    protected void execute() {
        speed = SmartDashboard.getNumber("Jag Speed", 0.0);
        if(SmartDashboard.getBoolean("Right Jag", false)){
            chassis.setRightJag(speed);
        }else{
            chassis.setRightJag(0.0);
        }
        if(SmartDashboard.getBoolean("Back Jag", false)){
            chassis.setBackJag(speed);
        }else{
            chassis.setBackJag(0.0);
        }
        if(SmartDashboard.getBoolean("Left Jag", false)){
            chassis.setLeftJag(speed);
        }else{
            chassis.setLeftJag(0.0);
        }
        SmartDashboard.putNumber("Right Rate", chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Back Rate", chassis.getBackEncoderRate());
        SmartDashboard.putNumber("Left Rate", chassis.getLeftEncoderRate());
    }

    protected boolean isFinished() {
        return false;
        //Eve:  return !SmartDashboard.getBoolean("Chassis Jags", true);
    }

    protected void end() {
        chassis.stopJags();
    }

    protected void interrupted() {
        end();
    }

}
