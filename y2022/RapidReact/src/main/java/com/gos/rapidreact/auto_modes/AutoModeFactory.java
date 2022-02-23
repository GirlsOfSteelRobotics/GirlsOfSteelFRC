package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.TrajectoryB5;
import com.gos.rapidreact.trajectory.TrajectoryB54;
import com.gos.rapidreact.trajectory.TrajectoryCurve;
import com.gos.rapidreact.trajectory.TrajectorySCurve;
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
    private static final double VERTICAL_CONVEYOR_TIMEOUT = 10;


    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor) {
        //need to have distance
        m_sendableChooser = new SendableChooser<>();

        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }


        m_defaultCommand = new DriveDistanceCommand(chassis, DRIVE_OFF_TARMAC_DISTANCE, ALLOWABLE_ERROR);
        //need to have distance, allowableError
        m_sendableChooser.setDefaultOption("DriveOffTarmac (Default)", m_defaultCommand);
        m_sendableChooser.addOption("One Ball Auto", new OneBallAuto(chassis, shooter, verticalConveyor, VERTICAL_CONVEYOR_TIMEOUT));
        m_sendableChooser.addOption("B54", TrajectoryB54.fromBto5to4(chassis));
        m_sendableChooser.addOption("B5 (straight)", TrajectoryB5.fromBto5(chassis));
        m_sendableChooser.addOption("TestCurve", TrajectoryCurve.curve(chassis));
        m_sendableChooser.addOption("TestSCurve", TrajectorySCurve.scurve(chassis));
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
