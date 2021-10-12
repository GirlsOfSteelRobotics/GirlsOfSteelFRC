package com.gos.codelabs.gitflow;

import com.gos.codelabs.gitflow.commands.TeleopDriveCommand;
import com.gos.codelabs.gitflow.subsystems.ChassisSubsystem;
import com.gos.codelabs.gitflow.subsystems.GraceGCodeLab2020Subsystem;
import com.gos.codelabs.gitflow.subsystems.JacksonCodelab2020Part1Part1;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.codelabs.gitflow.subsystems.PJCodelab2020Part1Subsystem;
import com.gos.codelabs.gitflow.subsystems.PJCodelab2020Part2Subsystem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class RobotContainer {
    private static final String DRIVETRAIN_NAME = "m_drivetrain";

    private PJCodelab2020Part1Subsystem m_pjCodelab2020Part1;
    private PJCodelab2020Part2Subsystem m_pjCodelab2020Part2;
    private JacksonCodelab2020Part1Part1 m_jacksonCodeLab2020Part1;
    private GraceGCodeLab2020Subsystem m_GraceCodeLab2020pt1;

    ///////////////////////////////////////
    // Don't touch things below here
    ///////////////////////////////////////

    private final ChassisSubsystem m_drivetrain;

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        m_drivetrain = new ChassisSubsystem();

        Field[] fields = RobotContainer.class.getDeclaredFields();

        List<String> sortedNames = new ArrayList<>();
        for (Field field : fields) {
            if (!DRIVETRAIN_NAME.equals(field.getName())) {
                sortedNames.add(field.getType().getName());
            }
        }

        sortedNames.sort(String::compareTo);

        System.out.println("\n\n\n\n********************************************************");
        for (String className : sortedNames) {
            try {
                Class<?> clazz = Class.forName(className);
                clazz.getDeclaredConstructor().newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace(); // NOPMD
            }
        }
        System.out.println("********************************************************\n\n\n\n");

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton JoystickButton}.
     */
    private void configureButtonBindings() {
        m_drivetrain.setDefaultCommand(new TeleopDriveCommand(m_drivetrain));
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return null;
    }
}
