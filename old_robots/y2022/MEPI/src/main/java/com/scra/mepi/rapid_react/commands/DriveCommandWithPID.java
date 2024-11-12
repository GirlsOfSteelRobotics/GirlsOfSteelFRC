package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveCommandWithPID extends Command {
    private final DrivetrainSubsystem m_drivetrain;

    public DriveCommandWithPID(DrivetrainSubsystem drivetrainSubsystem) {
        m_drivetrain = drivetrainSubsystem;
        addRequirements(m_drivetrain);
    }

    @Override
    public void execute() {
        m_drivetrain.smartVelocityControl(Units.inchesToMeters(48), Units.inchesToMeters(48));
    }

}
