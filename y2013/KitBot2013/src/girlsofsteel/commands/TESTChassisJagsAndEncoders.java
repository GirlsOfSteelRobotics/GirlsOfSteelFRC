package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTChassisJagsAndEncoders extends CommandBase {
    
    double speed;
    
    public TESTChassisJagsAndEncoders(){
        requires(chassisPID);
        SmartDashboard.putBoolean("Right Jags", false);
        SmartDashboard.putBoolean("Left Jags", false);
        SmartDashboard.putDouble("Jag speed", 0.0);
        SmartDashboard.putDouble("Right Encoder:", chassisPID.getRightEncoderDistance());
        SmartDashboard.putDouble("Left Encoder", chassisPID.getLeftEncoderDistance());
    }
    
    protected void initialize() {
        chassisPID.initEncoders();
    }

    protected void execute() {
        speed = SmartDashboard.getDouble("Jag speed", 0.0);
        SmartDashboard.putDouble("Right Encoder", chassisPID.getRightEncoderDistance());
        SmartDashboard.putDouble("Left Encoder", chassisPID.getLeftEncoderDistance());
        if(SmartDashboard.getBoolean("Right Jags", false)){
            chassisPID.setRightJags(speed);
        }
        if(SmartDashboard.getBoolean("Left Jags", false)){
            chassisPID.setLeftJags(speed);
        }
        System.out.println("R:" + chassisPID.getRightEncoderDistance());
        System.out.println("L:" + chassisPID.getLeftEncoderDistance());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassisPID.stopJags();
        chassisPID.endEncoders();
    }

    protected void interrupted() {
        end();
    }
    
}