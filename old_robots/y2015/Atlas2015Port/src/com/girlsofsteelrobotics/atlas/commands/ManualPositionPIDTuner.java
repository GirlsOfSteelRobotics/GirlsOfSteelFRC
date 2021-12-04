/**
 * A command to tune the chassis position PID.
 *
 *
 * DIFFERENT P'S ON THE PRACTICE BOT AND COMPETITION BOT
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualPositionPIDTuner extends CommandBase {

    double setpoint;
    double p = 0.0;
    double i;
    double d;
    double offBy = 0.05;
    boolean reset;
    boolean pid = false;

    public ManualPositionPIDTuner() {
        requires(driving);

    }

    protected void initialize() {
        chassis.initEncoders();
        chassis.resetEncoders();
        if (pid) {
            chassis.initPositionPIDS();
            chassis.resetPositionPIDError();
            SmartDashboard.putNumber("Chassis Position setpoint", 0);
            SmartDashboard.putNumber("Position P: ", 0);
            SmartDashboard.putNumber("Position I: ", 0);
            SmartDashboard.putNumber("Position D: ", 0);
        }
        SmartDashboard.putBoolean("Resetencoder", false);
    }

    protected void execute() {
        if (pid) {
            setpoint = SmartDashboard.getNumber("Chassis Position setpoint", 0);
            p = SmartDashboard.getNumber("Position P: ", 0);
            i = SmartDashboard.getNumber("Position I: ", 0);
            d = SmartDashboard.getNumber("Position D: ", 0);
        }

        reset = SmartDashboard.getBoolean("Resetencoder", false);

        if (reset) {
            chassis.resetEncoders();
        }

        SmartDashboard.putNumber("leftencoder chassis", chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("Left encoder rate", chassis.getLeftEncoderRate());
        SmartDashboard.putNumber("Left Encoder Get Chassis", chassis.getLeftEncoder());
        SmartDashboard.putNumber("Left Encoder Get Raw", chassis.getLeftRaw());
        SmartDashboard.putNumber("Right Encoder Get Raw", chassis.getRightRaw());
        SmartDashboard.putNumber("Right Encoder Get Chassis", chassis.getRightEncoder());
        SmartDashboard.putNumber("rightencoder chassis", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("right encoder rate", chassis.getRightEncoderRate());
        SmartDashboard.putNumber("Right encoder rate chassis", chassis.getRateRightEncoder());
        SmartDashboard.putNumber("Left encoder rate chassis", chassis.getRateLeftEncoder());

        System.out.println(chassis.getRightEncoderRate() + "\t" + chassis.getLeftEncoderRate());

        System.out.println("Get left chassis raw: " + chassis.getLeftRaw());
        System.out.println("Get right chassis raw: " + chassis.getRightRaw());

        if (pid) {
            if (p != 0 && setpoint != 0) {
                System.out.println("Here ---------------------------------");
                chassis.setLeftPositionPIDValues(p, i, d);
                chassis.setRightPositionPIDValues(p, i, d);
                chassis.setPositionSeparate(setpoint, setpoint);
            }
        }
    }

    protected boolean isFinished() {
        boolean finished =  (Math.abs((chassis.getLeftEncoderDistance() - setpoint)) < offBy || Math.abs((chassis.getRightEncoderDistance()-setpoint)) < offBy) && (setpoint != 0);
        System.out.println("Position PID is finished: " + finished);
        return finished;
    }

    protected void end() {
        if (pid) {
            chassis.disablePositionPID();
        }
    }

    protected void interrupted() {
        end();
    }
}
