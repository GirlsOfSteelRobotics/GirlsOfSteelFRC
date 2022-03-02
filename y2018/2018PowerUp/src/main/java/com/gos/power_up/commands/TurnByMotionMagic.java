package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class TurnByMotionMagic extends CommandBase {
    private static final double TURNING_FINISH_THRESHOLD = 7.0; // TODO tune (in degrees)

    private final double m_targetHeading; // in degrees
    private final boolean m_resetPigeon;

    private final Chassis m_chassis;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;


    public TurnByMotionMagic(Chassis chassis, double degrees) {
        this(chassis, degrees, true);
    }

    public TurnByMotionMagic(Chassis chassis, double degrees, boolean reset) {
        m_targetHeading = degrees;
        m_resetPigeon = reset;
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        addRequirements(m_chassis);
        // System.out.println("TurnByMotionMagic: constructed");
    }


    @Override
    public void initialize() {
        m_chassis.setInverted(false);

        m_chassis.configForTurnByMotionMagic();
        // System.out.println("TurnByMotionMagic: configured for motion magic");

        if (m_resetPigeon) {
            m_chassis.zeroSensors();
        }

        System.out.println("TurnByMotionMagic: heading: " + m_targetHeading + " reset=" + m_resetPigeon);


        m_rightTalon.set(ControlMode.MotionMagic, -10 * m_targetHeading);
        m_leftTalon.follow(m_rightTalon);

    }


    @Override
    public void execute() {

    }


    @Override
    public boolean isFinished() {

        double currentHeading = m_chassis.getYaw();
        double error = Math.abs(m_targetHeading - currentHeading);
        // System.out.println("DriveByMotionMagic: turning error = " + error);
        if (error < TURNING_FINISH_THRESHOLD) {
            System.out.println("TurnByMotionMagic: turning degrees reached");
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void end(boolean interrupted) {

        double currentHeading = m_chassis.getYaw();
        double degreesError = m_targetHeading - currentHeading;

        System.out.println("TurnByMotionMagic: ended. Error = " + degreesError + " degrees");
        m_chassis.stop();
        m_chassis.setInverted(false);
    }


    @Override
    protected void interrupted() {
        System.out.println("TurnByMotionMagic: interrupted");
        end();
    }
}
