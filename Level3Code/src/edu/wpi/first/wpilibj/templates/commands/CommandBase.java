package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.subsystems.Chassis;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command { //commandsbase is a subclass of command. It extends command.
    
    public static OI oi;
    
    public static Chassis chassis = new Chassis(); //initializes a new chassis

    public static void init() {

        oi = new OI(); //input output is open to get any values from commands

        //SmartDashboard.putData(exampleSubsystem);
    }

    public CommandBase(String name) {
        super(name);
        //commandsbase is a subclass of command. It extends command. 
        //When a class is a subclass, it inherits values and methods from it's mama class (parent). 
        
    }

    public CommandBase() {
        super();
        //Basically the same thing. Look above ^^
    }
}
