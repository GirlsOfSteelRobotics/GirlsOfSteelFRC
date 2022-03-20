package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.math.util.Units;


public class OneBallAuto extends SequentialCommandGroup {

    private static final double VERTICAL_CONVEYOR_TIMEOUT = 3;
    private static final double DRIVE_DISTANCE = Units.inchesToMeters(40);
    private static final double ALLOWABLE_ERROR = 0.05;

    //Robot flat on wall and rolled back until the corners of the bumper are on the edge of the tape lines
    //Three Ariella Feet from the wall
    public OneBallAuto(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor) {
        super(
            new ShooterRpmPIDCommand(shooter, 1850),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(VERTICAL_CONVEYOR_TIMEOUT),
            new DriveDistanceCommand(chassis, DRIVE_DISTANCE, ALLOWABLE_ERROR));

    }
}
