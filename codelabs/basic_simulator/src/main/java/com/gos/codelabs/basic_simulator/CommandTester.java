package com.gos.codelabs.basic_simulator;

import com.gos.codelabs.basic_simulator.commands.AutoDriveStraightDistanceCommand;
import com.gos.codelabs.basic_simulator.commands.AutoDriveStraightTimedCommand;
import com.gos.codelabs.basic_simulator.commands.ElevatorToPositionCommand;
import com.gos.codelabs.basic_simulator.commands.MovePunchCommand;
import com.gos.codelabs.basic_simulator.commands.ShooterRpmCommand;
import com.gos.codelabs.basic_simulator.commands.TurnToAngleCommand;
import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ElevatorSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.PunchSubsystem;
import com.gos.codelabs.basic_simulator.subsystems.ShooterSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class CommandTester {

    public CommandTester(RobotContainer robotContainer) {
        // Alias for readability
        ChassisSubsystem chassis = robotContainer.getChassis(); // NOPMD(CloseResource)
        ElevatorSubsystem elevator = robotContainer.getElevator(); // NOPMD(CloseResource)
        PunchSubsystem punch = robotContainer.getPunch(); // NOPMD(CloseResource)
        ShooterSubsystem shooter = robotContainer.getShooter(); // NOPMD(CloseResource)

        // Elevator
        addCommand("Lift To Position Low", new ElevatorToPositionCommand(elevator, ElevatorSubsystem.Positions.LOW));
        addCommand("Lift To Position Mid", new ElevatorToPositionCommand(elevator, ElevatorSubsystem.Positions.MID));
        addCommand("Lift To Position High", new ElevatorToPositionCommand(elevator, ElevatorSubsystem.Positions.HIGH));

        addCommand("Test Timed Drive Forwards", new AutoDriveStraightTimedCommand(chassis, .5, 2));
        addCommand("Test Timed Drive Backwards", new AutoDriveStraightTimedCommand(chassis, -.5, 2));
        addCommand("Test Drive Distance Forwards", new AutoDriveStraightDistanceCommand(chassis, Units.feetToMeters(5)));
        addCommand("Test Drive Distance Backwards", new AutoDriveStraightDistanceCommand(chassis, Units.feetToMeters(-5)));
        addCommand("Test Turn To 90", new TurnToAngleCommand(chassis, 90));
        addCommand("Test Turn To -45", new TurnToAngleCommand(chassis, -45));
        addCommand("Test Turn To 0", new TurnToAngleCommand(chassis, 0));

        // Punch
        addCommand("Test Extend Punch", new MovePunchCommand(punch, true));
        addCommand("Test Retract Punch", new MovePunchCommand(punch, false));

        // Shooter
        addCommand("Test Shooter RPM", new ShooterRpmCommand(shooter, ShooterSubsystem.SHOOTING_RPM));
    }

    private void addCommand(String name, Command command) {
        command.setName(name);
        SmartDashboard.putData(name, command);
    }
}
