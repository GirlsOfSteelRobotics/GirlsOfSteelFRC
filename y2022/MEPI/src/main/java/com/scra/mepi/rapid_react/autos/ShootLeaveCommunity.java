package com.scra.mepi.rapid_react.autos;

import com.scra.mepi.rapid_react.commands.ShooterPIDCommand;
import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootLeaveCommunity extends SequentialCommandGroup {
    //CONSTRUCTOR
    public ShootLeaveCommunity(DrivetrainSubsystem drivetrain, ShooterSubsytem shooter, String path) {
        Command shootAndLeaveCommunity = drivetrain.createPathFollowingCommand(path);
        //shoot a ball
        addCommands(new ShooterPIDCommand(shooter));

        //leave community
        addCommands(shootAndLeaveCommunity);
    }
}
