package com.gos.crescendo2024.commands;

import com.gos.crescendo2024.FieldConstants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.crescendo2024.subsystems.ArmPivotSubsystem;
import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.crescendo2024.subsystems.IntakeSubsystem;
import com.gos.crescendo2024.subsystems.ShooterSubsystem;


public class SpeakerShooterCommand extends CommandBase {
    private final ArmPivotSubsystem armPivotSubsystem;
    private final ChassisSubsystem chassisSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final ShooterSubsystem shooterSubsystem;

    public SpeakerShooterCommand(ArmPivotSubsystem armPivotSubsystem, ChassisSubsystem chassisSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        this.armPivotSubsystem = armPivotSubsystem;
        this.chassisSubsystem = chassisSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.armPivotSubsystem, this.chassisSubsystem, this.intakeSubsystem, this.shooterSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
    armPivotSubsystem.moveArmToAngle(ArmPivotSubsystem.ARM_SPEAKER_ANGLE.getValue());
    chassisSubsystem.turnToPointDrive(0, 0, FieldConstants.Speaker.CENTER_SPEAKER_OPENING);
    shooterSubsystem.setPidRpm(ShooterSubsystem.SHOOTER_SPEED.getValue());

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
