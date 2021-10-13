package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

 
public class Autonomous extends CommandGroup
{
    public Autonomous(){
            addSequential (new DriveForward());
            addSequential (new SpinCircle());
            addSequential (new DriveBackward());
            addSequential (new TurnLeft());
            addSequential (new DriveForward());
            addSequential (new TurnRight());
            addSequential (new DriveBackward());
 
    }
    
    
}