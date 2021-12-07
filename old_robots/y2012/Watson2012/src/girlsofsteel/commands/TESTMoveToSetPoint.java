package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTMoveToSetPoint extends CommandBase {

    private double distanceToMove;

    public TESTMoveToSetPoint(){
        requires(chassis);
        SmartDashboard.putNumber("Move,distance", 0.0);
    }

    @Override
    protected void initialize(){
        chassis.initEncoders();
        chassis.initPositionPIDs();
    }

    @Override
    protected void execute(){
        chassis.setPIDsPosition();
        distanceToMove = SmartDashboard.getNumber("Move,distance", 0.0);
        chassis.move(distanceToMove);
        SmartDashboard.putNumber("Right Encoder Position", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder Position", chassis.getLeftEncoderDistance());
    }

    @Override
    protected boolean isFinished(){
        return chassis.isMoveFinished(distanceToMove);
    }

    @Override
    protected void end(){
        chassis.disablePositionPIDs();
        chassis.endEncoders();
    }

    @Override
    protected void interrupted(){
        end();
    }
}
