package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;

/**
 * @author Jisue
 */
public class TankDrive extends CommandBase {
    /*  need values for joysticks
        need joystick here
        inputs are joysticks
        outputs are motors

        */
    private final Joystick m_left;
    private final Joystick m_right;
    private final Chassis m_chassis;

    public TankDrive(OI oi, Chassis chassis, Driving driving) {
        requires(driving);
        m_chassis = chassis;

        m_left = oi.getChassisJoystick();
        m_right = oi.getOperatorJoystick();
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        //System.out.println("Right: " + right.getY() + "\nLeft: " + left.getY());
        m_chassis.tankDrive(m_right.getY(), m_left.getY());

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
