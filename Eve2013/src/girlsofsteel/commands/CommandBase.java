package girlsofsteel.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import girlsofsteel.OI;
import girlsofsteel.RobotMap;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Climber;
import girlsofsteel.subsystems.EveCompressor;
import girlsofsteel.subsystems.DriveFlag;
import girlsofsteel.subsystems.Feeder;
import girlsofsteel.subsystems.Gripper;
import girlsofsteel.subsystems.Shooter;
import girlsofsteel.objects.PositionInfo;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Shooter shooter = new Shooter();
    public static Climber climber = new Climber();
    public static Feeder feeder = new Feeder();
    public static Chassis chassis = new Chassis();
    public static DriveFlag drive = new DriveFlag();
    public static EveCompressor compressor = new EveCompressor();
    public static final DigitalInput topOpenBottomCloseSwitch = new DigitalInput(RobotMap.TOP_GRIPPER_OPEN_BOTTOM_GRIPPER_CLOSE_SWITCH);
    public static final DigitalInput topCloseMiddleOppenSwitch = new DigitalInput(RobotMap.TOP_GRIPPER_CLOSE_MIDDLE_GRIPPER_OPEN_SWITCH);
    public static final DigitalInput middleCloseSwitch = new DigitalInput(RobotMap.MIDDLE_GRIPPER_CLOSE_SWITCH);
    public static final DigitalInput bottomOpenSwitch = new DigitalInput(RobotMap.BOTTOM_GRIPPER_OPEN_SWITCH);
    public static Gripper bottomGripper = new Gripper(bottomOpenSwitch, topOpenBottomCloseSwitch, RobotMap.OPEN_BOTTOM_GRIPPER_SOLENOID, RobotMap.CLOSE_BOTTOM_GRIPPER_SOLENOID);
//    public static Gripper topGripper = new Gripper(topOpenBottomCloseSwitch, topCloseMiddleOppenSwitch, RobotMap.OPEN_TOP_GRIPPER_SOLENOID, RobotMap.CLOSE_TOP_GRIPPER_SOLENOID);
//    public static Gripper middleGripper = new Gripper(topCloseMiddleOppenSwitch, middleCloseSwitch, RobotMap.OPEN_MIDDLE_GRIPPER_SOLENOID, RobotMap.CLOSE_MIDDLE_GRIPPER_SOLENOID);
    
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        

        // Show what command your subsystem is running on the SmartDashboard
//        SmartDashboard.putData(exampleSubsystem);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
