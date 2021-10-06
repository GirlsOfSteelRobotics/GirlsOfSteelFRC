package com.gos.power_up.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import com.gos.power_up.commands.LiftHold;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public final class Lift extends Subsystem {
    public static final double LIFT_MAX = 32500; //TODO tune
    public static final double LIFT_MIN = 0; //TODO tune
    public static final double LIFT_SWITCH = 12500; //TODO tune
    public static final double LIFT_SCALE = 32500; //TODO tune
    public static final double LIFT_GROUND = 1000; //TODO tune
    public static final double LIFT_INCREMENT = 300; //TODO tune 250

    private final WPI_TalonSRX m_lift;
    private final DigitalInput m_limitSwitch;

    private double m_goalLiftPosition;
    private boolean m_inRecoveryMode;

    private final StickyFaults m_faults = new StickyFaults();

    public Lift() {
        m_lift = new WPI_TalonSRX(RobotMap.LIFT);
        m_limitSwitch = new DigitalInput(RobotMap.LIMIT_SWITCH);
        m_lift.setInverted(true);
        m_lift.setSensorPhase(true);
        m_lift.configAllowableClosedloopError(0, 100, 0);
        m_lift.configContinuousCurrentLimit(0, 10);
        m_lift.enableCurrentLimit(false);
        m_lift.clearStickyFaults(10);
        setupLiftFPID();
        m_goalLiftPosition = 0;
        m_inRecoveryMode = false;
        //System.out.println("Lift Constructed");
        addChild(m_lift);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LiftHold(this));
    }

    public void setupLiftFPID() {
        //talon.setPosition(0); //TODO figure out new syntax
        m_lift.config_kF(0, 0, 10);
        m_lift.config_kP(0, 0.3, 10);
        m_lift.config_kI(0, 0, 10);
        m_lift.config_kD(0, 0, 10);
    }

    public void setLiftSpeed(double speed) {
        m_lift.set(speed); //value between -1.0 and 1.0;
    }

    public void stop() {
        m_lift.stopMotor();
    }

    public boolean isAtBottom() {
        return !m_limitSwitch.get();
    }

    public double getGoalLiftPosition() {
        return m_goalLiftPosition;
    }

    public void setGoalLiftPosition(double goal) {
        m_goalLiftPosition = goal;
    }

    public double getLiftPosition() {
        return m_lift.getSelectedSensorPosition(0);
    }

    public void enterRecoveryMode() {
        m_inRecoveryMode = true;
        System.out.println("Lift IN RECOVERY MODE");
    }

    public void printLimitSwitch() {
        if (isAtBottom()) {
            System.out.println("Lift: Limit switch ACTIVATED at bottom");
        } else {
            System.out.println("Lift: Limit switch NOT activated at bottom");
        }
    }

    public void holdLiftPosition() {
        //printLimitSwitch(); ///Testing Limit Switch

        m_lift.getStickyFaults(m_faults);
        if (m_faults.ResetDuringEn) {
            m_inRecoveryMode = true;
            m_goalLiftPosition = 0;
            m_lift.clearStickyFaults(10);
            System.out.println("Lift: Sticky fault detected, IN RECOVERY MODE");
        }
        if (m_inRecoveryMode && isAtBottom()) {
            m_lift.setSelectedSensorPosition(0, 0, 10);
            m_inRecoveryMode = false;
            System.out.println("Lift: encoder position recovered (limit switch activated at bottom)");
        }
        m_lift.set(ControlMode.Position, m_goalLiftPosition);
        //System.out.println("GoalLiftPosition: " + goalLiftPosition);
    }

    public void setLiftToScale() {
        if (!m_inRecoveryMode) {
            m_goalLiftPosition = LIFT_SCALE;
        } else {
            System.out.println("Lift in recovery mode, can't go to scale");
        }
    }

    public void setLiftToSwitch() {
        if (!m_inRecoveryMode) {
            m_goalLiftPosition = LIFT_SWITCH;
        } else {
            System.out.println("Lift in recovery mode, can't go to switch");
        }
    }

    public void setLiftToGround() {
        if (!m_inRecoveryMode) {
            m_goalLiftPosition = LIFT_GROUND;
        } else {
            System.out.println("Lift in recovery mode, can't go to ground");
        }
    }

    public void incrementLift() {
        double goalPosition = m_goalLiftPosition + LIFT_INCREMENT;
        if (!m_inRecoveryMode && (goalPosition >= LIFT_MAX)) {
            m_goalLiftPosition = LIFT_MAX;
        } else {
            m_goalLiftPosition = goalPosition;
            //System.out.println("Lift incremented. New goal : " + goalLiftPosition);
        }
    }

    public void decrementLift() {
        double goalPosition = m_goalLiftPosition - LIFT_INCREMENT;
        if (!m_inRecoveryMode && (goalPosition <= LIFT_MIN)) {
            m_goalLiftPosition = LIFT_MIN;
        } else {
            m_goalLiftPosition = goalPosition;
            //System.out.println("Lift decremented. New goal : " + goalLiftPosition);
        }
    }
}
