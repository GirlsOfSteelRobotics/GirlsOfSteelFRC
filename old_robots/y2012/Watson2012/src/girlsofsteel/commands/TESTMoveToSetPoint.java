package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTMoveToSetPoint extends CommandBase {

    double distanceToMove;

    public TESTMoveToSetPoint(){
        requires(chassis);
        SmartDashboard.putNumber("Move,distance", 0.0);
    }

    protected void initialize(){
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    protected void execute(){
        chassis.setPIDsPosition();
        distanceToMove = SmartDashboard.getNumber("Move,distance", 0.0);
        chassis.move(distanceToMove);
        SmartDashboard.putNumber("Right Encoder Position", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder Position", chassis.getLeftEncoderDistance());
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
