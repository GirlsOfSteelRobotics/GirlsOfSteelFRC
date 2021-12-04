/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.Timer;
import com.girlsofsteelrobotics.atlas.Configuration;

/**
 *
 * @author Sylvie
 *
 * Angle 0 is considered to be the horizontal
 * -18.3 degrees is the actual bottom
 * Top limit of the arm unknown (probably around 100?)
 *
 */
public class setArmAnglePID extends CommandBase {

    private final double desiredAngle;
    private double angle;
    private double changedTime;
    private final double startTime;
    private final double allowedAngleError;


    public setArmAnglePID(double desiredAngle) {
        requires(manipulator);
        this.desiredAngle = desiredAngle;
        allowedAngleError = 4; //2 degrees of error on both sides allowed
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void initialize() {
        angle = desiredAngle * Configuration.desiredAnglePivotArmSign;
    }

    @Override
    protected void execute() {
        if (angle < -18.2) {
            angle = -18.2;
        }
        else if(angle > 113) {
            angle = 110;
        }
        manipulator.setSetPoint(angle);
        //System.out.println("SSEENNNTTTT AARRRMMMMMMMMM AANNNGGLLLLEEE ----- "+angle);
        //System.out.println("AARRRMMMMMMMMM AANNNGGLLLLEEE ----- "+manipulator.getDistance());
        changedTime = Timer.getFPGATimestamp() - startTime;
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(desiredAngle-manipulator.getAbsoluteDistance()) < allowedAngleError;
    }

    @Override
    protected void end() {
        manipulator.holdAngle();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
