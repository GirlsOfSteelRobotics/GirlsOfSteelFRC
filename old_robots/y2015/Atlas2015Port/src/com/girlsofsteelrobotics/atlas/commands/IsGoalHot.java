/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlsofsteelrobotics.atlas.commands;

/**
 *
 * @author Sylvie
 */
public class IsGoalHot extends CommandBase {

    private double[] hots = new double[10];
    private int bool;
    private double average = 0;
    private int i;

    @Override
    protected void initialize() {
        i = 0;
    }

    @Override
    protected void execute() {
        if (CommandBase.camera.isGoalHot()) {
            bool = 1;
        } else {
            bool = 0;
        }
        hots[i] = bool;
        i++;
    }

    @Override
    protected boolean isFinished() {
        return i > hots.length;
    }

    @Override
    protected void end() {
        for (int i=0;i<hots.length;i++){
            average += hots[i];
        }
        average /= hots.length;
         if(average >= 0.5){ //If it's dead even, just say that it's HOT
            CommandBase.camera.isHot = true;
        }else {
             CommandBase.camera.isHot = false;
         }
    }

    @Override
    protected void interrupted() {
        end();
    }
}
