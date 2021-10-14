package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivePositionChassisTuning extends CommandBase{

    public DrivePositionChassisTuning(){
        requires(chassis);
        SmartDashboard.putDouble("Right P", 0.0);
        SmartDashboard.putDouble("Right D", 0.0);
        SmartDashboard.putDouble("Left P",0.0);
        SmartDashboard.putDouble("Left D",0.0);
        SmartDashboard.putDouble("DPST,setpoint", 0.0);
        SmartDashboard.putDouble("Right Encoder Position", 0.0);
        SmartDashboard.putDouble("Left Encoder Position", 0.0);
    }

    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    protected void execute() {
        chassis.setPositionPIDValues(SmartDashboard.getDouble("Right P", 0.0),
                SmartDashboard.getDouble("Right D", 0.0),
                SmartDashboard.getDouble("Left P", 0.0),
                SmartDashboard.getDouble("Left D",0.0));
        chassis.setPositionPIDSetPoint(SmartDashboard.getDouble("DPST,setpoint", 0.0));
        SmartDashboard.putDouble("Right Encoder Position", chassis.getRightEncoderDistance());
        SmartDashboard.putDouble("Left Encoder Position", chassis.getLeftEncoderDistance());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    protected void interrupted() {
        end();
    }

}
