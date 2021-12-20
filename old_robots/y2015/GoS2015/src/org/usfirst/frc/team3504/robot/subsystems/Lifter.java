package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.RobotMap;


public class Lifter extends Subsystem {

    // 170 is one rotation
    public static final double DISTANCE_ZERO_TOTES = -3600;// -250;//-3000;//-100;
    public static final double DISTANCE_ONE_TOTE = -3000;// -10453;
    public static final double DISTANCE_TWO_TOTES = -3000;// -20906;
    public static final double DISTANCE_THREE_TOTES = -3000;// -31359;
    public static final double DISTANCE_FOUR_TOTES = -15000;// -41812;

    private final CANTalon m_liftTalon;

    private final DigitalInput m_liftTopLimit;
    private final DigitalInput m_liftBottomLimit;

    public Lifter() {
        m_liftTalon = new CANTalon(RobotMap.FORKLIFT_CHANNEL_A);
        m_liftTopLimit = new DigitalInput(RobotMap.FORKLIFT_TOP_LIMIT);
        m_liftBottomLimit = new DigitalInput(RobotMap.FORKLIFT_BOTTOM_LIMIT);

        SmartDashboard.putNumber("P value", 3);
        SmartDashboard.putNumber("I value", 0);
        SmartDashboard.putNumber("D value", 0);

        m_liftTalon.config_kP(0, SmartDashboard.getNumber("P value", 0));
        m_liftTalon.config_kI(0, SmartDashboard.getNumber("I value", 0));
        m_liftTalon.config_kD(0, SmartDashboard.getNumber("D value", 0));

        SmartDashboard.putNumber("Lifter Setpoint", 1700);
    }

    public void tunePID() {
        m_liftTalon.config_kP(0, SmartDashboard.getNumber("P value", 0));
        m_liftTalon.config_kI(0, SmartDashboard.getNumber("I value", 0));
        m_liftTalon.config_kD(0, SmartDashboard.getNumber("D value", 0));

        m_liftTalon.set(ControlMode.Position, SmartDashboard.getNumber("Lifter Setpoint", 0)); // Number of
                                                                    // encoder
                                                                    // ticks
    }

    public void setPosition(double distance) {
        m_liftTalon.set(ControlMode.Position, distance);
    }

    public boolean isAtTopLevel() {
        return (m_liftTalon.getSelectedSensorPosition() == DISTANCE_FOUR_TOTES);
    }

    public boolean isAtBottomLevel() {
        return (m_liftTalon.getSelectedSensorPosition() == DISTANCE_ZERO_TOTES);
    }

    public void moveByJoystick(Joystick operatorJoystick) {
        printLifter();

        SmartDashboard.putNumber("Lifter throttle", (operatorJoystick.getY()));
        if ((Math.abs(operatorJoystick.getY()) < .2)) {
            SmartDashboard.putString("In", "in throttle zero");
            m_liftTalon.set(ControlMode.Position, m_liftTalon.getClosedLoopTarget());
        } else {
            if (isAtTop() && operatorJoystick.getY() < .2) {
                m_liftTalon.set(ControlMode.Position, m_liftTalon.getClosedLoopTarget());
            }
            else if (isAtBottom() && operatorJoystick.getY() > .2) {
                m_liftTalon.set(ControlMode.Position, m_liftTalon.getClosedLoopTarget());
            }
            else {
                m_liftTalon.set(ControlMode.Position, m_liftTalon.getSelectedSensorPosition() - (300 * operatorJoystick.getY()));
            }
            SmartDashboard.putString("In", "not in throttle zero");
        }
    }

    public void moveUpVelocityControl() {
        m_liftTalon.set(ControlMode.PercentOutput, .5);
    }

    public void moveDownVelocityControl() {
        m_liftTalon.set(ControlMode.PercentOutput, -.5);
    }

    public void stopMotorsVelocityControl() {
        m_liftTalon.set(ControlMode.PercentOutput, 0);
    }

    public boolean isAtPosition() {
        return (Math.abs(m_liftTalon.getSelectedSensorPosition() - m_liftTalon.getClosedLoopTarget()) <= 100);
    }

    public void printLifter() {
        SmartDashboard.putNumber("Lifter encoder", m_liftTalon.getSelectedSensorPosition());
        SmartDashboard.putBoolean("Top Limit Switch", !m_liftTopLimit.get());
        SmartDashboard.putBoolean("Bottom Limit", !m_liftBottomLimit.get());
    }

    public void stop() {
        m_liftTalon.set(ControlMode.Position, m_liftTalon.getSelectedSensorPosition());
    }

    public boolean isAtTop() {
        return !m_liftTopLimit.get();
    }

    public boolean isAtBottom() {
        return !m_liftBottomLimit.get();
    }

    @Override
    protected void initDefaultCommand() {
    }
}
