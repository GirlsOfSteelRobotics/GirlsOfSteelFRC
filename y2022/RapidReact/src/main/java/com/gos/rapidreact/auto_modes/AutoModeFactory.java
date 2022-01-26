package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rapidreact.commands.autonomous.DriveOffTarmacCommand;


public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;

    private static final boolean ENABLE_AUTO_SELECTION = true;

    private final Command m_defaultCommand;



    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(ChassisSubsystem chassis) {
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


        m_defaultCommand = new DriveOffTarmacCommand(chassis);



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
