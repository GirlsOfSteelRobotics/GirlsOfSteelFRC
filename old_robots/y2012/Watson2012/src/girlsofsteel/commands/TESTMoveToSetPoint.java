package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Chassis;

public class TESTMoveToSetPoint extends CommandBase {

    private final Chassis m_chassis;
    private double m_distanceToMove;

    public TESTMoveToSetPoint(Chassis chassis){
        m_chassis = chassis;
        requires(m_chassis);
        SmartDashboard.putNumber("Move,distance", 0.0);
    }

    @Override
    protected void initialize(){
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    protected void execute(){
        m_chassis.setPIDsPosition();
        m_distanceToMove = SmartDashboard.getNumber("Move,distance", 0.0);
        m_chassis.move(m_distanceToMove);
        SmartDashboard.putNumber("Right Encoder Position", m_chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("Left Encoder Position", m_chassis.getLeftEncoderDistance());
    }

    @Override
    protected boolean isFinished(){
        return m_chassis.isMoveFinished(m_distanceToMove);
    }

    @Override
    protected void end(){
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
    }

    @Override
    protected void interrupted(){
        end();
    }
}
