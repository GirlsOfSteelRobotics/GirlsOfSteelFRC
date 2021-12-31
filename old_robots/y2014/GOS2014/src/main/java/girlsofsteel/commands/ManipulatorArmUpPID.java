/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import girlsofsteel.Configuration;
import girlsofsteel.subsystems.Manipulator;

/**
 * @author Sylvie
 */
public class ManipulatorArmUpPID extends CommandBase {

    private final Manipulator m_manipulator;
    private double m_angle;

    public ManipulatorArmUpPID(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
        m_angle = m_manipulator.getAbsoluteDistance();
    }

    @Override
    protected void execute() {
        System.out.println("Up Encoder Value: " + m_manipulator.getAbsoluteDistance());
//        System.out.println("Up Angle Setpoint: " + angle * Configuration.desiredAnglePivotArmSign);
//        System.out.println("Error: " + manipulator.getError());
        m_manipulator.setSetPoint(m_angle * Configuration.desiredAnglePivotArmSign);
        if (m_angle < 110) {
            m_angle += 3;
        }
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
