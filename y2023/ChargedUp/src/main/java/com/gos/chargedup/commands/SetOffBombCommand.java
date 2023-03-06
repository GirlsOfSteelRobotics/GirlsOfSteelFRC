package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetOffBombCommand extends CommandBase {

    private String bomb;

    public SetOffBombCommand(String command) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        bomb = command;
        addRequirements();
    }

    @Override
    public void initialize() {

    }

    @Override
    //TODO: not really sure if this will actually set the variables back once the button is no longer being pushed???? idk figure it out
    public void execute() {
        if(bomb.equals("armNoWork"))
            ArmSubsystem.armNoWork = true;
        else if(bomb.equals("armPivotNoWork"))
            ArmSubsystem.armPivotNoWork = true;
        else if(bomb.equals("armExtendNoWork"))
            ArmSubsystem.armExtendNoWork = true;
        else if(bomb.equals("turretNoWork"))
            TurretSubsystem.turretNoWork = true;
        else if(bomb.equals("turretReverse"))
            TurretSubsystem.turretReverse = true;
        else if(bomb.equals("clawNoWork"))
            ClawSubsystem.clawNoWork = true;
        else if(bomb.equals("clawReverse"))
            ClawSubsystem.clawReverse = true;
        else if(bomb.equals("intakeNoWork"))
            IntakeSubsystem.intakeNoWork = true;
        else if(bomb.equals("intakeReverse"))
            IntakeSubsystem.intakeReverse = true;
        else {
            ArmSubsystem.armNoWork = false;
            ArmSubsystem.armPivotNoWork = false;
            ArmSubsystem.armExtendNoWork = false;
            TurretSubsystem.turretNoWork = false;
            TurretSubsystem.turretReverse = false;
            ClawSubsystem.clawNoWork = false;
            ClawSubsystem.clawReverse = false;
            IntakeSubsystem.intakeNoWork = false;
            IntakeSubsystem.intakeReverse = false;
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if(bomb.equals("armNoWork"))
            ArmSubsystem.armNoWork = false;
        else if(bomb.equals("armPivotNoWork"))
            ArmSubsystem.armPivotNoWork = false;
        else if(bomb.equals("armExtendNoWork"))
            ArmSubsystem.armExtendNoWork = false;
        else if(bomb.equals("turretNoWork"))
            TurretSubsystem.turretNoWork = false;
        else if(bomb.equals("turretReverse"))
            TurretSubsystem.turretReverse = false;
        else if(bomb.equals("clawNoWork"))
            ClawSubsystem.clawNoWork = false;
        else if(bomb.equals("clawReverse"))
            ClawSubsystem.clawReverse = false;
        else if(bomb.equals("intakeNoWork"))
            IntakeSubsystem.intakeNoWork = false;
        else
            IntakeSubsystem.intakeReverse = false;
    }
}
