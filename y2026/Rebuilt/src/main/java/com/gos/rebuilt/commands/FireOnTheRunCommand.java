package com.gos.rebuilt.commands;

import com.gos.lib.GetAllianceUtil;
import com.gos.lib.pathing.MaybeFlippedTranslation3d;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.gos.rebuilt.FireOnTheRun;
import com.gos.rebuilt.enums.Regions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.FeederSubsystem;
import com.gos.rebuilt.subsystems.PizzaSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.littletonrobotics.frc2026.FieldConstants.Hub;

public class FireOnTheRunCommand extends Command {
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Fire On The Run Throttling", 0.5);

    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_joystick;
    private final ShooterSubsystem m_shooter;
    private final PizzaSubsystem m_pizza;
    private final FeederSubsystem m_feeder;
    private final FireOnTheRun m_fotr;
    public static final MaybeFlippedTranslation3d LEFT_AIMING_TARGET = new MaybeFlippedTranslation3d(Hub.innerCenterPoint.getTranslation().getX() - 3, Hub.innerCenterPoint.getTranslation().getY() + 2, 0);
    public static final MaybeFlippedTranslation3d RIGHT_AIMING_TARGET = new MaybeFlippedTranslation3d(Hub.innerCenterPoint.getTranslation().getX() - 3, Hub.innerCenterPoint.getTranslation().getY() - 2, 0);

    public FireOnTheRunCommand(CommandXboxController joystick, ChassisSubsystem chassis, FeederSubsystem feeder, PizzaSubsystem pizza, ShooterSubsystem shooter) {
        m_joystick = joystick;
        m_chassis = chassis;
        m_pizza = pizza;
        m_shooter = shooter;
        m_feeder = feeder;

        m_fotr = new FireOnTheRun(chassis, shooter);

        addRequirements(m_chassis);
        addRequirements(m_pizza);
        addRequirements(m_shooter);
        addRequirements(m_feeder);
    }

    @Override
    public void execute() {
        MaybeFlippedTranslation3d goal;
        if (m_chassis.getRegion().equals(Regions.ALLIANCE)) {
            goal = new MaybeFlippedTranslation3d(Hub.topCenterPoint);
        } else if (m_chassis.getRegion().equals(Regions.LEFT)) {
            goal = LEFT_AIMING_TARGET;
        } else {
            goal = RIGHT_AIMING_TARGET;
        }
        Translation3d imaginaryPoint = m_fotr.findImaginary(goal.getTranslation());
        m_chassis.staringDrive(
            MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
            m_chassis.getShooterFaceAngle(imaginaryPoint.toTranslation2d())
        );
        if(m_chassis.getRegion().equals(Regions.ALLIANCE)) {
            m_shooter.shootFromDistance(m_chassis.getDistanceToObject(imaginaryPoint.toTranslation2d()));
        }
        else{
            m_shooter.shootFromDistanceBoosted(m_chassis.getDistanceToObject(imaginaryPoint.toTranslation2d()));
        }
        m_pizza.feed();
        m_feeder.feed();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
        m_pizza.stop();
        m_feeder.stop();
    }
}
