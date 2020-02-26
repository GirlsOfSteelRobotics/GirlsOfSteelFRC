package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.DeadbandHelper;
import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.Chassis;

public class TurnToAngle extends CommandBase {

    private static final PropertyManager.IProperty<Double> AUTO_KP = new PropertyManager.DoubleProperty("TurnToAngleKp", 0.02);

    private final Chassis m_chassis;
    private final double m_angle;
    private final double m_allowableError;

    private final DeadbandHelper m_deadbandHelper;

    private double m_error;

    public TurnToAngle(Chassis chassis, double angle, double allowableError) {
        m_chassis = chassis;
        m_deadbandHelper = new DeadbandHelper(5);

        m_angle = angle;
        m_allowableError = allowableError;

        addRequirements(chassis);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute() {
        double currentAngle = m_chassis.getHeading();
        m_error = m_angle - currentAngle;

        double turnSpeed = m_error * AUTO_KP.getValue();
        m_chassis.setSpeedAndSteer(0, -turnSpeed);

        System.out.println("error:" + m_error + "turnSpeed:" + turnSpeed);
    }

    @Override
    public boolean isFinished() {
        boolean isFinished = Math.abs(m_error) < m_allowableError;
        m_deadbandHelper.setIsGood(isFinished);
        if (m_deadbandHelper.isFinished()) {
            System.out.println("Done!");
            return true;
        }
        else {
            System.out.println("Turn to angle" + "error:" + m_error + "allowableError" + m_allowableError);
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeed(0);
    }
}
