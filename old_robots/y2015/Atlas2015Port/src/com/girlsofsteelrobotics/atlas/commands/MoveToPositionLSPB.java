/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.girlsofsteelrobotics.atlas.Configuration;

/**
 *
 * @author Sylvie and Jisue
 */
public class MoveToPositionLSPB extends CommandBase {

    double setPoint = 0.0;
    double startTime;
    double changeInTime;
    boolean firstTime = true;
    double offBy = 0.05;

    public MoveToPositionLSPB(double setPoint) {
        requires(driving);
        this.setPoint = setPoint;
        System.out.println("Moving Made!");
//         chassis.initEncoders();
//        chassis.resetEncoders();
    }

    protected void initialize() {
        SmartDashboard.putNumber("Setpoint Before", setPoint);
        setPoint *= Configuration.signOfChassisPositionPIDSetpoint;

        System.out.println("In initialize");
        chassis.initEncoders();
        chassis.resetEncoders();
        chassis.initPositionPIDS();
        chassis.resetPositionPIDError();
        startTime = System.currentTimeMillis();
        chassis.leftChassisPlanner.calculateVelocityGraph(setPoint);
        chassis.rightChassisPlanner.calculateVelocityGraph(setPoint);
    }

    protected void execute() {
        //System.out.println("Left Encoder: " + chassis.getLeftEncoderDistance() + "Right Encoder" + chassis.getRightEncoderDistance());
        changeInTime = System.currentTimeMillis() - startTime;
        chassis.setPositionSeparate(chassis.leftChassisPlanner.getDesiredPosition(changeInTime), chassis.rightChassisPlanner.getDesiredPosition(changeInTime));
        SmartDashboard.putNumber("LSPB Left Encoder", chassis.getLeftEncoderDistance());
        SmartDashboard.putNumber("LSPB Right Encoder", chassis.getRightEncoderDistance());
        SmartDashboard.putNumber("LSPB Left Raw", chassis.getLeftRaw());
        SmartDashboard.putNumber("LSPB Right Raw", chassis.getRightRaw());
        SmartDashboard.putNumber("LSPB Sent this setpoint: ", setPoint);
        System.out.println("Left encoder: " + chassis.getLeftEncoderDistance() + "\tRight encoder: " + chassis.getRightEncoderDistance());
    }

    protected boolean isFinished() {
        if (changeInTime > 3000) {
            System.out.println("Time Exit Out of Chassis");
            return true;
        }
        boolean ret = ((Math.abs(chassis.getLeftEncoderDistance() - setPoint) < offBy)
                && (Math.abs(chassis.getRightEncoderDistance() - setPoint) < offBy))
                || (chassis.getLeftEncoderDistance() > Math.abs(setPoint))
                || (chassis.getRightEncoderDistance() > Math.abs(setPoint));

        System.out.println("First: " + (Math.abs(chassis.getLeftEncoderDistance() - setPoint) < offBy));
        System.out.println("Second: " + (Math.abs(chassis.getRightEncoderDistance() - setPoint) < offBy));
        System.out.println("Third: " + (chassis.getLeftEncoderDistance() > Math.abs(setPoint)));
        System.out.println("Fourth: " + (chassis.getRightEncoderDistance() > Math.abs(setPoint)));
        System.out.println("Return value: " + ret);
        return ret;
        //This is bad. doesn't work for negatve. TODO fix later
    }

    protected void end() {

        chassis.disablePositionPID();
        chassis.stopJags();
        setPoint *= Configuration.signOfChassisPositionPIDSetpoint; //To undo what we did before
    }

    protected void interrupted() {
        end();
    }

}
