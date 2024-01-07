package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.Constants;
import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveOffTarmac extends SequentialCommandGroup {

    private static final double DRIVE_OFF_TARMAC_DISTANCE = .5 * (Constants.ROBOT_LENGTH + Constants.TARMAC_DEPTH);
    private static final double ALLOWABLE_ERROR = Units.inchesToMeters(6);

    public DriveOffTarmac(ChassisSubsystem chassis) {
        super(
            new DriveDistanceCommand(chassis, DRIVE_OFF_TARMAC_DISTANCE, ALLOWABLE_ERROR)
        );
    }
}
