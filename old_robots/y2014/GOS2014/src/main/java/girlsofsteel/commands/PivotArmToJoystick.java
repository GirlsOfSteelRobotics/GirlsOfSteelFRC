/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Manipulator;

/**
 * @author user
 */
public class PivotArmToJoystick extends CommandBase {

    private final Joystick m_operator;
    private final Manipulator m_manipulator;
    private double m_angle;

    public PivotArmToJoystick(OI oi, Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator); //HAVE TO REQUIRE MANIPULATOR SO THAT THIS DOESN'T INTERFERE WITH OTHER MANIPULATOR COMMANDS
        m_operator = oi.getOperatorJoystick();
    }

    @Override
    protected void initialize() {
        m_angle = m_manipulator.getAbsoluteDistance();
    }

    @Override
    protected void execute() {
        System.out.println("Operator Y: " + m_operator.getY());
        if (m_operator.getY() > 0.5) {
            m_manipulator.setSetPoint(m_angle);
            m_angle++; //+= 1.5;
        } else if (m_operator.getY() < -0.5) {
            m_manipulator.setSetPoint(m_angle);
            m_angle--; //-= 1.5;
        }
        m_manipulator.holdAngle();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_manipulator.holdAngle();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
