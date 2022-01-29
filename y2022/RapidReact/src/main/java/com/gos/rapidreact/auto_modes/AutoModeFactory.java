package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rapidreact.commands.autonomous.DriveOffTarmacCommand;
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
        // -- currently not using chassis
        m_sendableChooser = new SendableChooser<>();
        //TrajectoryModeFactory trajectoryModeFactory = new TrajectoryModeFactory();

        /*
        if (TEST_MODE) {
            m_sendableChooser.addOption("Limelight", new AlignLeftRight(chassis, limelight));
        }
        */

        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }


        m_defaultCommand = new DriveOffTarmacCommand(chassis, DRIVE_OFF_TARMAC_DISTANCE, ALLOWABLE_ERROR);
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
