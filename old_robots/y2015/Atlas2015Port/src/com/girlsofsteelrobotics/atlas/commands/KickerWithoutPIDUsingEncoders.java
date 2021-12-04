/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 * @author < ( *0* ) >
 */
public class KickerWithoutPIDUsingEncoders extends CommandBase {

    private final double loadingEncoderPosition = .45; // this value is "about"; currentEncoderValue % 360

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public KickerWithoutPIDUsingEncoders(int loadingOrShooting) //0 = loading; 1 = shooting
    {
        requires(kicker);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if(kicker.getEncoder() % 360 < loadingEncoderPosition)
            kicker.setJag(1.0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        kicker.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
