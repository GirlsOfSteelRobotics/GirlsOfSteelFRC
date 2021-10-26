package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTMoveToSetPoint extends CommandBase {

    double distanceToMove;

    public TESTMoveToSetPoint(){
        requires(chassis);
        SmartDashboard.putDouble("Move,distance", 0.0);
    }

    protected void initialize(){
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    protected void execute(){
        chassis.setPIDsPosition();
        distanceToMove = SmartDashboard.getDouble("Move,distance", 0.0);
        chassis.move(distanceToMove);
        SmartDashboard.putDouble("Right Encoder Position", chassis.getRightEncoderDistance());
        SmartDashboard.putDouble("Left Encoder Position", chassis.getLeftEncoderDistance());
    }

    protected boolean isFinished(){
        return chassis.isMoveFinished(distanceToMove);
    }

    protected void end(){
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    protected void interrupted(){
        end();
    }
}
