package com.gos.chargedup.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;


public class AimTurretCommand extends CommandBase {
    private static final Field2d DEBUG_FIELD = new Field2d();

    static {
        Shuffleboard.getTab("Debug").add(DEBUG_FIELD);
    }

    private final ArmSubsystem m_armSubsystem;
    private final ChassisSubsystem m_chassisSubsystem;
    private final TurretSubsystem m_turretSubsystem;
    private final double m_targetX;
    private final double m_targetY;
    private final double m_targetPitch;

    private double m_currentX;
    private double m_currentY;



    public AimTurretCommand(ArmSubsystem m_armSubsystem, ChassisSubsystem m_chassisSubsystem, TurretSubsystem m_turretSubsystem, double x, double y, double pitch) {
        this.m_armSubsystem = m_armSubsystem;
        this.m_chassisSubsystem = m_chassisSubsystem;
        this.m_turretSubsystem = m_turretSubsystem;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_armSubsystem, this.m_turretSubsystem);

        m_targetX = x;
        m_targetY = y;

        m_targetPitch = pitch;

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        DEBUG_FIELD.setRobotPose(m_chassisSubsystem.getPose());
        DEBUG_FIELD.getObject("AimGoal").setPose(new Pose2d(m_targetX, m_targetY, Rotation2d.fromDegrees(0)));

        m_currentX = m_chassisSubsystem.getPose().getX();
        m_currentY = m_chassisSubsystem.getPose().getY();

        double closestYvalue = m_chassisSubsystem.findingClosestNode(m_targetY);
        System.out.println(closestYvalue);

        double currentAngle = m_chassisSubsystem.getPose().getRotation().getDegrees();

        double targetAngle = Math.toDegrees(Math.atan2((closestYvalue) - m_currentY, m_targetX - m_currentX));

        double turretAngle = currentAngle - targetAngle;


        // System.out.println("\ngoal x: " + m_targetX + "\ngoal y: " + m_targetY);
        // System.out.println("current x: " + m_currentX + "\ncurrent y: " + m_currentY);
        // System.out.println("target angle: " + targetAngle);
        // System.out.println("robot angle: " + currentAngle);
        // System.out.println("turret angle: " + turretAngle);


        if (turretAngle > 180)
            turretAngle -= 360;
        else if (turretAngle < -180)
            turretAngle += 360;

        m_turretSubsystem.moveTurretToAngleWithPID(turretAngle);


        //System.out.println("pitch: " + m_targetPitch);
        m_armSubsystem.commandPivotArmToAngle(m_targetPitch);

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
