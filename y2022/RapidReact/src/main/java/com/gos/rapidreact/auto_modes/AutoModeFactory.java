package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<AutonMode> m_sendableChooser;

    private static final boolean ENABLE_AUTO_SELECTION = true;

    public enum AutonMode {
        DRIVE_OFF_TARMAC,
        ONE_BALL_LOW,
        ONE_BALL_HIGH,
        TWO_BALL_LOW,
        TWO_BALL_HIGH,
        THREE_BALL_LOW,
        FOUR_BALL_LOW,
        FOUR_BALL_HALF_HIGH,
        FOUR_BALL_HIGH,
        FIVE_BALL_LOW,
    }

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
    public AutoModeFactory(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector, ShooterLimelightSubsystem shooterLimelight) {
        //need to have distance
        m_sendableChooser = new SendableChooser<>();

        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }


        m_offTarmacAuto = new DriveOffTarmac(chassis);
        m_sendableChooser.addOption("Drive Off Tarmac (Default)", AutonMode.DRIVE_OFF_TARMAC);

        m_oneBallAutoLow = new OneBallAutoLow(chassis, shooter, verticalConveyor);
        m_sendableChooser.addOption("One Ball Auto Low", AutonMode.ONE_BALL_LOW);

        m_oneBallAutoHigh = new OneBallAutoHighCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor);
        m_sendableChooser.addOption("One Ball Auto High", AutonMode.ONE_BALL_HIGH);

        m_twoBallAutoLow = new TwoBallAutoCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.setDefaultOption("Two Ball Auto Low", AutonMode.TWO_BALL_LOW);

        m_twoBallAutoHigh = new TwoBallAutoHighCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Two Ball Auto High", AutonMode.TWO_BALL_HIGH);

        m_threeBallAutoLow = new ThreeBallAutoLow(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Three Ball Auto Low", AutonMode.THREE_BALL_LOW);

        m_fourBallAutoLow = new FourBallAutoLowCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Four Ball Auto Low", AutonMode.FOUR_BALL_LOW);

        m_fourBallAutoHalf = new FourBallAutoHalfCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Four Ball Auto Half", AutonMode.FOUR_BALL_HALF_HIGH);

        m_fourBallAutoHigh = new FourBallAutoHighCommandGroup(chassis, shooter, verticalConveyor, horizontalConveyor, collector, shooterLimelight);
        m_sendableChooser.addOption("Four Ball Auto High", AutonMode.FOUR_BALL_HIGH);

        m_fiveBallAutoLow = new FiveBallAutoLow(chassis, shooter, verticalConveyor, horizontalConveyor, collector);
        m_sendableChooser.addOption("Five Ball Auto Low", AutonMode.FIVE_BALL_LOW);
    }

    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public AutonMode autoModeLightSignal() {
        return m_sendableChooser.getSelected();
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private Command getCommandFromChooser(AutonMode mode) {
        switch (mode) {
        case DRIVE_OFF_TARMAC:
            return m_offTarmacAuto;
        case ONE_BALL_LOW:
            return m_oneBallAutoLow;
        case ONE_BALL_HIGH:
            return m_oneBallAutoHigh;
        case TWO_BALL_LOW:
            return m_twoBallAutoLow;
        case TWO_BALL_HIGH:
            return m_twoBallAutoHigh;
        case THREE_BALL_LOW:
            return m_threeBallAutoLow;
        case FOUR_BALL_LOW:
            return m_fourBallAutoLow;
        case FOUR_BALL_HALF_HIGH:
            return m_fourBallAutoHalf;
        case FOUR_BALL_HIGH:
            return m_fourBallAutoHigh;
        case FIVE_BALL_LOW:
            return m_fiveBallAutoLow;
        default:
            return null;
        }
    }

    public Command getAutonomousMode() {
        if (ENABLE_AUTO_SELECTION) {
            return getCommandFromChooser(m_sendableChooser.getSelected());
        }
        else {
            return m_offTarmacAuto;
        }
    }
}
