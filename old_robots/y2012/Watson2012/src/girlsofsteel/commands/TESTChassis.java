package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTChassis extends CommandBase {

    double speed;

    public TESTChassis(){
        requires(chassis);
        SmartDashboard.putBoolean("Right Jags", false);
        SmartDashboard.putBoolean("Left Jags", false);
        SmartDashboard.putNumber("Jag speed", 0.0);
        SmartDashboard.putNumber("Right Encoder:", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Left Scale", 1.0);
    }

    protected void initialize() {
        chassis.initEncoders();
    }

    protected void execute() {
        speed = SmartDashboard.getNumber("Jag speed", 0.0);
        SmartDashboard.putNumber("Right Encoder:", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder", chassis.getLeftEncoderDistance());
        if(SmartDashboard.getBoolean("Right Jags", false)){
            chassis.setRightJags(speed);
        }
        if(SmartDashboard.getBoolean("Left Jags", false)){
            double leftScale = SmartDashboard.getNumber("Left Scale", 1.0);
            chassis.setLeftJags(speed*leftScale);
        }
        System.out.println("R:" + chassis.getRightEncoderDistance());
        System.out.println("L:" + chassis.getLeftEncoderDistance());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }

}
