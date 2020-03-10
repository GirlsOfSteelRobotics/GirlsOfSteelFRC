package frc.robot.auto_modes;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.AutomatedConveyorIntake;
import frc.robot.commands.ConveyorWhileHeld;
import frc.robot.commands.IntakeCells;
import frc.robot.commands.MovePiston;
import frc.robot.commands.autonomous.AutoShoot;
import frc.robot.commands.autonomous.SetStartingPosition;
import frc.robot.commands.autonomous.TurnToAngle;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;
import frc.robot.subsystems.ShooterIntake;
import frc.robot.trajectory_modes.TrajectoryModeFactory;

public class ShootAndDriveToOpponentsTrenchCommandGroup extends SequentialCommandGroup {
    public ShootAndDriveToOpponentsTrenchCommandGroup(Chassis chassis, Shooter shooter, ShooterConveyor shooterConveyor, ShooterIntake shooterIntake, TrajectoryModeFactory trajectoryFactory,
                                                      boolean useSensor) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        addCommands(new SetStartingPosition(chassis, Constants.AUTO_LINE_LEFT_X, Constants.AUTO_LINE_LEFT_Y, 0));
        addCommands(new TurnToAngle(chassis, -25, 10));
        addCommands(new MovePiston(shooterIntake, true));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 2)); //Shoot pre-loaded cells
        addCommands(trajectoryFactory.getTrajectoryAutoLineToOpponentsTrench(chassis)
            .raceWith(new IntakeCells(shooterIntake, true)));

        if (useSensor) {
            SequentialCommandGroup intake = new SequentialCommandGroup();
            intake.addCommands(new AutomatedConveyorIntake(shooterIntake, shooterConveyor),
                    new AutomatedConveyorIntake(shooterIntake, shooterConveyor),
                    new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
         //               .raceWith(trajectoryFactory.getTrajectoryOpponentsTrenchToPickUpCell(chassis)));
        }
        else {
            addCommands(new ConveyorWhileHeld(shooterConveyor, true)
                    .raceWith(new IntakeCells(shooterIntake, true).withTimeout(1.2)));
         //           .raceWith(trajectoryFactory.getTrajectoryOpponentsTrenchToPickUpCell(chassis)));
        }
        addCommands(new IntakeCells(shooterIntake, true).withTimeout(.5)
            .raceWith(new ConveyorWhileHeld(shooterConveyor, false)).withTimeout(.15));
        addCommands(trajectoryFactory.getTrajectoryOpponentsTrenchToAutoLine(chassis));
        addCommands(new TurnToAngle(chassis, -30, 12));
        addCommands(new AutoShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM, 2));
    }
}