package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.lib.DeadbandHelper;

public class TurnToAngle extends CommandBase {

    private static final GosDoubleProperty AUTO_KP = new GosDoubleProperty(false, "TurnToAngleKp",
            0.02);
    private static final GosDoubleProperty AUTO_KI = new GosDoubleProperty(false, "TurnToAngleKi",
            0);
    private static final GosDoubleProperty AUTO_KD = new GosDoubleProperty(false, "TurnToAngleKd",
            0);

    private final Chassis m_chassis;
    private final double m_angle;
    private final double m_allowableError;

    private final DeadbandHelper m_deadbandHelper;

    private final PIDController m_steerPID;

    private double m_error;

    public TurnToAngle(Chassis chassis, double angle, double allowableError) {
        m_chassis = chassis;
        m_deadbandHelper = new DeadbandHelper(5);

        m_angle = angle;
        m_allowableError = allowableError;

        m_steerPID = new PIDController(0, 0, 0);
        m_steerPID.enableContinuousInput(-180, 180);

        addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        m_steerPID.setP(AUTO_KP.getValue());
        m_steerPID.setI(AUTO_KI.getValue());
        m_steerPID.setD(AUTO_KD.getValue());

        double steeringSpeed = m_steerPID.calculate(m_chassis.getHeading(), m_angle);
        steeringSpeed += Math.copySign(Constants.MINIMUM_TURN_SPEED, steeringSpeed);

        m_chassis.setSpeedAndSteer(0, -steeringSpeed);

        System.out.println(steeringSpeed);

        double currentAngle = m_chassis.getHeading();
        m_error = m_angle - currentAngle;

        // double turnSpeed = m_error * AUTO_KP.getValue();

        // System.out.println("error:" + m_error + "turnSpeed:" + turnSpeed);
    }

    @Override
    public boolean isFinished() {
        boolean isFinished = Math.abs(m_error) < m_allowableError;
        m_deadbandHelper.setIsGood(isFinished);
        if (m_deadbandHelper.isFinished()) {
            System.out.println("Done!");
            return true;
        } else {
            System.out.println("Turn to angle" + "error:" + m_error + "allowableError" + m_allowableError);
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeed(0);
    }
}
