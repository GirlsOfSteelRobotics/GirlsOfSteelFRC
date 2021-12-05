package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivePositionChassisTuning extends CommandBase{

    public DrivePositionChassisTuning(){
        requires(chassis);
        SmartDashboard.putNumber("Right P", 0.0);
        SmartDashboard.putNumber("Right D", 0.0);
        SmartDashboard.putNumber("Left P",0.0);
        SmartDashboard.putNumber("Left D",0.0);
        SmartDashboard.putNumber("DPST,setpoint", 0.0);
        SmartDashboard.putNumber("Right Encoder Position", 0.0);
        SmartDashboard.putNumber("Left Encoder Position", 0.0);
    }

    @Override
    protected void initialize() {
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        chassis.setPositionPIDValues(SmartDashboard.getNumber("Right P", 0.0),
                SmartDashboard.getNumber("Right D", 0.0),
                SmartDashboard.getNumber("Left P", 0.0),
                SmartDashboard.getNumber("Left D",0.0));
        chassis.setPositionPIDSetPoint(SmartDashboard.getNumber("DPST,setpoint", 0.0));
        SmartDashboard.putNumber("Right Encoder Position", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder Position", chassis.getLeftEncoderDistance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
