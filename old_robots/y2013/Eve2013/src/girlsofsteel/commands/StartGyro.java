package girlsofsteel.commands;

import girlsofsteel.subsystems.Chassis;

public class StartGyro extends CommandBase {

    private final Chassis m_chassis;
    private int m_angle;

    public StartGyro(Chassis chassis, int angle){
        m_chassis = chassis;
        this.m_angle = angle;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_angle -= m_chassis.getGyroAngle();
        m_chassis.setFieldAdjustment(m_angle);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
