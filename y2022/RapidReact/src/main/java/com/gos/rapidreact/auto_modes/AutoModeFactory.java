package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.subsystems.Chassis;



public class AutoModeFactory extends SequentialCommandGroup {

    private final SendableChooser<Command> m_sendableChooser;
    private static final boolean TEST_MODE = true;

    private static final boolean ENABLE_AUTO_SELECTION = true;

    private final Command m_defaultCommand;



    /**
     * Creates a new AutomatedConveyorIntake.
     */
    public AutoModeFactory(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake, Limelight limelight) {

        m_sendableChooser = new SendableChooser<>();
        TrajectoryModeFactory trajectoryModeFactory = new TrajectoryModeFactory();

        if (TEST_MODE) {
            m_sendableChooser.addOption("Limelight", new AlignLeftRight(chassis, limelight));
        }


        if (ENABLE_AUTO_SELECTION) {
            SmartDashboard.putData("Auto Mode", m_sendableChooser);
        }


        m_defaultCommand = new ShootToDriveForwardsNoSensor(chassis, shooter, shooterConveyor, shooterIntake, true, Constants.DEFAULT_RPM);



    }

    private Command createDrivePointCommand(Chassis chassis, double x, double y, double allowableError) {
        return new SetStartingPosition(chassis, 27 * 12, -13.5 * 12, 0).andThen(new GoToPosition(chassis, x, y, allowableError));
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