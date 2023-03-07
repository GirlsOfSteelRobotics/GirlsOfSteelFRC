package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ArmPivotSubsystem;
import com.gos.chargedup.subsystems.ArmExtensionSubsystem;
import com.gos.chargedup.subsystems.ClawSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetOffBombCommand extends CommandBase {

    private final String m_bomb;

    public SetOffBombCommand(String command) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        m_bomb = command;
        addRequirements();
    }

    @Override
    public void initialize() {

    }

    @Override
    //TODO: not really sure if this will actually set the variables back once the button is no longer being pushed???? idk figure it out
    public void execute() {
        if(m_bomb.equals("armNoWork")) {
            ArmPivotSubsystem.armPivotNoWork = true;
            ArmExtensionSubsystem.armExtendNoWork = true;
        } else if(m_bomb.equals("armPivotNoWork"))
            ArmPivotSubsystem.armPivotNoWork = true;
        else if(m_bomb.equals("armExtendNoWork"))
            ArmExtensionSubsystem.armExtendNoWork = true;
        else if(m_bomb.equals("turretNoWork"))
            TurretSubsystem.turretNoWork = true;
        else if(m_bomb.equals("turretReverse"))
            TurretSubsystem.turretReverse = true;
        else if(m_bomb.equals("clawNoWork"))
            ClawSubsystem.clawNoWork = true;
        else if(m_bomb.equals("clawReverse"))
            ClawSubsystem.clawReverse = true;
        else if(m_bomb.equals("intakeNoWork"))
            IntakeSubsystem.intakeNoWork = true;
        else if(m_bomb.equals("intakeReverse"))
            IntakeSubsystem.intakeReverse = true;
        else {
            ArmPivotSubsystem.armPivotNoWork = false;
            ArmExtensionSubsystem.armExtendNoWork = false;
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
        if(m_bomb.equals("armPivotNoWork"))
            ArmPivotSubsystem.armPivotNoWork = false;
        else if(m_bomb.equals("armExtendNoWork"))
            ArmExtensionSubsystem.armExtendNoWork = false;
        else if(m_bomb.equals("turretNoWork"))
            TurretSubsystem.turretNoWork = false;
        else if(m_bomb.equals("turretReverse"))
            TurretSubsystem.turretReverse = false;
        else if(m_bomb.equals("clawNoWork"))
            ClawSubsystem.clawNoWork = false;
        else if(m_bomb.equals("clawReverse"))
            ClawSubsystem.clawReverse = false;
        else if(m_bomb.equals("intakeNoWork"))
            IntakeSubsystem.intakeNoWork = false;
        else
            IntakeSubsystem.intakeReverse = false;
    }
}
