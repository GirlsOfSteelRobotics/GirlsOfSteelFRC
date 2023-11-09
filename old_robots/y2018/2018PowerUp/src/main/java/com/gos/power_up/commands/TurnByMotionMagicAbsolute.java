package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Shifters;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class TurnByMotionMagicAbsolute extends Command {
    private static final double TURNING_FINISH_THRESHOLD = 7.0; // TODO tune (in degrees)

    private final double m_targetHeading; // in degrees

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    public TurnByMotionMagicAbsolute(Chassis chassis, Shifters shifters, double degrees) {
        m_chassis = chassis;
        m_shifters = shifters;
        m_targetHeading = degrees;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        addRequirements(m_chassis);
        // System.out.println("TurnByMotionMagic: constructed");
    }


    @Override
    public void initialize() {
        m_shifters.shiftGear(Shifters.Speed.LOW);
        m_chassis.setInverted(false);

        m_chassis.configForTurnByMotionMagic();
        // System.out.println("TurnByMotionMagic: configured for motion magic");

        System.out.println("TurnByMotionMagicAbsolute: heading: " + m_targetHeading);

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
            System.out.println("TurnByMotionMagicAbsolute: turning degrees reached");
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void end(boolean interrupted) {

        double currentHeading = m_chassis.getYaw();
        double degreesError = m_targetHeading - currentHeading;

        System.out.println("TurnByMotionMagicAbsolute: ended. Error = " + degreesError + " degrees");
        m_chassis.stop();
        m_shifters.shiftGear(Shifters.Speed.HIGH);
        m_chassis.setInverted(false);
    }
}
