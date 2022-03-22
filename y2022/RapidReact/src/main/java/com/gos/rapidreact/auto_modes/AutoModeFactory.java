package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;

    private static final boolean ENABLE_AUTO_SELECTION = true;


    private final CommandBase m_offTarmacAuto;
    private final CommandBase m_oneBallAutoLow;
    private final CommandBase m_oneBallAutoHigh;
    private final CommandBase m_twoBallAutoLow;
    private final CommandBase m_twoBallAutoHigh;
    private final CommandBase m_threeBallAutoLow;
    private final CommandBase m_fourBallAutoLow;
    private final CommandBase m_fourBallAutoHalf;
    private final CommandBase m_fourBallAutoHigh;
    private final CommandBase m_fiveBallAutoLow;





    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        //need to have distance
        m_sendableChooser = new SendableChooser<>();

        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }


        m_offTarmacAuto = new DriveOffTarmac(chassis);
        m_sendableChooser.addOption("Drive Off Tarmac (Default)", m_offTarmacAuto);

        m_oneBallAutoLow = new OneBallAutoLow(chassis, shooter, verticalConveyor);
        m_sendableChooser.addOption("One Ball Auto Low", m_oneBallAutoLow);

        m_oneBallAutoHigh = new OneBallAutoHighCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor);
        m_sendableChooser.addOption("One Ball Auto High", m_oneBallAutoHigh);

        m_twoBallAutoLow = new TwoBallAutoCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.setDefaultOption("Two Ball Auto Low", m_twoBallAutoLow);

        m_twoBallAutoHigh = new TwoBallAutoHighCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Two Ball Auto High", m_twoBallAutoHigh);

        m_threeBallAutoLow = new ThreeBallAutoLow(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Three Ball Auto Low", m_threeBallAutoLow);

        m_fourBallAutoLow = new FourBallAutoLowCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Four Ball Auto Low", m_fourBallAutoLow);

        m_fourBallAutoHalf = new FourBallAutoHalfCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Four Ball Auto Half", m_fourBallAutoHalf);

        m_fourBallAutoHigh = new FourBallAutoHighCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Four Ball Auto High", m_fourBallAutoHigh);

        m_fiveBallAutoLow = new FiveBallAutoLow(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Five Ball Auto Low", m_fiveBallAutoLow);

    }

    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public int autoModeLightSignal() {
        int autoMode = -1;
        if (m_sendableChooser.getSelected() == m_offTarmacAuto) {
            autoMode = 0;
        }

        if (m_sendableChooser.getSelected() == m_oneBallAutoLow) {
            autoMode = 1;
        }

        if (m_sendableChooser.getSelected() == m_twoBallAutoLow) {
            autoMode = 2;
        }

        if (m_sendableChooser.getSelected() == m_threeBallAutoLow) {
            autoMode = 3;
        }

        if (m_sendableChooser.getSelected() == m_fourBallAutoLow) {
            autoMode = 4;
        }

        if (m_sendableChooser.getSelected() == m_fiveBallAutoLow) {
            autoMode = 5;
        }
        return autoMode;
    }


    public Command getAutonomousMode() {
        if (ENABLE_AUTO_SELECTION) {
            return m_sendableChooser.getSelected();
        }
        else {
            return m_offTarmacAuto;
        }
    }
}
