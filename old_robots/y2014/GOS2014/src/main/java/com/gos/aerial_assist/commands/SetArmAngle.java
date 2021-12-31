/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

NOTE!!!: The starting configuration of the pivot arm is STRAIGHT UP because
the pivot arm has to stay within the perimeter restraints

 */

package com.gos.aerial_assist.commands;

import com.gos.aerial_assist.subsystems.Manipulator;

/**
 * @author Sophia, Sam, and Abby
 */
//BACKUP TO PID SET ANGLE: this code is super out of date and we have to update it
//multiple definitions of angle need to be updated and changed to the same through all the code

public class SetArmAngle extends CommandBase {

    private final Manipulator m_manipulator;
    private double m_angle;
    private final double m_desired;

    public SetArmAngle(Manipulator manipulator, double desiredAngle) {
        m_manipulator = manipulator;
        requires(m_manipulator);
        m_desired = desiredAngle;
    }

    @Override
    protected void initialize() {
        m_manipulator.init();
    }

    @Override
    protected void execute() {
        //manipulator.getCurrentAngle(angle);
        System.out.println("Setting the angle! :D");
//        manipulator.moveAngle();
        System.out.println(m_angle);
        m_manipulator.moveJag(m_desired);
        m_angle = m_manipulator.getAbsoluteDistance();//TODO check logic

        // Calls SetArmAngle function. Passes angle from constructor.

        //double joystickPostion;
        //joystickPostion = oi.getRightJoystick().getRawAxis(3);
        //System.out.println ("joystickPostition");

//        double m=90;
//        double x = joystickPosition;
//        joystickPosition = m*x/2 + m/2;

        //SetArmAngle isn't a method yet....setArmAngle(joystickPosition);
        /* create a varable thats a double
         * read the knob with joystick.getraw axis (3)
         * gives us a double
         * double fills in variable
         * convert double to degrees
         * send number to this command
         */
    }

    @Override
    protected boolean isFinished() {
        System.out.println("Isfinished: " + m_manipulator.checkAngle(m_angle));
        return m_manipulator.checkAngle(m_desired);
    }

    @Override
    protected void end() {
        m_manipulator.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
