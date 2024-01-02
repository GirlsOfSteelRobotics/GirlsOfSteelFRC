/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author Mackenzie
 */
public class ArcadeDrive extends GosCommand {

    private final Chassis m_chassis;
    private final Joystick m_joystick1; //randomly picked right joystick from robot map

    private double m_xCoord;
    private double m_yCoord;

    public ArcadeDrive(Joystick joystick, Driving driving, Chassis chassis) {
        m_chassis = chassis;
        m_joystick1 = joystick;
        addRequirements(driving);
    }

    @Override
    public void initialize() {
        System.out.println("ARCADE DRIVE______________________________________________________");
    }

    @Override
    public void execute() {
        m_xCoord = m_joystick1.getX();
        //XcoordSq = chassis.square(Xcoord, 1.0);
        m_yCoord = m_joystick1.getY();
        //YcoordSq = chassis.square(Ycoord, 1.0);
        m_chassis.arcadeDrive(-m_xCoord, -m_yCoord);
        //System.out.println("");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
    }



}
