package com.gos.steam_works.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDoNothing extends Command {

    public AutoDoNothing() {
    }


    @Override
    protected void initialize() {
        System.out.println("AutoDoNothing Initialized");
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
        System.out.println("AutoDoNothing Finished.");
    }


}
