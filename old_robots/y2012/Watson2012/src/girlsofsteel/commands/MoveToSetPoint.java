package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;

public class MoveToSetPoint extends CommandBase {

    private final Chassis m_chassis;
    private double m_timeFinished = -1;
    private final double m_distanceToMove;

    public MoveToSetPoint(Chassis chassis, double distance) {
        m_chassis = chassis;
        m_distanceToMove = distance;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
        m_timeFinished = -1;
    }

    @Override
    protected void execute() {
        m_chassis.setPIDsPosition();
        m_chassis.move(m_distanceToMove);
        if (m_timeFinished == -1 && m_chassis.isMoveFinished(m_distanceToMove)) {
            m_timeFinished = timeSinceInitialized();
        }
//        if(timeSinceInitialized() > 3){
//            System.out.println("Move to Set Point Timed Out");
//            end();
//        }
    }

    @Override
    protected boolean isFinished() {
        if (m_timeFinished != -1 && timeSinceInitialized() - m_timeFinished > 0.5) {
            System.out.println("Move to Set Point Done");
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        System.out.println("Done with moving");
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
