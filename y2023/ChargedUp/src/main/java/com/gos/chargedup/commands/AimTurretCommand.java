package com.gos.chargedup.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AimTurretCommand extends CommandBase {
    private final ArmSubsystem m_armSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final TurretSubsystem m_turretSubsystem;
    private double targetX;
    private double targetY;

    private double currentX;
    private double currentY;

    private double targetPitch;

    public AimTurretCommand(ArmSubsystem m_armSubsystem, ChassisSubsystem m_chassisSubsystem, TurretSubsystem m_turretSubsystem, double x, double y, double pitch) {
        this.m_armSubsystem = m_armSubsystem;
        this.m_chassisSubsystem = m_chassisSubsystem;
        this.m_turretSubsystem = m_turretSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_turretSubsystem);

        targetX = x;
        targetY = y;

        targetPitch = pitch;


    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        currentX = m_chassisSubsystem.getPose().getX();
        currentY = m_chassisSubsystem.getPose().getY();

        System.out.println("goal x: " + targetX + "\ngoal y: " + targetY);
        System.out.println("current x: " + currentX + "\ncurrent y: " + currentY);

        double currentAngle = m_chassisSubsystem.getPose().getRotation().getDegrees();

        double turretAngle = Math.toDegrees(Math.atan2(targetY-currentY, targetX-currentX)) - currentAngle;
        System.out.println("turret angle: " + turretAngle);
        m_turretSubsystem.turretPID(turretAngle);

        System.out.println("pitch: " + targetPitch);
        m_armSubsystem.commandPivotArmToAngle(targetPitch);



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
