package com.girlsofsteelrobotics.atlas.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.girlsofsteelrobotics.atlas.OI;
import com.girlsofsteelrobotics.atlas.objects.Camera;
import com.girlsofsteelrobotics.atlas.subsystems.Chassis;
import com.girlsofsteelrobotics.atlas.subsystems.Collector;
import com.girlsofsteelrobotics.atlas.subsystems.Driving;
import com.girlsofsteelrobotics.atlas.subsystems.Kicker;
import com.girlsofsteelrobotics.atlas.subsystems.Manipulator;
import com.girlsofsteelrobotics.atlas.subsystems.UltrasonicSensor;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static final Chassis chassis = new Chassis();
    public static final Manipulator manipulator = new Manipulator();
    public static final Kicker kicker = new Kicker();
    public static final Collector collector = new Collector();
    public static final Driving driving = new Driving();
    public static final UltrasonicSensor ultra = new UltrasonicSensor();
    public static final Camera camera = new Camera();


    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        //SmartDashboard.putData(exampleSubsystem);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
