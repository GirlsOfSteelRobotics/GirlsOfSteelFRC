/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package girlsofsteel.commands;

import edu.wpi.first.wpilibj.Joystick;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Driving;

/**
 * @author Mackenzie
 */
public class ArcadeDrive extends CommandBase {

    private final Chassis m_chassis;
    private final Joystick m_joystick1; //randomly picked right joystick from robot map

    private double m_xCoord;
    private double m_yCoord;

    public ArcadeDrive(OI oi, Driving driving, Chassis chassis) {
        m_chassis = chassis;
        m_joystick1 = oi.getChassisJoystick();
        requires(driving);
    }

    @Override
    protected void initialize() {
        System.out.println("ARCADE DRIVE______________________________________________________");
    }

    @Override
    protected void execute() {
        m_xCoord = m_joystick1.getX();
        //XcoordSq = chassis.square(Xcoord, 1.0);
        m_yCoord = m_joystick1.getY();
        //YcoordSq = chassis.square(Ycoord, 1.0);
        m_chassis.arcadeDrive(-m_xCoord, -m_yCoord);
        //System.out.println("");
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
