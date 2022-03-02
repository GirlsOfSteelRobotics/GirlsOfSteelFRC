package com.gos.steam_works.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class AutoDoNothing extends CommandBase {


    @Override
    public void initialize() {
        System.out.println("AutoDoNothing Initialized");
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("AutoDoNothing Finished.");
    }


}
