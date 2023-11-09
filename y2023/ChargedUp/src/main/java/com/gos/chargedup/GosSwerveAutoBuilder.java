package com.gos.chargedup;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.BaseAutoBuilder;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GosSwerveAutoBuilder extends BaseAutoBuilder {
    private final PIDController m_xTranslationController;
    private final PIDController m_yTranslationController;
    private final PIDController m_rotationController;
    private final Consumer<ChassisSpeeds> m_outputChassisSpeeds;
    private final Subsystem[] m_driveRequirements;

    /**
     * Create an auto builder that will create command groups that will handle path following and
     * triggering events.
     *
     * <p>This auto builder will use PPSwerveControllerCommand to follow paths.
     *
     * @param poseSupplier A function that supplies the robot pose - use one of the odometry classes
     *     to provide this.
     * @param resetPose A consumer that accepts a Pose2d to reset robot odometry. This will typically
     *     be called once at the beginning of an auto.
     * @param xTranslationController PID Controller for the controller that will correct for translation
     *     error
     * @param rotationController PID Controller for the controller that will correct for rotation error
     * @param outputChassisSpeeds A function that takes the output ChassisSpeeds from path following
     *     commands
     * @param eventMap Map of event marker names to the commands that should run when reaching that
     *     marker.
     * @param driveRequirements The subsystems that the path following commands should require.
     *     Usually just a Drive subsystem.
     */
    public GosSwerveAutoBuilder(
        Supplier<Pose2d> poseSupplier,
        Consumer<Pose2d> resetPose,
        PIDController xTranslationController,
        PIDController yTranslationController,
        PIDController rotationController,
        Consumer<ChassisSpeeds> outputChassisSpeeds,
        Map<String, Command> eventMap,
        Subsystem... driveRequirements) {
        this(
            poseSupplier,
            resetPose,
            xTranslationController,
            yTranslationController,
            rotationController,
            outputChassisSpeeds,
            eventMap,
            false,
            driveRequirements);
    }

    /**
     * Create an auto builder that will create command groups that will handle path following and
     * triggering events.
     *
     * <p>This auto builder will use PPSwerveControllerCommand to follow paths.
     *
     * @param poseSupplier A function that supplies the robot pose - use one of the odometry classes
     *     to provide this.
     * @param resetPose A consumer that accepts a Pose2d to reset robot odometry. This will typically
     *     be called once at the beginning of an auto.
     * @param xTranslationController PID Controller for the controller that will correct for translation
     *     error
     * @param rotationController PID Controller for the controller that will correct for rotation error
     * @param outputChassisSpeeds A function that takes the output ChassisSpeeds from path following
     *     commands
     * @param eventMap Map of event marker names to the commands that should run when reaching that
     *     marker.
     * @param useAllianceColor Should the path states be automatically transformed based on alliance
     *     color? In order for this to work properly, you MUST create your path on the blue side of
     *     the field.
     * @param driveRequirements The subsystems that the path following commands should require.
     *     Usually just a Drive subsystem.
     */
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public GosSwerveAutoBuilder(
        Supplier<Pose2d> poseSupplier,
        Consumer<Pose2d> resetPose,
        PIDController xTranslationController,
        PIDController yTranslationController,
        PIDController rotationController,
        Consumer<ChassisSpeeds> outputChassisSpeeds,
        Map<String, Command> eventMap,
        boolean useAllianceColor,
        Subsystem... driveRequirements) {
        super(poseSupplier, resetPose, eventMap, DrivetrainType.HOLONOMIC, useAllianceColor);

        this.m_xTranslationController = xTranslationController;
        this.m_yTranslationController = yTranslationController;
        this.m_rotationController = rotationController;
        this.m_outputChassisSpeeds = outputChassisSpeeds;
        this.m_driveRequirements = driveRequirements;
    }

    @Override
    public Command followPath(PathPlannerTrajectory trajectory) {
        return new PPSwerveControllerCommand(
                trajectory,
                poseSupplier,
            m_xTranslationController,
            m_yTranslationController,
            m_rotationController,
            m_outputChassisSpeeds,
                useAllianceColor,
            m_driveRequirements);

    }
}
