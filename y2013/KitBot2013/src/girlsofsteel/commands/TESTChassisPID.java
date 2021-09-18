package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TESTChassisPID extends CommandBase {
    
    double rate;
    
    double rightP;
    double rightI;
    double rightD;
    double leftP;
    double leftI;
    double leftD;
    
    public TESTChassisPID(){
        requires(chassisPID);
        SmartDashboard.putBoolean("Right Velocity PID", false);
        SmartDashboard.putBoolean("Left Velocity PID", false);
        SmartDashboard.putDouble("PID rate", 0.0);
        SmartDashboard.putDouble("Right P", 0.0);
        SmartDashboard.putDouble("Right I", 0.0);
        SmartDashboard.putDouble("Right D", 0.0);
        SmartDashboard.putDouble("Left P", 0.0);
        SmartDashboard.putDouble("Left I", 0.0);
        SmartDashboard.putDouble("Left D", 0.0);
        SmartDashboard.putDouble("Right Encoder",
                chassisPID.getRightEncoderDistance());
        SmartDashboard.putDouble("Left Encoder",
                chassisPID.getLeftEncoderDistance());        
    }
    
    protected void initialize() {
        chassisPID.initEncoders();
        chassisPID.initPID();
    }

    protected void execute() {
        rightP = SmartDashboard.getDouble("Right P", 0.0);
        rightI = SmartDashboard.getDouble("Right I", 0.0);
        rightD = SmartDashboard.getDouble("Right D", 0.0);
        leftP = SmartDashboard.getDouble("Left P", 0.0);
        leftI = SmartDashboard.getDouble("Left I", 0.0);
        leftD = SmartDashboard.getDouble("Left D", 0.0);
        chassisPID.setRightPIDValues(rightP, rightI, rightD);
        chassisPID.setLeftPIDValues(leftP, leftI, leftD);
        rate = SmartDashboard.getDouble("PID speed", 0.0);
        if(SmartDashboard.getBoolean("Right Jags", false))
            chassisPID.setRightPIDRate(rate);
        if(SmartDashboard.getBoolean("Left PID", false))
            chassisPID.setLeftPIDRate(rate);
        SmartDashboard.putDouble("Right Encoder",
                chassisPID.getRightEncoderDistance());
        SmartDashboard.putDouble("Left Encoder", 
                chassisPID.getRightEncoderDistance());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
    
}
