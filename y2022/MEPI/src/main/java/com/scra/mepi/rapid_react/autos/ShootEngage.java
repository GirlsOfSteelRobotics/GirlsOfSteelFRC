package com.scra.mepi.rapid_react.autos;

import com.scra.mepi.rapid_react.commands.ShooterPIDCommand;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootEngage extends SequentialCommandGroup {
    public ShootEngage(DrivetrainSubsystem drivetrain, ShooterSubsytem shooter, String path) {
        Command shootEngage = drivetrain.createPathFollowingCommand(path);

        // STEP 1: shoot a ball
        addCommands(new ShooterPIDCommand(shooter));

        // STEP 2: move back onto charging station
        addCommands(shootEngage);


    }


}
