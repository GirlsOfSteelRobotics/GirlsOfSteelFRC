package com.gos.reefscape.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismObject2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("PMD.CloseResource")
public class SuperStructureViz extends SubsystemBase {
    private static final double CANVAS_WIDTH = Units.inchesToMeters(60);
    private static final double CANVAS_HEIGHT = Units.inchesToMeters(120);

    private static final double ELEVATOR_BASE_HEIGHT_METERS = 0.42;
    private static final double ELEVATOR_MECH2D_OFFSET = 0.5;

    private final ElevatorSubsystem m_elevator;
    private final PivotSubsystem m_pivot;
    private final MechanismLigament2d m_goalElevatorHeight;
    private final MechanismLigament2d m_elevatorMechanism;
    private final MechanismLigament2d m_goalPivotHeight;
    private final MechanismLigament2d m_pivotMechanism;

    private final StructArrayPublisher<Pose3d> m_ascopeMeasuredPosesEntry;
    private final StructArrayPublisher<Pose3d> m_ascopeGoalPosesEntry;

    private final Pose3d[] m_ascopeMeasuredPoses = {
        new Pose3d(0, 0, 0, new Rotation3d()), // Bumpers
        new Pose3d(0, 0, 0, new Rotation3d()), // First stage
        new Pose3d(0, 0, 0, new Rotation3d()), // Ramp
        new Pose3d(0, 0, 0, new Rotation3d()) // Mast
    };

    private final Pose3d[] m_ascopeGoalPoses = {
        new Pose3d(0, 0, 0, new Rotation3d()), // Bumpers
        new Pose3d(0, 0, 0, new Rotation3d()), // First stage
        new Pose3d(0, 0, 0, new Rotation3d()), // Ramp
        new Pose3d(0, 0, 0, new Rotation3d()) // Mast
    };

    public SuperStructureViz(ElevatorSubsystem elevatorSubsystem, PivotSubsystem pivotSubsystem) {
        Mechanism2d canvas = new Mechanism2d(CANVAS_WIDTH, CANVAS_HEIGHT);
        SmartDashboard.putData("SuperStructureMech", canvas);

        m_ascopeMeasuredPosesEntry = NetworkTableInstance.getDefault().getTable("SmartDashboard").getStructArrayTopic("AScopeMeasuredSuperstructure", Pose3d.struct).getEntry(new Pose3d[]{});
        m_ascopeGoalPosesEntry = NetworkTableInstance.getDefault().getTable("SmartDashboard").getStructArrayTopic("AScopeGoalSuperstructure", Pose3d.struct).getEntry(new Pose3d[]{});


        addReef(canvas);

        m_elevator = elevatorSubsystem;
        m_pivot = pivotSubsystem;

        MechanismRoot2d root = canvas.getRoot("elevator", CANVAS_WIDTH / 2, 0);
        m_elevatorMechanism = root.append(new MechanismLigament2d("elevator", 1, 90, 13, new Color8Bit(Color.kRoyalBlue)));
        m_goalElevatorHeight = root.append(new MechanismLigament2d("elevator goal height", 1, 90, 7, new Color8Bit(Color.kTomato)));
        m_pivotMechanism = m_elevatorMechanism.append(new MechanismLigament2d("pivot", .5, 90, 13, new Color8Bit(Color.kPapayaWhip)));
        m_goalPivotHeight = m_elevatorMechanism.append(new MechanismLigament2d("pivot goal height", .5, 90, 7, new Color8Bit(Color.kGold)));
    }

    @Override
    public void periodic() {

        double actualHeight = m_elevator.getHeight();
        double goalHeight = m_elevator.getGoalHeight();
        if (goalHeight == ElevatorSubsystem.NO_GOAL_HEIGHT) {
            goalHeight = actualHeight;
        }

        double actualAngle = m_pivot.getRelativeAngle();
        double goalAngle = m_pivot.getArmGoalAngle();
        if (goalAngle == PivotSubsystem.NO_GOAL_ANGLE) {
            goalAngle = actualAngle;
        }

        m_elevatorMechanism.setLength(ELEVATOR_BASE_HEIGHT_METERS + ELEVATOR_MECH2D_OFFSET + actualHeight);
        m_goalElevatorHeight.setLength(ELEVATOR_BASE_HEIGHT_METERS + ELEVATOR_MECH2D_OFFSET + goalHeight);
        m_goalPivotHeight.setAngle(goalAngle - 90);
        m_pivotMechanism.setAngle(actualAngle - 90);

        m_ascopeMeasuredPoses[0] = new Pose3d(0.25, 0, ELEVATOR_BASE_HEIGHT_METERS + actualHeight, new Rotation3d(0, -Math.toRadians(actualAngle), 0)); // Bumpers
        m_ascopeMeasuredPoses[1] = new Pose3d(0, 0, actualHeight / 2, new Rotation3d()); // First stage

        m_ascopeGoalPoses[0] = new Pose3d(0.25, 0, ELEVATOR_BASE_HEIGHT_METERS + goalHeight, new Rotation3d(0, -Math.toRadians(goalAngle), 0)); // Bumpers
        m_ascopeGoalPoses[1] = new Pose3d(0, 0, goalHeight / 2, new Rotation3d()); // First stage

        m_ascopeMeasuredPosesEntry.set(m_ascopeMeasuredPoses);
        m_ascopeGoalPosesEntry.set(m_ascopeGoalPoses);
    }


    private void addReef(Mechanism2d canvas) {
        // https://github.com/frc2053/Robot2025/blob/dd302767522e812b0bbfe350672c9a416b680353/src/main/include/str/SuperstructureDisplay.h
        double pipeThickness = Units.inchesToMeters(1.66);

        // Reef L2-L4
        MechanismRoot2d reef =
            canvas.getRoot("Reef", CANVAS_WIDTH - (pipeThickness / 2), 0);
        MechanismLigament2d reefMainPole = createReefLigament(reef, "MainPole", Units.inchesToMeters(24.149027), 90);
        createReefLigament(reefMainPole, "L2", Units.inchesToMeters(12.119637), 90 + -35);
        MechanismLigament2d l2Pole = createReefLigament(reefMainPole, "L2Pole", Units.inchesToMeters(15.84), 0);
        createReefLigament(l2Pole, "L3", Units.inchesToMeters(12.119637), 90 + -35);
        MechanismLigament2d l3Pole = createReefLigament(l2Pole, "L3Pole", Units.inchesToMeters(15.536492), 0);
        MechanismLigament2d l4DiagPole = createReefLigament(l3Pole, "L4DiagPole", Units.inchesToMeters(12.119637), 90 + -35);
        createReefLigament(l4DiagPole, "L4", Units.inchesToMeters(9.810891), -55);

        // Reef L1 Parts
        MechanismLigament2d reefBottom = createReefLigament(reef, "ReefBottom", Units.inchesToMeters(12.052349), 180);
        createReefLigament(reefBottom, "ReefFrontPlate", Units.inchesToMeters(17.875000), -90);
    }

    private MechanismLigament2d createReefLigament(MechanismObject2d parent, String name, double length, double angle) {
        return parent.append(new MechanismLigament2d(name, length, angle, 10, new Color8Bit(Color.kPurple)));
    }
}
