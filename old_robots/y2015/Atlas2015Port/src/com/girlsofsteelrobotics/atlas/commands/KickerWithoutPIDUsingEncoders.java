/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 * @author < ( *0* ) >
 */
public class KickerWithoutPIDUsingEncoders extends CommandBase {

    private double loadingEncoderPosition = .45; // this value is "about"; currentEncoderValue % 360

    public KickerWithoutPIDUsingEncoders(int loadingOrShooting) //0 = loading; 1 = shooting
    {
        requires(kicker);
    }

    protected void initialize() {

    }

    protected void execute() {
        if(kicker.getEncoder() % 360 < loadingEncoderPosition)
            kicker.setJag(1.0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        kicker.stopJag();
    }

    protected void interrupted() {
        end();
    }

}
