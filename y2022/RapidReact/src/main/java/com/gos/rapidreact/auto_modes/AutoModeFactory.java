package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.Constants;

public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;

    private static final boolean ENABLE_AUTO_SELECTION = true;

    private final Command m_defaultCommand;

    private static final double DRIVE_OFF_TARMAC_DISTANCE = .5 * (Constants.ROBOT_LENGTH + Constants.TARMAC_DEPTH);
    private static final double ALLOWABLE_ERROR = Units.inchesToMeters(6);


    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(ChassisSubsystem chassis) {
        //need to have distance
        m_sendableChooser = new SendableChooser<>();

        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }


        m_defaultCommand = new DriveDistanceCommand(chassis, DRIVE_OFF_TARMAC_DISTANCE, ALLOWABLE_ERROR);
        //need to have distance, allowableError
        m_sendableChooser.setDefaultOption("DriveOffTarmac (Default)", m_defaultCommand);



    }


    public Command getAutonomousMode() {
        if (ENABLE_AUTO_SELECTION) {
            return m_sendableChooser.getSelected();
        }
        else {
            return m_defaultCommand;
        }
    }
}
