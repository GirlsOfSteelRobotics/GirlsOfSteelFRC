package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;

public class GoToLocation extends CommandBase {

    private final Chassis m_chassis;
    private final double m_x;
    private final double m_y;
    private final double m_degreesToFace; //degree change relative to beginning position of the
    //robot to end facing

    public GoToLocation(Chassis chassis, double x, double y, double degreesToFace){
        m_chassis = chassis;
        this.m_x = x;
        this.m_y = y;
        this.m_degreesToFace = degreesToFace;
    }

    @Override
    protected void initialize() {
        m_chassis.initEncoders();
        m_chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        m_chassis.goToLocation(m_x, m_y, m_degreesToFace);
    }

    @Override
    protected boolean isFinished() {
        return m_chassis.isGoToLocationFinished(m_x, m_y);
    }

    @Override
    protected void end() {
        m_chassis.disableRatePIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
