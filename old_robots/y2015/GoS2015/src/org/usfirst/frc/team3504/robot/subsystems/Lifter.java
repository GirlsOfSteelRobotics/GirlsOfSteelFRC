package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.lifter.LiftByJoystick;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Lifter extends Subsystem {

    private final CANTalon liftTalon;

    private final DigitalInput liftTopLimit;
    private final DigitalInput liftBottomLimit;


    // 170 is one rotation

    public static final double DISTANCE_ZERO_TOTES = -3600;// -250;//-3000;//-100;
    public static final double DISTANCE_ONE_TOTE = -3000;// -10453;
    public static final double DISTANCE_TWO_TOTES = -3000;// -20906;
    public static final double DISTANCE_THREE_TOTES = -3000;// -31359;
    public static final double DISTANCE_FOUR_TOTES = -15000;// -41812;

    public Lifter() {
        liftTalon = new CANTalon(RobotMap.FORKLIFT_CHANNEL_A);
        liftTopLimit = new DigitalInput(RobotMap.FORKLIFT_TOP_LIMIT);
        liftBottomLimit = new DigitalInput(RobotMap.FORKLIFT_BOTTOM_LIMIT);

        SmartDashboard.putNumber("P value", 3);
        SmartDashboard.putNumber("I value", 0);
        SmartDashboard.putNumber("D value", 0);

        liftTalon.changeControlMode(CANTalon.TalonControlMode.Position);
        liftTalon.setPID(SmartDashboard.getNumber("P value", 0), SmartDashboard.getNumber("I value", 0),
                SmartDashboard.getNumber("D value", 0), 0, 0, 0, 0);

        SmartDashboard.putNumber("Lifter Setpoint", 1700);
    }

    public void tunePID() {
        liftTalon.setPID(SmartDashboard.getNumber("P value", 0), SmartDashboard.getNumber("I value", 0),
                SmartDashboard.getNumber("D value", 0), 0, 0, 0, 0);

        liftTalon.set(SmartDashboard.getNumber("Lifter Setpoint", 0)); // Number of
                                                                    // encoder
                                                                    // ticks
    }

    public void setPosition(double distance) {
        liftTalon.set(distance);
    }

    public boolean isAtTopLevel() {
        return (liftTalon.get() == DISTANCE_FOUR_TOTES);
    }

    public boolean isAtBottomLevel() {
        return (liftTalon.get() == DISTANCE_ZERO_TOTES);
    }

    public void moveByJoystick() {
        printLifter();

        SmartDashboard.putNumber("Lifter throttle", (Robot.oi.getOperatorJoystick().getY()));
        if ((Math.abs(Robot.oi.getOperatorJoystick().getY()) < .2)) {
            SmartDashboard.putString("In", "in throttle zero");
            liftTalon.set(liftTalon.getSetpoint());
        } else {
            if (isAtTop() && Robot.oi.getOperatorJoystick().getY() < .2) {
                liftTalon.set(liftTalon.getSetpoint());
            }
            else if (isAtBottom() && Robot.oi.getOperatorJoystick().getY() > .2) {
                liftTalon.set(liftTalon.getSetpoint());
            }
            else {
                liftTalon.set(liftTalon.get() - (300 * Robot.oi.getOperatorJoystick().getY()));
            }
            SmartDashboard.putString("In", "not in throttle zero");
        }
    }

    public void moveUpVelocityControl() {
        liftTalon.set(.5);
    }

    public void moveDownVelocityControl() {
        liftTalon.set(-.5);
    }

    public void stopMotorsVelocityControl() {
        liftTalon.set(0);
    }

    public boolean isAtPosition() {
        return (Math.abs(liftTalon.get() - liftTalon.getSetpoint()) <= 100);
    }

    public void printLifter() {
        SmartDashboard.putNumber("Lifter encoder", liftTalon.getEncPosition());
        SmartDashboard.putBoolean("Top Limit Switch", !liftTopLimit.get());
        SmartDashboard.putBoolean("Bottom Limit", !liftBottomLimit.get());
    }

    public void stop() {
        liftTalon.set(liftTalon.get());
    }

    public boolean isAtTop() {
        return !liftTopLimit.get();
    }

    public boolean isAtBottom() {
        return !liftBottomLimit.get();
    }

    @Override
    protected void initDefaultCommand() {
        // setDefaultCommand(new LifterTests());
        setDefaultCommand(new LiftByJoystick());
    }
}
