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

    double[] hots = new double[10];
    int bool;
    double average = 0;
    int i;

    protected void initialize() {
        i = 0;
    }

    protected void execute() {
        if (CommandBase.camera.isGoalHot()) {
            bool = 1;
        } else {
            bool = 0;
        }
        hots[i] = bool;
        i++;
    }

    protected boolean isFinished() {
        return i > hots.length;
    }

    protected void end() {
        for (int i=0;i<hots.length;i++){
            average += hots[i];
        }
        average /= hots.length;
         if(average >= 0.5){ //If it's dead even, just say that it's HOT
            CommandBase.camera.isHot = true;
        }else
            CommandBase.camera.isHot = false;
    }

    protected void interrupted() {
        end();
    }
}
